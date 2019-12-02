package com.example.fengtai.entity;

/**
 * Created by wzc on 19-11-22 下午7:44.
 */
public class Data<T> {
    private T t;
    private String typet;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getTypet() {
        return typet;
    }

    public void setTypet(String typet) {
        this.typet = typet;
    }
}
