package com.mumulx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import com.mumulx.entity.Notice;
import com.mumulx.service.NoticeService;
//Notice管理控制层
@Controller
@RequestMapping("/Notice")
public class NoticeController extends HelpDevController {

    /*业务层对象*/
    @Resource (name = "noticeService")
    NoticeService noticeService;
	public NoticeService getNoticeService() {
		return noticeService;
	}
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	@InitBinder("notice")
	public void initBinderNotice(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("notice.");
	}
	/*跳转到添加Notice视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Notice());
		return "Notice_add";
	}

	/*客户端ajax方式提交添加站内通知信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Notice notice, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        noticeService.addNotice(notice);
        message = "站内通知添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*ajax方式按照查询条件分页查询站内通知信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Notice> noticeList = noticeService.queryAllNotice();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Notice notice:noticeList) {
			JSONObject jsonNotice = new JSONObject();
			jsonNotice.accumulate("noticeId", notice.getNoticeId());
			jsonNotice.accumulate("title", notice.getTitle());
			jsonArray.put(jsonNotice);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询站内通知信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		List<Notice> noticeList = noticeService.queryNotice(title, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    noticeService.queryTotalPageAndRecordNumber(title, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = noticeService.getHelpDev().getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = noticeService.getHelpDev().getRecordNumber();
	    request.setAttribute("noticeList",  noticeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("addTime", addTime);
		return "Notice/notice_frontquery_result"; 
	}

     /*前台查询Notice信息*/
	@RequestMapping(value="/{noticeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer noticeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键noticeId获取Notice对象*/
        Notice notice = noticeService.getNotice(noticeId);

        request.setAttribute("notice",  notice);
        return "Notice/notice_frontshow";
	}

	/*ajax方式显示站内通知修改jsp视图页*/
	@RequestMapping(value="/{noticeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer noticeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键noticeId获取Notice对象*/
        Notice notice = noticeService.getNotice(noticeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonNotice = notice.getJsonObject();
		out.println(jsonNotice.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新站内通知信息*/
	@RequestMapping(value = "/{noticeId}/update", method = RequestMethod.POST)
	public void update(@Validated Notice notice, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			noticeService.updateNotice(notice);
			message = "站内通知更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "站内通知更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除站内通知信息*/
	@RequestMapping(value="/{noticeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer noticeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  noticeService.deleteNotice(noticeId);
	            request.setAttribute("message", "站内通知删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "站内通知删除失败!");
				return "error";
	        }
	}
	/*ajax方式删除多条站内通知记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String noticeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = noticeService.deleteNotices(noticeIds);
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
