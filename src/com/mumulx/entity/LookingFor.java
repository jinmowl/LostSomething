package com.mumulx.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class LookingFor {
    /*寻物id*/
    private Integer lookingForId;
    public Integer getLookingForId(){
        return lookingForId;
    }
    public void setLookingForId(Integer lookingForId){
        this.lookingForId = lookingForId;
    }

    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    private String goodsName;
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /*物品照片*/
    private String goodsPhoto;
    public String getGoodsPhoto() {
        return goodsPhoto;
    }
    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    /*丢失时间*/
    private String lostTime;
    public String getLostTime() {
        return lostTime;
    }
    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }

    /*丢失地点*/
    private String lostPlace;
    public String getLostPlace() {
        return lostPlace;
    }
    public void setLostPlace(String lostPlace) {
        this.lostPlace = lostPlace;
    }

    /*物品描述*/
    private String goodDesc;
    public String getGoodDesc() {
        return goodDesc;
    }
    public void setGoodDesc(String goodDesc) {
        this.goodDesc = goodDesc;
    }

    /*报酬*/
    private String reward;
    public String getReward() {
        return reward;
    }
    public void setReward(String reward) {
        this.reward = reward;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*学生*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
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
    	JSONObject jsonLookingFor=new JSONObject(); 
		jsonLookingFor.accumulate("lookingForId", this.getLookingForId());
		jsonLookingFor.accumulate("title", this.getTitle());
		jsonLookingFor.accumulate("goodsName", this.getGoodsName());
		jsonLookingFor.accumulate("goodsPhoto", this.getGoodsPhoto());
		jsonLookingFor.accumulate("lostTime", this.getLostTime().length()>19?this.getLostTime().substring(0,19):this.getLostTime());
		jsonLookingFor.accumulate("lostPlace", this.getLostPlace());
		jsonLookingFor.accumulate("goodDesc", this.getGoodDesc());
		jsonLookingFor.accumulate("reward", this.getReward());
		jsonLookingFor.accumulate("telephone", this.getTelephone());
		jsonLookingFor.accumulate("userObj", this.getUserObj().getName());
		jsonLookingFor.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonLookingFor.accumulate("addTime", this.getAddTime());
		return jsonLookingFor;
    }}