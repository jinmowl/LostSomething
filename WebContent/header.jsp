<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!--导航开始-->
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<!--小屏幕导航按钮和logo-->
		<div class="navbar-header">
			<button class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="<%=basePath%>index.jsp" class="navbar-brand">失物招领</a>
		</div>
		<!--小屏幕导航按钮和logo-->
		<!--导航-->
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-left">
				<li><a href="<%=basePath%>index.jsp">首页</a></li>
				<li><a href="<%=basePath%>UserInfo/frontlist">用户</a></li>
				<li><a href="<%=basePath%>Area/frontlist">学院</a></li>
				<li><a href="<%=basePath%>LookingFor/frontlist">寻物启事</a></li>
				<li><a href="<%=basePath%>LostFound/frontlist">失物招领</a></li>
				<li><a href="<%=basePath%>Claim/frontlist">认领</a></li>
				<li><a href="<%=basePath%>Praise/frontlist">表扬</a></li>
				<li><a href="<%=basePath%>Notice/frontlist">站内通知</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%
					String user_name = (String) session.getAttribute("user_name");
					if (user_name == null) {
				%>
				<li><a href="#" onclick="register();"><i
						class="fa fa-sign-in"></i>&nbsp;&nbsp;注册</a></li>
				<li><a href="#" onclick="login();"><i class="fa fa-user"></i>&nbsp;&nbsp;登录</a></li>

				<%
					} else {
				%>
				<li class="dropdown"><a id="dLabel" type="button"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<%=session.getAttribute("user_name")%> <span class="caret"></span>
				</a>
					<ul class="dropdown-menu" aria-labelledby="dLabel">
						<li><a href="<%=basePath%>index.jsp"><span
								class="glyphicon glyphicon-screenshot"></span>&nbsp;&nbsp;首页</a></li>
						<li><a href="<%=basePath%>LookingFor/lookingFor_frontAdd.jsp"><span
								class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;添加寻物启事</a></li>
						<li><a href="<%=basePath%>LostFound/lostFound_frontAdd.jsp"><span
								class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;添加失物招领</a></li>
						<li><a href="<%=basePath%>UserInfo/userInfo_frontModify.jsp"><span
								class="glyphicon glyphicon-credit-card"></span>&nbsp;&nbsp;修改个人资料</a></li>
						<li><a href="<%=basePath%>index.jsp"><span
								class="glyphicon glyphicon-heart"></span>&nbsp;&nbsp;我的收藏</a></li>
					</ul></li>
				<li><a href="<%=basePath%>logout.jsp"><span
						class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;退出</a></li>
				<%
					}
				%>
			</ul>

		</div>
		<!--导航-->
	</div>
</nav>
<!--导航结束-->


<div id="loginDialog" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<i class="fa fa-key"></i>&nbsp;系统登录
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="loginForm" id="loginForm"
					enctype="multipart/form-data" method="post" class="mar_t15">

					<div class="form-group">
						<label for="userInfo_user_name" class="col-md-3 text-right">学号:</label>
						<div class="col-md-8">
							<input type="text" id="login_username" name="login_username"
								class="form-control" placeholder="请输入学号">
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="col-md-3 text-right">登录密码:</label>
						<div class="col-md-8">
							<input type="password" id="login_password" name="login_password"
								class="form-control" placeholder="请输入登录密码">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-md-3 text-right">验证码</label>
						<div class="col-md-8">
						
						<div class="input-group" style="z-index: 9999">
					  			<input type="text" class="form-control" placeholder="请输入验证码" aria-describedby="basic-addon2" id="login_authCode" name="login_authCode">
					  			
					  			<span class="input-group-addon" style="padding: 0 0" id="basic-addon2"><a href="javascript:reloadCheckImg();" onclick="reloadCheckImg()"> <img  id="login_authCode_img" src="<%=basePath%>/authCode.jsp"/></a></span>
					  			
						</div>
					
						</div>
					</div>
				</form>
				<style>
