package com.mumulx.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.mumulx.entity.LostFound;
import com.mumulx.util.HelpDev;

public interface LostFoundService {
	
	public HelpDev getHelpDev() ;
    /*添加失物招领记录*/
    public void addLostFound(LostFound lostFound) throws Exception;

    /*按照查询条件分页查询失物招领记录*/
    public ArrayList<LostFound> queryLostFound(String title,String goodsName,String pickUpTime,String pickUpPlace,String connectPerson,String phone,int currentPage) throws Exception ;

    /*按照查询条件查询所有记录*/
    public ArrayList<LostFound> queryLostFound(String title,String goodsName,String pickUpTime,String pickUpPlace,String connectPerson,String phone) throws Exception  ;

    /*查询所有失物招领记录*/
    public ArrayList<LostFound> queryAllLostFound()  throws Exception ;

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String goodsName,String pickUpTime,String pickUpPlace,String connectPerson,String phone) throws Exception ;

    /*根据主键获取失物招领记录*/
    public LostFound getLostFound(int lostFoundId) throws Exception  ;
    /*更新失物招领记录*/
    public void updateLostFound(LostFound lostFound) throws Exception ;

    /*删除一条失物招领记录*/
    public void deleteLostFound (int lostFoundId) throws Exception;

    /*删除多条失物招领信息*/
    public int deleteLostFounds (String lostFoundIds,String goodsPhotos) throws Exception ;
}
