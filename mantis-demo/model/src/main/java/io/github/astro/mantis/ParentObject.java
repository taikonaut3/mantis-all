package io.github.astro.mantis;

import java.io.Serializable;

public class ParentObject implements Serializable {

    private String someValue;

    private ChildObject child;

    // getters 和 setters

    public String getSomeValue() {
        return someValue;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    public ChildObject getChild() {
        return child;
    }

    public void setChild(ChildObject child) {
        this.child = child;
    }

}

