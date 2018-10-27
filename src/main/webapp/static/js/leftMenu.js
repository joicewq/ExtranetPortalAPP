;define(["jquery","pager"],function($,pager){
	var url="";
	var breadcrumb="<a href='/salt/index'>首页</a>/";
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
					url=url;
					init(data);
				},
				error: function(){
					console.log("请求出错！")
				}
			})			
		}
		
		/*$(".menu-a").click(function(ele){			 
			console.info("ele:",ele.target);
			console.info("ele id:",ele.target.id);
		});
	 */
		
		function init(data){
			var h2 = "<h2 class='menu-h2'>";
				if(typeof data.icon != "undefined"){
					h2 += "<i class='"+data.icon+" font20'></i> ";
				}
				h2+=data.title+"</h2>";
			var ul = h2 + "<ul>";
			console.info("data:",data);			
			breadcrumb+=data.title;
			for(var i = 0; i <data.menu.length; i++){				
				if(data.menu[i].id==data.columnId){
					ul += "<li><a class='menu-a active'";
					//处理面包屑
					$("#breadcrumb").html(breadcrumb+"/"+data.menu[i].name);
				}
				else
				    ul += "<li><a class='menu-a'";
				    
				    ul +="href='javascript:void(0)' id='"+data.menu[i].id+"' type='"+
				          data.menu[i].columnType+"'>"+data.menu[i].name+"</a>" ;
				    
				if(data.menu[i].children){
					ul +="<ul id='"+data.menu[i].id +"' class='menu-list-second'>"
					for(var j = 0; j <data.menu[i].children.length; j++){
						if(data.menu[i].children[j].id==data.columnId){
							ul += "<li><a class='menu-a active'";		
							//处理面包屑
							$("#breadcrumb").html(breadcrumb+"/"+data.menu[i].children[j].name);
						}
						else						   
						    ul += "<li><a class='menu-a'";
						     
						ul += "href='javascript:void(0)' id='"+data.menu[i].children[j].id+"' type='"+
						      data.menu[i].children[j].columnType+"'>"+data.menu[i].children[j].name+"</a>"
					}
					ul += "</ul>";
				};
				ul += "</li>";		
			};
			ul += "</ul>";
			// $(ele).append(h2);
			$(ele).addClass("menu-list left-menu").html(ul);
			var first_href = data.menu[0].url;
//			$(ele+" .menu-a").first().addClass("active");
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
		
		
		
		pager.pagerGlobal(pager.methods);
		var callback = function(){
			$.each($("#policies-list-items").find(".content-item-date"),function(){
				$(this).text(new Date(parseInt($(this).text())).Format("yyyy-MM-dd"));			
			})
		}
		
		// 会员中心菜单点击
		$(ele).on("click",".menu-a", function(e){	
			var url="/portal/columnNewsPage";
			var pageNo=1;
			var pageSize=10;
			var $this = $(this);
			console.info("e :",e.target);
			console.info("e id:",e.target.id);
			console.info("e type:",e.target.type);			
			var columnId=e.target.id;
			var columnType=e.target.type;
			if(columnType==6)
				url="/portal/Questionnaire";
			//处理面包屑
			$("#breadcrumb").html(breadcrumb+"/"+$("#"+columnId).html());
//			showDeafaultTable(columnId,1,pageSize);
			pager.methods.showTemplateTable(columnId,pageNo, pageSize,url,
					"pagination","policies-list-items","tmpl",{},callback);

				$.ajax({
					url: "",
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