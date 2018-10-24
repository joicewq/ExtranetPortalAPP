require.config(config);
require(["jquery","validate","local","animation"],function($,validate,local,animation){
	var pageSize = 10;

	var url = "/prodlib/query";
	function showDeafaultTable(pageNo, pageSize) {
		showTemplateTable(pageNo, pageSize, url, "pagination", "view", "tmpl",
				base.serializeObject($("#rf")));
	}

	function deleteByIds(ids) {
		var url = "/prodlib/queryLast";
	  	$.get(url,{preId:ids.join(',')},function(result){
	  		var json=eval("("+result+")");
	  		if(json.length>0){
	  			layer.msg('当前记录包含下级分类，不可删除', {icon: 0});
	  		} else {
	  			layer.confirm('请确认是否删除', {
	  				btn: ['确定', '取消']
	  			,yes: function(index){
	  				layer.close(index);
	  				var url = "/category/delete";
	  				$.get(url,{id:ids.join(',')},function(result){
	  					if(result=="success"){
	  						layer.msg('操作成功', {icon: 1});
	  						showDeafaultTable(1, pageSize);
	  					}else if(result=="error"){
	  						layer.msg('操作失败', {icon: 2});
	  					}
	  				});
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
		
	$(function($) {
		showDeafaultTable(1, pageSize);
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
		
		//新增
		$('#addBtn').bind('click', function() {
			var index= layer.open({
		        type: 2,
		        title: '新增信息',
		        maxmin: false,
		        shadeClose: false,
		        closeBtn: 1,
		        area : ['500px' , '300px'],
		        content: '/prodlib/edit?pageType=1'
		    });
			return false;
		});
		
		//修改
		$('#updateBtn').on('click', function() {
			var ids = getSelectIds('category');
			if (ids.length == 1) {
				var index= layer.open({
			        type: 2,
			        title: '编辑信息',
			        maxmin: false,
			        shadeClose: false,
			        closeBtn: 1,
			        area : ['500px' , '300px'],
			        content: '/prodlib/edit?pageType=2&id='+ids[0]
			    });
				return false;
			} else {
				layer.msg('请选择一条记录', {
					icon : 0
				});
			}
			return false;
		});
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