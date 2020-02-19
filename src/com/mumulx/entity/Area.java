package com.mumulx.entity;
import org.json.JSONException;
import org.json.JSONObject;

public class Area {
    /*学院id*/
    private Integer areaId;
    public Integer getAreaId(){
        return areaId;
    }
    public void setAreaId(Integer areaId){
        this.areaId = areaId;
    }

    /*学院名称*/
    private String areaName;
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonArea=new JSONObject(); 
		jsonArea.accumulate("areaId", this.getAreaId());
		jsonArea.accumulate("areaName", this.getAreaName());
		return jsonArea;
    }}