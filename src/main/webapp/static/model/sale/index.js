require.config(config);
require(["jquery","validate","base","animation","pager"],function($,validate,base,animation,pager){
 	var pageSize = 10;

 	var url = "/sale/query";
 	var showDeafaultTable=pager.methods.showDeafaultTable= function(pageNo, pageSize) {
 		pager.methods.showTemplateTable(pageNo, pageSize, url, "pagination", "view", "tmpl",
 				base.serializeObject($("#rf")));
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
		jumbto("/sale/show?id="+id);
	}
	//删除
	function deleteBtn(ids) {
		layer.confirm('请确认是否删除', {
			title : "信息"
		}, function(index) {
			$.ajax({
				url : "/sale/delete",
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
		jumbto("/sale/eidtById?id="+ids);
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
 			jumbto("/sale/add");
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