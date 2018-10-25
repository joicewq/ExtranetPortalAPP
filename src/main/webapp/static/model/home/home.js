require.config(config);
require(["jquery","tab"],function($,flexslider){
	var picPath="../images/";
	const self=this;
	var tempInfo=null;
	var stationId=0;

	var columnOne=[];
	var columnTwo=[];
	var columnThr=[];
	var columnHash= {}; //后台中code-id的对照表
	var links= [{
		icon: "iconfont icon-jianchaguandayi",
		name: "最高人民检察院",
		aName: "http://www.gj.pro"
	},
	{
		icon: "fa fa-edit",
		name: "中国知网",
		aName: "http://10.12.100.200/kns/law/default.html"
	},
	{
		icon: "iconfont icon-wangshangjubao",
		name: "机关党建",
		routerName: "newsList",
		columnCode: "12"
	},
	{
		icon: "iconfont icon-jianfengjiandu",
		name: "廉政建设",
		routerName: "newsList",
		columnCode: "13"
	},
	{
		icon: "iconfont icon-lvshiyuyue",
		name: "应用链接",
		routerName: "newsList",
		columnCode: "14"
	}
	];
	var recentModule= { //最新更新
			list: [{
				id: "1",
				title: "",
				date: ""
			}]
	};
	var procuratorateNews= { //检察要闻
			list: []
	};
	var informations= { //通知公告
			list: []
	};
	var leaderSpeaking={ //领导讲话
			list: []
	};
	var leaderIndicate= { //领导批示
			list: []
	};
	var  procuratoratePropagate= { //检察宣传
			list: []
	};
	var  procuratorateForum= { //检察论坛
			list: []
	};
	var  culturalActivity= { //文体活动
			list: []
	};
	var painting={ //书画长廊
			list: []
	};
	var literature={ //文学随笔
			list: []
	};
	var photography= { //摄影欣赏
			list: []
	};
	var infoModule= [{ //信息公开
		columnCode: "16",
		name: "院级领导",
		icon: "fa fa-edit",
		theme: "green"
	}, {
		columnCode: "17",
		name: "机构设置",
		icon: "fa fa-edit",
		theme: "yellow"
	}, {
		columnCode: "18",
		name: "政策文件",
		icon: "fa fa-edit",
		theme: "purple"
	}, {
		columnCode: "19",
		name: "法律文书",
		icon: "fa fa-edit",
		theme: "red"
	}, {
		columnCode: "20",
		name: "工作文件",
		icon: "fa fa-edit",
		theme: "blue"
	}, {
		columnCode: "21",
		name: "工作简报",
		icon: "fa fa-edit",
		theme: "orange"
	}];


	$(document).ready(function(){	

		getTempInfo(false); 
	 
		//从后台获取栏目hash表
		if(columnHash==null) {				
			$.post("/portal/getColumnHash",{},
					function(data,status){	
				columnHash=data;
				initQuery();
			}); 			
		} 

		function initQuery() { //初始化查询方法
			queryContent(null, "recentModule");
		}


		function queryContent(columnId, dataKey) { //查询栏目新闻方法
			var params = {};
			params.columnId = columnId;
			params.pageNo = "1";
			params.pageSize = "5";		 
			params.stationId=stationId;				
			$.post("/portal/columnNewsPage",params,
					function(data,status){	
				var tempData = data;
				if(tempData!=null && tempData.code == "1") {
					var data = tempData.data;
					var dataArray = [];
					for(var i = 0; i < data.length; i++) {
						var tempJSON = {};
						tempJSON.id = data[i].id;
						tempJSON.title = data[i].title;
						tempJSON.date = formatDate_date(data[i].updateTime);
						dataArray.push(tempJSON);
					}
//					columnOne = dataArray;
					console.info("columnOne:",columnOne);
				} else {
					console.info("初始化数据失败：" + tempData.msg);
				}					  
			}); 
			
		}
		
		console.info("initColumn start");
//		alert("initColumn");
		if(isEmpty(tempInfo))
		   getTempInfo(true);
		else
		   initColumn();
		console.info("initColumn end");

		/* function initColumnHash() { //初始化栏目对照表方法
				var _time = 0;
				var _interval = setInterval(function() {
					if(!isEmpty(columnHash)) {
						columnHash = JSON.parse( columnHash );
						//self.initQuery();
						clearInterval(_interval);
					}
					if(_time > 50) {
						clearInterval(_interval);
						console.error("columnHash初始化失败");
					}
					_time++;
				}, 100);
			},*/


	});
	function getTempInfo(isInit){
	//请求模板	 
	$.post("/portal/queryTemplete",{
		testLink: window.location.origin
	},function(data,status){				 
		if (data.message === '[]' || data.message === undefined) {
			tempInfo = []
		} else {
			tempInfo = data.message[0];
		}
		stationId=tempInfo.stationId;
		if(isInit)
		   initColumn();
	});	 
	}
	function formatDate_date(stamp) {
		var date = new Date(stamp);
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		return year + "-" + (month > 9 ? month : ("0" + month)) + "-" + (day > 9 ? day : ("0" + day));
	}
 

	//查询前台展示栏目信息(1.标题栏目左对齐,2.信息公开栏目,3.标题栏目居中对齐)
	function  initColumn(){
//		var tempInfo = JSON.parse(tempInfo);	 
		console.info("initColumn");
		console.info("tempInfo :",tempInfo);
		var column=[1,2,3,4];
		var param={
			        portalType: tempInfo.type==="内宣门户"?"1":"2",//"2",
					isDisplay: "1",//栏目是否在前段显示
					columnNode:tempInfo.stationId,//通过顶级栏目ID查询所以子集
//					visualizationColumn:column,
					visualizationColumn:[1,2,3,4],
					orderBySort: "1",
		};
		var paramsString="columnNode=0&isDisplay=1&orderBySort=1&portalType=2&visualizationColumn[0]=1" +
				"&visualizationColumn[1]=2&visualizationColumn[2]=3&visualizationColumn[3]=4";
		if(!isEmpty(tempInfo)){
			$.post("/portal/queryNewColumn",paramsString,
			function(data,status){		
				var dataTemp = data.data;
				var length = dataTemp.length;
				console.info("dataTemp:",dataTemp);
				if(dataTemp.length>0){
					dataTemp.forEach(function(element,len) {						 
						//取出visualizationColumn==2的值 信息公开
						// var theme=["","green","yellow","purple","red","blue","orange"]
						/*if(element.visualizationColumn==="2"){
							switch (element.name){
							case "院级领导" :
								self.$set(element,"icon","iconfont1 icon-leader");
								self.$set(element,"theme",'green');
								break;
							case "机构设置":
								self.$set(element,"icon","iconfont1 icon-mechanism");
								self.$set(element,"theme",'yellow');
								break;
							case "政策文件":
								self.$set(element,"icon","iconfont1 icon-gov");
								self.$set(element,"theme",'purple');
								break;
							case "法律文书":
								self.$set(element,"icon","iconfont1 icon-law");
								self.$set(element,"theme",'red');
								break;
							case "工作文件":
								self.$set(element,"icon","iconfont1 icon-files");
								self.$set(element,"theme",'blue');
								break;
							case "工作简报":
								self.$set(element,"icon","iconfont1 icon-report");
								self.$set(element,"theme",'orange');
								break;
							default:
							}
							columnTwo.push(element);
							console.log("columnTwo",self.columnTwo);
						}*/
											
											
						$.post("/portal/columnNewsPage",{
							columnId:element.id,
							pageNo:"1",
							pageSize: "5",	
						},function(data,status){				 
							var tempData =data;
							if(!isEmpty(tempData) && tempData.code == "1") {
								var data = tempData.data;
								var dataArray = [];
								for(var i = 0; i < data.length; i++) {
									var tempJSON = {};
									tempJSON.id = data[i].id;
									tempJSON.title = data[i].title;
									tempJSON.date = formatDate_date(data[i].updateTime);
									dataArray.push(tempJSON);

								}
//								self.$set(element,element.id,{list:dataArray});
								element.list=dataArray;
								if(element.visualizationColumn==="1"){
									columnOne.push(element);
								}									
								if(element.visualizationColumn==="3"){
									console.info("visualizationColumn:",element.visualizationColumn);
									columnThr.push("element",element);
								}
								if(len=== length-1){
									columnOne.sort(compare('sort'));									 
									columnThr.sort(compare('sort'));
								}	
//								console.log("columnOne",columnOne);
//								console.log("columnThr",columnThr);
//								console.info("initPageDate start");
								initPageDate();
//								console.info("initPageDate end");								
							}  
						});	

					}, this);
					
				}   
			});			

		}
		
	}
	function initPageDate(){
//		console.info("initPageDate");		
		var columnOneData="",columnThrData="";
		
		columnOne.forEach((item,index,arr)=>{ 
			var style="<div class='module module-min module-border'>"+
				"<div class='module-title-wrap'>"+
					"<span class='module-title-primary'></span>"+
					"<div class='module-title inline-block'>"+
					     item.name+
					"</div>"+	
					"<a href='/ni/list' class='ds-more pull-right'>更多>></a>"+
				"</div>";
			var newsContone="<div class='module-content-wrap' id='company-publicity'>"+
					"<ul class='news-list'>";
			if(!isEmpty(item.list))
			 item.list.forEach((itemList,index,arr)=>{ 				 
                 var li="";
                 li+="<li>" +
                 		"<a v-bind:href='#' target='_blank' class='news-title' >"+itemList.title+"</a>"+
					    "<span class='news-hot-icon'></span>"+
					    "<span class='news-date' >"+itemList.date+"</span>"+
					 "</li>";
                 newsContone+=li;
			});	
			newsContone+="</ul>"+				        
			         "</div>"+
			        "</div>";
			style+=newsContone;
			columnOneData+=style; 	   			 
			});		
		
		columnThr.forEach((item,index,arr)=>{ 
			var style="<div class='module module-min module-border'>"+
				"<div class='module-title-wrap'>"+
					"<span class='module-title-primary'></span>"+
					"<div class='module-title inline-block'>"+
					     item.name+
					"</div>"+	
					"<a href='/ni/list' class='ds-more pull-right'>更多>></a>"+
				"</div>";
			var newsContone="<div class='module-content-wrap' id='company-publicity'>"+
					"<ul class='news-list'>";
			if(!isEmpty(item.list))
			 item.list.forEach((itemList,index,arr)=>{ 				 
                 var li="";
                 li+="<li>" +
                 		"<a v-bind:href='#' target='_blank' class='news-title' >"+itemList.title+"</a>"+
					    "<span class='news-hot-icon'></span>"+
					    "<span class='news-date' >"+itemList.date+"</span>"+
					 "</li>";
                 newsContone+=li;
			});	
			newsContone+="</ul>"+				        
			         "</div>"+
			        "</div>";
			style+=newsContone;
			columnThrData+=style; 	   			 
			});	
		$("#columnOne").html(columnOneData);	
		$("#columnThr").html(columnThrData);	
	}

	 
	 function arrayToJson(o) {
     if(o==null)
        return "\" \"";
    var r = [];
    if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
    if (typeof o == "object") {
      if (!o.sort) {
        for (var i in o)
          r.push(i + ":" + arrayToJson(o[i]));
        if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
          r.push("toString:" + o.toString.toString());
        }
        r = "{" + r.join() + "}";
      } else {
        for (var i = 0; i < o.length; i++) {
          r.push(arrayToJson(o[i]));
        }
        r = "[" + r.join() + "]";
      }
      return r;
    }
    return o.toString();
  }
	
	function compare(propertyName) {
		return function(a,b){
		var value1 = a[propertyName];
		var value2 = b[propertyName];
		return value1 - value2;
		}
	}

	/**
	 * 判断一个对象是否为空，其中字段串""也算是空
	 */
	function isEmpty(object) {
		var flag = false;

		switch(typeof(object)) {
		case("undefined"):
		{
			flag = true;
			break;
		}
		case("number"):
		{
			break;
		}
		case("string"):
		{
			if(object == "") {
				flag = true;
			}
			break;
		}
		case("boolean"):
		{
			break;
		}
		case("object"):
		{
			if(object == null) {
				flag = true;
			}
			break;
		}
		case("function"):
		{
			break;
		}
		default:
		{
			flag = true;
			break;
		}
		}
		return flag;
	}

})