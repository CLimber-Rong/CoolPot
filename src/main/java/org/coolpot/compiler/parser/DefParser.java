package org.coolpot.compiler.parser;

import org.coolpot.compiler.ExpressionParser;
import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.node.irnode.PushNode;
import org.coolpot.compiler.node.statment.DefNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.runtime.obj.StamonBase;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class DefParser implements SubParser {
    Token match;
    Parser parser;
    SourceFile file;

    public DefParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"def",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        Token token = parser.getToken();
        if(token.getType().equals(Token.Type.NAM)){
            if(!table.getThisScope().getInDefine().contains(token.getData())){
                table.getThisScope().getInDefine().add(token.getData());
                String name = token.getData();
                token = parser.getToken();
                if(token.getType().equals(Token.Type.SEM)&&token.getData().equals("=")) {
                    List<Token> expression = new ArrayList<>(); // 表达式解析
                    int i = 0;
                    do{
                        token = parser.getToken();
                        if (token.getType().equals(Token.Type.LP) && token.getData().equals("{")) i += 1;
                        if (token.getType().equals(Token.Type.LR) && token.getData().equals("}")) i -= 1;
                        if (i == 0 && token.getType().equals(Token.Type.END)) break;
                        expression.add(token);
                    }while (true);
                    ExpressionParser ep = new ExpressionParser(parser,expression);
                    return new GroupNode(ep.eval(table),new DefNode(name));
                }else if(token.getType().equals(Token.Type.END)){
                    return new GroupNode(new PushNode(StamonBase.value_null),
                            new DefNode(name));
                }else{
                    throw new SyntaxException(token,"'=' expected.");
                }
            }else throw new SyntaxException(token,"Type is already defined.");
        }else throw new SyntaxException(token,"<identifier> expected.");
    }
}
