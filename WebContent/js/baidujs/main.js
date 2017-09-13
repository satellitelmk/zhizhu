function allpublic(){}
allpublic.prototype={backtop:function(){$("body,html").animate({scrollTop:0})},indexs:function(a,b){return a.index(b)}};var allpublics=new allpublic;
//核心代码
(function() {
	// 自动设置上部分高度
	window.onresize = function() {
		autoheight();
	}
	window.onload = function(){
		autoheight();
	}
	function autoheight() {
		// 高度为浏览器高度减去下方导航条高度：100
		var heights = $(window).height()-100;
		$(".helpcenter").height(heights);
		$(".helpcenter_Cright, .helpcenter_Cleft").css("line-height", heights + "px");
	}
	var allbtn = $(".helpcenter_leftIn li a");
	if (allbtn.length == 0) {
		return false;
	}
	var alldiv = $(".helpcenter_rightInIn");
	var movewidth = 900;
	var heights = $(window).height() - 200;
	
	
	var localclosetrue = localStorage.getItem("helpcenterclose");
	if (localclosetrue == null) {
		var closeticing = setTimeout(function() {
			$(".helpcenter_tixing_close").parent().hide();
		}, 5000)
		localStorage.setItem("helpcenterclose", "1")
	} else {
		$(".helpcenter_tixing_close").parent().hide();
	}

	bottomBtn($(".displayblock>div").length);
	$(".helpcenter").height(heights);
	$(".helpcenter_Cright, .helpcenter_Cleft").css("line-height", heights + "px");
	$(".helpcenter_tixing_close").click(function() {
		$(this).parent().hide()
	})
	$("body").on("click", ".helpcenter_Cleft,.helpcenter_bBL", function() {
		clickright()
	})
	$("body").on("click", ".helpcenter_Cright,.helpcenter_bBR", function() {
		clickleft();
	})
	$("body").on("click", ".helpcenter_bB", function() {
		var index = allpublics.indexs($(".helpcenter_bB"), $(this));
		var nows = $(".helpcenter_rightIn>.displayblock");
		if (nows.is(":animated")) {
			return false;
		}
		var nowleft = -(index * movewidth);
		nows.children().removeClass("helpcenter_rightInsOn").eq(index).addClass("helpcenter_rightInsOn");
		nows.animate({
			left: nowleft
		})
		showBtn();
	})

	allbtn.click(function() {
		allbtn.removeClass("helpcenter_leftInOn");
		$(this).addClass("helpcenter_leftInOn");
		var numbers = allpublics.indexs(allbtn, $(this));
		alldiv.removeClass("displayblock").eq(numbers).addClass("displayblock");
		bottomBtn(alldiv.eq(allpublics.indexs(allbtn, $(this))).children().length);
	})
	document.body.onmousewheel = function(event) {
		event.wheelDelta > 0 ? clickright() : clickleft();
	};
	document.body.addEventListener("DOMMouseScroll", function(event) {
		event.detail < 0 ? clickright() : clickleft();
		console.log(event.detail)
	});

	function clickleft() {
		var nows = $(".helpcenter_rightIn>.displayblock");
		if (nows.is(":animated")) {
			return false;
		}
		var nowleft = parseInt(nows.css("left")) - movewidth;
		var index = -(nowleft / 900) + 1;
		if (index > nows.children().length) {
			alert("到尾巴了");
			return false;
		}
		nows.children().eq(index - 1).addClass("helpcenter_rightInsOn");
		nows.children().eq(index - 2).removeClass("helpcenter_rightInsOn");

		nows.animate({
			left: nowleft
		})
		showBtn();
	}

	function clickright() {
		var nows = $(".helpcenter_rightIn>.displayblock");
		if (nows.is(":animated")) {
			return false;
		}
		var nowleft = parseInt(nows.css("left")) + movewidth;
		var index = -(nowleft / 900) + 1;
		if (index < 1) {
			alert("到头了");
			return false;
		}
		nows.children().eq(index - 1).addClass("helpcenter_rightInsOn");
		nows.children().eq(index).removeClass("helpcenter_rightInsOn");
		nows.animate({
			left: nowleft
		})
		showBtn();
	}

	function bottomBtn(numbers) {
		var li = '';
		li += '<a class="helpcenter_bBL"><img src="images/help_bottomleft.png" alt="" /></a>';
		for (var i = 1; i <= numbers; i++) {
			li = li + '<a class="helpcenter_bB">' + i + '</a>';
		}
		li += '<a class="helpcenter_bBR"><img src="images/help_bottomright.png" alt="" /></a>';
		$('.helpcenter_bottom').html(li);
		$(".helpcenter_rightInIn").width(movewidth * numbers);
		showBtn();
	}

	function showBtn() {
		var nows = $(".helpcenter_rightIn>.displayblock>.helpcenter_rightInsOn");
		var index = allpublics.indexs(nows.parent().children(), nows);
		$(".helpcenter_bB").removeClass("helpcenter_bBon").eq(index).addClass("helpcenter_bBon");
	}
})();