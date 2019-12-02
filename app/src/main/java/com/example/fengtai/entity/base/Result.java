package com.example.fengtai.entity.base;

public class Result<T> {


    /**
     * status : 200
     * message : 用户登录成功
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzI0MTk0MDIsImV4cCI6MTU3MjUwNTgwMiwidXNlcl9pZCI6IjI5NDgwIiwidXNlcm5hbWUiOiJ0ZXN0In0.nha16AuSm2rRnp7mFTApeBkbT55s-T19OXhdLxXyvYA","user_id":"29480","username":"BreedDoc"}
     */

    private int status;
    private String message;
    private T data;
    private int code;

    private PageBean page;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * firstRow : 0
         * listRows : 10
         * parameter : {"user_id":"29480"}
         * totalRows : 1
         * totalPages : 1
         * rollPage : 3
         * lastSuffix : true
         */

        private int firstRow;
        private int listRows;
        private PageBean.ParameterBean parameter;
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

        public PageBean.ParameterBean getParameter() {
            return parameter;
        }

        public void setParameter(PageBean.ParameterBean parameter) {
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
             */

            private String user_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
