package com.wuzhanglao.niubi.mvp.model;

import java.util.List;

/**
 * Created by wuming on 2016/12/2.
 */

public class ShanbayResp {

    /**
     * status_code : 0
     * msg : SUCCESS
     * data : {"en_definitions":{"n":["an expression of greeting"]},"cn_definition":{"pos":"","defn":"int.（见面打招呼或打电话用语）喂,哈罗"},"id":3130,"retention":4,"definition":" int.（见面打招呼或打电话用语）喂,哈罗","target_retention":5,"en_definition":{"pos":"n","defn":"an expression of greeting"},"learning_id":2915819690,"content":"hello","pronunciation":"hә'lәu","audio":"http://media.shanbay.com/audio/us/hello.mp3","uk_audio":"http://media.shanbay.com/audio/uk/hello.mp3","us_audio":"http://media.shanbay.com/audio/us/hello.mp3"}
     */

    private int status_code;
    private String msg;
    /**
     * en_definitions : {"n":["an expression of greeting"]}
     * cn_definition : {"pos":"","defn":"int.（见面打招呼或打电话用语）喂,哈罗"}
     * id : 3130
     * retention : 4
     * definition :  int.（见面打招呼或打电话用语）喂,哈罗
     * target_retention : 5
     * en_definition : {"pos":"n","defn":"an expression of greeting"}
     * learning_id : 2915819690
     * content : hello
     * pronunciation : hә'lәu
     * audio : http://media.shanbay.com/audio/us/hello.mp3
     * uk_audio : http://media.shanbay.com/audio/uk/hello.mp3
     * us_audio : http://media.shanbay.com/audio/us/hello.mp3
     */

    private DataBean data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private EnDefinitionsBean en_definitions;
        /**
         * pos :
         * defn : int.（见面打招呼或打电话用语）喂,哈罗
         */

        private CnDefinitionBean cn_definition;
        private int id;
        private int retention;
        private String definition;
        private int target_retention;
        /**
         * pos : n
         * defn : an expression of greeting
         */

        private EnDefinitionBean en_definition;
        private long learning_id;
        private String content;
        private String pronunciation;
        private String audio;
        private String uk_audio;
        private String us_audio;

        public EnDefinitionsBean getEn_definitions() {
            return en_definitions;
        }

        public void setEn_definitions(EnDefinitionsBean en_definitions) {
            this.en_definitions = en_definitions;
        }

        public CnDefinitionBean getCn_definition() {
            return cn_definition;
        }

        public void setCn_definition(CnDefinitionBean cn_definition) {
            this.cn_definition = cn_definition;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRetention() {
            return retention;
        }

        public void setRetention(int retention) {
            this.retention = retention;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public int getTarget_retention() {
            return target_retention;
        }

        public void setTarget_retention(int target_retention) {
            this.target_retention = target_retention;
        }

        public EnDefinitionBean getEn_definition() {
            return en_definition;
        }

        public void setEn_definition(EnDefinitionBean en_definition) {
            this.en_definition = en_definition;
        }

        public long getLearning_id() {
            return learning_id;
        }

        public void setLearning_id(long learning_id) {
            this.learning_id = learning_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getUk_audio() {
            return uk_audio;
        }

        public void setUk_audio(String uk_audio) {
            this.uk_audio = uk_audio;
        }

        public String getUs_audio() {
            return us_audio;
        }

        public void setUs_audio(String us_audio) {
            this.us_audio = us_audio;
        }

        public static class EnDefinitionsBean {
            private List<String> n;

            public List<String> getN() {
                return n;
            }

            public void setN(List<String> n) {
                this.n = n;
            }
        }

        public static class CnDefinitionBean {
            private String pos;
            private String defn;

            public String getPos() {
                return pos;
            }

            public void setPos(String pos) {
                this.pos = pos;
            }

            public String getDefn() {
                return defn;
            }

            public void setDefn(String defn) {
                this.defn = defn;
            }
        }

        public static class EnDefinitionBean {
            private String pos;
            private String defn;

            public String getPos() {
                return pos;
            }

            public void setPos(String pos) {
                this.pos = pos;
            }

            public String getDefn() {
                return defn;
            }

            public void setDefn(String defn) {
                this.defn = defn;
            }
        }
    }
}
