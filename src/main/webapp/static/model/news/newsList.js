require.config(config);
require(["jquery", "leftMenu", "animation", "datepicker", "vue","pager","base"], function($, menu, animation, datepicker, Vue, pager ,base) {
	var pageSize = 10;
	var url = "/article/query";
	var type=0;
	var showDeafaultTable = pager.methods.showDeafaultTable = function(pageNo, pageSize , params) {
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
				"title": "新闻动态",
				"icon":"iconfont icon-lineflie",
				"menu":[

					{
						"name": "盐业动态"
					},
					{
						"name": "盐政执法"
					}
				]
			};
		animation.load({
			container:$("#container")
		});
		menu.setmenu("#menu", menuData,function(ele){
			/*if(ele.text()=="盐业动态"){
				showDeafaultTable(1, pageSize,{columnName:"新闻动态",type:1});
			}
			else if(ele.text()=="盐政执法"){
				showDeafaultTable(1, pageSize,{columnName:"新闻动态",type:0});
			}*/
			$("#spec").val("");
			if(ele.next("ul").length==0){
				showDeafaultTable(1,pageSize);
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