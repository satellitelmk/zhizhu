
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
		<br><br><br>
		 <div id="main" style="width: 850px;height:600px;"></div>
		 
		</div>	 
    <script src="js/echarts/echarts.js "></script>
        <script src="js/echarts/worldcloud.js "></script>
    <script src="js/sigmajs/jquery/jquery.min.js" type="text/javascript"></script>
    <script>
		   
			
		    
		    $(document).ready(function() {
		    	
			$.getJSON("showWordsCloudAction",
					
					 function (datajson) {
										
				if (datajson != null) {
				
				
					
				drawGraph(datajson.words);
					
				
											
				       }				
			       }
			);
		    	
		    	
		    	
		    });
		    
		    
		    
		    function drawGraph(data){
		    		
		    	  var chart = echarts.init(document.getElementById('main'));

		            var option = {
		                 title: {
		        text: '关键词云图',
		        link: 'https://www.baidu.com/s?wd=' + encodeURIComponent('ECharts'),
		        x: 'center',
		        textStyle: {
		            fontSize: 23
		        }

		    },
		    backgroundColor: '#FFFFFF',
		    tooltip: {
		        show: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {
		                iconStyle: {
		                    normal: {
		                        color: '#FFFFFF'
		                    }
		                }
		            }
		        }
		    },
		    series: [{
		        name: '关键词',
		        type: 'wordCloud',
		        //size: ['9%', '99%'],
		        sizeRange: [16, 176],
		        //textRotation: [0, 45, 90, -45],
		        rotationRange: [-45, 90],
		        //shape: 'circle',
		        textPadding: 0,
		        autoSize: {
		            enable: true,
		            minSize: 6
		        },
		        textStyle: {
		            normal: {
		                color: function() {
		                    return 'rgb(' + [
		                        Math.round(Math.random() * 160),
		                        Math.round(Math.random() * 160),
		                        Math.round(Math.random() * 160)
		                    ].join(',') + ')';
		                }
		            },
		            emphasis: {
		                shadowBlur: 10,
		                shadowColor: '#333'
		            }
		        },
		                    data: data
		                } ]
		            };

					



						


						 //myChart.setOption(option);
					 chart.on('click', function (params) {
						                //alert((params.name));
						                window.open('https://www.baidu.com/s?wd=' + encodeURIComponent(params.name));

						            });
				    chart.setOption(option);
		    	
		    	
		    	
		    }
		    
		    
		    
		    
		    
		    
		    
			
    </script>
		
		
		
		
		
		
	</body>
</html>