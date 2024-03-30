package org.coolpot.runtime.obj;

public class StamonClass extends StamonBase<Class<?>>{
    @Override
    public Class<?> getData() {
        return null;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<class>\n");
    }
}
