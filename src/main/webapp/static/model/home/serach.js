require.config(config);
require([ "jquery", "leftMenu", "animation", "datepicker", "vue", "pager","base" ],
		function($, menu, animation, datepicker, Vue, pager,base) {
			var pageSize = 10;
			var url = "/salt/query";
			var showDeafaultTable = pager.methods.showDeafaultTable = function(
					pageNo, pageSize) {
				pager.methods.showTemplateTable(pageNo, pageSize, url,
						"pagination", "policies-list-items", "tmpl", base.serializeObject($("#rf")),afterSuccess)
			}
			pager.pagerGlobal(pager.methods);
			
			var afterSuccess=function(){
				$("#policies-list li").each(function() {
					var tdEle = $(this).find(".content-item-date");
					if(tdEle.text().length > 0) {
						var d = new Date(parseInt(tdEle.text()));
						tdEle.text(d.Format("yyyy-MM-dd"));
					}
				});
			}
			$(function() {
				$("#pageMsgTemplate").clone().attr("id","pageMsgTemplate").appendTo($("body"));
				animation.pageLoad();
				showDeafaultTable(1, pageSize);

				// 查询
				$('#queryBtn').on('click', function() {
					showDeafaultTable(1, pageSize);
					return false;
				});
			})
 



})
