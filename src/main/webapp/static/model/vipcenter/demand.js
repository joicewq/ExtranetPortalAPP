require.config(config);
require(["jquery","validate","base","animation","pager","underscore","layer"],
	
	function($,validate,base,animation,pager,_,layer){
		
		var pageSize = 10;
	
		var url = "/demand/query";
		var showDeafaultTable=pager.methods.showDeafaultTable= function(pageNo, pageSize) {
			pager.methods.showTemplateTable(pageNo, pageSize, url, "pagination12", "view12", "tmpl12",
					base.serializeObject($("#rf")));
		}
		pager.pagerGlobal(pager.methods);
		
		function deleteByIds(ids) {
			layer.confirm('请确认是否删除', {
				btn: ['确定', '取消'],
				yes: function(index){
					layer.close(index);
					var url = "/demand/delete";
					$.get(url,{ids:ids.join(',')},function(result){
						if(result.code=="1"){
							layer.msg('操作成功', {icon: 1});
							pager.methods.showDeafaultTable(1, pageSize);
						}else if(result.code=="0"){
							layer.msg('操作失败', {icon: 2});
						}
					});
				}
		  	});
		}
		
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
		function getSelectSta(clazz) {
			var sta = "";
			$("." + clazz+  ":checked").each(function(i){
				sta = $(this).attr("sta");
			});
			return sta;
		}
		//查看跳转
		function show() {
			var id = $(this).parent().parent().attr("id");
			$("#rightContent").load('/demand/info?id='+id);
		}
		//更新方法
		function update(ids,productState,info){
			layer.confirm('请确认是否'+info, {
				btn: ['确定', '取消'],
				yes: function(index){
					layer.close(index);
					$.get("/demand/updateState",{ids:ids.join(','),productState:productState},function(result){
						if(result.code=="1"){
							layer.msg('操作成功', {icon: 1});
							pager.methods.showDeafaultTable(1, pageSize);
						}else if(result.code=="0"){
							layer.msg('操作失败', {icon: 2});
						}
					});
				}
		  	});
		}
		
		$(function($) {
			pager.methods.showDeafaultTable(1, pageSize);
			//清空
			$('#resetBtn').on('click', function() {
				$('#rf')[0].reset();
				return false;
			});
			//查询
			$('#queryBtn').on('click', function() {
				showDeafaultTable(1, pageSize);
				return false;
			});
			//挂牌
			$('#listedBtn').on('click', function() {
				var ids = getSelectIds('category');
				var sta = getSelectSta('category');
				if (ids.length == 1) {
					if(sta==1||sta==3||sta==6){
						layer.msg('当前记录不允许操作', {icon : 0});
					}else{
						update(ids,4,"挂牌");
					}
				} else {
					layer.msg('请选择一条记录', {icon : 0});
				}
				return false;
			});
			//停牌
			$('#delistedBtn').on('click', function() {
				var ids = getSelectIds('category');
				var sta = getSelectSta('category');
				if (ids.length == 1) {
					if(sta==1||sta==3||sta==6){
						layer.msg('当前记录不允许操作', {icon : 0});
					}else{
						update(ids,5,"停牌");
					}
				} else {
					layer.msg('请选择一条记录', {icon : 0});
				}
				return false;
			});
			//查看
			$('#view12').on('click','#showBtn',show);
			//删除
			$('#delBtn').on('click', function() {
				var ids = getSelectIds('category');
				if (ids.length > 0) {
					deleteByIds(ids);
				} else {
					layer.msg('请选择一条记录', {
						icon : 0
					});
				}
				return false;
			});
		});
		  
		
});