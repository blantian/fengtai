package com.example.fengtai.entity.breed;

import android.os.Parcel;
public class BreedsResult{


    /**
     * data : [{"id":"14","name":"家禽",
     * "addtime":"0000-00-00 00:00:00","
     * shenhe":"1","img":null,
     * "memberid":"0","count":null},
     * {"id":"11","name":"猪",
     * "addtime":"0000-00-00 00:00:00",
     * "shenhe":"1","img":null,
     * "memberid":"0","count":null},
     * {"id":"10","name":"山羊",
     * "addtime":"0000-00-00 00:00:00",
     * "shenhe":"1","img":null,
     * "memberid":"0","count":null},
     * {"id":"9","name":"绵羊",
     * "addtime":"0000-00-00 00:00:00",
     * "shenhe":"1","img":null,
     * "memberid":"0","count":"120"},
     * {"id":"4","name":"骆驼",
     * "addtime":"2019-04-26 06:00:00",
     * "shenhe":"0",
     * "img":null,
     * "memberid":"0",
     * "count":null},
     * {"id":"3",
     * "name":"马",
     * "addtime":"2019-04-26 06:00:00",
     * "shenhe":"0",
     * "img":null,
     * "memberid":"0",
     * "count":null},
     * {"id":"1",
     * "name":"牛",
     * "addtime":"2019-04-26 06:00:00",
     * "shenhe":"0",
     * "img":null,"memberid":"0",
     * "count":null}]
     */


        private String id;
        private String name;
        private String addtime;
        private String shenhe;
        private Object img;
        private String memberid;
        private Object count;

    protected BreedsResult(Parcel in) {
        id = in.readString();
        name = in.readString();
        addtime = in.readString();
        shenhe = in.readString();
        memberid = in.readString();
    }


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

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

}
