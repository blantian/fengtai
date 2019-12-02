package com.example.fengtai.entity;

public class Item {

    /**
     * 用于解决spinner 键值绑定
     * item :
     * value : 12
     */
    private String id;
    private String item;
    private String value;


    public Item(String item, String value) {
        this.item = item;
        this.value = value;
    }

    public Item(String id, String item, String value) {
        this.id = id;
        this.item = item;
        this.value = value;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String  getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
