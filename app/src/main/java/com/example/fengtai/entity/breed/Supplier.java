package com.example.fengtai.entity.breed;


public class Supplier {

    /**
     * id : 3
     * name : 牧户
     * addtime : null
     * shenhe : 1
     * memberid : 0
     */

    private String id;
    private String name;
    private Object addtime;
    private String shenhe;
    private String memberid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAddtime() {
        return addtime;
    }

    public void setAddtime(Object addtime) {
        this.addtime = addtime;
    }

    public String getShenhe() {
        return shenhe;
    }

    public void setShenhe(String shenhe) {
        this.shenhe = shenhe;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }
}
