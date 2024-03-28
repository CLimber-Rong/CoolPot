package org.coolpot.runtime.obj;

public class StamonIdentifier extends StamonBase<String>{
    String identifier;
    public StamonIdentifier(String identifier){
        this.identifier = identifier;
    }
    @Override
    public String getData() {
        return identifier;
    }
}
