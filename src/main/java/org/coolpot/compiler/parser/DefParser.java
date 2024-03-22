package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.irnode.MemberNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

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
                token = parser.getToken();
                if(token.getType().equals(Token.Type.SEM)&&token.getData().equals("=")) {

                }else if(token.getType().equals(Token.Type.END)){

                }else{
                    throw new SyntaxException(token,"'=' expected.");
                }
                return new MemberNode(null);
            }else throw new SyntaxException(token,"Type is already defined.");
        }else throw new SyntaxException(token,"<identifier> expected.");
    }
}
