require.config(config);
require([ "jquery", "animation","base" ], function($, animation,base) {
	function queryPage(pageNo,pageSize) {
		$.ajax({
			url : '/vipcenter/systemIndexQuery',
			type : "post",
			cache : false,// 再请求不读缓存
			data : {
				'pageSize' : pageSize,
				'pageNo' : pageNo
			},
			dataType : "json",
			success : function(data) {
				$("#zwxx").hide(sum);		
				if (data.code == 0) {
					if(data.list.length > 0){
						loadView(data.list);
						// 加载分页信息
						$("#page-index").html(pageNo);
						var sum = Math.ceil(data.count/10);
						$("#page-sum").html(sum);	
						$("#pageNum").val(pageNo);
					} else {
						$("#zwxx").show(sum);		
					}
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
	}
	// 加载内容
	function loadView(list) {
		$("#msgView").html('');
		$.each(list, function (index, item) {
			var $liStr=$($("#template").html());
			$("#msgView").append($liStr);
			$("#msgView li").last().find(".biaoti").html(item.title).css('cursor','pointer');//鼠标悬浮变手指
			$("#msgView li").last().find(".riqi").html(new Date(item.creatDate).Format("yyyy-MM-dd"));//格式化日期
			//如果是保存和未读状态，则标记为红色
			if(item.msgSta == 1 || item.msgSta == 0){
				$("#msgView li").last().addClass("text-red");
			}
			//绑定点击事件
			$("#msgView li").last().find(".biaoti").on("click",function(){
				jumbto('/vipcenter/toSystemInfo?id='+item.id);
			})
			
		});
	}
	$(function($) {
		queryPage(1, 10);
		//页数框焦点移开校验
		$("#pageNum").on("blur",function(){
			var num = $(this).val();
			var sum = $("#page-sum").html();
			if(isNaN(num) || num <= 0 || num > sum){
				$(this).val(1)
			}
		});
		//首页
		$("#pageIndex").on("click",function(){
			queryPage(1, 10);
		});
		//上一页
		$("#pageUp").on("click",function(){
			var index = $("#pageNum").val();
			var sum = $("#page-sum").html();
			if(isNaN(index) || index-1 <= 0){
				return false;
			}	
			queryPage(index-1, 10);
		});		
		//下一页
		$("#pageNext").on("click",function(){
			var index = $("#pageNum").val();
			var sum = $("#page-sum").html();
			if(isNaN(index) || index+1 > sum){
				return false;
			}	
			queryPage(index+1, 10);			
		});
	});
});