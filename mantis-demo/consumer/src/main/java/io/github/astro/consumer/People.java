package io.github.astro.consumer;

import java.io.Serial;
import java.io.Serializable;

public class People implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public String name;

    public int age;

    public String sex;

    public People() {

    }

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
