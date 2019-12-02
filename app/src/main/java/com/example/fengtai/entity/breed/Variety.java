package com.example.fengtai.entity.breed;


public class Variety {

    /**
     * id : 6
     * name : 杜泊
     * addtime : null
     * memberid : 0
     * shenhe : 1
     */

    private String id;
    private String name;
    private Object addtime;
    private String memberid;
    private String shenhe;

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

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getShenhe() {
        return shenhe;
    }

    public void setShenhe(String shenhe) {
        this.shenhe = shenhe;
    }
}
