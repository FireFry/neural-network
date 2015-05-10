package org.vladyslav.math;

public abstract class AbstractVector implements Vector {

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("{");
        for (int i = 0; i < size(); i++) {
            b.append(get(i));
            if (i < size() - 1) {
                b.append(", ");
            } else {
                b.append("}");
            }
        }
        return b.toString();
    }

}
