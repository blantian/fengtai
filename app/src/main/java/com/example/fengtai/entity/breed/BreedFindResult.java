package com.example.fengtai.entity.breed;

public class BreedFindResult {

    /**
     * code : 200
     * message : 获取成功!
     * data : {"id":"63","user_id":"29542","hukou_id":"23","breedclass_id":"9","number":"120","acquisition_time":"2019-10-17 00:00:00","title":"基础母羊2","age":"309","become_time":"2019-10-17 00:00:00","become_price":"162000.00","price":"163200.00","addtime":"2019-11-06 12:11:39","shenhe":null,"img":"http://admin.fengtaiiot.com","common":"0","mother":"0","sheng":"内蒙古自治区","shi":"鄂尔多斯市","xian":"鄂托克旗","variety_id":"1","supplier_id":"1","user_idt":"0","birthday":"2019-01-01","insurance_type":"1","weight":"0","dizhi":"阿尔巴斯苏木乌兰其日嘎嘎查","typet":2,"variety":"杜蒙","supplier":"好力保","gong":"0","mu":"120"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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
         * sheng : 内蒙古自治区
         * shi : 鄂尔多斯市
         * xian : 鄂托克旗
         * variety_id : 1
         * supplier_id : 1
         * user_idt : 0
         * birthday : 2019-01-01
         * insurance_type : 1
         * weight : 0
         * dizhi : 阿尔巴斯苏木乌兰其日嘎嘎查
         * typet : 2
         * variety : 杜蒙
         * supplier : 好力保
         * gong : 0
         * mu : 120
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
        private int typet;
        private String variety;
        private String supplier;
        private String gong;
        private String mu;

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

        public int getTypet() {
            return typet;
        }

        public void setTypet(int typet) {
            this.typet = typet;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getGong() {
            return gong;
        }

        public void setGong(String gong) {
            this.gong = gong;
        }

        public String getMu() {
            return mu;
        }

        public void setMu(String mu) {
            this.mu = mu;
        }
    }
}
