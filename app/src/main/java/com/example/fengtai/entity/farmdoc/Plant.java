package com.example.fengtai.entity.farmdoc;


public class Plant {

    /**
     * 获取草原单条数据
     * id : 19
     * user_id : 29569
     * hukou_id : 35
     * area : 1000
     * area_type : 1
     * name : 草原
     * addtime : 2019-11-09 19:11:00
     * shenhe : null
     * ste_type : 1
     */

    private String id;
    private String user_id;
    private String hukou_id;
    private String area;
    private int area_type;
    private String name;
    private String addtime;
    private Object shenhe;
    private int ste_type;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getArea_type() {
        return area_type;
    }

    public void setArea_type(int area_type) {
        this.area_type = area_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getSte_type() {
        return ste_type;
    }

    public void setSte_type(int ste_type) {
        this.ste_type = ste_type;
    }
}
