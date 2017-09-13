<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-gb" lang="en" xmlns:og="http://opengraphprotocol.org/schema/" xmlns:fb="http://www.facebook.com/2008/fbml" itemscope itemtype="http://schema.org/Map">

<head>
<title>OII Network Visualisation Example</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1,user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />


<!--[if IE]><script type="text/javascript" src="js/excanvas.js"></script><![endif]--> <!-- js/default.js -->
  <script src="js/sigmajs/jquery/jquery.min.js" type="text/javascript"></script>
  <script src="js/sigmajs/sigma/sigma.min.js" type="text/javascript" language="javascript"></script>
    <script src="js/sigmajs/sigma/sigma.parseJson.js" type="text/javascript" language="javascript"></script>
  <script src="js/sigmajs/fancybox/jquery.fancybox.pack.js" type="text/javascript" language="javascript"></script>
  <script src="js/sigmajs/main.js" type="text/javascript" language="javascript"></script>
  
  <script type="text/javascript" src="js/sigmajs/jquery/tc.min.js"></script>
  <script type="text/javascript" src="js/boxjs/wordbox.js"></script>
  <script type="text/javascript" src="js/boxjs/jscharts.js"></script>
	
  <link rel="stylesheet" type="text/css" href="css/boxcss/wordbox.css">
  
  
  

  <link rel="stylesheet" type="text/css" href="js/sigmajs/fancybox/jquery.fancybox.css"/>
  <link rel="stylesheet" href="css/sigmacss/style.css" type="text/css" media="screen" />
  <link rel="stylesheet" media="screen and (max-height: 770px)" href="css/sigmacss/tablet.css" />
  
  <style type="text/css">

i{ font-style:normal;font-family: "Microsoft YaHei"}
.test{	position:absolute;width:270px;height:295px;border:0px solid #ccc;background:#efefef; display:none;box-shadow:0 0 8px; border-radius:2px 2px 0 0;font-size:15px}
.test .tit{ background:#0997F7; display:block; height:30px; cursor:move;font-family: "Microsoft YaHei" ;margin:0;font-weight:600;text-align:center;border-radius:2px 2px 0 0;color:#fff;line-height:30px }
.test .tit i{position:absolute; right:1px;top:1px;cursor:pointer; float:right;  padding:0 0px;border-radius:2px;}

#tagscloud{width:250px;height:250px;position:relative;font-size:12px;color:#333;text-align:center;margin:5px auto }

</style>
  
  
  
</head>


<body>




<s:div id="questionNum" style="display:none;" >${ sessionScope.questionPage.questionId}</s:div>

  <div class="sigma-parent">
    <div class="sigma-expand" id="sigma-canvas"></div>
  </div>

	<div id="zoom">
  		<div class="z" rel="in"></div> <div class="z" rel="out"></div> <div class="z" rel="center"></div>
	</div>

<div id="attributepane">
<div class="text">
	<div title="Close" class="left-close returntext"><div class="c cf"><span>返回完全网络视图</span></div></div>	
<div class="headertext">
	<span>信息窗口</span>
</div>	
  <div class="nodeattributes">
    <div class="name"></div>
	<div class="data"></div>
    <div class="p">Connections:</div>
    <div class="link">
      <ul>
      </ul>
    </div>
  </div>
	</div>
</div>
<div id="developercontainer">
	<a href="http://www.oii.ox.ac.uk" title="Oxford Internet Institute"><div id="oii"><span>OII</span></div></a>
	<a href="http://jisc.ac.uk" title="JISC"><div id="jisc"><span>JISC</span></div></a>	
</div>













<div id="detail" class = "test">
<div class="tit">关键词<i class="close"><img src = "images/close.png" /></i></div>
<div id="tagscloud">
<div id="box-fixedWidth2" class="wordbox"></div>
</div>

</div>

<div id="detail2" class = "test">
<div class="tit">观点摘要<i class="close"><img src = "images/close.png" /></i></div>

<div id= "summary" style= "margin:8px 4px 8px 12px;line-height:20px;font-size:15px"></div>


</div>


<div id="detail3" class = "test">
<div class="tit">情感组成<i class="close"><img src = "images/close.png" /></i></div>

<div id= "moods" >Loading graph...</div>


</div>

















<script type="text/javascript">


  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21293169-4']);
  _gaq.push(['_setDomainName', 'none']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

  
  $('#showSummary').live('click', function() {  
	  
	  var idnum = $('#showSummary').attr("idnum");
	  
	  
	  $.post("showSummaryAction",
			  {
			    id:idnum
			  },
			  function(data,status){
			    
				  
				  
				  var words = [];
					
				var titles = data.keywords;
				
				
				
			    for(var i = 0; i < titles.length; i++) {
			        words[i] = {
			            'title' : titles[i],
			            'url' : 'https://www.zhihu.com/search?type=content&q='+titles[i]
			        }
			    }				    
			    
			    var colors3 = ['#49B4E0', '#FCBDA2', '#EBADBD', '#D5C2AF', '#C0BDE5', '#CBCC7F', '#FFDA7F', '#8dd0c3', '#bbbfc6', '#a4d9ef', '#bbdb98'];
			    
			    
			    
			    $('#box-fixedWidth2').wordbox({
			        isLead: false,    
			        leadWord: null,
			        words: words,
			        colors: colors3,
			        borderWidth: 2,
			        isFixedWidth: true,
			        width: 250,
			        height: 250
			    });
			    
			    
			    
			    var summaryDiv = $("#summary");
			    summaryDiv.append(data.summary);
			    
			    
			    var emotions= data.emotions;
			    
			    var myData = [];
			   
			    
			    var k = 0;
			    for(var key in emotions){
			    	
			    	myData[k] = [];
			    	myData[k][0] = key;
			    	
			    	myData[k][1] =emotions[key];
			    	k++;
			    }
			    
			    
			       var colorList = ["#FFFF99","#B5FF91","#94DBFF","#FFBAFF","#FFBD9D","#C7A3ED","#CC9898"];
			    	
			    	var colors = [];
			    	var s= 0;
			    	for(var key in emotions){
			    		
			    		colors[s] =  colorList[s];
					    	s++;
			    	}
			    	
			    	var myChart = new JSChart('moods', 'pie');
			    	myChart.setDataArray(myData);
			    	myChart.colorizePie(colors);
			    	myChart.setTitle('');
			    	myChart.setTitleColor('#5E5E5E');
			    	myChart.setTitleFontSize(8);
			    	myChart.setTextPaddingTop(30);
			    	myChart.setPieUnitsColor('#5E5E5E');
			    	myChart.setPieValuesColor('#5E5E5E');
			    	myChart.setSize(270, 285);
			    	myChart.setPiePosition(130,125);
			    	myChart.setPieRadius(85);
			    	myChart.draw();
			    

			    
			    popWin("detail"); 
			    popWin("detail2");
			    popWin("detail3");
				  
			    
			  });
	  
	  
  });
	  
	  
	

$("div#detail>div>i").click(function(){
	
	
	
	var oDiv=document.getElementById('box-fixedWidth2');
		while(oDiv.hasChildNodes()) //当div下还存在子节点时 循环继续
	{
		 oDiv.removeChild(oDiv.firstChild);
	}

	
});

$("div#detail2>div>i").click(function(){
	
	$("#summary").empty();

	
});

$("div#detail3>div>i").click(function(){
	
	$("#moods").empty();

	
});


  
  
</script>
</body>
</html>
