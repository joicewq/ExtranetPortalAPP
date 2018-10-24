require.config(config);
require(["jquery","validate","base","animation","pager"],function($,validate,base,animation,pager){
 	var pageSize = 10;

 	var url = "/natural/query";
 	var showDeafaultTable=pager.methods.showDeafaultTable= function(pageNo, pageSize) {
 		pager.methods.showTemplateTable(pageNo, pageSize, url, "pagination", "view", "tmpl",
 				base.serializeObject($("#rf")),afterSuccess);
 	}
 	
	//日期格式转换
	var afterSuccess=function(){
		$("#tableTemplate tr").each(function() {
			var tdEle = $(this).find("td").eq(1);
			if(tdEle.text().length > 0&&tdEle.text()!=''&&tdEle.text()!='无符合条件数据') {
				var d = new Date(parseInt(tdEle.text()));
				tdEle.text(d.Format("yyyy-MM-dd"));
				tdEle.attr('title',d.Format("yyyy-MM-dd"))
			}
		});
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
		var paras = "approvalPerson=" + encodeURI(encodeURI(auditor)) + "&modifyDate=" + auditorDate + "&approvalSuggestion=" + encodeURI(encodeURI(auditorSuggestion));
		jumbto("/natural/showDetailHis?" + paras)
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
	//警用或解禁
	function deleteBtn(ids) {
		layer.confirm('请确认是否删除', {
			title : "信息"
		}, function(index) {
			$.ajax({
				url : "/natural/delete",
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
	
	$(function($) {
		pager.methods.showDeafaultTable(1, pageSize);
	
		//查询
		$('#queryBtn').on('click', function() {
			showDeafaultTable(1, pageSize);
			return false;
		});
		
		//新增
 		$('#addBtn').bind('click', function() {
// 			window.location.href="/natural/add";
 			jumbto("/natural/add");
 			return false;
 		});
		
 		//删除
		$("#deleteBtn").on("click", function() {
			var ids = getSelectIds('associator');
			var idArray = $(".associator");
			var approval ="";
			for (var i = 0; i < idArray.length; i++) {
				if (idArray[i].checked) {
					approval = $(idArray[i]).parent().parent().find("td").eq(7).attr("id");
				}
			}
			if(ids.length <= 0 || ids.length > 1){
				layer.msg('请选择一条记录', {
					icon: 7
				});
			}else if(approval!=12){
				layer.msg('该记录不能删除', {
					icon: 7
				});
			}else if(ids.length == 1) {
				deleteBtn(ids);
			} 
			return false;
		});
		//编辑
		$("#editBtn").on("click", function() {
			var ids = getSelectIds('associator');
			var idArray = $(".associator");
			var approval ="";
			for (var i = 0; i < idArray.length; i++) {
				if (idArray[i].checked) {
					approval = $(idArray[i]).parent().parent().find("td").eq(7).attr("id");
				}
			}
			if(ids.length <= 0 || ids.length > 1){
				layer.msg('请选择一条记录', {
					icon: 7
				});
			}else if(approval!=12){
				layer.msg('该记录不能修改', {
					icon: 7
				});
			}else if(ids.length == 1) {
				jumbto("/natural/editById?id="+ids);
			} 
			return false;
		});
 		
		//详情
		$('#detailBtn').on('click', function() {
			var ids = getSelectIds('associator');
			if (ids.length == 1 ) {
				jumbto("/natural/detail?id="+ids);
			} else {
				layer.msg('请选择一条记录', {
					icon : 0
				});
			}
			return false;
		});
		
		if($("#approval-status").val()!="11"){
    		layer.msg('企业信息审核未通过，沒有操作权限！', {
				icon: 1,
				time:3000
			});
    	}
		
	});
});