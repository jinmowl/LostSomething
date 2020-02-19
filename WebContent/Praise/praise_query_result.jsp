<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/praise.css" /> 

<div id="praise_manage"></div>
<div id="praise_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="praise_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="praise_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="praise_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="praise_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="praise_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="praiseQueryForm" method="post">
			招领信息：<input class="textbox" type="text" id="lostFoundObj_lostFoundId_query" name="lostFoundObj.lostFoundId" style="width: auto"/>
			标题：<input type="text" class="textbox" id="title" name="title" style="width:110px" />
			表扬时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="praise_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="praiseEditDiv">
	<form id="praiseEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">表扬id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="praise_praiseId_edit" name="praise.praiseId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">招领信息:</span>
			<span class="inputControl">
				<input class="textbox"  id="praise_lostFoundObj_lostFoundId_edit" name="praise.lostFoundObj.lostFoundId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">标题:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="praise_title_edit" name="praise.title" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">表扬内容:</span>
			<span class="inputControl">
				<textarea id="praise_contents_edit" name="praise.contents" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">表扬时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="praise_addTime_edit" name="praise.addTime" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Praise/js/praise_manage.js"></script> 
