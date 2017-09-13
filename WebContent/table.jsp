
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 


<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Table</title>
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="css/global.css" media="all">
		<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/table.css" />
	</head>

	<body>
		<div class="admin-main">

		<fieldset class="layui-elem-field layui-field-title">
			<legend>问题</legend>
		</fieldset>
		<p class="layui-elem-quote"><s:property  value ="#session.questionPage.questionTitle "/></p>
				
		<fieldset class="layui-elem-field layui-field-title">
			<legend>问题描述</legend>
		</fieldset>
		<p class="layui-elem-quote"><s:property  value ="#session.questionPage.questionDes "/></p>
		
		
		
		
			<fieldset class="layui-elem-field">
				<legend>回答列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover"  style="TABLE-LAYOUT: fixed">
					    <colgroup>
					      <col width="80">
					      <col width="50">
					      <col width="50">
					      <col width="270">
					      <col width="150">
					      <col width="100">
					      <col width="100">
					      <col width="50">
					   
					    </colgroup>
						<thead>
							<tr>
								<th><b>答主</b></th>
								<th><b>点赞量</b></th>
								<th><b>评论量</b></th>
								<th><b>观点摘要</b></th>
								<th><b>关键词</b></th>
								<th><b>情感比例</b></th>
								<th><b>时间</b></th>
								<th><b>操作</b></th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>

				</div>
			</fieldset>
			<div class="admin-table-page">
				<div id="page" class="page">
				</div>
			</div>
		</div>
		<script type="text/javascript" src="plugins/layui/layui.js"></script>
		<script>
			layui.config({
				base: 'plugins/layui/modules/'
			});

			layui.use(['icheck', 'laypage','layer'], function() {

					var $ = layui.jquery,
					laypage = layui.laypage,
					layer = parent.layer === undefined ? layui.layer : parent.layer;

					
					
				
					
					$(document).ready(function () {
						getEachPage(1);
						
						});
					
					
					
					function getEachPage(curr){
					
					
						
						$.getJSON("showAnswersTableAction",
							{//参数
							page:curr,
							
							}, function (datajson) {
												
						if (datajson != null) {
						
						
							var lineText = "";
							$(datajson.list).each(function(n,obj){
							
								lineText+="<tr><td style='WORD-WRAP: break-word'>";
								lineText+=obj.author+"</td><td style='WORD-WRAP: break-word'>";
								lineText+= obj.upCount+"</td><td style='WORD-WRAP: break-word'>";
								lineText+= obj.commentCount+"</td><td style='WORD-WRAP: break-word'>";
								lineText+= obj.summary+"</td><td style='WORD-WRAP: break-word'>";
								lineText+= obj.keywords+"</td><td style='WORD-WRAP: break-word'>";
								lineText+= obj.sentiments+"</td><td>";
								lineText+= obj.time+"</td><td><a href = 'https://www.zhihu.com/question/";
								lineText+=  <s:property  value ='#session.questionPage.questionId '/>;
								lineText+= "/answer/"+obj.answerId +"' target ='_blank' style = 'color:blue'>";
								lineText+="查看</a></td></tr>";

							})
						
							$('tbody').html(lineText);
						
							laypage({
									cont: 'page',
									pages: datajson.pages //总页数
										,
									groups: 5 //连续显示分页数
										,
									curr:curr||1,
									jump: function(obj, first) {
											//得到了当前页，用于向服务端请求对应数据
										var curr = obj.curr;
										if(!first) {
												
										getEachPage(obj.curr);
												//需要在这里进行ajax函数的调用

													}
												 }
				                   });											
						       }				
					       }
					)};
					
					
					
					
				//page



				$('.site-table tbody tr').on('click', function(event) {
					var $this = $(this);
					var $input = $this.children('td').eq(0).find('input');
					$input.on('ifChecked', function(e) {
						$this.css('background-color', '#EEEEEE');
					});
					$input.on('ifUnchecked', function(e) {
						$this.removeAttr('style');
					});
					$input.iCheck('toggle');
				}).find('input').each(function() {
					var $this = $(this);
					$this.on('ifChecked', function(e) {
						$this.parents('tr').css('background-color', '#EEEEEE');
					});
					$this.on('ifUnchecked', function(e) {
						$this.parents('tr').removeAttr('style');
					});
				});

			});
		</script>
	</body>

</html>