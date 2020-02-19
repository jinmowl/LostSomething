package com.mumulx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mumulx.entity.Area;
import com.mumulx.service.AreaService;

//Area管理控制层
@Controller
@RequestMapping("/Area")
public class AreaController extends HelpDevController{
	@Autowired
	@Qualifier("areaService")
	AreaService areaService;
	
	@InitBinder("area")
	public void initBinderArea(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("area.");
	}
	
	/*ajax方式按照查询条件分页查询学院信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Area> areaList = areaService.queryAllArea();
        
		response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Area area:areaList) {
			JSONObject jsonArea = new JSONObject();
			jsonArea.accumulate("areaId", area.getAreaId());
			jsonArea.accumulate("areaName", area.getAreaName());
			jsonArray.put(jsonArea);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}
	
	
	/*前台按照查询条件分页查询学院信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage,Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<Area> areaList = areaService.queryArea(currentPage);
	    /*计算总的页数和总的记录数*/
	    areaService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = areaService.getHelpDev().getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = areaService.getHelpDev().getRecordNumber();
	    request.setAttribute("areaList",  areaList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "Area/area_frontquery_result";
	}
	
    /*前台查询Area信息*/
	@RequestMapping(value="/{areaId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer areaId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键areaId获取Area对象*/
       Area area = areaService.getArea(areaId);
       request.setAttribute("area",  area);
       return "Area/area_frontshow";
	}
	
	/*ajax方式显示学院修改jsp视图页*/
	@RequestMapping(value="/{areaId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer areaId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键areaId获取Area对象*/
        Area area = areaService.getArea(areaId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonArea = area.getJsonObject();
		out.println(jsonArea.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新学院信息*/
	@RequestMapping(value = "/{areaId}/update", method = RequestMethod.POST)
	public void update(@Validated Area area, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			Boolean updateArea = areaService.updateArea(area);
			if(!updateArea) {
				message = "学院已存在！";
				writeJsonResponse(response, success, message);
				return;
			}
			message = "学院更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "学院更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除学院信息*/
	@RequestMapping(value="/{areaId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer areaId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  areaService.deleteArea(areaId);
	            request.setAttribute("message", "学院删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "学院删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条学院记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String areaIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = areaService.deleteAreas(areaIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}
	/*客户端ajax方式提交添加学院信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Area area, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        Boolean addArea = areaService.addArea(area);
        if(!addArea) {
        	message = "学院已存在！";
			writeJsonResponse(response, success, message);
			return ;
        }
        message = "学院添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}

}
