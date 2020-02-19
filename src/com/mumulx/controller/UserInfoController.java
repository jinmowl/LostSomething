package com.mumulx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.mumulx.entity.Area;
import com.mumulx.entity.UserInfo;
import com.mumulx.service.AreaService;
import com.mumulx.service.UserInfoService;
import com.mumulx.util.UserException;

//UserInfo管理控制层
@Controller
@RequestMapping("/UserInfo")
public class UserInfoController extends HelpDevController {

	/* 业务层对象 */
	@Autowired
	@Qualifier("userInfoService")
	UserInfoService userInfoService;
	@Autowired
	@Qualifier("areaService")
	AreaService areaService;
	@InitBinder("areaObj")
	public void initBinderareaObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("areaObj.");
	}

	@InitBinder("userInfo")
	public void initBinderUserInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userInfo.");
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/* 客户端ajax方式提交添加用户信息 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(UserInfo userInfo, BindingResult br, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		// System.out.println(userInfo);
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return;
		}
		if (userInfoService.getUserInfo(userInfo.getUser_name()) != null) {
			message = "学号已经存在！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			userInfo.setUserPhoto(this.handlePhotoUpload(request, "userPhotoFile"));
		} catch (UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return;
		}
		userInfoService.addUserInfo(userInfo);
		message = "用户添加成功!";
		success = true;
		writeJsonResponse(response, success, message);
	}

	// 前台用户登录
	@RequestMapping(value = "/frontLogin", method = RequestMethod.POST)
	public void frontLogin(@RequestParam(value = "login_username", required = true) String userName,
			@RequestParam(value = "login_password", required = true) String password, HttpServletResponse response,
			HttpSession session) throws Exception {
		// System.out.println(userName+""+"password");
		boolean success = true;
		String msg = "";
		if (!userInfoService.checkLogin(userName, password)) {
			msg = userInfoService.getHelpDev().getErrMessage();
			success = false;
		}
		if (success) {
			session.setAttribute("user_name", userName);
			ModelAndView mm = new ModelAndView();
			mm.addObject("user_name", userName);
			// System.out.println("**************"+userName);
		}
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 将要被返回到客户端的对象
		JSONObject json = new JSONObject();
		json.accumulate("success", success);
		json.accumulate("msg", msg);
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/* 前台按照查询条件分页查询用户信息 */
	@RequestMapping(value = { "/frontlist" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String frontlist(String user_name, @ModelAttribute("areaObj") Area areaObj, String name, String birthday,
			String telephone, Integer currentPage, Model model, HttpServletRequest request) throws Exception {
		if (currentPage == null || currentPage == 0)
			currentPage = 1;
		if (user_name == null)
			user_name = "";
		if (name == null)
			name = "";
		if (birthday == null)
			birthday = "";
		if (telephone == null)
			telephone = "";
		// 查询当前页数据
		List<UserInfo> userInfoList = userInfoService.queryUserInfo(user_name, areaObj, name, birthday, telephone,
				currentPage);
		/* 计算总的页数和总的记录数 */
		userInfoService.queryTotalPageAndRecordNumber(user_name, areaObj, name, birthday, telephone);
		/* 获取到总的页码数目 */
		int totalPage = userInfoService.getHelpDev().getTotalPage();
		/* 当前查询条件下总记录数 */
		int recordNumber = userInfoService.getHelpDev().getRecordNumber();
		request.setAttribute("userInfoList", userInfoList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("recordNumber", recordNumber);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("user_name", user_name);
		request.setAttribute("areaObj", areaObj);
		request.setAttribute("name", name);
		request.setAttribute("birthday", birthday);
		request.setAttribute("telephone", telephone);
		List<Area> areaList = areaService.queryAllArea();
		request.setAttribute("areaList", areaList);
		return "UserInfo/userInfo_frontquery_result";
	}

	// 检查验证码
	@RequestMapping(value = { "/checkAuthCode" }, method = RequestMethod.POST)
	public void checkAuthCode(@RequestParam("login_authCode") String checkcodeClient, HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		boolean isTrue = false;
		// 真实的验证码值
		String checkcodeServer = (String) request.getSession().getAttribute("CKECKCODE");
		if (checkcodeServer.equals(checkcodeClient)) {
			isTrue = true;
		}
		json.put("valid", isTrue);
		out.print(json);// 返回json对象
		out.flush();
		out.close();
	}

	/* ajax方式显示用户修改jsp视图页 */
	@RequestMapping(value = "/{user_name}/update", method = RequestMethod.GET)
	public void update(@PathVariable String user_name, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/* 根据主键user_name获取UserInfo对象 */
		UserInfo userInfo = userInfoService.getUserInfo(user_name);
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 将要被返回到客户端的对象
		JSONObject jsonUserInfo = userInfo.getJsonObject();
		out.println(jsonUserInfo.toString());
		out.flush();
		out.close();
	}

	/* ajax方式更新用户信息 */
	@RequestMapping(value = "/{user_name}/update", method = RequestMethod.POST)
	public void update(@Validated UserInfo userInfo, BindingResult br, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String userPhotoFileName = this.handlePhotoUpload(request, "userPhotoFile");
		// 重新选择了头像就赋值
		if (!userPhotoFileName.equals("upload/NoImage.jpg")) {
			// 删除用户头像文件
			String imgSrc = userInfo.getUserPhoto();
			userInfoService.getHelpDev().deleteImgPhoto(imgSrc);
			// 重新赋值用户头像文件
			userInfo.setUserPhoto(userPhotoFileName);
		} else {
			// 没有选择头像，就不修改
			userInfo.setUserPhoto(null);
		}
		try {
			userInfoService.updateUserInfo(userInfo);
			message = "用户更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户更新失败!";
			writeJsonResponse(response, success, message);
		}
	}

	/* 前台查询UserInfo详细信息 */
	@RequestMapping(value = "/{user_name}/frontshow", method = RequestMethod.GET)
	public String frontshow(@PathVariable String user_name, Model model, HttpServletRequest request) throws Exception {
		/* 根据主键user_name获取UserInfo对象 */
		UserInfo userInfo = userInfoService.getUserInfo(user_name);

		List<Area> areaList = areaService.queryAllArea();
		request.setAttribute("areaList", areaList);
		request.setAttribute("userInfo", userInfo);
		return "UserInfo/userInfo_frontshow";
	}

	/* 删除用户信息 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@PathVariable String user_name, HttpServletRequest request)
			throws UnsupportedEncodingException {
		try {
			userInfoService.deleteUserInfo(user_name);
			request.setAttribute("message", "用户删除成功!");
			return "message";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "用户删除失败!");
			return "error";

		}

	}

	/* ajax方式删除多条用户记录 */
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public void delete(String user_names, String userPhotos, HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException {
		String message = "";
		boolean success = false;
		try {
			int count = userInfoService.deleteUserInfos(user_names, userPhotos);
			success = true;
			message = count + "条记录删除成功";
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			// e.printStackTrace();
			message = "有记录存在外键约束,删除失败";
			writeJsonResponse(response, success, message);
		}
	}

	/* ajax方式按照查询条件分页查询用户信息 */
	@RequestMapping(value = { "/listAll" }, method = { RequestMethod.GET, RequestMethod.POST })
	public void listAll(HttpServletResponse response) throws Exception {
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for (UserInfo userInfo : userInfoList) {
			JSONObject jsonUserInfo = new JSONObject();
			jsonUserInfo.accumulate("user_name", userInfo.getUser_name());
			jsonUserInfo.accumulate("name", userInfo.getName());
			jsonArray.put(jsonUserInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

}
