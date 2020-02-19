package com.mumulx.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class LostFound {
    /*招领id*/
    private Integer lostFoundId;
    public Integer getLostFoundId(){
        return lostFoundId;
    }
    public void setLostFoundId(Integer lostFoundId){
        this.lostFoundId = lostFoundId;
    }

    /*标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    /*物品照片*/
    private String goodsPhoto;
    public String getGoodsPhoto() {
        return goodsPhoto;
    }
    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }
    /*物品名称*/
    private String goodsName;
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /*捡得时间*/
    private String pickUpTime;
    public String getPickUpTime() {
        return pickUpTime;
    }
    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    /*拾得地点*/
    private String pickUpPlace;
    public String getPickUpPlace() {
        return pickUpPlace;
    }
    public void setPickUpPlace(String pickUpPlace) {
        this.pickUpPlace = pickUpPlace;
    }

    /*描述说明*/
    private String contents;
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }

    /*联系人*/
    private String connectPerson;
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }

    /*联系电话*/
    private String phone;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
    	JSONObject jsonLostFound=new JSONObject(); 
		jsonLostFound.accumulate("lostFoundId", this.getLostFoundId());
		jsonLostFound.accumulate("title", this.getTitle());
		jsonLostFound.accumulate("goodsName", this.getGoodsName());
		jsonLostFound.accumulate("goodsPhoto", this.getGoodsPhoto());
		jsonLostFound.accumulate("pickUpTime", this.getPickUpTime().length()>19?this.getPickUpTime().substring(0,19):this.getPickUpTime());
		jsonLostFound.accumulate("pickUpPlace", this.getPickUpPlace());
		jsonLostFound.accumulate("contents", this.getContents());
		jsonLostFound.accumulate("connectPerson", this.getConnectPerson());
		jsonLostFound.accumulate("phone", this.getPhone());
		jsonLostFound.accumulate("addTime", this.getAddTime());
		return jsonLostFound;
    }}