;define(["jquery"],function($){
	var isDebug = true,
	urlObj = {};
	if(isDebug){
		urlObj = {
			queryGrains:"/commonController/queryGrains.do",//粮食类型
			queryAreas:"/commonController/queryAreas.do",//地区
			querySells:"../data/sells.json",//供求信息列表
			queryBuys:"/data/buys.json",//供求信息列表
			pageList:"../data/company.json",//最新企业
			noticeList:"../data/notice.json",//政策法规
			varietyList:"../data/hotVariety.json"//首页盐种类列表
		}
	}
	else{
		urlObj = {
				queryGrains:"/commonController/queryGrains.do",//粮食类型
				queryAreas:"/commonController/queryAreas.do",//地区
				querySells:"/commonController/querySells.do",//供求信息列表
				queryBuys:"/commonController/queryBuys.do",//供求信息列表
				pageList:"/commonController/companys.do",
				noticeList:"/notice/getNoticeList/"+1+"/"+6+".do",
				varietyList:"..."
		}	
	}
	var service={
		 url:urlObj,
		 ajax:function(url,data,callback,async){
			 $.ajax({
	             type:"POST",
	             url: url,
	             async: async,
	             data: JSON.stringify(data), 
	             dataType: "JSON",
	             contentType:"application/json;charset=UTF-8",
	             success: callback,
	             error:function(data){
	            	//("error:"+data); 
	             }
	         });
		 },
		 ajaxNoAnnotation:function(url,data,callback,async){
			 $.ajax({
				 type:"POST",
				 url: url,
				 async: async,
				 data: (data), 
				 dataType: "JSON",
				 success: callback,
				 error:function(data){
					 //("error:"+data); 
				 }
			 });
		 },
		 queryGrains:function(data,callback,async){
			 this.ajax(this.url.queryGrains,data,callback,async);
		 },
		 queryAreas:function(data,callback,async){
			 this.ajaxNoAnnotation(this.url.queryAreas,data,callback,async);
		 },
		 querySells:function(data,callback,async){
			 this.ajax(this.url.querySells,data,callback,async);
		 },
		 queryBuys:function(data,callback,async){
			 this.ajax(this.url.queryBuys,data,callback,async);
		 },
		 pageList:function(data,callback){
			 this.ajax(this.url.pageList,data,callback);
		 },
		 noticeList:function(data,callback){
			 this.ajax(this.url.noticeList,data,callback);
		 },
		 varietyList:function(data,callback){
			 this.ajax(this.url.varietyList,data,callback)
		 }
	};
	
	
	$(function() {
		//$("header").html(header);
		$("#him").attr("class","ds-nav-li-active");
		//initHotSpot(hotSpotSys.him);
		//$("#hotSpot").html(cookieText);
		
	});
	return service;
});
