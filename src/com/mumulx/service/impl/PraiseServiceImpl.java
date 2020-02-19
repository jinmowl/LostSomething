package com.mumulx.service.impl;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mumulx.entity.LostFound;
import com.mumulx.entity.Praise;
import com.mumulx.mapper.PraiseMapper;
import com.mumulx.service.PraiseService;
import com.mumulx.util.HelpDev;
@Service("praiseService")
public class PraiseServiceImpl implements PraiseService{

	@Resource
	PraiseMapper praiseMapper;
	@Autowired
	HelpDev helpDev;
	
	
	
	public PraiseMapper getPraiseMapper() {
		return praiseMapper;
	}

	public void setPraiseMapper(PraiseMapper praiseMapper) {
		this.praiseMapper = praiseMapper;
	}

	public HelpDev getHelpDev() {
		return helpDev;
	}

	public void setHelpDev(HelpDev helpDev) {
		this.helpDev = helpDev;
	}

	@Override
    /*添加表扬记录*/
    public void addPraise(Praise praise) throws Exception {
    	praiseMapper.addPraise(praise);
    }

	@Override
    /*按照查询条件分页查询表扬记录*/
    public ArrayList<Praise> queryPraise(LostFound lostFoundObj,String title,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != lostFoundObj && lostFoundObj.getLostFoundId()!= null && lostFoundObj.getLostFoundId()!= 0)  where += " and t_praise.lostFoundObj=" + lostFoundObj.getLostFoundId();
    	if(!title.equals("")) where = where + " and t_praise.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_praise.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return praiseMapper.queryPraise(where, startIndex, this.helpDev.getRows());
    }

	@Override
	public ArrayList<Praise> queryPraise(LostFound lostFoundObj, String title, String addTime) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Praise> queryAllPraise() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(LostFound lostFoundObj,String title,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != lostFoundObj && lostFoundObj.getLostFoundId()!= null && lostFoundObj.getLostFoundId()!= 0)  where += " and t_praise.lostFoundObj=" + lostFoundObj.getLostFoundId();
    	if(!title.equals("")) where = where + " and t_praise.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_praise.addTime like '%" + addTime + "%'";
        this.helpDev.computeTotalPag(praiseMapper.queryPraiseCount(where));
    }

	@Override
    /*根据主键获取表扬记录*/
    public Praise getPraise(int praiseId) throws Exception  {
        Praise praise = praiseMapper.getPraise(praiseId);
        return praise;
    }
	@Override
    /*更新表扬记录*/
    public void updatePraise(Praise praise) throws Exception {
        praiseMapper.updatePraise(praise);
    }
	@Override
	public void deletePraise(int praiseId) throws Exception {
		// TODO Auto-generated method stub
	}
	@Override
    /*删除多条表扬信息*/
    public int deletePraises (String praiseIds) throws Exception {
    	String _praiseIds[] = praiseIds.split(",");
    	for(String _praiseId: _praiseIds) {
    		praiseMapper.deletePraise(Integer.parseInt(_praiseId));
    	}
    	return _praiseIds.length;
    }
}
