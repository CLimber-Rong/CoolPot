package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.node.statment.ClassNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class SugarClassParser implements SubParser{
    Token match;
    Parser parser;
    SourceFile file;

    public SugarClassParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"class",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        List<ASTNode> nodes = new ArrayList<>();
        Token token = parser.getToken();
        String name = "";
        if(token.getType().equals(Token.Type.NAM)){
            if(!table.isDefine(token.getData())){
                if(table.hasScope(SymbolTable.ScopeType.IF) || table.hasScope(SymbolTable.ScopeType.WHILE)
                        || table.hasScope(SymbolTable.ScopeType.FUNC) || table.hasScope(SymbolTable.ScopeType.CLASS))
                    throw new SyntaxException(token,"Illegal statement.");

                table.getThisScope().getInDefine().add(token.getData());
                table.createNewScope(new SymbolTable.Scope("class:"+token.getData(), SymbolTable.ScopeType.CLASS));

                name = token.getData();
                List<Token> block = new ArrayList<>();
                token = parser.getToken();

                if((token.getType().equals(Token.Type.KEY)&&token.getData().equals("extends"))||
                (token.getType().equals(Token.Type.SEM)&&token.getData().equals(":"))){
                    token = parser.getToken();
                    if(table.isDefine(token.getData())){
                        token = parser.getToken();
                        //TODO 类继承措施未定义
                    }else throw new SyntaxException(token,"Unable to resolve symbols.");
                }

                int i = 1;
                if (!(token.getType().equals(Token.Type.LP) && token.getData().equals("{")))
                    throw new SyntaxException(token,"Missing class body.");
                try {
                    do {
                        token = parser.getToken();
                        if (token.getType().equals(Token.Type.LP) && token.getData().equals("{")) i += 1;
                        if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) i -= 1;
                        if (i == 0) break;
                        block.add(token);
                    } while (true);
                } catch (NullPointerException e) {
                    throw new SyntaxException(token,"'}' expected.");
                }
                nodes.add(subParser(table,block));
            }else throw new SyntaxException(token,"Type is already defined.");
        }else throw new SyntaxException(token,"<identifier> expected.");
        table.popScope();
        return new ClassNode(name,new GroupNode( nodes));
    }

    public ASTNode subParser(SymbolTable table,List<Token> tokens){
        return new BlockParser(file,tokens).eval(table);
    }
}
