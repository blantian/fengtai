package com.example.fengtai.entity;
public class LoginResult {

        /**
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzU3MDc5NzEsImV4cCI6MTU3NTc5NDM3MSwidXNlcl9pZCI6IjI5NTQyIiwidXNlcm5hbWUiOiJcdTU5NDdcdTUyOWJcdTY4M2NcdTVjMTQifQ.q1r0zssvk3o_uHXR9U2suqg-DUvbtNykD1TgN98q7p8
         * user_id : 29542
         * username : 奇力格尔
         * userdata : {"user_id":"29542","username":"奇力格尔","password":"fcea920f7412b5da7be0cf42b8c93759","phone":"15847316516","email":"","nickname":null,"sex":"1","address":"阿尔巴斯苏木乌兰其日嘎嘎查乌兰其日嘎小队","id_card":"","age":"0","avatar":null,"open_id":null,"user_api_key":null,"company_api_key":null,"fial_time":null,"is_admin":"3","register_code":null,"flag_code":null,"last_code":null,"smx_username":null,"smx_password":null,"iot_username":null,"iot_password":"","appkey":"","appsecret":"","login_ip":"1.27.51.56","created_at":"2019-09-29 15:26:27","updated_at":"2019-09-30 16:52:35","deleted_at":null,"xian":"95","shi":"86","sheng":"66","address_id":null,"smx_id":"-1","smx_type":"2","smx_ticket":"","usertype_id":"29480"}
         */

        private String token;
        private String user_id;
        private String username;
        private UserDataBean userdata;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public UserDataBean getUserdata() {
            return userdata;
        }

        public void setUserdata(UserDataBean userdata) {
            this.userdata = userdata;
        }
    }
