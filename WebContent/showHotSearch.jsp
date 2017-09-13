<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>������Ѷ</title>
		<link rel="stylesheet" type="text/css" href="css/hotsearchcss/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/hotsearchcss/style.css"/>
	</head>
	<body>
		
		

		<div class="newslist-wrap">
			<p class="newslist-tit">��������</p>
			
				<s:iterator var = "item" value = "#request.questionList" status="statu" >
		
		<span class="news-line">
				<img src="images/news-line.png"/>
		</span>		
		
		<dl class="newslist-box clearfix">
				<dt class="newslist-pic">
					<img src="${item.questionId }/zhihuGraph.png" width="100%" height="100%" />
				
				</dt>
				<dd class="newslist-txt">
					<a href="news-con.html" class="newslist-font24">��ǰ�ȶȣ�<span style = "color:red"> ${item.questionDes }</span></a>
					<ul class="newslist-ul">
						<li class="newslist-li">
						<br>
							<p class="newslist-time">����ʱ�䣺${item.dataTime }</p>
						</li>
						<li class="newslist-li">
							<p class="newslist-time">�����ţ�${item.questionId }</p>
							<p class="newslist-time">������⣺${item.questionTitle }</p>
							<p class="newslist-time">���������${item.scanNum }</p>
							<p class="newslist-time">����������${item.commentNum }</p>
							<p class="newslist-time">��ע������${item.careNum }</p>
							<p class="newslist-time">�ش�������${item.answerNum }</p>
					
						</li>
						
						<li class="newslist-time"><br><a style ="color:#55abed; font-size:18px" target = "_blank" href="https://www.zhihu.com/question/${item.questionId }">&lt;����鿴&gt;</a></li>
					</ul>
				</dd>
		</dl>	
		
						

		</s:iterator>
			
			


			<ul class="shop-paging clearfix">

			</ul>
		</div>

		
	</body>
	<!--jq����-->
	  <script src="js/sigmajs/jquery/jquery.min.js" type="text/javascript"></script>

</html>
