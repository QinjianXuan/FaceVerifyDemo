package com.ebgsplatform.badouloanapp.module;

import com.google.gson.annotations.SerializedName;

/**
 * Author: XuanQinjian
 * Create on: 2017-09-25 16:04
 * <p/>
 * Create:
 * <p/>
 * Description:
 * <p/>
 * Update:
 */

public class VerifyResultInfo {


    /**
     * id_exceptions : {"id_photo_monochrome":0,"id_attacked":0}
     * face_genuineness : {"mask_confidence":0,"screen_replay_confidence":0,"mask_threshold":0.5,"synthetic_face_confidence":0,"synthetic_face_threshold":0.5,"screen_replay_threshold":0.5,"face_replaced":0}
     * request_id : 1506335757,e4c623c1-5b99-4516-92e2-59d5365a2921
     * time_used : 1123
     * result_faceid : {"confidence":88.109,"thresholds":{"1e-3":62.169,"1e-5":74.399,"1e-4":69.315,"1e-6":78.038}}
     */

    private IdExceptionsBean id_exceptions;
    private FaceGenuinenessBean face_genuineness;
    private String request_id;
    private int time_used;
    private ResultFaceidBean result_faceid;
    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public IdExceptionsBean getId_exceptions() {
        return id_exceptions;
    }

    public void setId_exceptions(IdExceptionsBean id_exceptions) {
        this.id_exceptions = id_exceptions;
    }

    public FaceGenuinenessBean getFace_genuineness() {
        return face_genuineness;
    }

    public void setFace_genuineness(FaceGenuinenessBean face_genuineness) {
        this.face_genuineness = face_genuineness;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public ResultFaceidBean getResult_faceid() {
        return result_faceid;
    }

    public void setResult_faceid(ResultFaceidBean result_faceid) {
        this.result_faceid = result_faceid;
    }

    public static class IdExceptionsBean {
        /**
         * id_photo_monochrome : 0
         * id_attacked : 0
         */

        private int id_photo_monochrome;
        private int id_attacked;

        public int getId_photo_monochrome() {
            return id_photo_monochrome;
        }

        public void setId_photo_monochrome(int id_photo_monochrome) {
            this.id_photo_monochrome = id_photo_monochrome;
        }

        public int getId_attacked() {
            return id_attacked;
        }

        public void setId_attacked(int id_attacked) {
            this.id_attacked = id_attacked;
        }
    }

    public static class FaceGenuinenessBean {
        /**
         * mask_confidence : 0
         * screen_replay_confidence : 0
         * mask_threshold : 0.5
         * synthetic_face_confidence : 0
         * synthetic_face_threshold : 0.5
         * screen_replay_threshold : 0.5
         * face_replaced : 0
         */

        private int mask_confidence;
        private int screen_replay_confidence;
        private double mask_threshold;
        private int synthetic_face_confidence;
        private double synthetic_face_threshold;
        private double screen_replay_threshold;
        private int face_replaced;

        public int getMask_confidence() {
            return mask_confidence;
        }

        public void setMask_confidence(int mask_confidence) {
            this.mask_confidence = mask_confidence;
        }

        public int getScreen_replay_confidence() {
            return screen_replay_confidence;
        }

        public void setScreen_replay_confidence(int screen_replay_confidence) {
            this.screen_replay_confidence = screen_replay_confidence;
        }

        public double getMask_threshold() {
            return mask_threshold;
        }

        public void setMask_threshold(double mask_threshold) {
            this.mask_threshold = mask_threshold;
        }

        public int getSynthetic_face_confidence() {
            return synthetic_face_confidence;
        }

        public void setSynthetic_face_confidence(int synthetic_face_confidence) {
            this.synthetic_face_confidence = synthetic_face_confidence;
        }

        public double getSynthetic_face_threshold() {
            return synthetic_face_threshold;
        }

        public void setSynthetic_face_threshold(double synthetic_face_threshold) {
            this.synthetic_face_threshold = synthetic_face_threshold;
        }

        public double getScreen_replay_threshold() {
            return screen_replay_threshold;
        }

        public void setScreen_replay_threshold(double screen_replay_threshold) {
            this.screen_replay_threshold = screen_replay_threshold;
        }

        public int getFace_replaced() {
            return face_replaced;
        }

        public void setFace_replaced(int face_replaced) {
            this.face_replaced = face_replaced;
        }
    }

    public static class ResultFaceidBean {
        /**
         * confidence : 88.109
         * thresholds : {"1e-3":62.169,"1e-5":74.399,"1e-4":69.315,"1e-6":78.038}
         */

        private double confidence;
        private ThresholdsBean thresholds;

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public ThresholdsBean getThresholds() {
            return thresholds;
        }

        public void setThresholds(ThresholdsBean thresholds) {
            this.thresholds = thresholds;
        }

        public static class ThresholdsBean {
            /**
             * 1e-3 : 62.169
             * 1e-5 : 74.399
             * 1e-4 : 69.315
             * 1e-6 : 78.038
             */

            @SerializedName("1e-3")
            private double _$1e3;
            @SerializedName("1e-5")
            private double _$1e5;
            @SerializedName("1e-4")
            private double _$1e4;
            @SerializedName("1e-6")
            private double _$1e6;

            public double get_$1e3() {
                return _$1e3;
            }

            public void set_$1e3(double _$1e3) {
                this._$1e3 = _$1e3;
            }

            public double get_$1e5() {
                return _$1e5;
            }

            public void set_$1e5(double _$1e5) {
                this._$1e5 = _$1e5;
            }

            public double get_$1e4() {
                return _$1e4;
            }

            public void set_$1e4(double _$1e4) {
                this._$1e4 = _$1e4;
            }

            public double get_$1e6() {
                return _$1e6;
            }

            public void set_$1e6(double _$1e6) {
                this._$1e6 = _$1e6;
            }
        }
    }
}
