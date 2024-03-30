package org.coolpot.runtime.obj;

public class StamonInteger extends StamonBase<Integer>{
    int data;
    public StamonInteger(int data){
        this.data = data;
    }

    @Override
    public Integer getData() {
        return data;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<int:").append(data).append(">\n");
    }
}
