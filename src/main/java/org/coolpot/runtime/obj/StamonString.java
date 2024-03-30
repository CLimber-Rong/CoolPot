package org.coolpot.runtime.obj;

public class StamonString extends StamonBase<String>{
    String data;
    public StamonString(String data){
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<string:").append(data).append(">\n");
    }
}
