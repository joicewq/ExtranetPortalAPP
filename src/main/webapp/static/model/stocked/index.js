require.config(config);
require(["jquery","validate","base","animation","pager"],function($,validate,base,animation,pager){
 	var pageSize = 10;

 	var url = "/stocked/query";
 	var showDeafaultTable=pager.methods.showDeafaultTable= function(pageNo, pageSize) {
 		pager.methods.showTemplateTable(pageNo, pageSize, url, "pagination", "view", "tmpl",
 				base.serializeObject($("#rf")),afterSuccess);
 	}
 	
	//日期格式转换
	var afterSuccess=function(){
		/*$("#tableTemplate tr").each(function() {
			var tdEle = $(this).find("td").eq(1);
			if(tdEle.text().length > 0) {
				var d = new Date(parseInt(tdEle.text()));
				tdEle.text(d.Format("yyyy-MM-dd"));
			}
		});*/
	}
	//列表状态转换
	pager.methods.stautsChange=function(approvalStatus){
		var stauts="";
		if(approvalStatus==10){
			return stauts="";
		}else if(approvalStatus==11){
			return stauts="审核同意";
		}else if(approvalStatus==12 ){
			return stauts="审核驳回";
		}
	}
	pager.methods.showDetail=function(approvalStatus){
		var status="";
		if(approvalStatus!="" && typeof(approvalStatus)!="undefined"){
		  return status="详情"
		}else{
		 return	status=""
		}
	}
	//详情
	pager.methods.showDetail2=function(auditorDate,auditor,auditorSuggestion) {
		var paras = "approvalPerson=" + auditor + "&modifyDate=" + auditorDate + "&approvalSuggestion=" + auditorSuggestion;
		window.location.href="/natural/showDetailHis?" + paras
	}
	
	//列表状态转换
	pager.methods.status=function(status){
		var stauts="";
		if(status!=""){
			return stauts="有";
		}else{
			return stauts="无";
		}
	}
	pager.pagerGlobal(pager.methods);
	
	function getSelectIds(clazz) {
		var idArray = $("." + clazz);
		var ids = [];
		for (var i = 0; i < idArray.length; i++) {
			if (idArray[i].checked) {
				ids.push(idArray[i].name);
			}
		}
		return ids;
	}
	function show(id) {
		jumbto("/stocked/show?id="+id);
	}
	//删除
	function deleteBtn(ids) {
		layer.confirm('请确认是否删除', {
			title : "信息"
		}, function(index) {
			$.ajax({
				url : "/stocked/delete",
				type : "post",
				cache : false,
				data : {
					'ids' : ids.join(',')
				},
				dataType : "json",
				success : function(data) {
					if (data.code == 1) {
						layer.msg('操作成功', {
							icon : 1
						});
						setTimeout(function(){
							showDeafaultTable(1, pageSize);
						}, 2000);
					} else {
						layer.msg('操作失败', {
							icon : 2
						});
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					layer.msg('操作失败', {
						icon : 2
					});
				}
			});
		});
	}
	//修改
	function updateBtn(ids) {
		jumbto("/stocked/eidtById?id="+ids);
	}
	
	$(function($) {
		pager.methods.showDeafaultTable(1, pageSize);
	
		//查询
		$('#queryBtn').on('click', function() {
			showDeafaultTable(1, pageSize);
			return false;
		});
		
		//新增
 		$('#addBtn').bind('click', function() {
 			jumbto("/stocked/add");
 			return false;
 		});
		
 		//删除
		$("#deleteBtn").on("click", function() {
			var ids = getSelectIds('stocked');
			if(ids.length == 1) {
				deleteBtn(ids);
			} else {
				layer.msg('请选择一条记录', {
					icon: 7
				});
			}
			return false;
		});
		
		//详情
		$("#detailBtn").on("click", function() {
			var ids = getSelectIds('stocked');
			if(ids.length == 1) {
				show(ids);
			} else {
				layer.msg('请选择一条记录', {
					icon: 7
				});
			}
			return false;
		});
 		
		//修改
		$('#updateBtn').on('click', function() {
			var ids = getSelectIds('stocked');
			if (ids.length > 0) {
				updateBtn(ids);
			} else {
				layer.msg('请选择一条记录', {
					icon : 0
				});
			}
			return false;
		});
		
	});
});