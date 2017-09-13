
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
		<br><br>
		 <div id="main" style="width: 1450px;height:400px;"></div>
		 
		</div>	 
    <script src="js/echarts/echarts.js "></script>
    <script src="js/sigmajs/jquery/jquery.min.js" type="text/javascript"></script>
    <script>
		   
			
		    
		    $(document).ready(function() {
		    	
			$.getJSON("showLineGraphAction",
					
					 function (datajson) {
										
				if (datajson != null) {
				
				
					
				drawGraph(datajson.date, datajson.data);
					
				
											
				       }				
			       }
			);
		    	
		    	
		    	
		    });
		    
		    
		    
		    function drawGraph(date, data){
		    		
		     var myChart = echarts.init(document.getElementById('main'));
				 myChart.title = '坐标轴刻度与标签对齐';
		    	
				option = {
				
				
					
				    tooltip: {
				        trigger: 'axis',
				        position: function (pt) {
				            return [pt[0], '10%'];
				        }
				    },
				    title: {
				        left: 'center',
				        text: '知乎问题回答数量随时间分布视图',
				    },
				    toolbox: {
				        feature: {
				            dataZoom: {
				                yAxisIndex: 'none'
				            },
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: date
				    },
				    yAxis: {
				        type: 'value',
				        boundaryGap: [0, '100%']
				    },
				    dataZoom: [{
				        type: 'inside',
				        start: 0,
				        end: 10
				    }, {
				        start: 0,
				        end: 10,
				        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
				        handleSize: '80%',
				        handleStyle: {
				            color: '#fff',
				            shadowBlur: 3,
				            shadowColor: 'rgba(0, 0, 0, 0.6)',
				            shadowOffsetX: 2,
				            shadowOffsetY: 2
				        }
				    }],
				    series: [
				        {
				            name:'回答数量',
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                    color: 'rgb(85, 171, 237)'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: 'rgb(85, 171, 237)'
				                    }, {
				                        offset: 1,
				                        color: 'rgb(192, 194, 242)'
				                    }])
				                }
				            },
				            data: data
				        }
				    ]
				};
				    myChart.setOption(option);
		    	
		    	
		    	
		    }
		    
		    
		    
		    
		    
		    
		    
			
    </script>
		
		
		
		
		
		
	</body>
</html>