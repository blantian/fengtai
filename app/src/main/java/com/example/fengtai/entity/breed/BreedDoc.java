package com.example.fengtai.entity.breed;

public class BreedDoc {


    /**
     * id : 72020
     * breed_id : 63
     * eartag_id : 3544
     * addtime : 2019-11-06 12:11:39
     * type : 2
     * pid : 0
     * weight : 35
     * age : 309
     * birthday : null
     * type_in : 1
     * time : 2019-10-17 00:00:00
     * survival : null
     * img : http://admin.fengtaiiot.com/uploadImage/5dc24d3c3640b.jpg
     * user_id : 29542
     * qrcode : BXR2019110655555051.png
     * length :
     * update_userid : 29547
     * edittime : 2019-11-06 12:34:07
     * hah : 1
     * eartag : {"id":"3544","number":"152725201900000000008442","type":"3","addtime":"2019-10-30 19:25:45","user_id":"29542","user_type":"2"}
     * breed : {"id":"63","user_id":"29542","hukou_id":"23","breedclass_id":"9","number":"120","acquisition_time":"2019-10-17 00:00:00","title":"基础母羊2","age":"309","become_time":"2019-10-17 00:00:00","become_price":"162000.00","price":"163200.00","addtime":"2019-11-06 12:11:39","shenhe":null,"img":"http://admin.fengtaiiot.com","common":"0","mother":"0","sheng":"66","shi":"86","xian":"95","variety_id":"1","supplier_id":"1","user_idt":"0","birthday":"2019-01-01","insurance_type":"1","weight":"0","dizhi":"阿尔巴斯苏木乌兰其日嘎嘎查"}
     * piddata : null
     * pidcount : 0
     */

    private String id;
    private String breed_id;
    private String eartag_id;
    private String addtime;
    private String type;
    private String pid;
    private String weight;
    private String age;
    private Object birthday;
    private String type_in;
    private String time;
    private Object survival;
    private String img;
    private String user_id;
    private String qrcode;
    private String length;
    private String update_userid;
    private String edittime;
    private String hah;
    private EartagBean eartag;
    private BreedBean breed;
    private Object piddata;
    private String pidcount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public String getEartag_id() {
        return eartag_id;
    }

    public void setEartag_id(String eartag_id) {
        this.eartag_id = eartag_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public String getType_in() {
        return type_in;
    }

    public void setType_in(String type_in) {
        this.type_in = type_in;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Object getSurvival() {
        return survival;
    }

    public void setSurvival(Object survival) {
        this.survival = survival;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getUpdate_userid() {
        return update_userid;
    }

    public void setUpdate_userid(String update_userid) {
        this.update_userid = update_userid;
    }

    public String getEdittime() {
        return edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public String getHah() {
        return hah;
    }

    public void setHah(String hah) {
        this.hah = hah;
    }

    public EartagBean getEartag() {
        return eartag;
    }

    public void setEartag(EartagBean eartag) {
        this.eartag = eartag;
    }

    public BreedBean getBreed() {
        return breed;
    }

    public void setBreed(BreedBean breed) {
        this.breed = breed;
    }

    public Object getPiddata() {
        return piddata;
    }

    public void setPiddata(Object piddata) {
        this.piddata = piddata;
    }

    public String getPidcount() {
        return pidcount;
    }

    public void setPidcount(String pidcount) {
        this.pidcount = pidcount;
    }

    public static class EartagBean {
        /**
         * id : 3544
         * number : 152725201900000000008442
         * type : 3
         * addtime : 2019-10-30 19:25:45
         * user_id : 29542
         * user_type : 2
         */

        private String id;
        private String number;
        private String type;
        private String addtime;
        private String user_id;
        private String user_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }
    }

    public static class BreedBean {
        /**
         * id : 63
         * user_id : 29542
         * hukou_id : 23
         * breedclass_id : 9
         * number : 120
         * acquisition_time : 2019-10-17 00:00:00
         * title : 基础母羊2
         * age : 309
         * become_time : 2019-10-17 00:00:00
         * become_price : 162000.00
         * price : 163200.00
         * addtime : 2019-11-06 12:11:39
         * shenhe : null
         * img : http://admin.fengtaiiot.com
         * common : 0
         * mother : 0
         * sheng : 66
         * shi : 86
         * xian : 95
         * variety_id : 1
         * supplier_id : 1
         * user_idt : 0
         * birthday : 2019-01-01
         * insurance_type : 1
         * weight : 0
         * dizhi : 阿尔巴斯苏木乌兰其日嘎嘎查
         */

        private String id;
        private String user_id;
        private String hukou_id;
        private String breedclass_id;
        private String number;
        private String acquisition_time;
        private String title;
        private String age;
        private String become_time;
        private String become_price;
        private String price;
        private String addtime;
        private Object shenhe;
        private String img;
        private String common;
        private String mother;
        private String sheng;
        private String shi;
        private String xian;
        private String variety_id;
        private String supplier_id;
        private String user_idt;
        private String birthday;
        private String insurance_type;
        private String weight;
        private String dizhi;

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

        public String getBreedclass_id() {
            return breedclass_id;
        }

        public void setBreedclass_id(String breedclass_id) {
            this.breedclass_id = breedclass_id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAcquisition_time() {
            return acquisition_time;
        }

        public void setAcquisition_time(String acquisition_time) {
            this.acquisition_time = acquisition_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBecome_time() {
            return become_time;
        }

        public void setBecome_time(String become_time) {
            this.become_time = become_time;
        }

        public String getBecome_price() {
            return become_price;
        }

        public void setBecome_price(String become_price) {
            this.become_price = become_price;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }

        public String getMother() {
            return mother;
        }

        public void setMother(String mother) {
            this.mother = mother;
        }

        public String getSheng() {
            return sheng;
        }

        public void setSheng(String sheng) {
            this.sheng = sheng;
        }

        public String getShi() {
            return shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getXian() {
            return xian;
        }

        public void setXian(String xian) {
            this.xian = xian;
        }

        public String getVariety_id() {
            return variety_id;
        }

        public void setVariety_id(String variety_id) {
            this.variety_id = variety_id;
        }

        public String getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(String supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getUser_idt() {
            return user_idt;
        }

        public void setUser_idt(String user_idt) {
            this.user_idt = user_idt;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getInsurance_type() {
            return insurance_type;
        }

        public void setInsurance_type(String insurance_type) {
            this.insurance_type = insurance_type;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getDizhi() {
            return dizhi;
        }

        public void setDizhi(String dizhi) {
            this.dizhi = dizhi;
        }
    }
}
