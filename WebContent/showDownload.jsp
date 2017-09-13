<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/baiducss/main.css">
<title>��������</title>
</head>
<body style="overflow: hidden;">
<!--��������������-->
<div class="helpcenterWild">

	<div class="new100content">
		<div class="helpcenter clearfloat">
			<div class="helpcenter_Cleft">
				<img src="images/help_left.png">
				<span></span>
			</div>
			<div class="helpcenter_Cright">
				<img src="images/help_right.png">
				<span></span>
			</div>
			<div class="helpcenter_left" style="display:none">
				<h2>�������ģ��ٶȾ���</h2>
				<div id="content_1" class="helpcenter_leftIn mCustomScrollbar _mCS_1">
					<!-- ����б� -->
					<ul>
						<li><a class="helpcenter_leftInOn">��������1</a></li>
						<li><a>��������2</a></li>
					</ul>
					<!-- ����б�end -->
				</div>
			</div>
			<div class="helpcenter_right">
			<!-- ���������Ҳ� -->
				<div class="helpcenter_rightIn">

					<!--��������1����Ϣ-->
					<div class="helpcenter_rightInIn displayblock">
						<!-- ͼ��1 -->
						
				<s:iterator var = "item" value = "#request.questionList" status="statu" >
						
						<s:if test = "#statu.count == 1">
													<div class="helpcenter_rightIns helpcenter_rightInsOn">
							<div class="helpcenter_rightInL">
								<h2><span>��������</span><span class="helpcenter_span1"><s:property   value = "#statu.count" /></span>
								<span>/</span><span><s:property value="#request.questionList.size" /></span></h2>
								
								<div class = "questionText" style= "line-height:35px;padding:0 0 0 20px" ><br>
									<b>�����ţ�</b>${item.questionId }<br>
									<b>������⣺</b>${item.questionTitle }<br>
									<b>���������</b>${item.scanNum }<br>
									<b>����������</b>${item.commentNum }<br>
									<b>�ش�������</b>${item.answerNum }<br>
									<b>��ע������</b>${item.careNum }<br>
									<b>�������ڣ�</b>${item.dataTime }<br>

									<b><a href = "downloadDataAction?questionId=${item.questionId }"  style= "color:#2292DD">�����������</a></b>									
								</div>
								
								
								
								
								
							</div>
							<div class="helpcenter_rightInR">
								<img src="${item.questionId }/zhihuGraph.png">
							</div>
						</div>
						
						
						
						
						</s:if>
						<s:else>
						
						
						<div class="helpcenter_rightIns">
							<div class="helpcenter_rightInL">
								<h2><span>��������</span><span class="helpcenter_span1"><s:property   value = "#statu.count" /></span>
								<span>/</span><span><s:property value="#request.questionList.size" /></span></h2>
								
								<div class = "questionText" style= "line-height:35px;padding:0 0 0 20px" ><br>
									<b>�����ţ�</b>${item.questionId }<br>
									<b>������⣺</b>${item.questionTitle }<br>
									<b>���������</b>${item.scanNum }<br>
									<b>����������</b>${item.commentNum }<br>
									<b>�ش�������</b>${item.answerNum }<br>
									<b>��ע������</b>${item.careNum }<br>
									<b>�������ڣ�</b>${item.dataTime }<br>
									
									<b><a href = "downloadDataAction?questionId=${item.questionId }"  style= "color:#2292DD">�����������</a></b>	
								
								</div>
								
								
								
								
								
							</div>
							<div class="helpcenter_rightInR">
								<img src="${item.questionId }/zhihuGraph.png">
							</div>
						</div>
						
						</s:else>
						
					</s:iterator>
						
					</div>
					
					
					<!--over-->
				</div>
			</div>
		</div>
	</div>
	<div class="helpcenter_bottom">
		<a class="helpcenter_bBL"><img src="images/help_bottomleft.png" alt="" /></a>
		<a class="helpcenter_bB helpcenter_bBon">1</a><a class="helpcenter_bB">2</a><a class="helpcenter_bB">3</a>
		<a class="helpcenter_bBR"><img src="images/help_bottomright.png" alt="" /></a>
	</div>
</div>
<!--�������Ľ���-->
<script type="text/javascript" src="js/baidujs/jquery.min.js"></script>
<script type="text/javascript" src="js/baidujs/main.js"></script>

</body>
</html>



