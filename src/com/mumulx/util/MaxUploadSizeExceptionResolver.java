package com.mumulx.util;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
@Component
public class MaxUploadSizeExceptionResolver extends SimpleMappingExceptionResolver {
	private static Logger log = Logger.getLogger(MaxUploadSizeExceptionResolver.class);
	public static final String UTF_8 = "utf-8";
	public static final String ACCEPT = "accept";
	public static final String APPLICATION_JSON = "application/json";
	public static final String X_REQUESTED_WITH = "X-Requested-With";
	public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
	/**
	 * 重写Spring统一异常处理方法
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView("error");
		//文件太大异常
		if(MaxUploadSizeExceededException.class.isInstance(ex)) {
			response.setContentType("text/json;charset=UTF-8");
			try {
				PrintWriter out = response.getWriter(); 
				//将要被返回到客户端的对象 
				JSONObject json=new JSONObject();
				boolean success=false;
				String message="图片太大！";
				json.accumulate("success", success);
				json.accumulate("message", message);
				out.println(json.toString());
				out.flush(); 
				out.close();
				return null;
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}else {
			mv.addObject("exception",ex.toString());
		}
		return mv;
	}

}
