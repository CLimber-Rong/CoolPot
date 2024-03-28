package org.coolpot.compiler.parser;

import org.coolpot.compiler.*;
import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.EmptyNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class BlockParser implements SubParser{
    Token match;
    Parser parser;
    SourceFile file;
    List<Token> block;
    BParser block_parser;
    public Token buffer;
    int index;

    public BlockParser(SourceFile file, Parser parser){
        this.match = new Token(Token.Type.LP,"{",0,file);
        this.parser = parser;
        this.file = file;
        this.block = null;
    }

    public BlockParser(SourceFile file, List<Token> block) {
        this.file = file;
        this.block = block;
        this.index = 0;
        this.block_parser = new BParser(file,block,this);
    }

    public Token getToken(){
        if(block != null){
            if(buffer != null){
                Token t = buffer;
                buffer = null;
                return t;
            }
            if (index >= block.size()) return null;
            Token token = block.get(index);
            index++;
            return token;
        }else return parser.getToken();
    }

    private ASTNode parserStatement(SymbolTable table){
        SubParser sp = block_parser.getParser();
        if(sp == null) return null;
        if(sp instanceof NullParser){
            Token token = getToken();

            if(token == null) return new EmptyNode();
            List<Token> expression;
            ExpressionParser expressionParser;
            if(token.getType().equals(Token.Type.NAM)){
                expression = new ArrayList<>();
                do{
                    expression.add(token);
                    token = getToken();
                }while (!token.getType().equals(Token.Type.END));
                expressionParser = new ExpressionParser(block_parser,expression);
                return expressionParser.eval(table);
            }else{
                throw new SyntaxException(token,"Not a statement.");
            }
        }else {
            return sp.eval(table);
        }
    }

    @Override
    public Token getMatchToken() {
        return match;
    }

    @Override
    public ASTNode eval(SymbolTable table) {
        List<ASTNode> nodes = new ArrayList<>();
        ASTNode buffer;
        while ((buffer = parserStatement(table)) != null)
            nodes.add(buffer);
        return new GroupNode(nodes);
    }
}
