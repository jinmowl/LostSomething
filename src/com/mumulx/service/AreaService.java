package com.mumulx.service;

import java.util.ArrayList;
import com.mumulx.entity.Area;
import com.mumulx.util.HelpDev;

public interface AreaService {
	
	//获取帮助类
	public HelpDev getHelpDev();
    /*添加学院记录*/
    public Boolean addArea(Area area) throws Exception ;

    /*按照查询条件分页查询学院记录*/
    public ArrayList<Area> queryArea(int currentPage) throws Exception ;

    /*按照查询条件查询所有记录*/
    public ArrayList<Area> queryArea() throws Exception  ;

    /*查询所有学院记录*/
    public ArrayList<Area> queryAllArea()  throws Exception ;

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception ;

    /*根据主键获取学院记录*/
    public Area getArea(int areaId) throws Exception  ;

    /*更新学院记录*/
    public Boolean updateArea(Area area) throws Exception ;

    /*删除一条学院记录*/
    public void deleteArea (int areaId) throws Exception ;

    /*删除多条学院信息*/
    public int deleteAreas (String areaIds) throws Exception;
}
