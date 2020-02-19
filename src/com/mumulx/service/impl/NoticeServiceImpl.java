package com.mumulx.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mumulx.entity.Notice;
import com.mumulx.mapper.NoticeMapper;
import com.mumulx.service.NoticeService;
import com.mumulx.util.HelpDev;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	NoticeMapper noticeMapper;
	@Autowired
	HelpDev helpDev;
	public NoticeMapper getNoticesMapper() {
		return noticeMapper;
	}
	public void setNoticesMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	public HelpDev getHelpDev() {
		return helpDev;
	}
	public void setHelpDev(HelpDev helpDev) {
		this.helpDev = helpDev;
	}

	@Override
    /*添加站内通知记录*/
    public void addNotice(Notice notice) throws Exception {
    	noticeMapper.addNotice(notice);
    }

	@Override
	   /*按照查询条件分页查询站内通知记录*/
    public ArrayList<Notice> queryNotice(String title,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_notice.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_notice.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return noticeMapper.queryNotice(where, startIndex, this.helpDev.getRows());
    }

	@Override
	public ArrayList<Notice> queryNotice(String title, String addTime) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    /*查询所有站内通知记录*/
    public ArrayList<Notice> queryAllNotice()  throws Exception {
        return noticeMapper.queryNoticeList("where 1=1");
    }

	@Override
    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_notice.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_notice.addTime like '%" + addTime + "%'";
    	this.helpDev.computeTotalPag(noticeMapper.queryNoticeCount(where));
    }

	@Override
    /*根据主键获取站内通知记录*/
    public Notice getNotice(int noticeId) throws Exception  {
        Notice notice = noticeMapper.getNotice(noticeId);
        return notice;
    }

	@Override
    /*更新站内通知记录*/
    public void updateNotice(Notice notice) throws Exception {
        noticeMapper.updateNotice(notice);
    }

	@Override
    /*删除一条站内通知记录*/
    public void deleteNotice (int noticeId) throws Exception {
        noticeMapper.deleteNotice(noticeId);
    }

	@Override
    /*删除多条站内通知信息*/
    public int deleteNotices (String noticeIds) throws Exception {
    	String _noticeIds[] = noticeIds.split(",");
    	for(String _noticeId: _noticeIds) {
    		noticeMapper.deleteNotice(Integer.parseInt(_noticeId));
    	}
    	return _noticeIds.length;
    }
}
