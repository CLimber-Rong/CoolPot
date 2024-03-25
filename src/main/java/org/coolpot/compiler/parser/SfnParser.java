package org.coolpot.compiler.parser;

import org.coolpot.compiler.Parser;
import org.coolpot.compiler.SourceFile;
import org.coolpot.compiler.SymbolTable;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.node.irnode.MemberNode;
import org.coolpot.compiler.node.irnode.PushNode;
import org.coolpot.compiler.node.irnode.SfnNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.runtime.obj.StamonInteger;
import org.coolpot.runtime.obj.StamonString;
import org.coolpot.util.MetaConfig;
import org.coolpot.util.error.SyntaxException;

public class SfnParser implements SubParser{

    Token match;
    Parser parser;
    SourceFile file;

    public SfnParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.KEY,"sfn",0,file);
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
        if(!file.isSFN()) {
            if(MetaConfig.disableSFN){
                throw new SyntaxException(token,"Cannot use sfn command in user script.");
            }else System.out.println("[Warn]: It is not recommended to call SFN commands directly in user scripts");
        }
        if(token.getType().equals(Token.Type.NUM)){
            Token port = token;
            token = parser.getToken();
            if(!(token.getType().equals(Token.Type.SEM) && token.getData().equals(",")))
                throw new SyntaxException(token,"',' expected.");
            token = parser.getToken();
            if(token.getType().equals(Token.Type.NAM)){
                if(table.getThisScope().getInDefine().contains(token.getData())){
                    return new GroupNode(new GroupNode(
                            new PushNode(new StamonString(token.getData())),
                            new MemberNode()
                    ),
                            new PushNode(new StamonInteger(Integer.parseInt(port.getData()))),
                            new SfnNode());
                }else throw new SyntaxException(token,"Unable to resolve symbols.");
            }else throw new SyntaxException(token,"Type name is not valid.");
        }else if(token.getType().equals(Token.Type.NAM)) {
            if(table.getThisScope().getInDefine().contains(token.getData())) {

                return new GroupNode();
            }else throw new SyntaxException(token,"Unable to resolve symbols.");
        }else throw new SyntaxException(token,"Type name is not valid.");
    }
}
