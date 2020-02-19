package com.mumulx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mumulx.entity.Claim;
import com.mumulx.entity.LostFound;
import com.mumulx.entity.UserInfo;
import com.mumulx.service.ClaimService;
import com.mumulx.service.LostFoundService;
import com.mumulx.service.UserInfoService;

//Claim管理控制层
@Controller
@RequestMapping("/Claim")
public class ClaimController extends HelpDevController {

    /*业务层对象*/
    @Resource(name="claimService")
    ClaimService claimService;

    @Resource(name="lostFoundService")
    LostFoundService lostFoundService;
    @Resource(name="userInfoService")
    UserInfoService  userInfoService ;
	public ClaimService getClaimService() {
		return claimService;
	}
	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}
	public LostFoundService getLostFoundService() {
		return lostFoundService;
	}
	public void setLostFoundService(LostFoundService lostFoundService) {
		this.lostFoundService = lostFoundService;
	}
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	@InitBinder("lostFoundObj")
	public void initBinderlostFoundObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("lostFoundObj.");
	}
	@InitBinder("claim")
	public void initBinderClaim(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("claim.");
	}
	
	/*前台按照查询条件分页查询认领信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("lostFoundObj") LostFound lostFoundObj,String personName,String claimTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (personName == null) personName = "";
		if (claimTime == null) claimTime = "";
		List<Claim> claimList = claimService.queryClaim(lostFoundObj, personName, claimTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    claimService.queryTotalPageAndRecordNumber(lostFoundObj, personName, claimTime);
	    /*获取到总的页码数目*/
	    int totalPage = claimService.getHelpDev().getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = claimService.getHelpDev().getRecordNumber();
	    request.setAttribute("claimList",  claimList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("lostFoundObj", lostFoundObj);
	    request.setAttribute("personName", personName);
	    request.setAttribute("claimTime", claimTime);
	    List<LostFound> lostFoundList = lostFoundService.queryAllLostFound();
	    request.setAttribute("lostFoundList", lostFoundList);
		return "Claim/claim_frontquery_result"; 
	}
	
	/*客户端ajax方式提交添加认领信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Claim claim, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		String user_name = claim.getPersonName();
		UserInfo userInfo = userInfoService.getUserInfo(user_name);
		if(userInfo==null) {
			message = "输入用户学号不存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
        Boolean rs = claimService.addClaim(claim);
        if(rs) {
        	message="认领添加成功!";
        	success = true;
        }else {
        	message="认领物品已被认领！";
        }
        writeJsonResponse(response, success, message);
	}
	/*前台查询Claim信息*/
	@RequestMapping(value="/{claimId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer claimId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键claimId获取Claim对象*/
        Claim claim = claimService.getClaim(claimId);

        List<LostFound> lostFoundList = lostFoundService.queryAllLostFound();
        request.setAttribute("lostFoundList", lostFoundList);
        request.setAttribute("claim",  claim);
        return "Claim/claim_frontshow";
	}
	/*ajax方式删除多条认领记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String claimIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = claimService.deleteClaims(claimIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}
	/*ajax方式显示认领修改jsp视图页*/
	@RequestMapping(value="/{claimId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer claimId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键claimId获取Claim对象*/
        Claim claim = claimService.getClaim(claimId);
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonClaim = claim.getJsonObject();
		out.println(jsonClaim.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新认领信息*/
	@RequestMapping(value = "/{claimId}/update", method = RequestMethod.POST)
	public void update(@Validated Claim claim, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String user_name = claim.getPersonName();
		UserInfo userInfo = userInfoService.getUserInfo(user_name);
		if(userInfo==null) {
			message = "输入用户学号不存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			
			int  lfoOldId = Integer.parseInt(request.getParameter("lfoOldId"));
			Boolean rs = claimService.updateClaim(claim,lfoOldId);
			if(rs) {
	        	message="认领更新成功!";
	        	success = true;
	        }else {
	        	message="认领物品已被认领,请重新选择！";
	        }
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "认领更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
	
	
	
	
	
	
	
	
	
	
}
