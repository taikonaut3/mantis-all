package org.coco.mantis;

import java.io.Serial;

/**
 * @Author WenBo Zhou
 * @Date 2023/7/26 16:05
 */
public class CaiXuKun {

    @Serial
    private static final long serialVersionUID = 1L;

    public String name;

    public int age;

    public String sex;

    public CaiXuKun() {

    }

    public CaiXuKun(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
