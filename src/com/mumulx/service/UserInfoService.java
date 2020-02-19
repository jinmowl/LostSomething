package com.mumulx.service;
import java.util.ArrayList;
import com.mumulx.entity.Area;
import com.mumulx.entity.UserInfo;
import com.mumulx.util.HelpDev;
public interface UserInfoService {
	
	//返回帮助类
	
	public HelpDev getHelpDev();
	/* 添加用户记录 */
	public void addUserInfo(UserInfo userInfo) throws Exception;

	// 检查用户信息
	public Boolean checkLogin(String userName, String password);

	/* 按照查询条件分页查询用户记录 */
	public ArrayList<UserInfo> queryUserInfo(String user_name, Area areaObj, String name, String birthday,
			String telephone, int currentPage) throws Exception;

	/* 按照查询条件查询所有记录 */
	public ArrayList<UserInfo> queryUserInfo(String user_name, Area areaObj, String name, String birthday,
			String telephone) throws Exception;

	/* 查询所有用户记录 */
	public ArrayList<UserInfo> queryAllUserInfo() throws Exception;

	/* 当前查询条件下计算总的页数和记录数 */
	public void queryTotalPageAndRecordNumber(String user_name, Area areaObj, String name, String birthday,
			String telephone) throws Exception;

	/* 根据主键获取用户记录 */
	public UserInfo getUserInfo(String user_name) throws Exception;

	/* 更新用户记录 */
	public void updateUserInfo(UserInfo userInfo) throws Exception;

	/* 删除一条用户记录 */
	public void deleteUserInfo(String user_name) throws Exception;

	/* 删除多条用户信息 */
	public int deleteUserInfos(String user_names,String userPhotos) throws Exception;
}
