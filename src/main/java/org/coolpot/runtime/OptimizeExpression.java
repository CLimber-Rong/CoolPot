package org.coolpot.runtime;

import org.coolpot.compiler.node.ASTNode;
import org.coolpot.compiler.node.irnode.PushNode;
import org.coolpot.compiler.tokens.Token;
import org.coolpot.runtime.obj.*;
import org.coolpot.util.error.OptimizeException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class OptimizeExpression {
    List<Token> tokens;
    Deque<StamonBase<?>> op_stack = new LinkedList<>();
    public OptimizeExpression(List<Token> tokens){
        this.tokens = tokens;
    }

    public void div(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonDouble((Double) o2.getData() / (Double) o.getData()));
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() / (Integer) o.getData()));
        }
    }

    private void mul(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonDouble((Double) o2.getData() * (Double) o.getData()));
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() * (Integer) o.getData()));
        }
    }

    private void sub(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonDouble((Double) o2.getData() - (Double) o.getData()));
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() - (Integer) o.getData()));
        }
    }

    private void add(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            op_stack.push(new StamonString((String) o2.getData() + o.getData()));
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonDouble((Double) o.getData() + (Double) o2.getData()));
        }else {
            op_stack.push(new StamonInteger((Integer)o.getData() + (Integer) o2.getData()));
        }
    }

    public void cpl(){
        StamonBase<?> o = op_stack.pop();
        if(o instanceof StamonNull ||  o instanceof StamonBoolean||o instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble){
            throw new OptimizeException();
        }else {
            op_stack.push(new StamonInteger(~ (Integer) o.getData()));
        }
    }

    private void mod(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonDouble((Double) o2.getData() % (Double) o.getData()));
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() % (Integer) o.getData()));
        }
    }

    private void lsh(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            throw new OptimizeException();
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() << (Integer) o.getData()));
        }
    }

    private void rsh(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            throw new OptimizeException();
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() >> (Integer) o.getData()));
        }
    }

    private void big(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonBoolean((Double) o2.getData() > (Double) o.getData()));
        }else {
            op_stack.push(new StamonBoolean((Integer)o2.getData() > (Integer) o.getData()));
        }
    }

    private void less(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonBoolean((Double) o2.getData() < (Double) o.getData()));
        }else {
            op_stack.push(new StamonBoolean((Integer)o2.getData() < (Integer) o.getData()));
        }
    }

    private void beq(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonBoolean((Double) o2.getData() >= (Double) o.getData()));
        }else {
            op_stack.push(new StamonBoolean((Integer)o2.getData() >= (Integer) o.getData()));
        }
    }

    private void leq(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonBoolean((Double) o2.getData() <= (Double) o.getData()));
        }else {
            op_stack.push(new StamonBoolean((Integer)o2.getData() <= (Integer) o.getData()));
        }
    }

    private void equ(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString||o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonBoolean(Objects.equals(o2.getData(), o.getData())));
        }else {
            op_stack.push(new StamonBoolean(Objects.equals(o2.getData(), o.getData())));
        }
    }

    private void unequ(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString||o instanceof StamonDouble || o2 instanceof StamonDouble){
            op_stack.push(new StamonBoolean(!Objects.equals(o2.getData(), o.getData())));
        }else {
            op_stack.push(new StamonBoolean(!Objects.equals(o2.getData(), o.getData())));
        }
    }

    private void band(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            throw new OptimizeException();
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() & (Integer) o.getData()));
        }
    }

    private void bxor(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            throw new OptimizeException();
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() ^ (Integer) o.getData()));
        }
    }

    private void bor(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonNull || o2 instanceof StamonNull || o instanceof StamonBoolean || o2 instanceof StamonBoolean){
            throw new OptimizeException();
        }else if(o instanceof StamonString || o2 instanceof StamonString){
            throw new OptimizeException();
        }else if(o instanceof StamonDouble || o2 instanceof StamonDouble){
            throw new OptimizeException();
        }else {
            op_stack.push(new StamonInteger((Integer)o2.getData() | (Integer) o.getData()));
        }
    }

    private void land(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonBoolean && o2 instanceof StamonBoolean){
            op_stack.push(new StamonBoolean(((StamonBoolean) o).getData() && ((StamonBoolean) o2).getData()));
        }else throw new OptimizeException();
    }

    private void lor(){
        StamonBase<?> o = op_stack.pop();
        StamonBase<?> o2 = op_stack.pop();
        if(o instanceof StamonBoolean && o2 instanceof StamonBoolean){
            op_stack.push(new StamonBoolean(((StamonBoolean) o).getData() || ((StamonBoolean) o2).getData()));
        }else throw new OptimizeException();
    }

    public ASTNode eval(){

        for(Token token : tokens){
            switch (token.getType()){
                case NUM,BIN,B16 -> op_stack.push(new StamonInteger(Integer.parseInt(token.getData())));
                case DBL -> op_stack.push(new StamonDouble(Double.parseDouble(token.getData())));
                case STR -> op_stack.push(new StamonString(token.getData()));
                case KEY -> {
                    switch (token.getData()){
                        case "true" -> op_stack.push(new StamonBoolean(true));
                        case "false" -> op_stack.push(new StamonBoolean(false));
                        case "null" -> op_stack.push(StamonBase.value_null);
                        default -> throw new OptimizeException();
                    }
                }
                case NBF,NAM -> throw new OptimizeException();
                case SEM -> {
                    switch (token.getData()){
                        case "+" -> add();
                        case "-" -> sub();
                        case "*" -> mul();
                        case "~" -> cpl();
                        case "/" -> div();
                        case "%" -> mod();
                        case "<<" -> lsh();
                        case ">>" -> rsh();
                        case ">" -> big();
                        case "<" -> less();
                        case ">=" -> beq();
                        case "<=" -> leq();
                        case "==" -> equ();
                        case "!=" -> unequ();
                        case "&" -> band();
                        case "^" -> bxor();
                        case "|" -> bor();
                        case "&&" -> land();
                        case "||" -> lor();
                        case "=","." -> throw new OptimizeException();
                    }
                }
            }
        }
        return new PushNode(op_stack.pop());
    }
}
