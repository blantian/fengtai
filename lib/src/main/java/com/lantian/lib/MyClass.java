package com.lantian.lib;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {

    public static void main(String[] args) {

        Schema schema = new Schema(1,"fengtaiapp.db");

        Entity userInfo = schema.addEntity("UserInfo");
        userInfo.addStringProperty("user_id");
        userInfo.addStringProperty("username");
        userInfo.addStringProperty("password");
        userInfo.addStringProperty("phone");
        userInfo.addStringProperty("email");
        userInfo.addStringProperty("nickname");
        userInfo.addBooleanProperty("sex");
        userInfo.addStringProperty("address");
        userInfo.addStringProperty("id_card");
        userInfo.addStringProperty("age");
        userInfo.addStringProperty("avatar");
        userInfo.addStringProperty("created_at");
        userInfo.addStringProperty("shengdata");
        userInfo.addStringProperty("shidata");
        userInfo.addStringProperty("xiandata");

        userInfo.addIdProperty();
        Property productsId = userInfo.addLongProperty("productId").getProperty();

        Entity products = schema.addEntity("Product");
        products.addStringProperty("product_id");
        products.addStringProperty("iot_product_id");
        products.addStringProperty("product_name");
        products.addStringProperty("product_type");
        products.addStringProperty("product_pic");
        products.addStringProperty("content");
        products.addStringProperty("agreement");
        products.addStringProperty("default_time");
        products.addStringProperty("product_min");
        products.addStringProperty("product_max");
        products.addStringProperty("product_scope");
        products.addStringProperty("type");
        products.addStringProperty("created_at");
        products.addStringProperty("updated_at");
        products.addIdProperty();
        userInfo.addToOne(products,productsId);

        try {
            new DaoGenerator().generateAll(schema,"app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
