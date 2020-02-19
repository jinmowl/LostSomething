package com.mumulx.service;

import java.util.ArrayList;
import com.mumulx.entity.Claim;
import com.mumulx.entity.LostFound;
import com.mumulx.util.HelpDev;
public interface ClaimService {

    /*添加认领记录*/
    public Boolean addClaim(Claim claim) throws Exception ;

    /*按照查询条件分页查询认领记录*/
    public ArrayList<Claim> queryClaim(LostFound lostFoundObj,String personName,String claimTime,int currentPage) throws Exception;

    /*按照查询条件查询所有记录*/
    public ArrayList<Claim> queryClaim(LostFound lostFoundObj,String personName,String claimTime) throws Exception ;

    /*查询所有认领记录*/
    public ArrayList<Claim> queryAllClaim()  throws Exception ;

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(LostFound lostFoundObj,String personName,String claimTime) throws Exception ;

    /*根据主键获取认领记录*/
    public Claim getClaim(int claimId) throws Exception  ;

    /*更新认领记录*/
    public Boolean updateClaim(Claim claim,int lfoOldId) throws Exception ;

    /*删除一条认领记录*/
    public void deleteClaim (int claimId) throws Exception ;

    /*删除多条认领信息*/
    public int deleteClaims (String claimIds) throws Exception ;
    
	//获取帮助类
	public HelpDev getHelpDev() ;
}
