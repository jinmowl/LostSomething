package com.mumulx.service.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mumulx.entity.LostFound;
import com.mumulx.mapper.LostFoundMapper;
import com.mumulx.service.LostFoundService;
import com.mumulx.util.HelpDev;
@Service("lostFoundService")
public class LostFoundServiceImpl implements LostFoundService{
	@Autowired
	LostFoundMapper lostFoundMapper;
	public LostFoundMapper getLostFoundMapper() {
		return lostFoundMapper;
	}
	public void setLostFoundMapper(LostFoundMapper lostFoundMapper) {
		this.lostFoundMapper = lostFoundMapper;
	}
	@Autowired
	private HelpDev helpDev;
	public HelpDev getHelpDev() {
		return helpDev;
	}
	public void setHelpDev(HelpDev helpDev) {
		this.helpDev = helpDev;
	}
	@Override
    /*添加失物招领记录*/
    public void addLostFound(LostFound lostFound) throws Exception {
    	lostFoundMapper.addLostFound(lostFound);
    }
	@Override
    /*按照查询条件分页查询失物招领记录*/
    public ArrayList<LostFound> queryLostFound(String title,String goodsName,String pickUpTime,String pickUpPlace,String connectPerson,String phone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_lostFound.title like '%" + title + "%'";
    	if(!goodsName.equals("")) where = where + " and t_lostFound.goodsName like '%" + goodsName + "%'";
    	if(!pickUpTime.equals("")) where = where + " and t_lostFound.pickUpTime like '%" + pickUpTime + "%'";
    	if(!pickUpPlace.equals("")) where = where + " and t_lostFound.pickUpPlace like '%" + pickUpPlace + "%'";
    	if(!connectPerson.equals("")) where = where + " and t_lostFound.connectPerson like '%" + connectPerson + "%'";
    	if(!phone.equals("")) where = where + " and t_lostFound.phone like '%" + phone + "%'";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return lostFoundMapper.queryLostFound(where, startIndex, this.helpDev.getRows());
    }


	@Override
	public ArrayList<LostFound> queryLostFound(String title, String goodsName, String pickUpTime, String pickUpPlace,
			String connectPerson, String phone) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    /*查询所有失物招领记录*/
    public ArrayList<LostFound> queryAllLostFound()  throws Exception {
        return lostFoundMapper.queryLostFoundList("where 1=1");
    }

	@Override
	 /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String goodsName,String pickUpTime,String pickUpPlace,String connectPerson,String phone) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_lostFound.title like '%" + title + "%'";
    	if(!goodsName.equals("")) where = where + " and t_lostFound.goodsName like '%" + goodsName + "%'";
    	if(!pickUpTime.equals("")) where = where + " and t_lostFound.pickUpTime like '%" + pickUpTime + "%'";
    	if(!pickUpPlace.equals("")) where = where + " and t_lostFound.pickUpPlace like '%" + pickUpPlace + "%'";
    	if(!connectPerson.equals("")) where = where + " and t_lostFound.connectPerson like '%" + connectPerson + "%'";
    	if(!phone.equals("")) where = where + " and t_lostFound.phone like '%" + phone + "%'";
    	this.helpDev.computeTotalPag(lostFoundMapper.queryLostFoundCount(where));
    }
	@Override
    /*根据主键获取失物招领记录*/
    public LostFound getLostFound(int lostFoundId) throws Exception  {
        LostFound lostFound = lostFoundMapper.getLostFound(lostFoundId);
        return lostFound;
    }


	@Override
    /*更新失物招领记录*/
    public void updateLostFound(LostFound lostFound) throws Exception {
        lostFoundMapper.updateLostFound(lostFound);
    }
	@Override
	public void deleteLostFound(int lostFoundId) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	/*删除多条失物招领信息*/
    public int deleteLostFounds (String lostFoundIds ,String goodsPhotos) throws Exception {
    	String _lostFoundIds[] = lostFoundIds.split(",");
    	String _goodsPhotos[] = goodsPhotos.split(",");
    	int i = 0;
    	for(String _lostFoundId: _lostFoundIds) {
    		String imgSrc = _goodsPhotos[i];
    		i++;
    		this.helpDev.deleteImgPhoto(imgSrc);
    		lostFoundMapper.deleteLostFound(Integer.parseInt(_lostFoundId));
    	}
    	return _lostFoundIds.length;
    }

}
