package com.example.lhz.myapplication;

import java.util.List;


public class DeliveryMessages {
    //派送单号
    private String nu;

    //快递公司名称
    private String com;

    //快递信息
    private List<Message> data;

    //消息类
    public static class Message {

        //时间，格式为年-月-日 时:分:秒
        private String time;

        //详细信息内容
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
