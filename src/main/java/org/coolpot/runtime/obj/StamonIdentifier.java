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

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<identifier:").append(identifier).append(">\n");
    }
}
