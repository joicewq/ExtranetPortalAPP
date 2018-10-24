require.config(config);
require(["jquery", "leftMenu", "animation", "datepicker", "vue","pager","base"], function($, menu, animation, datepicker, Vue,pager,base) {
	var pageSize = 10;
	var url = "/article/query";
	var type=0;
	var showDeafaultTable = pager.methods.showDeafaultTable = function(pageNo, pageSize , params) {
		var data={
			startDate:$("#startDTime").val(),
			endDate:$("#endDTime").val(),
			title:$("#spec").val()
		};
		data.columnName="政策法规";
		if($(".menu-a.active").text()=="国家政策法规"){
			data.type=0;
		}
		else if($(".menu-a.active").text()=="地方政策法规"){
			data.type=1;
		}
		else if($(".menu-a.active").text()=="产品标准"){
			data.type=2;
		}
		data=(params==undefined?data:$.extend({},data,params));
		pager.methods.showTemplateTable(pageNo, pageSize, url,"pagination","policies-list-items","tmpl",data,callback);		
	}
	pager.pagerGlobal(pager.methods);
	var callback = function(){
		$.each($("#policies-list-items").find(".content-item-date"),function(){
			$(this).text(new Date(parseInt($(this).text())).Format("yyyy-MM-dd"));			
		})
	}
	$(function() {
		var menuData={
				"title": "政策法规",
				"icon":"iconfont icon-yytb-dangfenglianzhengjianshe",
				"menu":[

					{
						"name": "国家政策法规"
					},
					{
						"name": "地方政策法规"
					},
					{
						"name": "产品标准"
					}
				]
			};
		//$("#pageMsgTemplate").clone().attr("id","pageMsgTemplate").appendTo($("body"));
		animation.load({
			container:$("#container")
		});
		menu.setmenu("#menu", menuData,function(ele){
			/*if(ele.text()=="国家政策法规"){
				type=0;
				showDeafaultTable(1,pageSize,{columnName:"政策法规",type:0});
			}
			else if(ele.text()=="地方政策法规"){
				type=1;
				showDeafaultTable(1,pageSize,{columnName:"政策法规",type:1});
			}
			else if(ele.text()=="产品标准"){
				type=2;
				showDeafaultTable(1,pageSize,{columnName:"政策法规",type:2});
			}*/
			$("#spec").val("");
			if(ele.next("ul").length==0){
				showDeafaultTable(1, pageSize);
			}
		});
		showDeafaultTable(1, pageSize);
		$("#queryBtn").on("click",function(){
			var params = {
				startDate:$("#startDTime").val(),
				endDate:$("#endDTime").val(),
				title:$("#spec").val()
			}
			showDeafaultTable(1, pageSize,params);
		})
	})
})