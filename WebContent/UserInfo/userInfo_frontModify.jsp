<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.mumulx.entity.UserInfo" %>
<%@ page import="com.mumulx.entity.Area" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的areaObj信息
    List<Area> areaList = (List<Area>)request.getAttribute("areaList");
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");
	
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改用户信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
  <link rel="shortcut icon" href="<%=basePath %>favicon.ico" >
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="../<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">用户信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="userInfoEditForm" id="userInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="userInfo_user_name_edit" class="col-md-3 text-right">学号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="userInfo_user_name_edit" name="userInfo.user_name" class="form-control" placeholder="请输入学号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="userInfo_password_edit" class="col-md-3 text-right">登录密码:</label>
		  	 <div class="col-md-9">
			    <input type="password" id="userInfo_password_edit" name="userInfo.password" class="form-control" placeholder="请输入登录密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_areaObj_areaId_edit" class="col-md-3 text-right">所在学院:</label>
		  	 <div class="col-md-9">
			    <select id="userInfo_areaObj_areaId_edit" name="userInfo.areaObj.areaId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_name_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_name_edit" name="userInfo.name" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_sex_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_sex_edit" name="userInfo.sex" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_userPhoto_edit" class="col-md-3 text-right">学生照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="userInfo_userPhotoImg_edit" border="0px"/><br/>
			    <input type="hidden" id="userInfo_userPhoto_edit" name="userInfo.userPhoto"/>
			    <input id="userPhotoFile" name="userPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_birthday_edit" class="col-md-3 text-right">出生日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date userInfo_birthday_edit col-md-12" data-link-field="userInfo_birthday_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="userInfo_birthday_edit" name="userInfo.birthday" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_telephone_edit" name="userInfo.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userInfo_address_edit" class="col-md-3 text-right">家庭地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userInfo_address_edit" name="userInfo.address" class="form-control" placeholder="请输入家庭地址">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxUserInfoModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#userInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath%>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>

//用户选择图片后更新img地址
$(document).ready(function() {
	$("#userPhotoFile").change(function(){
		var file = this.files[0];//获取文件信息
        if (window.FileReader)
        {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            //监听文件读取结束后事件    
            reader.onloadend = function (e) {
                $("#userInfo_userPhotoImg_edit").attr("src",e.target.result);
            };
        }
	});
});
var basePath = "<%=basePath%>";
/*弹出修改用户界面并初始化数据*/
function userInfoEdit(user_name) {
	$.ajax({
		url :  basePath + "UserInfo/" + user_name + "/update",
		type : "get",
		dataType: "json",
		success : function (userInfo, response, status) {
			if (userInfo) {
				$("#userInfo_user_name_edit").val(userInfo.user_name);
				//$("#userInfo_password_edit").val(userInfo.password);
				$.ajax({
					url: basePath + "Area/listAll",
					type: "get",
					success: function(areas,response,status) { 
						$("#userInfo_areaObj_areaId_edit").empty();
						var html="";
		        		$(areas).each(function(i,area){
		        			html += "<option value='" + area.areaId + "'>" + area.areaName + "</option>";
		        		});
		        		$("#userInfo_areaObj_areaId_edit").html(html);
		        		$("#userInfo_areaObj_areaId_edit").val(userInfo.areaObjPri);
					}
				});
				$("#userInfo_name_edit").val(userInfo.name);
				$("#userInfo_sex_edit").val(userInfo.sex);
				$("#userInfo_userPhoto_edit").val(userInfo.userPhoto);
				$("#userInfo_userPhotoImg_edit").attr("src", basePath +userInfo.userPhoto);
				$("#userInfo_birthday_edit").val(userInfo.birthday);
				$("#userInfo_telephone_edit").val(userInfo.telephone);
				$("#userInfo_address_edit").val(userInfo.address);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交用户信息表单给服务器端修改*/
function ajaxUserInfoModify() {
	//提交之前先验证表单
	$("#userInfoEditForm").data('bootstrapValidator').validate();
	if(!$("#userInfoEditForm").data('bootstrapValidator').isValid()){
		return;
	}
	$.ajax({
		url :  basePath + "UserInfo/" + $("#userInfo_user_name_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#userInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#userInfoQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    /*出生日期组件*/
    $('.userInfo_birthday_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    }).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#userInfoEditForm').data('bootstrapValidator').updateStatus('userInfo.birthday', 'NOT_VALIDATED',null).validateField('userInfo.birthday');
	});
    
    userInfoEdit("<%=session.getAttribute("user_name")%>");
 })

 $(function() {
		
	//验证用户添加表单字段
	$('#userInfoEditForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			"userInfo.password" : {
				validators : {
					regexp : {
						regexp : /^(\w){6,20}$/,
						message : '密码只能由6-20个字母、数字、下划线组成'
					}
				}
			},
			"userInfo.name" : {
				validators : {
					notEmpty : {
						message : "姓名不能为空",
					},
					regexp : {
						regexp : /^[\u4E00-\u9FA5A-Za-z0-9]+$/,
						message : '只能输入中文和英文和数字'
					}
				}
			},
			"userInfo.sex" : {
				validators : {
					notEmpty : {
						message : "性别不能为空",
					},
					regexp : {
						regexp : /^(男|女)$/,
						message : '性别只能为男或女'
					}
				}
			},
			
			"userInfo.telephone" : {
				validators : {
					notEmpty : {
						message : "联系电话不能为空",
					},
					regexp : {
						regexp : /^1(3|4|5|6|7|8|9)\d{9}$/,
						message : '电话格式不正确'
					}
				}
			},
			"userInfo.address" : {
				validators : {
					notEmpty : {
						message : "家庭地址不能为空",
					}
				}
			},
			"userInfo.birthday": {
				validators : {
					notEmpty : {
						message : "出生日期不能为空",
					}
				}
			}
		}
	});
});

 
 
 
 
 
 
 </script> 
</body>
</html>

