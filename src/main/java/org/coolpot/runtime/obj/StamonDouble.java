package org.coolpot.runtime.obj;

public class StamonDouble extends StamonBase<Double>{
    double data;

    public StamonDouble(double data){
        this.data = data;
    }

    @Override
    public Double getData() {
        return data;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<double:").append(data).append(">\n");
    }
}
