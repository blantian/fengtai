package com.example.fengtai.entity.farmdoc;

public class Compensate {

    /**
     * id : 25
     * user_id : 29573
     * hukou_id : 40
     * title : 公积金
     * price : 12000.00
     * addtime : 2019-11-16 00:00:00
     * shenhe : null
     * datacountsum : 24000.00
     */

    private String id;
    private String user_id;
    private String hukou_id;
    private String title;
    private String price;
    private String addtime;
    private Object shenhe;
    private String datacountsum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHukou_id() {
        return hukou_id;
    }

    public void setHukou_id(String hukou_id) {
        this.hukou_id = hukou_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public Object getShenhe() {
        return shenhe;
    }

    public void setShenhe(Object shenhe) {
        this.shenhe = shenhe;
    }

    public String getDatacountsum() {
        return datacountsum;
    }

    public void setDatacountsum(String datacountsum) {
        this.datacountsum = datacountsum;
    }
}
