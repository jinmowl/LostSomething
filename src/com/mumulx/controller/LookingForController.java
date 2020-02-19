                                  package com.mumulx.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.mumulx.entity.LookingFor;
import com.mumulx.entity.UserInfo;
import com.mumulx.service.LookingForService;
import com.mumulx.service.UserInfoService;
import com.mumulx.util.UserException;

//LookingFor管理控制层
@Controller
@RequestMapping("/LookingFor")
public class LookingForController extends HelpDevController {
	@Autowired
	@Qualifier("lookingForService")
    LookingForService lookingForService;
	@Resource(name = "userInfoService")
    UserInfoService userInfoService;
	public LookingForService getLookingForService() {
		return lookingForService;
	}
	public void setLookingForService(LookingForService lookingForService) {
		this.lookingForService = lookingForService;
	}
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("lookingFor")
	public void initBinderLookingFor(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("lookingFor.");
	}
	/*前台按照查询条件分页查询寻物启事信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,String goodsName,String lostTime,String lostPlace,String telephone,@ModelAttribute("userObj") UserInfo userObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (goodsName == null) goodsName = "";
		if (lostTime == null) lostTime = "";
		if (lostPlace == null) lostPlace = "";
		if (telephone == null) telephone = "";
		// 查询满足查询条件当前页的所有数据
		List<LookingFor> lookingForList = lookingForService.queryLookingFor(title, goodsName, lostTime, lostPlace, telephone, userObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    lookingForService.queryTotalPageAndRecordNumber(title, goodsName, lostTime, lostPlace, telephone, userObj);
	    /*获取到总的页码数目*/
	    int totalPage = lookingForService.getHelpDev().getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = lookingForService.getHelpDev().getRecordNumber();
	    request.setAttribute("lookingForList",  lookingForList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("goodsName", goodsName);
	    request.setAttribute("lostTime", lostTime);
	    request.setAttribute("lostPlace", lostPlace);
	    request.setAttribute("telephone", telephone);
	    request.setAttribute("userObj", userObj);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "LookingFor/lookingFor_frontquery_result"; 
	}

	/*客户端ajax方式提交添加寻物启事信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LookingFor lookingFor, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			lookingFor.setGoodsPhoto(this.handlePhotoUpload(request, "goodsPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        lookingForService.addLookingFor(lookingFor);
        message = "寻物启事添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*前台查询LookingFor信息*/
	@RequestMapping(value="/{lookingForId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer lookingForId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键lookingForId获取LookingFor对象*/
        LookingFor lookingFor = lookingForService.getLookingFor(lookingForId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("lookingFor",  lookingFor);
        return "LookingFor/lookingFor_frontshow";
	}
	/*ajax方式显示寻物启事修改jsp视图页*/
	@RequestMapping(value="/{lookingForId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer lookingForId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键lookingForId获取LookingFor对象*/
        LookingFor lookingFor = lookingForService.getLookingFor(lookingForId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLookingFor = lookingFor.getJsonObject();
		out.println(jsonLookingFor.toString());
		out.flush();
		out.close();
	}
	/*ajax方式更新寻物启事信息*/
	@RequestMapping(value = "/{lookingForId}/update", method = RequestMethod.POST)
	public void update(@Validated LookingFor lookingFor, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String goodsPhotoFileName = this.handlePhotoUpload(request, "goodsPhotoFile");
		//重新选择了头像就赋值
		if(!goodsPhotoFileName.equals("upload\\NoImage.jpg")) {
			//删除用户头像文件
			String imgSrc = lookingFor.getGoodsPhoto();
			lookingForService.getHelpDev().deleteImgPhoto(imgSrc);
			//重新赋值用户头像文件
			lookingFor.setGoodsPhoto(goodsPhotoFileName); 
		}else {
			//没有选择头像，就不修改
			lookingFor.setGoodsPhoto(null); 
		}
		try {
			lookingForService.updateLookingFor(lookingFor);
			message = "寻物启事更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "寻物启事更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
	/*ajax方式删除多条寻物启事记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String lookingForIds,String goodsPhotos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = lookingForService.deleteLookingFors(lookingForIds,goodsPhotos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	
	
	
	
	

}
