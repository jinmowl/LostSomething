package com.mumulx.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Notice {
    /*通知id*/
    private Integer noticeId;
    public Integer getNoticeId(){
        return noticeId;
    }
    public void setNoticeId(Integer noticeId){
        this.noticeId = noticeId;
    }

    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonNotice=new JSONObject(); 
		jsonNotice.accumulate("noticeId", this.getNoticeId());
		jsonNotice.accumulate("title", this.getTitle());
		jsonNotice.accumulate("content", this.getContent());
		jsonNotice.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonNotice;
    }}