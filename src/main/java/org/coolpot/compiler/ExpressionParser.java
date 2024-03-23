package org.coolpot.compiler;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.GroupNode;
import org.coolpot.compiler.parser.SubParser;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.util.error.SyntaxException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ExpressionParser implements SubParser {
    Parser parser;
    List<Token> tokens;
    Token buffer = null;
    int index;

    public ExpressionParser(Parser parser,List<Token> tokens){
        this.parser = parser;
        this.tokens = tokens;
        this.index = 0;
    }

    private Token getToken() {
        Token t;
        if (buffer == null) {
            if (index >= tokens.size()) return null;
            t = tokens.get(index);
            index += 1;
        } else {
            t = buffer;
            buffer = null;
        }
        return t;
    }

    public List<Token> suffix(SymbolTable table){
        boolean isUnary = true;
        Deque<Token> op_stack = new LinkedList<>();
        List<Token> suffixList = new LinkedList<>();
        Token token = null;

        try {
            while (true) {
                token = getToken();
                if (token == null) break;

            }
        }catch (NullPointerException e){
            throw new SyntaxException(token,"Illegal combination of expressions.");
        }

        while (!op_stack.isEmpty()) {
            suffixList.add(op_stack.pop());
        }
        return suffixList;
    }

    public ASTNode calculate(List<Token> suffix){
        List<ASTNode> nodes = new ArrayList<>();

        return new GroupNode(nodes);
    }

    @Override
    public Token getMatchToken() {
        return null;
    }

    @Override
    public ASTNode eval(SymbolTable table){
        return calculate(suffix(table));
    }
}
