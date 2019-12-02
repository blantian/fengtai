package com.example.fengtai.entity.breed;

import com.google.gson.annotations.SerializedName;

public class BreedIndexResult {


    /**
     * page : {"firstRow":0,"listRows":10,"parameter":{"user_id":"29480","breedclass_id":"1"},"totalRows":"1","totalPages":1,"rollPage":3,"lastSuffix":true}
     * data : {"0":{"id":"1","user_id":"29480","hukou_id":"4","breedclass_id":"1","number":"200","acquisition_time":"2019-04-26 06:00:00","title":"阿尔卑斯羊","age":"1","become_time":"2019-08-26 06:00:00","become_price":"0.00","price":"986.20","addtime":"2019-04-26 17:04:37","shenhe":null,"img":null,"common":null,"mother":null,"sheng":null,"shi":null,"xian":null,"variety_id":null,"supplier_id":null}}
     */


    /**
     * id : 1
     * user_id : 29480
     * hukou_id : 4
     * breedclass_id : 1
     * number : 200
     * acquisition_time : 2019-04-26 06:00:00
     * title : 阿尔卑斯羊
     * age : 1
     * become_time : 2019-08-26 06:00:00
     * become_price : 0.00
     * price : 986.20
     * addtime : 2019-04-26 17:04:37
     * shenhe : null
     * img : null
     * common : null
     * mother : null
     * sheng : null
     * shi : null
     * xian : null
     * variety_id : null
     * supplier_id : null
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
    private Object img;
    private Object common;
    private Object mother;
    private Object sheng;
    private Object shi;
    private Object xian;
    private Object variety_id;
    private Object supplier_id;

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

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Object getCommon() {
        return common;
    }

    public void setCommon(Object common) {
        this.common = common;
    }

    public Object getMother() {
        return mother;
    }

    public void setMother(Object mother) {
        this.mother = mother;
    }

    public Object getSheng() {
        return sheng;
    }

    public void setSheng(Object sheng) {
        this.sheng = sheng;
    }

    public Object getShi() {
        return shi;
    }

    public void setShi(Object shi) {
        this.shi = shi;
    }

    public Object getXian() {
        return xian;
    }

    public void setXian(Object xian) {
        this.xian = xian;
    }

    public Object getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(Object variety_id) {
        this.variety_id = variety_id;
    }

    public Object getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Object supplier_id) {
        this.supplier_id = supplier_id;
    }

    public static class PageBean {
        /**
         * firstRow : 0
         * listRows : 10
         * parameter : {"user_id":"29480","breedclass_id":"1"}
         * totalRows : 1
         * totalPages : 1
         * rollPage : 3
         * lastSuffix : true
         */

        private int firstRow;
        private int listRows;
        private ParameterBean parameter;
        private String totalRows;
        private int totalPages;
        private int rollPage;
        private boolean lastSuffix;

        public int getFirstRow() {
            return firstRow;
        }

        public void setFirstRow(int firstRow) {
            this.firstRow = firstRow;
        }

        public int getListRows() {
            return listRows;
        }

        public void setListRows(int listRows) {
            this.listRows = listRows;
        }

        public ParameterBean getParameter() {
            return parameter;
        }

        public void setParameter(ParameterBean parameter) {
            this.parameter = parameter;
        }

        public String getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(String totalRows) {
            this.totalRows = totalRows;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getRollPage() {
            return rollPage;
        }

        public void setRollPage(int rollPage) {
            this.rollPage = rollPage;
        }

        public boolean isLastSuffix() {
            return lastSuffix;
        }

        public void setLastSuffix(boolean lastSuffix) {
            this.lastSuffix = lastSuffix;
        }

        public static class ParameterBean {
            /**
             * user_id : 29480
             * breedclass_id : 1
             */

            private String user_id;
            private String breedclass_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getBreedclass_id() {
                return breedclass_id;
            }

            public void setBreedclass_id(String breedclass_id) {
                this.breedclass_id = breedclass_id;
            }
        }
    }
}