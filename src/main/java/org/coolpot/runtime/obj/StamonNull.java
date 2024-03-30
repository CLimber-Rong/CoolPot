package org.coolpot.runtime.obj;

public class StamonNull extends StamonBase<Object>{
    @Override
    public Object getData() {
        return null;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<null>\n");
    }
}
