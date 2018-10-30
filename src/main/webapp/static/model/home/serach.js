require.config(config);
require([ "jquery", "leftMenu", "animation", "pager", "base" ], function($,
		menu, animation, pager, base) {
	      var query = { // 搜索条件
			flag : 1, // 1为简单查询，2为复杂查询
			simple : "", // 简单式
			complex : { // 复杂式
				allKey : "", // 包含全部关键词
				completeKey : "", // 完整关键词
				eitherKey : "", // 任意一个关键词
				excludeKey : "", // 不包含关键词
				updateTimeFlag : "1", // 时间
				updateTime : [], // 更新实际时间段
				filetype : [], // 文档类型
				sort : "1", // 排序
				keyLocation : "1" // 关键词位置
			}
	       };
			var pageSize = 10;
			var url = "/solr/queryPage/";
			var showDeafaultTable = pager.methods.showDeafaultTable = function(
					pageNo, pageSize) {
				 
				var title=$("#keyName").val();				 
				query.simple=title;
				pager.methods.showTemplateTableSolr(query,pageNo, pageSize, url,
						"pagination", "policies-list-items", "tmpl", afterSuccess)
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
				
				// 高级查询
				$('#queryBtn').on('click', function() {
					query.flag=1;
					query.simple=$("#title").val();					 
					$("#keyName").val(query.simple);
//					showDeafaultTable(1, pageSize);
					pager.methods.showTemplateTableSolr(query,1, pageSize, url,
							"pagination", "policies-list-items", "tmpl", afterSuccess)
					return false;
				});

				// 高级查询
				$('#superQueryBtn').on('click', function() {
					query.flag=2;
					var filetype=[];
					var updateTime=[];
					var updateTimeFlag='';
					var allKey=$("#allKey").val();
					var completeKey=$("#completeKey").val();
					var eitherKey=$("#eitherKey").val();
					var excludeKey=$("#excludeKey").val();
					var sort='';
					var keyLocation='';
					
					
					var names=document.getElementsByName("filetype");
					var updateTimeFlags=document.getElementsByName("updateTimeFlag");
					var sorts=document.getElementsByName("sort");
					var keyLocations=document.getElementsByName("keyLocation");
					for(var x=0;x<names.length;x++){
						if(names[x].checked){ 
							 filetype.push(names[x].value);
						}
					}
					for(var x=0;x<updateTimeFlags.length;x++){
						if(updateTimeFlags[x].checked){ 
							updateTimeFlag=updateTimeFlags[x].value;
							if(updateTimeFlag=='6'){
								updateTime.push($("#updateTime1").val());
								updateTime.push($("#updateTime2").val());
							}
							break;
						}
					}
					for(var x=0;x<sorts.length;x++){
						if(sorts[x].checked){ 
							sort=sorts[x].value;
							break;
						}
					}
					for(var x=0;x<keyLocations.length;x++){
						if(keyLocations[x].checked){ 
							keyLocation=keyLocations[x].value;
							break;
						}
					}
					 console.info("filetype:",filetype);
					 console.info("updateTimeFlag:",updateTimeFlag);
					 console.info("updateTime:",updateTime);
					 console.info("allKey:",allKey);
					 console.info("completeKey:",completeKey);
					 console.info("eitherKey:",eitherKey);
					 console.info("excludeKey:",excludeKey);
					 console.info("sort:",sort);
					 console.info("keyLocation:",keyLocation);
					 
					 query.complex.filetype=filetype;
					 query.complex.updateTimeFlag=updateTimeFlag;
					 query.complex.updateTime=updateTime;
					 query.complex.allKey=allKey;
					 query.complex.completeKey=completeKey;
					 query.complex.eitherKey=eitherKey;
					 query.complex.excludeKey=excludeKey;
					 query.complex.sort=sort;
					 query.complex.keyLocation=keyLocation;
					
					query.simple=$("#title").val();
					console.info("query:",query);
//					console.info("simple:",query.simple);
//					console.info("updateTimeFlag:",query.updateTimeFlag);
					$("#keyName").val(query.simple);
//					showDeafaultTable(1, pageSize);
					pager.methods.showTemplateTableSolr(query,1, pageSize, url,
							"pagination", "policies-list-items", "tmpl", afterSuccess)
					return false;
				});
			})
			
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
