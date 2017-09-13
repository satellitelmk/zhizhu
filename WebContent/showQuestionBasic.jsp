<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
</head>
<body>
	<s:if test="%{#request.msg == 'fail'}"><h2 style="color:red">&emsp;&emsp;&emsp;您输入的问题代号有误</h2></s:if>
	<s:else>
	<div style="margin: 15px;">
		<fieldset class="layui-elem-field layui-field-title">
			<legend>问题</legend>
		</fieldset>
		<p class="layui-elem-quote"><s:property  value ="#session.questionPage.questionTitle "/></p>
		
		<fieldset class="layui-elem-field layui-field-title">
			<legend>问题描述</legend>
		</fieldset>
		<p class="layui-elem-quote"><s:property  value ="#session.questionPage.questionDes "/></p>

		<fieldset class="layui-elem-field layui-field-title">
			<legend>所属标签</legend>
		</fieldset>
		<p class="layui-elem-quote">
		
		<c:forEach items="${sessionScope.questionPage.tags }" var="tag" varStatus="loop">
				
				<a href = "https://www.zhihu.com${sessionScope.questionPage.tagsId[loop.index]}" target = "_blank">${tag}</a>&emsp;&emsp;
		</c:forEach>
		
		
		</p>
		<fieldset class="layui-elem-field layui-field-title">
			<legend>基本信息</legend>
		</fieldset>
		<p class="layui-elem-quote">
			评论数量：<s:property  value ="#session.questionPage.commentNum "/>&emsp;&emsp;
			回答数量：<s:property  value ="#session.questionPage.answerNum "/>&emsp;&emsp;
			关注数量：<s:property  value ="#session.questionPage.careNum "/>&emsp;&emsp;
			浏览数量：<s:property  value ="#session.questionPage.scanNum "/>&emsp;&emsp;
		
		</p>			
		<br>
		
		
		
		
			
	</div>

	</s:else>	








</body>
</html>