#bookTypeAddForm .form-group {
	margin-bottom: 10px;
}
</style>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="ajaxLogin();">登录</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<div id="registerDialog" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<i class="fa fa-sign-in"></i>&nbsp;用户注册
				</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="registerForm" id="registerForm"
					enctype="multipart/form-data" method="post" class="mar_t15">

					<div class="form-group">
						<label for="userInfo_user_name" class="col-md-3 text-right">学号:</label>
						<div class="col-md-8">
							<input type="text" id="userInfo_user_name"
								name="userInfo.user_name" class="form-control"
								placeholder="请输入学号">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-md-3 text-right">登录密码:</label>
						<div class="col-md-8">
							<input type="password" id="password" name="userInfo.password"
								class="form-control" placeholder="请输入登录密码">
						</div>
					</div>
					<div class="form-group">
						<label for="userInfo_areaObj_areaId" class="col-md-3 text-right">所在学院:</label>
						<div class="col-md-8">
							<select id="userInfo_areaObj_areaId"
								name="userInfo.areaObj.areaId" class="form-control">
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="userInfo_name" class="col-md-3 text-right">姓名:</label>
						<div class="col-md-8">
							<input type="text" id="userInfo_name" name="userInfo.name"
								class="form-control" placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group">
						<label for="userInfo_sex" class="col-md-3 text-right">性别:</label>
						<div class="col-md-8">
							<input type="text" id="userInfo_sex" name="userInfo.sex"
								class="form-control" placeholder="请输入性别">
						</div>
					</div>

					<div class="form-group">
						<label for="userInfo_userPhoto_header" class="col-md-3 text-right">学生照片:</label>
						<div class="col-md-8">
							<img class="img-responsive" id="userInfo_userPhotoImg"
								border="0px" /><br /> <input type="hidden"
								id="userInfo_userPhoto_header" name="userInfo.userPhoto" /> 
								
								<input id="userPhotoFile_header" name="userPhotoFile" type="file" size="50" />
						</div>
					</div>
					<div class="form-group">
						<label for="userInfo_birthdayDiv" class="col-md-3 text-right">出生日期:</label>
						<div class="col-md-8">
							<div id="userInfo_birthdayDiv"
								class="input-group date userInfo_birthday col-md-12"
								data-link-field="userInfo_birthday"
								data-link-format="yyyy-mm-dd">
								<input class="form-control" id="userInfo_birthday"
									name="userInfo.birthday" size="16" type="text" value=""
									placeholder="请选择出生日期" readonly> 
									
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-remove">
										</span>
									</span> 
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar">
										</span>
									</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="userInfo_telephone" class="col-md-3 text-right">联系电话:</label>
						<div class="col-md-8">
							<input type="text" id="userInfo_telephone"
								name="userInfo.telephone" class="form-control"
								placeholder="请输入联系电话">
						</div>
					</div>
					<div class="form-group">
						<label for="userInfo_address" class="col-md-3 text-right">家庭地址:</label>
						<div class="col-md-8">
							<input type="text" id="userInfo_address" name="userInfo.address"
								class="form-control" placeholder="请输入家庭地址">
						</div>
					</div>
				</form>
				<style>
					#bookTypeAddForm .form-group {
						margin-bottom: 10px;
					}
				</style>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary"
					onclick="ajaxRegister();">注册</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath%>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>

<script>
//用户选择图片后更新img地址
$(document).ready(function() {
	$("#userPhotoFile_header").change(function(){
		var file = this.files[0];//获取文件信息
        if (window.FileReader)
        {
            var reader = new FileReader();
            reader.readAsDataURL(file);
            //监听文件读取结束后事件    
            reader.onloadend = function (e) {
                $("#userInfo_userPhotoImg").attr("src",e.target.result);
            };
        }
	});
});
/*小屏幕导航点击关闭菜单*/
$('.navbar-collapse a').click(function(){
	$(this).css("background","lightgreen");
    $('.navbar-collapse').collapse('hide');
});
new WOW().init();

var basePath = "<%=basePath%>";

function reloadCheckImg()
{
    $("#login_authCode_img").attr("src", basePath+"/authCode.jsp?t="+(new Date().getTime()));  //<img src="...">
}
function register() {
	$("#registerDialog input").val("");
	$("#registerDialog textarea").val("");
	$("#registerDialog img").attr("src","");
	$('#registerDialog').modal('show');
}


