var navs = [{
	"title": "视图",
	"icon": "fa-cubes",
	"spread": true,
	"children": [{
		"title": "点线视图",
		"icon": "&#xe641;",
		"href": "showNetworkAction"
	}, {
		"title": "表格视图",
		"icon": "&#xe63c;",
		"href": "table.jsp"
	}]
}, {
	"title": "分析",
	"icon": "fa-cogs",
	"spread": false,
	"children": [{
		"title": "时间分布",
		"icon": "fa-table",
		"href": "timeDistribute.jsp"
	}, {
		"title": "情感组成",
		"icon": "fa-navicon",
		"href": "centimentsGraph.jsp"
	}, {
		"title": "关键词云图",
		"icon": "&#xe628;",
		"href": "wordsCloud.jsp"
	}]
}, {
	"title": "推荐",
	"icon": "&#xe609;",
	"spread": false,
	"children": [{
		"title": "热门搜索",
		"icon": "&#xe60c;",
		"href": "showHotSearchAction"
	}]
}, {
	"title": "数据下载",
	"icon": "fa-stop-circle",
	"href": "downloadAction",
	"spread": false
},{
	"title": "操作引导",
	"icon": "fa-address-book",
	"href": "guide.html",
	"spread": false
}];