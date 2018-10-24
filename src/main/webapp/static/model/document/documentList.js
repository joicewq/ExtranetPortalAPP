require.config(config);
require(["jquery", "leftMenu", "animation", "datepicker", "vue","pager"], function($, menu, animation, datepicker, Vue,pager) {
	var pageSize = 10;
	var url = "/dm/query";
	var showDeafaultTable = pager.methods.showDeafaultTable = function(pageNo, pageSize,params) {
		params = $.extend({},{dTitle:$("#spec").val()},params);
		pager.methods.showTemplateTable(pageNo, pageSize, url,"pagination","policies-list-items","tmpl",params)		
	}
	pager.pagerGlobal(pager.methods);
	$(function() {
		var menuData={
				"title": "下载专区",
				"icon":"iconfont icon-yytb-dangfenglianzhengjianshe",
				"menu":[

					{
						"name": "资料下载",
						"url": "/dm/download"
					}
				]
			};
		animation.pageLoad();
		menu.setmenu("#menu", menuData);
		showDeafaultTable(1, pageSize,{});
		$(document).on("click",".js-download-btn",function(){
			var id=$(this).attr("data-id");
			var docId=$(this).attr("data-docId");
			var iframe=$("#download-iframe");
			$.ajax({
				url:"/dm/updateDegree",
				type:"POST",
				data:{id:id},
				success:function(data){
					if(data=="success"){
						iframe.attr("src",$("#accessory_url").val()+"/doc/download/"+docId);
					}
					else{
						layer.alert("下载失败，请稍后重试");
					}
				},
				error:function(){
					layer.alert("下载失败，请稍后重试");
				}
			})
		})
		
		$("#queryBtn").on("click",function(){
			showDeafaultTable(1, pageSize,{dTitle:$("#spec").val()});
		})
	})
})