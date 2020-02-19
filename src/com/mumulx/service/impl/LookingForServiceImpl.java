package com.mumulx.service.impl;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mumulx.entity.LookingFor;
import com.mumulx.entity.UserInfo;
import com.mumulx.mapper.LookingForMapper;
import com.mumulx.service.LookingForService;
import com.mumulx.util.HelpDev;

@Service("lookingForService")
public class LookingForServiceImpl implements LookingForService{

	@Autowired
	LookingForMapper lookingForMapper;
	public LookingForMapper getLookingForMapper() {
		return lookingForMapper;
	}
	public void setLookingForMapper(LookingForMapper lookingForMapper) {
		this.lookingForMapper = lookingForMapper;
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
    /*添加寻物启事记录*/
    public void addLookingFor(LookingFor lookingFor) throws Exception {
    	lookingForMapper.addLookingFor(lookingFor);
    }

	/*按照查询条件分页查询寻物启事记录*/
    public ArrayList<LookingFor> queryLookingFor(String title,String goodsName,String lostTime,String lostPlace,String telephone,UserInfo userObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_lookingFor.title like '%" + title + "%'";
    	if(!goodsName.equals("")) where = where + " and t_lookingFor.goodsName like '%" + goodsName + "%'";
    	if(!lostTime.equals("")) where = where + " and t_lookingFor.lostTime like '%" + lostTime + "%'";
    	if(!lostPlace.equals("")) where = where + " and t_lookingFor.lostPlace like '%" + lostPlace + "%'";
    	if(!telephone.equals("")) where = where + " and t_lookingFor.telephone like '%" + telephone + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_lookingFor.userObj='" + userObj.getUser_name() + "'";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return lookingForMapper.queryLookingFor(where, startIndex,this.helpDev.getRows());
    }


	@Override
	public ArrayList<LookingFor> queryLookingFor(String title, String goodsName, String lostTime, String lostPlace,
			String telephone, UserInfo userObj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LookingFor> queryAllLookingFor() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	 /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String goodsName,String lostTime,String lostPlace,String telephone,UserInfo userObj) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_lookingFor.title like '%" + title + "%'";
    	if(!goodsName.equals("")) where = where + " and t_lookingFor.goodsName like '%" + goodsName + "%'";
    	if(!lostTime.equals("")) where = where + " and t_lookingFor.lostTime like '%" + lostTime + "%'";
    	if(!lostPlace.equals("")) where = where + " and t_lookingFor.lostPlace like '%" + lostPlace + "%'";
    	if(!telephone.equals("")) where = where + " and t_lookingFor.telephone like '%" + telephone + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_lookingFor.userObj='" + userObj.getUser_name() + "'";
        this.helpDev.computeTotalPag(lookingForMapper.queryLookingForCount(where));
    }

	@Override
    /*根据主键获取寻物启事记录*/
    public LookingFor getLookingFor(int lookingForId) throws Exception  {
        LookingFor lookingFor = lookingForMapper.getLookingFor(lookingForId);
        return lookingFor;
    }
	@Override
    /*更新寻物启事记录*/
    public void updateLookingFor(LookingFor lookingFor) throws Exception {
        lookingForMapper.updateLookingFor(lookingFor);
    }
	@Override
	public void deleteLookingFor(int lookingForId) throws Exception {
		// TODO Auto-generated method stub
	}
	@Override
	/*删除多条寻物启事信息*/
    public int deleteLookingFors (String lookingForIds,String goodsPhotos) throws Exception {
    	String _lookingForIds[] = lookingForIds.split(",");
    	String _goodsPhotos[] = goodsPhotos.split(",");
    	int i=0;
    	for(String _lookingForId: _lookingForIds) {
    		String imgSrc = _goodsPhotos[i];
    		i++;
    		this.helpDev.deleteImgPhoto(imgSrc);
    		lookingForMapper.deleteLookingFor(Integer.parseInt(_lookingForId));
    	}
    	return _lookingForIds.length;
    }
}
