<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新闻资讯</title>
		<link rel="stylesheet" type="text/css" href="css/hotsearchcss/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/hotsearchcss/style.css"/>
	</head>
	<body>
		
		

		<div class="newslist-wrap">
			<p class="newslist-tit">热门搜索</p>
			
				<s:iterator var = "item" value = "#request.questionList" status="statu" >
		
		<span class="news-line">
				<img src="images/news-line.png"/>
		</span>		
		
		<dl class="newslist-box clearfix">
				<dt class="newslist-pic">
					<img src="${item.questionId }/zhihuGraph.png" width="100%" height="100%" />
				
				</dt>
				<dd class="newslist-txt">
					<a href="news-con.html" class="newslist-font24">当前热度：<span style = "color:red"> ${item.questionDes }</span></a>
					<ul class="newslist-ul">
						<li class="newslist-li">
						<br>
							<p class="newslist-time">数据时间：${item.dataTime }</p>
						</li>
						<li class="newslist-li">
							<p class="newslist-time">问题编号：${item.questionId }</p>
							<p class="newslist-time">问题标题：${item.questionTitle }</p>
							<p class="newslist-time">浏览数量：${item.scanNum }</p>
							<p class="newslist-time">评论数量：${item.commentNum }</p>
							<p class="newslist-time">关注数量：${item.careNum }</p>
							<p class="newslist-time">回答数量：${item.answerNum }</p>
					
						</li>
						
						<li class="newslist-time"><br><a style ="color:#55abed; font-size:18px" target = "_blank" href="https://www.zhihu.com/question/${item.questionId }">&lt;点击查看&gt;</a></li>
					</ul>
				</dd>
		</dl>	
		
						

		</s:iterator>
			
			


			<ul class="shop-paging clearfix">

			</ul>
		</div>

		
	</body>
	<!--jq调用-->
	  <script src="js/sigmajs/jquery/jquery.min.js" type="text/javascript"></script>

</html>
