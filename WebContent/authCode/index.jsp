<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
  <head>
    <script src="<%=basePath%>plugins/jquery.min.js"></script>
    </script>
    <script type="text/javascript" >

      function reloadCheckImg()
      {
          $("img").attr("src", "img.jsp?t="+(new Date().getTime()));  //<img src="...">
      }

      $(document).ready(function(){


        $("#checkcodeId").blur(function(){

          var $checkcode = $("#checkcodeId").val();
          //校验   :文本框中输入的值 发送到服务端。
           //服务端： 获取文本框输入的值 ，和真实验证码图片中的值对比 ，并返回验证结果
          $.post(
                  "<%=basePath%>UserInfo/checkAuthCode",//服务端地址
                  "checkcode="+$checkcode ,
                  function(result){//图片地址（imgs/right.jpg   imgs/wrong.jpg）
                    //result:  imgs/right.jpg
                  var resultHtml =  $("<img src='"+result+"' height='15' width='15px'   />") ;
                    $("#tip").html(resultHtml);
                  }
          );

        });

      });
    </script>
    <title>验证码</title>
  </head>
  <body>
        验证码：
       	<input type="text" name="checkcode" id="checkcodeId" size="4"  />
          <!-- 验证码-->
        <a href="javascript:reloadCheckImg();"> <img src="img.jsp"/></a>
        <span id="tip">  </span>
  </body>
</html>