//提交添加用户信息
function ajaxRegister() { 
	//提交之前先验证表单
	$("#registerForm").data('bootstrapValidator').validate();
	if(!$("#registerForm").data('bootstrapValidator').isValid()){
		return;
	}
	
/* 	var formData = new FormData($("#registerForm")[0]);
	for (var value of formData) {
	    console.log(value);
	 }  */
	jQuery.ajax({
		type : "post",
		url : basePath + "UserInfo/add",
		dataType : "json" ,
		data: new FormData($("#registerForm")[0]) ,  
		processData: false,   // jQuery不要去处理发送的数据
		contentType: false,
		success : function(obj) {                                                                                                                                 
			if(obj.success){ 
				alert("保存成功！");
				$("#registerForm").find("input").val("");
				$("#registerForm").find("textarea").val("");
				$('#registerDialog').modal('hide');
				window.location.href="<%=basePath%>index.jsp";
				
			} else {
				alert(obj.message);
			}
		}
	});
} 
function login() {
	$("#loginDialog input").val("");
	$('#loginDialog').modal('show');
}
function ajaxLogin() {
	$.ajax({
		url : basePath + "UserInfo/frontLogin",
		type : 'post',
		dataType: "json",
		data : {
			"login_username" : $('#login_username').val(),
			"login_password" : $('#login_password').val(),
		}, 
		success : function (obj, response, status) {
			if (obj.success) {
				
				location.href = "<%=basePath%>index.jsp";
				} else {
					alert(obj.msg);
				}
			}
		});
	}
	$(function() {
		
		//验证用户添加表单字段
		$('#registerForm').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				"userInfo.user_name" : {
					validators : {
						notEmpty : {
							message : "学号不能为空",
						},
						regexp : {
							regexp : /^[0-9]{10}$/,
							message : '学号是长度为10的数字'
						}
					}
				},
				"userInfo.password" : {
					validators : {
						notEmpty : {
							message : "登录密码不能为空",
						},
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
				
				"userInfo.birthday" : {
					validators : {
						notEmpty : {
							message : "出生日期不能为空",
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
			}
		});
		//验证登录输入loginDialog
		$('#loginDialog').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				"login_username" : {
					validators : {
						notEmpty : {
							message : "学号不能为空",
						},
						regexp : {
							regexp : /^[0-9]{10}$/,
							message : '学号是长度为10的数字'
						}
					}
				},
				"login_password" : {
					validators : {
						notEmpty : {
							message : "登录密码不能为空",
						},
						regexp : {
							regexp : /^(\w){6,20}$/,
							message : '密码只能由6-20个字母、数字、下划线组成'
						}
					}
				},
				"login_authCode" : {
					validators : {
						notEmpty : {
							message : "验证码不能为空",
						},stringLength: {
                            min: 4,
                            max: 4,
                            message: '请输入四位验证码'
                        },
						remote: {
							//发起Ajax请求。
	                         url: "<%=basePath%>UserInfo/checkAuthCode",//验证地址
	                         data:{checkcode:$('input[name="login_authCode"]').val() },
	                         message: '验证码错误',//提示消息
	                         delay :  1000,//设置1秒发起名字验证
	                         type: 'POST' //请求方式
	                     }
					}
				}
			}
		}); 
		
		//初始化所在学院下拉框值 
		$.ajax({
			url : basePath + "Area/listAll",
			type : "get",
			success : function(areas, response, status) {
				$("#userInfo_areaObj_areaId").empty();
				var html = "";
				$(areas).each(
						function(i, area) {
							html += "<option value='" + area.areaId + "'>"
									+ area.areaName + "</option>";
						});
				$("#userInfo_areaObj_areaId").html(html);
			}
		});
		//出生日期组件
		$('#userInfo_birthdayDiv').datetimepicker({
			language:  'zh-CN',  //显示语言
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
			$('#registerForm').data('bootstrapValidator').updateStatus('userInfo.birthday', 'NOT_VALIDATED',null).validateField('userInfo.birthday');
		});
	});
</script>


