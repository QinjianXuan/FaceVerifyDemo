package com.ebgsplatform.badouloanapp.module;

/**
 * Author: XuanQinjian
 * Create on: 2017-09-25 15:28
 * <p/>
 * Create:
 * <p/>
 * Description:
 * <p/>
 * Update:
 */

public class IDCardFrontInfo {

    /**
     * birthday : {"year":"1987","day":"19","month":"7"}
     * name : 孙超
     * race : 汉
     * address : 河北省廊市固安县牛驼镇前庞家冬村乓号
     * time_used : 672
     * gender : 男
     * head_rect : {"rt":{"y":0.18301886,"x":0.8713318},"lt":{"y":0.19056603,"x":0.6027088},"lb":{"y":0.6962264,"x":0.6139955},"rb":{"y":0.6886792,"x":0.89503384}}
     * request_id : 1506324288,431c8c89-3bd0-4c8f-a086-d57cef7cf395
     * id_card_number : 13102219870719203X
     * side : front
     *
     */

    private BirthdayBean birthday;
    private String name;
    private String race;
    private String address;
    private int time_used;
    private String gender;
    private HeadRectBean head_rect;
    private String request_id;
    private String id_card_number;
    private String side;
    private String issued_by;
    private String valid_date;

    public String getIssued_by() {
        return issued_by;
    }

    public void setIssued_by(String issued_by) {
        this.issued_by = issued_by;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public BirthdayBean getBirthday() {
        return birthday;
    }

    public void setBirthday(BirthdayBean birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HeadRectBean getHead_rect() {
        return head_rect;
    }

    public void setHead_rect(HeadRectBean head_rect) {
        this.head_rect = head_rect;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getId_card_number() {
        return id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public static class BirthdayBean {
        /**
         * year : 1987
         * day : 19
         * month : 7
         */

        private String year;
        private String day;
        private String month;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }

    public static class HeadRectBean {
        /**
         * rt : {"y":0.18301886,"x":0.8713318}
         * lt : {"y":0.19056603,"x":0.6027088}
         * lb : {"y":0.6962264,"x":0.6139955}
         * rb : {"y":0.6886792,"x":0.89503384}
         */

        private RtBean rt;
        private LtBean lt;
        private LbBean lb;
        private RbBean rb;

        public RtBean getRt() {
            return rt;
        }

        public void setRt(RtBean rt) {
            this.rt = rt;
        }

        public LtBean getLt() {
            return lt;
        }

        public void setLt(LtBean lt) {
            this.lt = lt;
        }

        public LbBean getLb() {
            return lb;
        }

        public void setLb(LbBean lb) {
            this.lb = lb;
        }

        public RbBean getRb() {
            return rb;
        }

        public void setRb(RbBean rb) {
            this.rb = rb;
        }

        public static class RtBean {
            /**
             * y : 0.18301886
             * x : 0.8713318
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }

        public static class LtBean {
            /**
             * y : 0.19056603
             * x : 0.6027088
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }

        public static class LbBean {
            /**
             * y : 0.6962264
             * x : 0.6139955
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }

        public static class RbBean {
            /**
             * y : 0.6886792
             * x : 0.89503384
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }
    }
}
