package com.mumulx.service;

import java.util.ArrayList;
import com.mumulx.entity.LostFound;
import com.mumulx.entity.Praise;
import com.mumulx.util.HelpDev;
public interface PraiseService {

    /*添加表扬记录*/
    public void addPraise(Praise praise) throws Exception ;

    /*按照查询条件分页查询表扬记录*/
    public ArrayList<Praise> queryPraise(LostFound lostFoundObj,String title,String addTime,int currentPage) throws Exception;

    /*按照查询条件查询所有记录*/
    public ArrayList<Praise> queryPraise(LostFound lostFoundObj,String title,String addTime) throws Exception ;

    /*查询所有表扬记录*/
    public ArrayList<Praise> queryAllPraise()  throws Exception ;
    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(LostFound lostFoundObj,String title,String addTime) throws Exception ;

    /*根据主键获取表扬记录*/
    public Praise getPraise(int praiseId) throws Exception  ;

    /*更新表扬记录*/
    public void updatePraise(Praise praise) throws Exception ;

    /*删除一条表扬记录*/
    public void deletePraise (int praiseId) throws Exception ;

    /*删除多条表扬信息*/
    public int deletePraises (String praiseIds) throws Exception ;
    //获取帮助类
	public HelpDev getHelpDev() ;
}
