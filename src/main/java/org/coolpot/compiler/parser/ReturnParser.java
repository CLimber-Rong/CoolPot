package org.coolpot.compiler.parser;

import org.coolpot.compiler.ExpressionParser;
import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.irnode.ReturnNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class ReturnParser implements SubParser{
    Token match;
    Parser parser;
    SourceFile file;

    public ReturnParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"return",0,file);
        this.parser = parser;
        this.file = file;
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        if(table.hasScope(SymbolTable.ScopeType.FUNC)){
            Token token = parser.getToken();
            if(token.getType().equals(Token.Type.END)){
                return new ReturnNode(ASTNode.empty);
            }else parser.setBuffer(token);

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
            return new ReturnNode(ep.eval(table));
        }else throw new SyntaxException(parser.getCurrentToken(),"return outside function.");
    }
}
