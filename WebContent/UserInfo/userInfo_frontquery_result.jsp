<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8"%>
<%@ page import="com.mumulx.entity.UserInfo"%>
<%@ page import="com.mumulx.entity.Area"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<UserInfo> userInfoList = (List<UserInfo>) request.getAttribute("userInfoList");
	//获取所有的areaObj信息
	List<Area> areaList = (List<Area>) request.getAttribute("areaList");
	int currentPage = (Integer) request.getAttribute("currentPage"); //当前页
	int totalPage = (Integer) request.getAttribute("totalPage"); //一共多少页
	int recordNumber = (Integer) request.getAttribute("recordNumber"); //一共多少记录
	String user_name = (String) request.getAttribute("user_name"); //学号查询关键字
	Area areaObj = (Area) request.getAttribute("areaObj");
	String name = (String) request.getAttribute("name"); //姓名查询关键字
	String birthday = (String) request.getAttribute("birthday"); //出生日期查询关键字
	String telephone = (String) request.getAttribute("telephone"); //联系电话查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1 , user-scalable=no">
<title>用户查询</title>
<link href="<%=basePath%>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath%>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath%>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath%>plugins/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<link rel="shortcut icon" href="<%=basePath%>favicon.ico">
</head>
<body style="margin-top: 70px;">
	<div class="container">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="col-md-9 wow fadeInLeft">
			<ul class="breadcrumb">
				<li><a href="<%=basePath%>index.jsp">首页</a></li>
				<li><a href="<%=basePath%>UserInfo/frontlist">用户信息列表</a></li>
				<li class="active">查询结果显示</li>
				<a class="pull-right"
					href="<%=basePath%>UserInfo/userInfo_frontAdd.jsp"
					style="display: none;">添加用户</a>
			</ul>
			<div class="row">
				<%
					/*计算起始序号*/
					int startIndex = (currentPage - 1) * 5;
					/*遍历记录*/
					for (int i = 0; i < userInfoList.size(); i++) {
						int currentIndex = startIndex + i + 1; //当前记录的序号
						UserInfo userInfo = userInfoList.get(i); //获取到用户对象
						String clearLeft = "";
						if (i % 4 == 0)
							clearLeft = "style=\"clear:left;\"";
				%>
				<div class="col-md-3 bottom15" <%=clearLeft%>>
					<a
						href="<%=basePath%>UserInfo/<%=userInfo.getUser_name()%>/frontshow"><img
						class="img-responsive"
						src="<%=basePath%><%=userInfo.getUserPhoto()%>" /></a>
					<div class="showFields">
						<div class="field">
							学号:<%=userInfo.getUser_name()%>
						</div>
						<div class="field">
							所在学院:<%=userInfo.getAreaObj().getAreaName()%>
						</div>
						<div class="field">
							姓名:<%=userInfo.getName()%>
						</div>
						<div class="field">
							性别:<%=userInfo.getSex()%>
						</div>
						<div class="field">
							出生日期:<%=userInfo.getBirthday()%>
						</div>
						<div class="field">
							联系电话:<%=userInfo.getTelephone()%>
						</div>
						<a class="btn btn-primary top5"
							href="<%=basePath%>UserInfo/<%=userInfo.getUser_name()%>/frontshow">详情</a>
						<%-- <a class="btn btn-primary top5" onclick="userInfoEdit('<%=userInfo.getUser_name() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="userInfoDelete('<%=userInfo.getUser_name() %>');" style="display:none;">删除</a> --%>
						<a class="btn btn-primary top5"
							onclick="userInfoEdit('<%=userInfo.getUser_name()%>');">修改</a>
						<a class="btn btn-primary top5"
							onclick="userInfoDelete('<%=userInfo.getUser_name()%>','<%=userInfo.getUserPhoto()%>');">删除</a>
					</div>
				</div>
				<%
					}
				%>

				<div class="row">
					<div class="col-md-12">
						<nav class="pull-left">
							<ul class="pagination">
								<li><a href="#"
									onclick="GoToPage(<%=currentPage - 1%>,<%=totalPage%>);"
									aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
								<%
									int startPage = currentPage - 5;
									int endPage = currentPage + 5;
									if (startPage < 1)
										startPage = 1;
									if (endPage > totalPage)
										endPage = totalPage;
									for (int i = startPage; i <= endPage; i++) {
								%>
								<li class="<%=currentPage == i ? "active" : ""%>"><a href="#"
									onclick="GoToPage(<%=i%>,<%=totalPage%>);"><%=i%></a></li>
								<%
									}
								%>
								<li><a href="#"
									onclick="GoToPage(<%=currentPage + 1%>,<%=totalPage%>);"><span
										aria-hidden="true">&raquo;</span></a></li>
								<li>
									<div class="col-lg-3">
										<div class="input-group">
											<input type="text" id="changePageValue" class="form-control"
												placeholder="跳转到指定页面"> <span class="input-group-btn">
												<button class="btn btn-default" type="button"
													onclick="changepage(<%=totalPage%>)">Go</button>
											</span>
										</div>
									</div>
								</li>
								<li>
									<div class="col-lg-4">
										<div class="pull-right" style="line-height: 34px;">
											共有<%=recordNumber%>条记录，当前第
											<%=currentPage%>/<%=totalPage%>
											页
										</div>
									</div>
								</li>

							</ul>
							<ul>&nbsp;
							</ul>
							<ul>&nbsp;
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-3 wow fadeInRight">
			<div class="page-header">
				<h1>用户查询</h1>
			</div>
			<form name="userInfoQueryForm" id="userInfoQueryForm"
				action="<%=basePath%>UserInfo/frontlist" class="mar_t15"
				method="post">
				<div class="form-group">
					<label for="user_name">学号:</label> <input type="text"
						id="user_name" name="user_name" value="<%=user_name%>"
						class="form-control" placeholder="请输入学号">
				</div>
				<div class="form-group">
					<label for="areaObj_areaId">所在学院：</label> <select
						id="areaObj_areaId" name="areaObj.areaId" class="form-control">
						<option value="0">不限制</option>
						<%
							for (Area areaTemp : areaList) {
								String selected = "";
								if (areaObj != null && areaObj.getAreaId() != null
										&& areaObj.getAreaId().intValue() == areaTemp.getAreaId().intValue())
									selected = "selected";
						%>
						<option value="<%=areaTemp.getAreaId()%>" <%=selected%>><%=areaTemp.getAreaName()%></option>
						<%
							}
						%>
					</select>
				</div>
				<div class="form-group">
					<label for="name">姓名:</label> <input type="text" id="name"
						name="name" value="<%=name%>" class="form-control"
						placeholder="请输入姓名">
				</div>
				<div class="form-group">
					<label for="birthday">出生日期:</label> <input type="text"
						id="birthday" name="birthday" class="form-control"
						placeholder="请选择出生日期" value="<%=birthday%>"
						onclick="SelectDate(this,'yyyy-MM-dd')" />
				</div>
				<div class="form-group">
					<label for="telephone">联系电话:</label> <input type="text"
						id="telephone" name="telephone" value="<%=telephone%>"
						class="form-control" placeholder="请输入联系电话">
				</div>
				<input type=hidden name=currentPage value="<%=currentPage%>" />
				<button type="submit" class="btn btn-primary">查询</button>
			</form>
		</div>

	</div>
	</div>
	<div id="userInfoEditDialog" class="modal fade" tabindex="-1"
		role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<i class="fa fa-edit"></i>&nbsp;用户信息编辑
					</h4>
				</div>
				<div class="modal-body" style="height: 450px; overflow: auto;">
					<form class="form-horizontal" name="userInfoEditForm"
						id="userInfoEditForm" enctype="multipart/form-data" method="post"
						class="mar_t15">
						<div class="form-group">
							<label for="userInfo_user_name_edit" class="col-md-3 text-right">学号:</label>
							<div class="col-md-9">
								<input type="text" id="userInfo_user_name_edit"
									name="userInfo.user_name" class="form-control"
									placeholder="请输入学号" readOnly>
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_password_edit" class="col-md-3 text-right">登录密码:</label>
							<div class="col-md-9">
								<input type="text" id="userInfo_password_edit"
									name="userInfo.password" class="form-control"
									placeholder="请输入登录密码">
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_areaObj_areaId_edit"
								class="col-md-3 text-right">所在学院:</label>
							<div class="col-md-9">
								<select id="userInfo_areaObj_areaId_edit"
									name="userInfo.areaObj.areaId" class="form-control">
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_name_edit" class="col-md-3 text-right">姓名:</label>
							<div class="col-md-9">
								<input type="text" id="userInfo_name_edit" name="userInfo.name"
									class="form-control" placeholder="请输入姓名">
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_sex_edit" class="col-md-3 text-right">性别:</label>
							<div class="col-md-9">
								<input type="text" id="userInfo_sex_edit" name="userInfo.sex"
									class="form-control" placeholder="请输入性别">
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_userPhoto_edit" class="col-md-3 text-right">学生照片:</label>
							<div class="col-md-9">
								<img class="img-responsive" id="userInfo_userPhotoImg_edit"
									border="0px" /><br /> <input type="hidden"
									id="userInfo_userPhoto_edit" name="userInfo.userPhoto" /> <input
									id="userPhotoFile_edit" name="userPhotoFile" type="file"
									size="50" />
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_birthday_edit" class="col-md-3 text-right">出生日期:</label>
							<div class="col-md-9">
								<div class="input-group date userInfo_birthday_edit col-md-12"
									data-link-field="userInfo_birthday_edit"
									data-link-format="yyyy-mm-dd">
									<input class="form-control" id="userInfo_birthday_edit"
										name="userInfo.birthday" size="16" type="text" value=""
										placeholder="请选择出生日期" readonly> <span
										class="input-group-addon"><span
										class="glyphicon glyphicon-remove"></span></span> <span
										class="input-group-addon"><span
										class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_telephone_edit" class="col-md-3 text-right">联系电话:</label>
							<div class="col-md-9">
								<input type="text" id="userInfo_telephone_edit"
									name="userInfo.telephone" class="form-control"
									placeholder="请输入联系电话">
							</div>
						</div>
						<div class="form-group">
							<label for="userInfo_address_edit" class="col-md-3 text-right">家庭地址:</label>
							<div class="col-md-9">
								<input type="text" id="userInfo_address_edit"
									name="userInfo.address" class="form-control"
									placeholder="请输入家庭地址">
							</div>
						</div>
					</form>
					<style>
