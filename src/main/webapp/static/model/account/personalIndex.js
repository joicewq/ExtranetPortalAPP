require.config(config);
require(["jquery","layer","animation","validate"],function($,layer,animation,validate){
	$(function(){

		//会员中心,默认选中第一个标签
		selectIndexMenu();
		
		// 会员中心菜单点击
		$("#menu").unbind("dbclick",".menu-a",function(){
			return ;
		});
		$("#menu").on("click",".menu-a", function(){
			$("#showPage").val("");
			var $this = $(this);
			var href = $this.attr("data-href");
			if(href){
				$.ajax({
					url: href,
					type: "get",
					dataType: "html",
					cache: false,
					success: function(data){
						console.log(data)
						$("#rightContent").html('');
						$("#rightContent").html(data);
						$("#menu .menu-a").removeClass("active");
						$this.addClass("active");
						$this.parent().siblings().find("ul:first").slideUp();
						$this.parent().siblings().children("a").find("i").removeClass("fa-sort-up").addClass("fa-sort-down").css("top","5px");
						if(history.pushState){
							var showpage = $this.attr("data-showpage");
							history.pushState({},{},window.location.pathname +'?showPage='+ showpage)
						}else{
							return;
						}
					},
					error: function(){
						console.log("找不到"+href);
					}
				})
			}
			if($this.next("ul").css("display")=="none"){
				var uls = $(this).parent().siblings().find("ul:first");
				var ies = $(this).parent().siblings().find("i:first");
				uls.each(function(){
					$(this).slideUp();
				});
				ies.each(function(){
					$(this).removeClass("fa-sort-up").addClass("fa-sort-down").css("top","5px");
				});
				$this.next("ul").slideDown();
				$this.children("i").removeClass("fa-sort-down").addClass("fa-sort-up").css("top","12px");
			}else{
				$this.next("ul").slideUp();
				$this.children("i").removeClass("fa-sort-up").addClass("fa-sort-down").css("top","5px");
			}

		});
	})
	
	/**
	 * 会员中心默认选中第一个标签
	 */
	function selectIndexMenu(){
		$("#menu .menu-a").removeClass('active');
		var showPage=$("#showPage").val();
		showPage ? showPage : showPage = '0_0'
		var first_href = $("#menu >.menu-a").first().attr("data-href");
		if(showPage!=""){
			var pageArr=showPage.split("_");
			if(pageArr.length==2){
				$("#menu>ul>li>.menu-a").eq(pageArr[0]).find("i").removeClass("fa-sort-down");
				$("#menu>ul>li>.menu-a").eq(pageArr[0]).find("i").addClass("fa-sort-up");
				first_href=$("#menu>ul>li>.menu-a").eq(pageArr[0]).next().find(".menu-a").eq(pageArr[1]).attr("data-href");
				$("#menu>ul>li>.menu-a").eq(pageArr[0]).next().slideDown();
				$("#menu>ul>li>.menu-a").eq(pageArr[0]).next().find(".menu-a").eq(pageArr[1]).addClass("active");
			}else{
				first_href=$("#menu>ul>li>.menu-a").eq(pageArr[0]).attr("data-href");
				$("#menu>ul>li>.menu-a").eq(pageArr[0]).addClass("active");
			}
		}else{
			$("#menu>ul>li>.menu-a").first().addClass("active");
		}

		$.ajax({
			url: first_href,
			type: "get",
			dataType: "html",
			cache: false,
			success: function(data){
				$("#rightContent").html(data);
			},
			error: function(){
				console.log("找不到"+first_href);
			}
		});
	}

	// ajax进来的页面需要跳转页面使用此方法在ID容器内跳转
	function jumbto(url){
		animation.load({container:$("#rightContent")});
		$.ajax({
			url: url,
			type: "get",
			dataType: "html",
			cache: false,
			success: function(data){
				$("#rightContent").html(data);
			},
			error: function(){
				console.log("找不到"+url);
			}
		})
	}
	window.jumbto=jumbto;
})