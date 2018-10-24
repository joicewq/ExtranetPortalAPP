require.config(config);
require(["jquery","layer","animation","validate"],function($,layer,animation,validate){
	$(function(){

		function GetParameter(name)
		{
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substring(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
		function setmenu(ele,url){
			if($(ele)){
				$.ajax({
					url: "/static/data/helpmenu.json",
					type: 'get',
					dataType: 'json',
					success: function(data){
						var menu = '<h2 class="helpmenu-title">'+data[0].title+'</h2>'
						menu += '<dl>';
						for(var i=0; i < data[0].menu.length; i++){
							if(data[0].menu[i].url){
								menu += '<dd><a id="'+data[0].menu[i].id+'" href="javascript:void(0)" data-href="'+data[0].menu[i].url+'">'+data[0].menu[i].name+'</a></dd>'
							}else{
								menu += '<dt>'+data[0].menu[i].name+'</dt>';
							}
						}
						menu += '</dl>';
						$(ele).html(menu);
						var href = GetParameter("id");
						var open_href = $("#"+href).attr("data-href");
						$("#"+href).addClass("active");
						if(open_href){
							$.ajax({
								url: open_href,
								type: "get",
								dataType: "html",
								cache: false,
								success: function(data){
									$("#helpcenter-content").html(data);
								},
								error: function(){
									console.log("找不到"+open_href);
								}
							})
						}
					},
					error: function(){
						console.log("请求出错！")
					}
				})
			}else{
				console.log("容器不存在!")
			}
		}
		setmenu("#help-menu","/static/data/helpmenu.json");

		//帮助中心,默认选中第一个标签
		// 帮助中心菜单点击
		$("#help-menu").on("click","a", function(){
			var $this = $(this);
			var href = $this.attr("data-href");
			var id = $(this).attr('id');
			if(href){
				$.ajax({
					url: href,
					type: "get",
					dataType: "html",
					cache: false,
					success: function(data){
						$("#helpcenter-content").html(data);
						$("#help-menu a").removeClass("active");
						$this.addClass("active");
						history.pushState({},{},'index?id='+id);
					},
					error: function(){
						console.log("找不到"+href);
					}
				})
			}
		});
	})
	
})