#userInfoEditForm .form-group {
	margin-bottom: 5px;
}
</style>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="ajaxUserInfoModify();">提交</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<jsp:include page="../footer.jsp"></jsp:include>
	<script src="<%=basePath%>plugins/jquery.min.js"></script>
	<script src="<%=basePath%>plugins/bootstrap.js"></script>
	<script src="<%=basePath%>plugins/wow.min.js"></script>
	<script
		src="<%=basePath%>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
	<script src="<%=basePath%>plugins/bootstrap-datetimepicker.min.js"></script>
	<script
		src="<%=basePath%>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="<%=basePath%>js/jsdate.js" type="text/javascript"></script>

	<script>

// 用户选择图片后更新img地址
$(document).ready(function() {
	$("#userPhotoFile_edit").change(function(){
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
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.userInfoQueryForm.currentPage.value = currentPage;
    document.userInfoQueryForm.submit();
}
/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=$("#changePageValue").val();
     if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
     
    document.userInfoQueryForm.currentPage.value = pageValue;
    document.userInfoQueryForm.submit();
}
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
				$('#userInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}
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
});
/*删除用户信息*/
function userInfoDelete(user_name,userPhoto) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "UserInfo/deletes",
			data : {
				user_names : user_name,
				userPhotos : userPhoto
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#userInfoQueryForm").submit();
					//location.href= basePath + "UserInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}
/*ajax方式提交用户信息表单给服务器端修改*/
function ajaxUserInfoModify() {
	$.ajax({
		url :  basePath + "UserInfo/" + $("#userInfo_user_name_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#userInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#userInfoQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

</script>
</body>
</html>

