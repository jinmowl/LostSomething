package com.mumulx.service;

import java.util.ArrayList;
import com.mumulx.entity.LookingFor;
import com.mumulx.entity.UserInfo;
import com.mumulx.util.HelpDev;
public interface LookingForService {


    /*添加寻物启事记录*/
    public void addLookingFor(LookingFor lookingFor) throws Exception ;

    /*按照查询条件分页查询寻物启事记录*/
    public ArrayList<LookingFor> queryLookingFor(String title,String goodsName,String lostTime,String lostPlace,String telephone,UserInfo userObj,int currentPage) throws Exception ;

    /*按照查询条件查询所有记录*/
    public ArrayList<LookingFor> queryLookingFor(String title,String goodsName,String lostTime,String lostPlace,String telephone,UserInfo userObj) throws Exception ;

    /*查询所有寻物启事记录*/
    public ArrayList<LookingFor> queryAllLookingFor()  throws Exception ;

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String goodsName,String lostTime,String lostPlace,String telephone,UserInfo userObj) throws Exception ;

    /*根据主键获取寻物启事记录*/
    public LookingFor getLookingFor(int lookingForId) throws Exception ;
    /*更新寻物启事记录*/
    public void updateLookingFor(LookingFor lookingFor) throws Exception ;

    /*删除一条寻物启事记录*/
    public void deleteLookingFor (int lookingForId) throws Exception ;
    /*删除多条寻物启事信息*/
    public int deleteLookingFors (String lookingForIds,String goodsPhotos) throws Exception;
    //获取帮助类
	public HelpDev getHelpDev() ;
}
