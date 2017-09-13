<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta author="zrong.me 曾荣">
	<title>登录 - 知著 - Recognize the Whole</title>
	<link rel="stylesheet" type="text/css" href="style/register-login.css">
</head>
<body>
<div id="box"></div>
<div class="cent-box">
	<div class="cent-box-header">
		<h1 class="main-title hide">知著</h1>
		<h2 class="sub-title">生活热爱分享 -  Recognize the Whole</h2>
	</div>

	<div class="cont-main clearfix">
		<div class="index-tab">
			<div class="index-slide-nav">
				<a href="login.html" class="active">登录</a>
				<a href="https://www.zhihu.com/#signup">注册</a>
				<div class="slide-bar"></div>				
			</div>
		</div>
	<font style = "color:red"><s:property value="#request.msg"  /></font>
	<s:form method="post"  action="virtualLoginAction">
		<div class="login form">
			<div class="group">
				<div class="group-ipt email">
					<s:textfield  name="email" id="email" cssClass="ipt" placeholder="邮箱地址" data-validate="required:请填写邮箱"/>
				</div>
				<div class="group-ipt password" >
					<s:password  name="password" id="password" cssClass="ipt" placeholder="输入您的登录密码" data-validate="required:请填写密码"/>

				</div>
				<div class="group-ipt verify" style = "display:none" id = "capcha">
					<s:textfield  name="verify" id="verify" cssClass="ipt" placeholder="输入验证码" data-validate="required:请输入验证码"/>
					<img src="" class="imgcode">
				</div>
				<s:hidden name = "xsrf" id = "xsrfValue"  value = ""   />
			</div>
		</div>

		<div class="button">
			<s:submit type="submit" cssClass="login-btn register-btn" id="button" value="登录"/>
		</div>
	</s:form>

		<div class="remember clearfix">
			<label class="remember-me"><span class="icon"><span class="zt"></span></span><input type="checkbox" name="remember-me" id="remember-me" class="remember-mecheck" checked>记住我</label>
			<label class="forgot-password">
				<a href="#">忘记密码？</a>
			</label>
		</div>
	</div>
</div>

<div class="footer">
	<p>知著 - Recognize the Whole</p>
	<p>Designed By ZhiYe & <a href="zrong.me">balabala.net</a> 2017</p>
</div>

<div id = "test"></div>

<script src='js/indexJs/particles.js' type="text/javascript"></script>
<script src='js/indexJs/background.js' type="text/javascript"></script>
<script src='js/indexJs/jquery.min.js' type="text/javascript"></script>
<script src='js/indexJs/layer/layer.js' type="text/javascript"></script>
<script src='js/indexJs/index.js' type="text/javascript"></script>
<script>
	$('.imgcode').hover(function(){
		layer.tips("看不清？点击更换", '.verify', {
        		time: 6000,
        		tips: [2, "#3c3c3c"]
    		})
	},function(){
		layer.closeAll('tips');
	}).click(function(){
		$(this).attr('src','capchaAction?random=' + Math.random());
	});
	$("#remember-me").click(function(){
		var n = document.getElementById("remember-me").checked;
		if(n){
			$(".zt").show();
		}else{
			$(".zt").hide();
		}
	});
	
	$(document).ready(function(){
		
		$.get("showXsrfAction", function(data){
			$("#xsrfValue").attr('value',data);
			});
		
		
		
		
		
	})
	
	$("#button").click(function(){
		
		
		var n = $("#capcha");

		
		if(n.attr('style') ==  "display:none"){
			
			$('.imgcode').attr('src','capchaAction?random=' + Math.random());
			n.removeAttr('style');
			
			
			return false;
		}
		
	});
	
	
	
	
	
	
</script>
</body>
</html>