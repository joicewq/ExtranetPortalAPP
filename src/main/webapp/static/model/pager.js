;define(["jquery", "jtemp","animation"], function($, jtemp,animation) {
	var pageSize = 10;
	function showDeafaultTable(columnId,pageNo, pageSize) {
		alert("请实现自己的分页查询!");
	}

	/**
	 * 根据用户输入跳转到第n页面
	 */
	function jumpPage(data) {
		var $page = $(data).parent().parent().find("input:first");
		if(isNaN($page.val())) {
			layer.msg('请输入正确的页数', {
				icon: 0
			});
			var curPageNo = $("#curPageid").val();
			$(".inp-number").val(curPageNo);
			return;
		} else {
			var pageNo = parseInt($page.val());
			if(isNaN(pageNo)) {
				pageNo = 1;
				$page.val(pageNo);
			}
			var totalPageNo = $(data).attr("totalPageAttr");
			/* if (pageNo <= 0)
				pageNo = 1; */
			if(pageNo < 1 || pageNo > totalPageNo) {
				layer.msg('请输入正确的页数', {
					icon: 0
				});
				var curPageNo = $("#curPageid").val();
				$(".inp-number").val(curPageNo);
				return;
			}
		}
		//pageNo = totalPageNo;
		pager.showDeafaultTable(columnId,pageNo, pageSize);
	}

	function selectPageSize(select) {
		pageSize = $(select).val();
		pager.showDeafaultTable(columnId,1, pageSize);
	}

	function afterSuccess() {}
	/**
	 * pageNo 第几页
	 * pageSize 页面记录条数
	 * url 获取table json 的链接请求，不带查询条件
	 * pageMsgTemplateId 分页信息所放的div ID
	 * tableTemplateId 生成的表放在哪div中(div ID)
	 * tableTemplateTxtId 表的模版
	 */
	function showTemplateTable(columnId,pageNo, pageSize, url, pageMsgTemplateId,
		tableTemplateId, tableTemplateTxtId, pageParams,callback) {
		animation.load({
			container:$("#" + tableTemplateId)
		});
		//请求参数，包括表单参数与分页参数
		var pageParam = {
			"columnId":columnId,
			"pageNo": pageNo,
			"pageSize": pageSize			 
		};
		$.extend(pageParams, pageParam); //将queryParams,pageParam合并到queryParams中,返回值为合并后的queryParams
		
		$.ajax({
			url: url,
			type: "post",
			data: pageParams,
			dataType: "json",
			success: function(page) {
				
				//支持多个分页div
				var pageDiv = pageMsgTemplateId.split(",");
				animation.destory({
					callback:function(){
						$("#" + tableTemplateId).setTemplateElement(
								tableTemplateTxtId);						
					}
				})
				$(pageDiv).each(
					function(i) {
						$("#pageMsgTemplate").clone().appendTo($("body"));
						page=typeof page == "object" ? page : JSON.parse(page);
						 
						var totalPage=page.totalCount/pageSize;
						if(page.totalCount%pageSize>0)
							totalPage++;
						page.curPage=parseInt(page.currentPage);
						page.pageLine=parseInt(page.pageLine);
						page.totalPage=parseInt(totalPage);
						page.totalRow=parseInt(page.totalCount);
						page.columnId=columnId;						
//						console.info("page",page);
						page.menuTitle=pageParam.menuTitle;
						
						 
						/*if(!isEmpty($("#breadcrumb").html())){
							var  breadcrumb=($("#breadcrumb").html()+"").split("</a>");
							console.info("split：",breadcrumb);
							page.menuTitle=breadcrumb[1];
						}*/
						console.info("page.menuTitle：",page.menuTitle);

						$('#' + this).setTemplateElement(
							"pageMsgTemplate");
						$('#' + this).processTemplate(page);
					});
				$("#" + tableTemplateId).processTemplate(page);
				//表格鼠标滑过行变色；奇偶行色不同
				$(".content_tab0 tr").mouseover(function() {
					$(this).addClass("over");
				}).mouseout(function() {
					$(this).removeClass("over");
				});
				$(".content_tab0 tr:odd").css("background-color",
					"#fff");
				$(".content_tab0 tr:even").css("background-color",
					"#F5F5F5");
				afterSuccess();
				callback && typeof callback == "function" && callback();
			},
			error: function(XMLHttpRequest, textStatus) {
				console.log(XMLHttpRequest);
			}
		});
	}
	
	 
		 

	/**
	 * pageNo 第几页
	 * pageSize 页面记录条数
	 * url 获取table json 的链接请求，不带查询条件
	 * pageMsgTemplateId 分页信息所放的div ID
	 * tableTemplateId 生成的表放在哪div中(div ID)
	 * tableTemplateTxtId 表的模版
	 */
	function showTemplateTableSolr(pageParams,pageNo, pageSize, url, pageMsgTemplateId,
		tableTemplateId, tableTemplateTxtId,callback) {
		animation.load({
			container:$("#" + tableTemplateId)
		});
//		//请求参数，包括表单参数与分页参数
//		var pageParam = {
//			"columnId":columnId,
//			"pageNo": pageNo,
//			"pageSize": pageSize			 
//		};
//		$.extend(pageParams, pageParam); //将queryParams,pageParam合并到queryParams中,返回值为合并后的queryParams

		url+=pageNo+"/"+pageSize;
		var params={
			query:"",
			station:0
		};
		params.query=JSON.stringify(pageParams);
		console.info("pageParams:",params.query);
		
		//请求模板	 
		$.post("/portal/queryTemplete",{
			testLink: window.location.origin
		},
		function(data,status){	
			var templeteInfo=[];
	         if (data.message === '[]' || data.message === undefined) {
	             templeteInfo = []
	         } else {
	             templeteInfo = data.message[0];
	         }
	         params.station=templeteInfo.stationId;     
		});
		
		$.ajax({
			url: url,
			type: "post",
			data: params,
			dataType: "json",
			success: function(page) {
				
				//支持多个分页div
				var pageDiv = pageMsgTemplateId.split(",");
				animation.destory({
					callback:function(){
						$("#" + tableTemplateId).setTemplateElement(
								tableTemplateTxtId);						
					}
				})
				$(pageDiv).each(
					function(i) {
						$("#pageMsgTemplate").clone().appendTo($("body"));
						page=typeof page == "object" ? page : JSON.parse(page);
						 
						var totalPage=page.sum/pageSize;
						if(page.sum%pageSize>0)
							totalPage++;
						page.curPage=parseInt(pageNo);
						page.pageLine=parseInt(pageSize);
						page.totalPage=parseInt(totalPage);
						page.totalRow=parseInt(page.sum);
						page.query=pageParams;
						
//						console.info("page:",page);

						$('#' + this).setTemplateElement(
							"pageMsgTemplate");
						$('#' + this).processTemplate(page);
						
					});
				$("#" + tableTemplateId).processTemplate(page);
				//表格鼠标滑过行变色；奇偶行色不同
				$(".content_tab0 tr").mouseover(function() {
					$(this).addClass("over");
				}).mouseout(function() {
					$(this).removeClass("over");
				});
				$(".content_tab0 tr:odd").css("background-color",
					"#fff");
				$(".content_tab0 tr:even").css("background-color",
					"#F5F5F5");
				afterSuccess();
				callback && typeof callback == "function" && callback();
				
				 $.each(page.data,function(index,item){ 
					var solrTitle=$("#solrTitle"+item.id).html()+"";
//					solrTitle=removeHTMLTag(solrTitle);
//					$("#solrTitle"+item.id).innerHTML=solrTitle;
					$("#solrTitle"+item.id).html(solrTitle);
//					 console.info("solrTitle:",$("#solrTitle"+item.id).html());
				});	
				
				
				 
			},
			error: function(XMLHttpRequest, textStatus) {
				console.log(XMLHttpRequest);
			}
		});
	}
	 

	/**
	 *单独应用一个table 后台传一个list json
	 * url 获取table json 的链接请求，不带查询条件
	 * tableTemplateId 生成的表放在哪div中(div ID)
	 * tableTemplateTxtId 表的模版
	 */
	function showTemplateTableList(url, tableTemplateId, tableTemplateTxtId,
		paramData) {
		var load = "<div align='center' style='height:220px;margin-top:100px;'><img alt='loadding...' src='" +
			"<c:url value='/static/js/bigloading.gif'/>" + "'/></div>";

		$("#" + tableTemplateId).empty().html(load);
		$
			.ajax({
				url: url,
				type: "post",
				data: paramData,
				dataType: "json",
				success: function(list) {
					$("#" + tableTemplateId).setTemplateElement(
						tableTemplateTxtId);
					$("#" + tableTemplateId).processTemplate(list);
					//表格鼠标滑过行变色；奇偶行色不同
					$(".content_tab0 tr").mouseover(function() {
						$(this).addClass("over");
					}).mouseout(function() {
						$(this).removeClass("over");
					});
					$(".content_tab0 tr:odd").css("background-color",
						"#fff");
					$(".content_tab0 tr:even").css("background-color",
						"#F5F5F5");
					if(list == null || (list && list.length == 0))
						$("#" + tableTemplateId)
						.append(
							'<div align="center" > <b sytle="color:red;">暂无数据</b></div>');
					afterSuccess();
				},
				error: function() {
					console.log('error');
				}
			});
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

	
	function pagerGlobal(pager){
		/*window.pager={
				showDeafaultTable:showDeafaultTable,
				jumpPage:jumpPage,
				selectPageSize:selectPageSize,
				afterSuccess:afterSuccess,
				showTemplateTable:showTemplateTable,
				showTemplateTableList:showTemplateTableList
		}*/
		window.pager=pager;
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
	
		
	
	return {
		/*showTemplateTable:showTemplateTable,
		showDeafaultTable:showDeafaultTable*/
		methods:{
			showDeafaultTable:showDeafaultTable,
			showTemplateTableSolr:showTemplateTableSolr,
			jumpPage:jumpPage,
			selectPageSize:selectPageSize,
			afterSuccess:afterSuccess,
			showTemplateTable:showTemplateTable,
			showTemplateTableList:showTemplateTableList
		},
		pagerGlobal:pagerGlobal
	}
})