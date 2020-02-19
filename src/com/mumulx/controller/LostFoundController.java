package com.mumulx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mumulx.entity.LookingFor;
import com.mumulx.entity.LostFound;
import com.mumulx.service.LostFoundService;
import com.mumulx.util.UserException;
//LostFound管理控制层
@Controller
@RequestMapping("/LostFound")
public class LostFoundController extends HelpDevController {

    /*业务层对象*/
    @Resource(name="lostFoundService")
    LostFoundService lostFoundService;

	public LostFoundService getLostFoundService() {
		return lostFoundService;
	}

	public void setLostFoundService(LostFoundService lostFoundService) {
		this.lostFoundService = lostFoundService;
	}

	@InitBinder("lostFound")
	public void initBinderLostFound(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("lostFound.");
	}
	
	/*客户端ajax方式提交添加失物招领信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LostFound lostFound, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			lostFound.setGoodsPhoto(this.handlePhotoUpload(request, "goodsPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        lostFoundService.addLostFound(lostFound);
        message = "失物招领添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*前台按照查询条件分页查询失物招领信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,String goodsName,String pickUpTime,String pickUpPlace,String connectPerson,String phone,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (goodsName == null) goodsName = "";
		if (pickUpTime == null) pickUpTime = "";
		if (pickUpPlace == null) pickUpPlace = "";
		if (connectPerson == null) connectPerson = "";
		if (phone == null) phone = "";
		List<LostFound> lostFoundList = lostFoundService.queryLostFound(title, goodsName, pickUpTime, pickUpPlace, connectPerson, phone, currentPage);
	    /*计算总的页数和总的记录数*/
	    lostFoundService.queryTotalPageAndRecordNumber(title, goodsName, pickUpTime, pickUpPlace, connectPerson, phone);
	    /*获取到总的页码数目*/
	    int totalPage = lostFoundService.getHelpDev().getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = lostFoundService.getHelpDev().getRecordNumber();
	    request.setAttribute("lostFoundList",  lostFoundList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("goodsName", goodsName);
	    request.setAttribute("pickUpTime", pickUpTime);
	    request.setAttribute("pickUpPlace", pickUpPlace);
	    request.setAttribute("connectPerson", connectPerson);
	    request.setAttribute("phone", phone);
		return "LostFound/lostFound_frontquery_result"; 
	}
	/*前台查询LostFound信息*/
	@RequestMapping(value="/{lostFoundId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer lostFoundId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键lostFoundId获取LostFound对象*/
        LostFound lostFound = lostFoundService.getLostFound(lostFoundId);

        request.setAttribute("lostFound",  lostFound);
        return "LostFound/lostFound_frontshow";
	}
	/*ajax方式删除多条失物招领记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String lostFoundIds,String goodsPhotos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = lostFoundService.deleteLostFounds(lostFoundIds,goodsPhotos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}
	/*ajax方式显示失物招领修改jsp视图页*/
	@RequestMapping(value="/{lostFoundId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer lostFoundId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键lostFoundId获取LostFound对象*/
        LostFound lostFound = lostFoundService.getLostFound(lostFoundId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLostFound = lostFound.getJsonObject();
		out.println(jsonLostFound.toString());
		out.flush();
		out.close();
	}
	/*ajax方式更新失物招领信息*/
	@RequestMapping(value = "/{lostFoundId}/update", method = RequestMethod.POST)
	public void update(@Validated LostFound lostFound, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String photoFileName = this.handlePhotoUpload(request, "goodsPhotoFile");
		//重新选择了头像就赋值
		if(!photoFileName.equals("upload/NoImage.jpg")) {
			//删除用户头像文件
			String imgSrc = lostFound.getGoodsPhoto();
			lostFoundService.getHelpDev().deleteImgPhoto(imgSrc);
			//重新赋值用户头像文件
			lostFound.setGoodsPhoto(photoFileName); 
		}else {
			//没有选择头像，就不修改
			lostFound.setGoodsPhoto(null); 
		}
		try {
			lostFoundService.updateLostFound(lostFound);
			message = "失物招领更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "失物招领更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
	/*ajax方式按照查询条件分页查询失物招领信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LostFound> lostFoundList = lostFoundService.queryAllLostFound();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LostFound lostFound:lostFoundList) {
			JSONObject jsonLostFound = new JSONObject();
			jsonLostFound.accumulate("lostFoundId", lostFound.getLostFoundId());
			jsonLostFound.accumulate("title", lostFound.getTitle());
			jsonArray.put(jsonLostFound);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}


}
