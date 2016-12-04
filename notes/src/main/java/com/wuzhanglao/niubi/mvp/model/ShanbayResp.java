package com.wuzhanglao.niubi.mvp.model;

import java.util.List;

/**
 * Created by wuming on 2016/12/2.
 */

public class ShanbayResp {

    /**
     * msg : SUCCESS
     * status_code : 0
     * data : {"pronunciations":{"uk":"'eni","us":"'eni"},"en_definitions":{"adv":["to any degree or extent"],"adj":["one or some or every or all without specification"]},"audio_addresses":{"uk":["https://words-audio.baydn.com/uk%2Fa%2Fan%2Fany.mp3","http://words-audio.qiniu.baydn.com/uk/a/an/any.mp3"],"us":["https://words-audio.baydn.com/us%2Fa%2Fan%2Fany.mp3","http://words-audio.qiniu.baydn.com/us/a/an/any.mp3"]},"uk_audio":"http://media.shanbay.com/audio/uk/any.mp3","conent_id":204,"audio_name":"any","cn_definition":{"pos":"","defn":"adj. 任何的\nadv. 稍\ndet. 一些\nprep. 无论哪个\npron. 任何"},"num_sense":1,"content_type":"vocabulary","sense_id":0,"id":204,"definition":" adj. 任何的\nadv. 稍\ndet. 一些\nprep. 无论哪个\npron. 任何","content_id":204,"url":"https://www.shanbay.com/bdc/mobile/preview/word?word=any","has_audio":true,"en_definition":{"pos":"adv","defn":"to any degree or extent"},"object_id":204,"content":"any","pron":"'eni","pronunciation":"'eni","audio":"http://media.shanbay.com/audio/us/any.mp3","us_audio":"http://media.shanbay.com/audio/us/any.mp3"}
     */

    private String msg;
    private int status_code;
    /**
     * pronunciations : {"uk":"'eni","us":"'eni"}
     * en_definitions : {"adv":["to any degree or extent"],"adj":["one or some or every or all without specification"]}
     * audio_addresses : {"uk":["https://words-audio.baydn.com/uk%2Fa%2Fan%2Fany.mp3","http://words-audio.qiniu.baydn.com/uk/a/an/any.mp3"],"us":["https://words-audio.baydn.com/us%2Fa%2Fan%2Fany.mp3","http://words-audio.qiniu.baydn.com/us/a/an/any.mp3"]}
     * uk_audio : http://media.shanbay.com/audio/uk/any.mp3
     * conent_id : 204
     * audio_name : any
     * cn_definition : {"pos":"","defn":"adj. 任何的\nadv. 稍\ndet. 一些\nprep. 无论哪个\npron. 任何"}
     * num_sense : 1
     * content_type : vocabulary
     * sense_id : 0
     * id : 204
     * definition :  adj. 任何的
     adv. 稍
     det. 一些
     prep. 无论哪个
     pron. 任何
     * content_id : 204
     * url : https://www.shanbay.com/bdc/mobile/preview/word?word=any
     * has_audio : true
     * en_definition : {"pos":"adv","defn":"to any degree or extent"}
     * object_id : 204
     * content : any
     * pron : 'eni
     * pronunciation : 'eni
     * audio : http://media.shanbay.com/audio/us/any.mp3
     * us_audio : http://media.shanbay.com/audio/us/any.mp3
     */

    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uk : 'eni
         * us : 'eni
         */

        private PronunciationsBean pronunciations;
        private EnDefinitionsBean en_definitions;
        private AudioAddressesBean audio_addresses;
        private String uk_audio;
        private int conent_id;
        private String audio_name;
        /**
         * pos :
         * defn : adj. 任何的
         adv. 稍
         det. 一些
         prep. 无论哪个
         pron. 任何
         */

        private CnDefinitionBean cn_definition;
        private int num_sense;
        private String content_type;
        private int sense_id;
        private int id;
        private String definition;
        private int content_id;
        private String url;
        private boolean has_audio;
        /**
         * pos : adv
         * defn : to any degree or extent
         */

        private EnDefinitionBean en_definition;
        private int object_id;
        private String content;
        private String pron;
        private String pronunciation;
        private String audio;
        private String us_audio;

        public PronunciationsBean getPronunciations() {
            return pronunciations;
        }

        public void setPronunciations(PronunciationsBean pronunciations) {
            this.pronunciations = pronunciations;
        }

        public EnDefinitionsBean getEn_definitions() {
            return en_definitions;
        }

        public void setEn_definitions(EnDefinitionsBean en_definitions) {
            this.en_definitions = en_definitions;
        }

        public AudioAddressesBean getAudio_addresses() {
            return audio_addresses;
        }

        public void setAudio_addresses(AudioAddressesBean audio_addresses) {
            this.audio_addresses = audio_addresses;
        }

        public String getUk_audio() {
            return uk_audio;
        }

        public void setUk_audio(String uk_audio) {
            this.uk_audio = uk_audio;
        }

        public int getConent_id() {
            return conent_id;
        }

        public void setConent_id(int conent_id) {
            this.conent_id = conent_id;
        }

        public String getAudio_name() {
            return audio_name;
        }

        public void setAudio_name(String audio_name) {
            this.audio_name = audio_name;
        }

        public CnDefinitionBean getCn_definition() {
            return cn_definition;
        }

        public void setCn_definition(CnDefinitionBean cn_definition) {
            this.cn_definition = cn_definition;
        }

        public int getNum_sense() {
            return num_sense;
        }

        public void setNum_sense(int num_sense) {
            this.num_sense = num_sense;
        }

        public String getContent_type() {
            return content_type;
        }

        public void setContent_type(String content_type) {
            this.content_type = content_type;
        }

        public int getSense_id() {
            return sense_id;
        }

        public void setSense_id(int sense_id) {
            this.sense_id = sense_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHas_audio() {
            return has_audio;
        }

        public void setHas_audio(boolean has_audio) {
            this.has_audio = has_audio;
        }

        public EnDefinitionBean getEn_definition() {
            return en_definition;
        }

        public void setEn_definition(EnDefinitionBean en_definition) {
            this.en_definition = en_definition;
        }

        public int getObject_id() {
            return object_id;
        }

        public void setObject_id(int object_id) {
            this.object_id = object_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPron() {
            return pron;
        }

        public void setPron(String pron) {
            this.pron = pron;
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

        public String getUs_audio() {
            return us_audio;
        }

        public void setUs_audio(String us_audio) {
            this.us_audio = us_audio;
        }

        public static class PronunciationsBean {
            private String uk;
            private String us;

            public String getUk() {
                return uk;
            }

            public void setUk(String uk) {
                this.uk = uk;
            }

            public String getUs() {
                return us;
            }

            public void setUs(String us) {
                this.us = us;
            }
        }

        public static class EnDefinitionsBean {
            private List<String> adv;
            private List<String> adj;

            public List<String> getAdv() {
                return adv;
            }

            public void setAdv(List<String> adv) {
                this.adv = adv;
            }

            public List<String> getAdj() {
                return adj;
            }

            public void setAdj(List<String> adj) {
                this.adj = adj;
            }
        }

        public static class AudioAddressesBean {
            private List<String> uk;
            private List<String> us;

            public List<String> getUk() {
                return uk;
            }

            public void setUk(List<String> uk) {
                this.uk = uk;
            }

            public List<String> getUs() {
                return us;
            }

            public void setUs(List<String> us) {
                this.us = us;
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
