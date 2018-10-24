require.config(config);
require(["jquery", "animation", "datepicker", "vue","pager","base"], function($, animation, datepicker, Vue,pager,base) {
	var pageSize = 10;
	var url = "/article/query";
	var showDeafaultTable = pager.methods.showDeafaultTable = function(pageNo, pageSize,params) {
		var data = {
				startDate:$("#startDTime").val(),
				endDate:$("#endDTime").val(),
				title:$("#spec").val(),
				columnName:"通知公告"
				};
		data = $.extend({},data,params);
		pager.methods.showTemplateTable(pageNo, pageSize, url,"pagination","policies-list-items","tmpl",data,callback);		
	}
	var callback = function(){
		$.each($("#policies-list-items").find(".content-item-date"),function(){
			$(this).text(new Date(parseInt($(this).text())).Format("yyyy-MM-dd"));			
		})
	}
	pager.pagerGlobal(pager.methods);
	$(function() {
		animation.load({
			container:$("#container")
		});
		showDeafaultTable(1, pageSize,{columnName:"通知公告"});
		$("#queryBtn").on("click",function(){
			var params = {
				startDate:$("#startDTime").val(),
				endDate:$("#endDTime").val(),
				title:$("#spec").val(),
				columnName:"通知公告"
			}
			showDeafaultTable(1, pageSize,params);
		})
	})
})