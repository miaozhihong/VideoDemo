package com.jm.bean;

import java.io.Serializable;

/**
 * 作者 Created by $miaozhihong on 2018/11/2 11:32
 * 页面功能:
 */
public class UMMessage implements Serializable {

    /**
     * display_type : notification
     * extra : {"test":"helloworld"}
     * msg_id : uuvyd5f154112651940901
     * body : {"after_open":"go_app","ticker":"己美","text":"客户15835899582咨询进店时间是：2018-10-18 10:46 请及时联系！","title":"己美"}
     * random_min : 0
     */

    private String display_type;
    private ExtraBean extra;
    private String msg_id;
    private BodyBean body;
    private int random_min;

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public static class ExtraBean {
        /**
         * test : helloworld
         */

        private String test;

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }

    public static class BodyBean {
        /**
         * after_open : go_app
         * ticker : 己美
         * text : 客户15835899582咨询进店时间是：2018-10-18 10:46 请及时联系！
         * title : 己美
         */

        private String after_open;
        private String ticker;
        private String text;
        private String title;

        public String getAfter_open() {
            return after_open;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public UMMessage(String display_type, ExtraBean extra, String msg_id, BodyBean body, int random_min) {
        this.display_type = display_type;
        this.extra = extra;
        this.msg_id = msg_id;
        this.body = body;
        this.random_min = random_min;
    }

    public UMMessage() {
    }
}
