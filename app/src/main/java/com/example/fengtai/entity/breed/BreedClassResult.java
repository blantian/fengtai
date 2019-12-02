package com.example.fengtai.entity.breed;


import java.util.List;

public class BreedClassResult {

    /**
     * code : 200
     * message : 获取成功!
     * data : [{"id":"14","name":"家禽","addtime":"0000-00-00 00:00:00","shenhe":"1","img":null,"memberid":"0"},{"id":"11","name":"猪","addtime":"0000-00-00 00:00:00","shenhe":"1","img":null,"memberid":"0"},{"id":"10","name":"山羊","addtime":"0000-00-00 00:00:00","shenhe":"1","img":null,"memberid":"0"},{"id":"9","name":"绵羊","addtime":"0000-00-00 00:00:00","shenhe":"1","img":null,"memberid":"0"},{"id":"4","name":"骆驼","addtime":"2019-04-26 06:00:00","shenhe":"0","img":null,"memberid":"0"},{"id":"3","name":"马","addtime":"2019-04-26 06:00:00","shenhe":"0","img":null,"memberid":"0"},{"id":"1","name":"牛","addtime":"2019-04-26 06:00:00","shenhe":"0","img":null,"memberid":"0"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 14
         * name : 家禽
         * addtime : 0000-00-00 00:00:00
         * shenhe : 1
         * img : null
         * memberid : 0
         */

        private String id;
        private String name;
        private String addtime;
        private String shenhe;
        private Object img;
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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getShenhe() {
            return shenhe;
        }

        public void setShenhe(String shenhe) {
            this.shenhe = shenhe;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }
    }
}
