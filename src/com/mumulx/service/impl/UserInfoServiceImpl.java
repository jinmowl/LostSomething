package com.mumulx.service.impl;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mumulx.entity.Area;
import com.mumulx.entity.UserInfo;
import com.mumulx.mapper.UserInfoMapper;
import com.mumulx.service.UserInfoService;
import com.mumulx.util.HelpDev;
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	public UserInfoMapper getUserinfoMapper() {
		return userInfoMapper;
	}
	public void setUserinfoMapper(UserInfoMapper userinfoMapper) {
		this.userInfoMapper = userinfoMapper;
	}
	@Autowired
	private HelpDev helpDev;
	public HelpDev getHelpDev() {
		return helpDev;
	}
	public void setHelpDev(HelpDev helpDev) {
		this.helpDev = helpDev;
	}
	//注册，添加用户
	@Override
	public void addUserInfo(UserInfo userInfo) throws Exception {
		// TODO Auto-generated method stub
		//对密码进行加密
		String pwdMD5 = helpDev.md5Encode(userInfo.getPassword().getBytes());
		userInfo.setPassword(pwdMD5);
		userInfoMapper.addUserInfo(userInfo);
	}
	
	//检查用户信息
    public Boolean checkLogin(String userName,String password) {
    	String pw = userInfoMapper.checkLogin(userName);
    	//进行加密
    	String pwdMD5 = helpDev.md5Encode(password.getBytes());
    	if(pw==null) {
    		//用户不存在
    		helpDev.setErrMessage("用户不存在");
    		return false;
    	}else if(!pw.equals(pwdMD5)) {
    		//密码错误
    		helpDev.setErrMessage("密码错误");
    		return false;
    	}
    	return true;
    }
    
    /*按照查询条件分页查询用户记录*/
    public ArrayList<UserInfo> queryUserInfo(String user_name,Area areaObj,String name,String birthday,String telephone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!user_name.equals("")) where = where + " and t_userInfo.user_name like '%" + user_name + "%'";
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_userInfo.areaObj=" + areaObj.getAreaId();
    	if(!name.equals("")) where = where + " and t_userInfo.name like '%" + name + "%'";
    	if(!birthday.equals("")) where = where + " and t_userInfo.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) where = where + " and t_userInfo.telephone like '%" + telephone + "%'";
    	int startIndex = (currentPage-1) * this.helpDev.getRows();
    	return userInfoMapper.queryUserInfo(where, startIndex, this.helpDev.getRows());
    }

	@Override
	public ArrayList<UserInfo> queryUserInfo(String user_name, Area areaObj, String name, String birthday,
			String telephone) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    /*查询所有用户记录*/
    public ArrayList<UserInfo> queryAllUserInfo()  throws Exception {
        return userInfoMapper.queryUserInfoList("where 1=1");
    }
	 /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String user_name,Area areaObj,String name,String birthday,String telephone) throws Exception {
     	String where = "where 1=1";
    	if(!user_name.equals("")) where = where + " and t_userInfo.user_name like '%" + user_name + "%'";
    	if(null != areaObj && areaObj.getAreaId()!= null && areaObj.getAreaId()!= 0)  where += " and t_userInfo.areaObj=" + areaObj.getAreaId();
    	if(!name.equals("")) where = where + " and t_userInfo.name like '%" + name + "%'";
    	if(!birthday.equals("")) where = where + " and t_userInfo.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) where = where + " and t_userInfo.telephone like '%" + telephone + "%'";
        this.helpDev.computeTotalPag(userInfoMapper.queryUserInfoCount(where));
    }
	/*根据主键获取用户记录*/
    public UserInfo getUserInfo(String user_name) throws Exception  {
        UserInfo userInfo = userInfoMapper.getUserInfo(user_name);
        return userInfo;
    }
    /*更新用户记录*/
    public void updateUserInfo(UserInfo userInfo) throws Exception {
    	String pwd = userInfo.getPassword();
    	//用户对密码进行了修改，进行加密
    	if(pwd!=null && !pwd.equals("")) {
    		pwd=helpDev.md5Encode(pwd.getBytes());
    		//userInfoMapper.updateUserInfo(userInfo);
    	}else {
    		//没进行密码修改给密码赋默认值，方便mybatis进行更新
    		pwd=null;
    	}
    	userInfo.setPassword(pwd);
        //userInfoMapper.updateUserInfoNoPwd(userInfo);
    	userInfoMapper.updateUserInfo(userInfo);
    }

    /*删除一条用户记录*/
    public void deleteUserInfo (String user_name) throws Exception {
        userInfoMapper.deleteUserInfo(user_name);
    }

    /*删除多条用户信息*/
    public int deleteUserInfos (String user_names,String userPhotos) throws Exception {
    	String _user_names[] = user_names.split(",");
    	String _userPhotos[] = userPhotos.split(",");
    	int i=0;
    	for(String _user_name: _user_names) {
    		String imgSrc = _userPhotos[i];
    		i++;
    		//删除图片
    		this.helpDev.deleteImgPhoto(imgSrc);
    		userInfoMapper.deleteUserInfo(_user_name);
    	}
    	return _user_names.length;
    }

}
