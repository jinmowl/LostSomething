package com.mumulx.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mumulx.entity.Claim;
import com.mumulx.entity.LostFound;
import com.mumulx.mapper.ClaimMapper;
import com.mumulx.service.ClaimService;
import com.mumulx.util.HelpDev;
@Service("claimService")
public class ClaimServiceImpl implements ClaimService{
	@Autowired
	ClaimMapper claimMapper;
	@Autowired
	HelpDev helpDev;
	
	public ClaimMapper getClaimMapper() {
		return claimMapper;
	}
	public void setClaimMapper(ClaimMapper claimMapper) {
		this.claimMapper = claimMapper;
	}

	public HelpDev getHelpDev() {
		return helpDev;
	}

	public void setHelpDev(HelpDev helpDev) {
		this.helpDev = helpDev;
	}

	@Override
    /*添加认领记录*/
    public Boolean addClaim(Claim claim) throws Exception {
		//添加认领记录之前要先确定，该物品是否被认领，如果该物品被认领则，报错
		Integer lostFoundId = claim.getLostFoundObj().getLostFoundId();
		int result = claimMapper.queryLostFoundID(lostFoundId);
		if(result==0) {//物品还没被认领
			claimMapper.addClaim(claim);
			return true;
		}else {
			return false;
		}
    }

	@Override
	
	/*按照查询条件分页查询认领记录*/
    public ArrayList<Claim> queryClaim(LostFound lostFoundObj,String personName,String claimTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != lostFoundObj && lostFoundObj.getLostFoundId()!= null && lostFoundObj.getLostFoundId()!= 0)  where += " and t_claim.lostFoundObj=" + lostFoundObj.getLostFoundId();
    	if(!personName.equals("")) where = where + " and t_claim.personName like '%" + personName + "%'";
    	if(!claimTime.equals("")) where = where + " and t_claim.claimTime like '%" + claimTime + "%'";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return claimMapper.queryClaim(where, startIndex, this.helpDev.getRows());
    }

	@Override
	  /*按照查询条件查询所有记录*/
    public ArrayList<Claim> queryClaim(LostFound lostFoundObj,String personName,String claimTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != lostFoundObj && lostFoundObj.getLostFoundId()!= null && lostFoundObj.getLostFoundId()!= 0)  where += " and t_claim.lostFoundObj=" + lostFoundObj.getLostFoundId();
    	if(!personName.equals("")) where = where + " and t_claim.personName like '%" + personName + "%'";
    	if(!claimTime.equals("")) where = where + " and t_claim.claimTime like '%" + claimTime + "%'";
    	return claimMapper.queryClaimList(where);
    }

	@Override
	public ArrayList<Claim> queryAllClaim() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(LostFound lostFoundObj,String personName,String claimTime) throws Exception {
     	String where = "where 1=1";
    	if(null != lostFoundObj && lostFoundObj.getLostFoundId()!= null && lostFoundObj.getLostFoundId()!= 0)  where += " and t_claim.lostFoundObj=" + lostFoundObj.getLostFoundId();
    	if(!personName.equals("")) where = where + " and t_claim.personName like '%" + personName + "%'";
    	if(!claimTime.equals("")) where = where + " and t_claim.claimTime like '%" + claimTime + "%'";
    	this.helpDev.computeTotalPag(claimMapper.queryClaimCount(where));
    }

	@Override
    /*根据主键获取认领记录*/
    public Claim getClaim(int claimId) throws Exception  {
        Claim claim = claimMapper.getClaim(claimId);
        return claim;
    }
	@Override
    public Boolean updateClaim(Claim claim,int lfoOldId) throws Exception {
		Integer lostFoundId = claim.getLostFoundObj().getLostFoundId();
		//没有修改认领物品id
		if(lostFoundId == lfoOldId) {
			claimMapper.updateClaim(claim);
			return true;
		}else {		//修改了认领物品
			//添加认领记录之前要先确定，该物品是否被认领，如果该物品已经被认领则，报错
			int result = claimMapper.queryLostFoundID(lostFoundId);
			if(result==0) {//物品还没被认领
				claimMapper.updateClaim(claim);
				return true;
			}else {
				return false;
			}
		}
    }

	@Override
	public void deleteClaim(int claimId) throws Exception {
		// TODO Auto-generated method stub
	}
	
	
	@Override
    /*删除多条认领信息*/
    public int deleteClaims (String claimIds) throws Exception {
    	String _claimIds[] = claimIds.split(",");
    	for(String _claimId: _claimIds) {
    		claimMapper.deleteClaim(Integer.parseInt(_claimId));
    	}
    	return _claimIds.length;
    }

}
