;define(["jquery"],function($){
	/**
	 * 左侧菜单生成
	 * @param ele:菜单外层元素，接受字符串
	 * @param url:菜单内容数据，接受对象或者URL，数据中title为菜单标题，icon为菜单标题中的图标
	 * */
	function setmenu(ele,url,callback){
		if(typeof url=="object"){
			init(url);
		}
		else{
			$.ajax({
				url: url,
				type: "get",
				dataType: "json",
				success: function(data){
					init(data);
				},
				error: function(){
					console.log("请求出错！")
				}
			})			
		}
		
		function init(data){
			var h2 = "<h2 class='menu-h2'>";
				if(typeof data.icon != "undefined"){
					h2 += "<i class='"+data.icon+" font20'></i> ";
				}
				h2+=data.title+"</h2>";
			var ul = h2 + "<ul>";
			for(var i = 0; i <data.menu.length; i++){
				ul += "<li><a class='menu-a' href='javascript:void(0)'>"+data.menu[i].name+"</a>" ;
				if(data.menu[i].childs){
					ul +="<ul class='menu-list-second'>"
					for(var j = 0; j <data.menu[i].childs.length; j++){
						ul += "<li><a class='menu-a' href='javascript:void(0)'>"+data.menu[i].childs[j].name+"</a></li>" 
					}
					ul += "</ul>";
				};
				ul += "</li>";		
			};
			ul += "</ul>";
			// $(ele).append(h2);
			$(ele).addClass("menu-list left-menu").html(ul);
			var first_href = data.menu[0].url;
			$(ele+" .menu-a").first().addClass("active");
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
		
		// 会员中心菜单点击
		$(ele).on("click",".menu-a", function(){
			var $this = $(this);
			var href = $this.attr("data-href");
			if(href){
				$.ajax({
					url: href,
					type: "get",
					dataType: "html",
					cache: false,
					success: function(data){
						$("#rightContent").html(data);
						$(ele+" .menu-a").removeClass("active");
						$this.addClass("active");
						$this.parent().siblings().find("ul:first").slideUp();
						$this.parent().siblings().children("a").find("i").removeClass("fa-sort-up").addClass("fa-sort-down").css("top","5px");
					},
					error: function(){
						console.log("找不到"+href);
					}
				})
			}
			if($this.next("ul").length==0){
				$(ele+" .menu-a").removeClass("active");
				$this.addClass("active");
				$this.parent().siblings().find("ul:first").slideUp();
				$this.parent().siblings().children("a").find("i").removeClass("fa-sort-up").addClass("fa-sort-down").css("top","5px");
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
			callback && typeof callback=="function" && callback($this);
		});
	};
	
	return {
		setmenu:setmenu
	};
})