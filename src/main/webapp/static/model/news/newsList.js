require.config(config);
require(["jquery", "leftMenu", "animation","pager","base"], function($, menu, animation,  pager ,base) {
	
	var itemList ="";
	var leftTreeItems=""; 
	var columnId ="";	
	
	var menuData={
			"title": "新闻动态",
			"icon":"iconfont icon-lineflie",			 
		};
	
	var pageSize = 10;
	var url = "/portal/columnNewsPage";
//	/article/query
//	/portal/columnNewsPage
	var type=0;
	var showDeafaultTable = pager.methods.showDeafaultTable = function(columnId,pageNo, pageSize , params) {
		var data={
			startDate:$("#startDTime").val(),
			endDate:$("#endDTime").val(),
			title:$("#spec").val()
		};
		data.columnName="新闻动态";
		if($(".menu-a.active").text()=="盐业动态"){
			data.type=0;
		}
		else if($(".menu-a.active").text()=="盐政执法"){
			data.type=1;
		}
		data=(params==undefined?data:$.extend({},data,params));
		pager.methods.showTemplateTable(columnId,pageNo, pageSize, url,"pagination","policies-list-items","tmpl",data,callback);		
	}
	pager.pagerGlobal(pager.methods);
	var callback = function(){
		$.each($("#policies-list-items").find(".content-item-date"),function(){
			$(this).text(new Date(parseInt($(this).text())).Format("yyyy-MM-dd"));			
		})
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
	
	function getQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");       
       var r = window.location.search.substr(1).match(reg);           
         if(r!=null){
         // alert(unescape(r[2]));
            return  unescape(r[2])
         }
        return null;
     }	 
	/**
		 * 转换树
		 */
		 function list2tree(list, idKey, parentKey){
			var result = [];
			var hash = {};
			//把数组遍历出用ID作为KEY的JSON对象
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

		
	
	function initMenu() { //初始化栏目方法
		
		var queryParams={};
		
		$.post("/portal/queryColumnTree",{
			columnId: columnId,
			columnTypes: ["1", "4"]	
		},function(data,status){		 		
			if(!isEmpty(data) && data.code == "1") {				
				itemList = data.data;
				leftTreeItems = list2tree(data.data, "id", "pColumnId");
				menuData.title=leftTreeItems[0].name;
				menuData.menu=leftTreeItems[0].children;
				menuData.columnId=columnId;				 
				menu.setmenu("#menu", menuData,function(ele){			
					$("#spec").val("");
					if(ele.next("ul").length==0){
						showDeafaultTable(columnId,1,pageSize);
					}
				});
			} else {
				console.info("初始化栏目失败：" + data.msg);
			}			
			

		});			 
	}
	
	$(function() {
//		alert("initMenu");
		columnId = getQueryString("id");
		initMenu();
		
		animation.load({
			container:$("#container")
		});
		
		showDeafaultTable(columnId,1, pageSize);
		$("#queryBtn").on("click",function(){
			var params = {
				startDate:$("#startDTime").val(),
				endDate:$("#endDTime").val(),
				title:$("#spec").val()
			}
			showDeafaultTable(columnId,1, pageSize);
		})
	})
})