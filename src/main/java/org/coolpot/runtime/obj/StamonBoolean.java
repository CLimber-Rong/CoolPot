package org.coolpot.runtime.obj;

//FIXME 无法确定是否有boolean类型
public class StamonBoolean extends StamonBase<Boolean> {
    boolean data;

    public StamonBoolean(boolean data) {
        this.data = data;
    }

    @Override
    public Boolean getData() {
        return data;
    }

    @Override
    public void getString(int trace, StringBuilder sb) {
        sb.append(" ".repeat(Math.max(0, trace)));
        sb.append("<boolean:").append(data).append(">\n");
    }
}
