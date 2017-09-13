
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
		<br>
		 <div id="main" style="width: 1000px;height:700px;"></div>
		 
		</div>	 
    <script src="js/echarts/echarts.js "></script>
    <script src="js/sigmajs/jquery/jquery.min.js" type="text/javascript"></script>
    <script>
		   
			
		    
		    $(document).ready(function() {
		    	
			$.getJSON("showPieGraphAction",
					
					 function (datajson) {
										
				if (datajson != null) {
				
				
					
				drawGraph(datajson.emotions);
					
				
											
				       }				
			       }
			);
		    	
		    	
		    	
		    });
		    
		    
		    
		    function drawGraph(data){
		    		
		     var myChart = echarts.init(document.getElementById('main'));
				 myChart.title = '坐标轴刻度与标签对齐';
		    	
				 option = {
						    backgroundColor: '#fff',
						    title: {
						        text: '情感组成视图',
						        x: 'center',
						        y: 'center',
						        textStyle: {
						            fontWeight: 'normal',
						            fontSize: 20
						        }
						    },
						    tooltip: {
						        show: true,
						        trigger: 'item',
						        formatter: "{b}: {c} ({d}%)"
						    },
						    legend: {
						        orient: 'horizontal',
						        bottom: '0%',
						        data: ['Happy', 'Good', 'Anger', 'Sorrow', 'Fear','Evil','Suprise']
						    },
						    series: [{
						        type: 'pie',
						        selectedMode: 'single',
						        radius: ['25%', '58%'],
						        color: ['#86D560', '#AF89D6', '#59ADF3', '#FF999A', '#FFCC67','#00FFCC','#44BBBB'],

						        label: {
						            normal: {
						                position: 'inner',
						                formatter: '{d}%',

						                textStyle: {
						                    color: '#fff',
						                    fontWeight: 'bold',
						                    fontSize: 14
						                }
						            }
						        },
						        labelLine: {
						            normal: {
						                show: false
						            }
						        },
						        data: data
						    }, {
						        type: 'pie',
						        radius: ['58%', '83%'],
						        itemStyle: {
						            normal: {
						                color: '#F2F2F2'
						            },
						            emphasis: {
						                color: '#ADADAD'
						            }
						        },
						        label: {
						            normal: {
						                position: 'inner',
						                formatter: '{c}个词',
						                textStyle: {
						                    color: '#777777',
						                    fontWeight: 'bold',
						                    fontSize: 14
						                }
						            }
						        },
						        data: data
						    }]
						};
				    myChart.setOption(option);
		    	
		    	
		    	
		    }
		    
		    
		    
		    
		    
		    
		    
			
    </script>
		
		
		
		
		
		
	</body>
</html>