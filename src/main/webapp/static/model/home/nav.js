;(function(factory){
	if(typeof require != "undefined"){
		require.config({
			paths:{
				"jquery":"../../js/jquery-1.8.3.min",
				"layer":"../../js/layer/layer"
			}
		});
		require(["jquery","layer"],function($,layer){
			$(function(){
				factory();
			})
		});
	}
	else{
		window.onload=function(){
			factory();			
		}
	}
})(function(){
		/**
		 * navList需要配置地址与导航条对应关系的页面列表，key为需要配置页面的地址，value为对应的导航条栏目地址
		 * navData为导航栏所显示的项目
		 * 下列配置对应的规则为点击供应信息，可跳至销售列表(/trade/sellList),销售列表(/trade/sellList)、销售详情(/trade/sellDetails)中供应信息项为激活状态
		 * navList:{
		 * 	"/trade/sellList":"003",
		 * 	"/trade/sellDetails":"003
		 * },
		 * navData:[
		 * 	{
		 * 		id:"003",
		 * 		text:"供应信息",
		 * 		"url":"/trade/sellList"
		 * 	}
		 * ]
		 * */
		var data={
			navList:{
				"/salt/index":"001",
				"/policies/list":"003",
				"/prodlib/moreIndex":"003",
				"/policies/detail":"003",
				"/news/detail":"002",
				"/notice/detail":"004",
				"/ni/detail":"005",
				"/spi/detail":"005"
			},
			navData:[
//			         {
//			        	 id:"001",
//			        	 text:"首页",
//			        	 url:"/"
//			         },
//			         {
//			        	 id:"002",
//			        	 text:"新闻动态",
//			        	 url:"/news/list"
//			         },
//			         {
//			        	 id:"003",
//			        	 text:"政策法规",
//			        	 url:"/policies/list"
//			         },
			         {
			        	 id:"004",
			        	 text:"通知公告",
			        	 url:"",
			        	 child:[
			        	        {
			        	        	id:"004_1",
			        	        	text:"双公示1",
			        	        	url:"",
			        	        }
			        	       ]
			         },
//			         {
//			        	 id:"005",
//			        	 text:"检测公示",
//			        	 url:"/ni/list"
//			         },
//			         {
//			        	id:"006",
//			        	text:"网上办事大厅",
//			        	url:"http://www.gdbs.gov.cn/portal/public/procedures?orgId=63ae8b75-a328-4e51-b731-83b96bba67ca",
//			        	isBlank:true
//			         },
//			         {
//			        	 id:"007",
//			        	 text:"资料下载",
//			        	 url:"/dm/download"
//			         }
			         ]
		}
		var curUrl=location.pathname;
		if(data["navList"][curUrl]){
			var activeItemId=data["navList"][curUrl];
		}
		var navData=data["navData"]//queryColumn();
		function createNav(data,$container,$parentLi){
			$.each(data,function(i,ele){
				var thisObj=$(ele)[0];
				var $li=$("<li></li>");
				var url=thisObj["url"]==null?"javascript:void(0);":thisObj["url"];
				var $a = $("<a></a>").text(thisObj["text"]).attr("href",url);
				if(typeof thisObj["child"] != "undefined"){
					var $ul = $("<ul></ul>").addClass("hide child-nav");
					$li.addClass("has-child").append($ul);
					$.each(thisObj["child"],function(i,eleindex){
						var child = $(eleindex)[0];
						createNav(thisObj["child"],$ul,$li);
					})
				}
				$li.prepend($a).appendTo($container);
				
				
			})
		}
		createNav(navData,$("#nav"));		
		
		$("#nav").find("li").hover(function(){
			if($(this).children("ul").length>0){
				$(this).children("ul").show().removeClass("hide");
			}
		},function(){
			if($(this).children("ul").length>0){
				$(this).children("ul").hide().addClass("hide");				
			}
		})
		
		/**
		 * 登录处理
		 */
		var islogin = $('#state').val();
 		if(islogin==1){
 			$('#notLogin').hide();
 			$('#isLogin').show();
 		}
 		/**
 		 * 查询栏目信息
 		 */
 		function queryColumn(){
 			var params = {
 					portalType:"2",//"2",
					isDisplay: "1",//栏目是否在前段显示
					columnNode:"0",//通过顶级栏目ID查询所以子集
					orderBySort: "1"
 			}
 			debugger;
 			$.ajax({
 				url: "/portal/queryNewColumn", //url  action是方法的名称
 				type: 'post',
 				data: params,
 				dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
 				//contentType: "application/json; charset=utf-8",
 				success: function(msg) {
 					if(msg.code == "1"){
 						var data = msg.data;
 						var dataItem = list2tree(data, "id", "pColumnId");
 						console.log("dataItem",dataItem);
 						return dataItem;
 					}else{
 						layer.msg("操作失败",{icon:2});
 					}
 				},
 				error: function(msg) {
 		        	layer.msg("操作失败",{icon:2});
 		        }
 			
 			});
 		}
 		/**
 		 * 转换树
 		 */
 		 function list2tree(list, idKey, parentKey){
 			var result = [];
 			var hash = {};

 			//把数组遍历出用ID作为KEY的JSON对象
 			/*list.forEach(ele => {
 				hash[ele[idKey]] = ele;
 			});
 			list.forEach(ele => {
 				var _id = ele[idKey]; //当前节点的id
 				var _parentKey = hash[_id][parentKey]; //当前节点的父节点KEY

 				if(_parentKey && hash[_parentKey]) { //如果有父节点KEY，并且父节点存在
 					if(!hash[_parentKey].children) { //如果当前节点还没有子节点，为其添加一个空的子节点
 						hash[_parentKey].children = [];
 					}
 					hash[_parentKey].children.push(ele); //把当前节点添加到父节点的子节点路径上
 				} else {
 					result.push(ele); //添加没有父节点KEY或者没有父节点存在的节点到根节点上
 				}
 			});*/
			  $.each(list, function(i,ele){     
				  hash[ele[idKey]] = ele;
			  });    
			  $.each(list, function(i,ele){
	 				var _id = ele[idKey]; //当前节点的id
	 				var _parentKey = hash[_id][parentKey]; //当前节点的父节点KEY

	 				if(_parentKey && hash[_parentKey]) { //如果有父节点KEY，并且父节点存在
	 					if(!hash[_parentKey].children) { //如果当前节点还没有子节点，为其添加一个空的子节点
	 						hash[_parentKey].children = [];
	 					}
	 					hash[_parentKey].children.push(ele); //把当前节点添加到父节点的子节点路径上
	 				} else {
	 					result.push(ele); //添加没有父节点KEY或者没有父节点存在的节点到根节点上
	 				}
	 			
			  });  
 			
 			return result;
 		}
 		/**
 		 * 退出
 		 */
 		function exit(){
 			$.ajax({
 				url: "/login/delSession", //url  action是方法的名称
 				type: 'GET',
 				dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
 				//contentType: "application/json; charset=utf-8",
 				success: function(msg) {
 					if(msg.result == 1){
 						layer.alert("操作成功",{},function(){
 							location.href="/salt/index"
 						});
 					}else{
 						layer.msg("操作失败",{icon:2});
 					}
 				},
 				error: function(msg) {
 		        	layer.msg("操作失败",{icon:2});
 		        }
 			
 			});
 		}
 		
 		$('#alogout').on('click', function() {
 		 
 			$.ajax({
 				url: "/login/delsession", //url  action是方法的名称
 				type: 'GET',
 				dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
 				//contentType: "application/json; charset=utf-8",
 				success: function(msg) {
// 					console.info("aa:"+msg)
 					if(msg.result == 1){
 						layer.alert("操作成功",{},function(){
 							location.href="/salt/index"
 						});
 					}else{
 						layer.msg("操作失败",{icon:2});
 					}
 				},
 				error: function(msg) {
 		        	layer.msg("操作失败1",{icon:2});
 		        }
 			
 			});

 		});
 		
 		/**
 		 * 搜索
 		 */
/* 		$('#serach').on('click', function() { 
 			var keyName =  $("#keyName").val();
 			var str = "";
 			for(i=0;i<keyName.length;i++){
 				var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]$/;
 				if(reg.test(keyName.charAt(i))){
 					str+=keyName.charAt(i);
 				}
 			}
 			console.log(str);
 			//location.href="/salt/serach?keyName="+keyName;
 		});*/

 		// 系统通知
		var loginId = $('#loginId').val();
 		if(loginId){
 			console.log(1)
 			/*setInterval(function(){
 				$.post(url,{},function(data){
	 				if(data){
	 					$("#userName").after('<a href="/vipcenter/index"><i class="fa fa-bell"></i>('+data+')</a>')
	 				}
	 			})
 			},10000)*/
 		}

})