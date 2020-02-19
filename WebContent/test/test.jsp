<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-select.min.css" rel="stylesheet">

</head>
<body>


<label for="id_select">下拉框:</label>
<select id="id_select" class="selectpicker bla bla bli" multiple data-live-search="true">
	<option>123</option>
	<option>456</option>
	<option selected>789</option>
</optgroup>
</select>



<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/bootstrap-select.min.js"></script>

<script>
$(window).on('load', function () {  
	$('.selectpicker').selectpicker({  
	'selectedText': 'cat'
	});  
	// $('.selectpicker').selectpicker('hide');  
});  






</script>

</body>
</html>