package com.mumulx.service;

import java.util.ArrayList;
import com.mumulx.entity.Notice;
import com.mumulx.util.HelpDev;
public interface NoticeService {

    /*添加站内通知记录*/
    public void addNotice(Notice notice) throws Exception ;

    /*按照查询条件分页查询站内通知记录*/
    public ArrayList<Notice> queryNotice(String title,String addTime,int currentPage) throws Exception ;

    /*按照查询条件查询所有记录*/
    public ArrayList<Notice> queryNotice(String title,String addTime) throws Exception  ;

    /*查询所有站内通知记录*/
    public ArrayList<Notice> queryAllNotice()  throws Exception ;

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String addTime) throws Exception ;

    /*根据主键获取站内通知记录*/
    public Notice getNotice(int noticeId) throws Exception  ;

    /*更新站内通知记录*/
    public void updateNotice(Notice notice) throws Exception ;

    /*删除一条站内通知记录*/
    public void deleteNotice (int noticeId) throws Exception;

    /*删除多条站内通知信息*/
    public int deleteNotices (String noticeIds) throws Exception ;
    //添加帮助类
    public HelpDev getHelpDev();
}
