;define(["jquery"],function($){
	/**
	 * params $tabContent 选项卡切换用于显示的内容容器，接受jq选择器:$(xxx)，建议用id选择器
	 * parms $ul 选项卡切换标签容器ul，接受jq选择器:$(xxx)，建议用id选择器
	 * params tabLoadIndex 加载的gif的标记，默认为空，接受字符串
	 * params callback 选项卡初始化/切换时load的回调函数，接受函数，可解决加载页面后页面中js没调用
	 * params data-callback 选项卡初始化/切换后的回调函数，每次切换都会调用，接受函数
	 * params data-once-callback 选项卡初始化/切换后的回调函数，只在第一次点击时调用，接受函数
	 * params data-img 选项卡中是否插入图片，当该参数为true时可插入一张图片至选项卡
	 * params data-iframe 选项卡中是否插入iframe，当该参数为true时可插入一个iframe
	 * params data-id 当data-img或data-iframe为true时，data-id的值为插入的图片/iframe的id
	 * */
	function tabLoad($tabContent,$ul,callback){
		var loadGifSrc="/static/css/layer/default/loading-1.gif";
		
		
		var status={};
		status.onceCallbackStatus={};
		status.loadStatus={};
		var tabContentId=$tabContent.attr("id")?$tabContent.attr("id"):"load";
		
		//处理loadGIF的id
		var imgId=$("#load-gif-1").length==0?"load-gif-1":"load-gif-"+parseInt($("#load-gif-1").length)+1;
		
		//tab-li
		if($ul.parent(".tabsparent").length>0){
			var liWidthSum = calcLiWidthSum();
			var $ulWarp = $ul.parent(".tabsparent");
			var ulWarpWidth = $ulWarp.width();
			insertBtn();
			window.onresize=function(){
				liWidthSum = calcLiWidthSum();
				ulWarpWidth = $ulWarp.width();
				insertBtn();
			}
		}
		
		
		//<a>标签
		$ul.find("li").each(function(){
			var index = $(this).index();
			var $a = $(this).find("a");
			$a[0].index=index;
			$a[0].tabHref=$a.attr("data-href");
			status.loadStatus[$a[0].index]=false;
			createDiv($a,$tabContent);
		})
		
		//tab-content
		$tabContent.show();
		if($tabContent.find(".tab-content").length>0){
			$tabContent.find(".tab-content").show(); 		
		}
		var tabHref;
		$ul.each(function(i){
			$(this).find("li:first").addClass("active").show();
			var $a=$(this).find("li:first").find("a");
			$('<img id='+imgId+' src='+loadGifSrc+'></img>').css("margin","0 auto").appendTo($tabContent).show();
			status.loadStatus[$a[0].index]==false?divLoad($a,callback):"";
			status.showElement="#tab-"+tabContentId+$a[0].index;
			if($a.attr("data-once-callback")){
				eval($a.attr("data-once-callback"))("#tab-"+tabContentId+"-"+$a.parent().index());
				status.onceCallbackStatus[index]=true;
				$("#"+imgId).hide();
			}
			$a.attr("data-callback") && eval($a.attr("data-callback"))("#tab-"+tabContentId+"-"+$a.parent().index());	
		});
		
		$ul.find("li").click(function() {
			var $a = $(this).find("a");
			$(this).siblings("li.active").removeClass("active");
			$(this).addClass("active");
			var contentDivId="tab-"+tabContentId+"-"+$a[0].index;
			if(status.showElement != "#"+contentDivId){
				status.onceCallbackStatus[$a[0].index]=(status.onceCallbackStatus[$a[0].index]==undefined?false:true);
				$("#"+contentDivId).siblings("div").hide();
				$(status.showElement).hide();
				status.showElement = "#" + contentDivId;
				if(status.loadStatus[$a[0].index] == false || $a.attr("data-isLoad")=="true") {
					divLoad($a,callback);
				} else {
					$("#" + contentDivId).show();
					$("#" + imgId).hide();
				}
				if($a.attr("data-once-callback")) {
					status.onceCallbackStatus[$a[0].index] == false ? eval($a.attr("data-once-callback"))("#tab-" + tabContentId + "-" + $a[0].index) : "";
					status.onceCallbackStatus[$a[0].index] = true;
					$("#"+imgId).hide();
				}
				$a.attr("data-callback") && eval($a.attr("data-callback"))("#tab-" + tabContentId + "-" + $a.parent().index());	
			}
			else{
				return false;
			}
		}); 
		
		function createDiv($a,$tabContent){
			var $div=$("<div></div>");
			var index=$a.parent().index();
			var divId=$tabContent.attr("id")==undefined?"load":$tabContent.attr("id");
			$div.css({
				"width":"100%",
				"height":"100%"
			}).attr("id","tab-"+tabContentId+"-"+index).hide().appendTo($tabContent);	 	
		}
		
		function divLoad($a,callback){
			$("#"+imgId).show();
			var divId="tab-"+tabContentId+"-"+$a[0].index;
			if($a.attr("data-img")=="true" || $a.attr("data-iframe")=="true"){
				var eleId = $a.attr("data-id")||"";
				var eleClassName = $a.attr("data-class")||"";
				var $ele;
				if($a.attr("data-img")=="true"){
					$ele=$("<img></img>").attr("src",$a[0].tabHref);
					$("#"+imgId).hide();
					$("#"+divId).show();
				}
				else{
					$ele=$("<iframe></iframe>").attr("src",href).width("100%");
				}
				$ele.attr("id",eleId).addClass(eleClassName).appendTo($("#"+divId));
			}
			
			else if($a[0].tabHref){
				var willCallback=false;
				pageLoad($("#"+divId),$a[0].tabHref,function(callback){
					$("#"+imgId).hide();
					if($ul.find("li").eq($a[0].index).hasClass("active")){
						$("#"+divId).show();	 				
					}
					willCallback=true;
				});
				var isFinished=setInterval(function(){
					if(willCallback){
						callback && callback();	 
						clearInterval(isFinished);
					}
				},1000);
			}
			status.loadStatus[$a[0].index]=true;
		}
		
		
		function pageLoad(container,url,callback){
			if(container && url){
				$.ajax({
					url:url,
					dataType:"html",
					success:function(data){
						callback && typeof callback=="function" && callback();
						container.html(data);
					}
				})			 
			}
		}
		
		
		function calcLiWidthSum(){
			var liWidthSum = 0;
			$ul.find("li").each(function(){
				var ml = parseFloat($(this).css("marginLeft"));
				var mr = parseFloat($(this).css("marginRight"));
				liWidthSum += $(this).outerWidth()+ml+mr;
			})
			return liWidthSum;
		}
		function insertBtn(){
			if(liWidthSum>ulWarpWidth){
				var leftBtn = $(".fa-angle-left.ulBtn").length>0?$(".fa-angle-left.ulBtn"):$("<i class='fa fa-angle-left ulBtn'></i>");
				var rightBtn = $(".fa-angle-right.ulBtn").length>0?$(".fa-angle-right.ulBtn"):$("<i class='fa fa-angle-right ulBtn'></i>");
				$ulWarp.css({
					"position":"relative",
					"overflow":"hidden"
				});
				$ul.width(liWidthSum+10);
				if($(".fa-angle-left.ulBtn").length==0){
					leftBtn.prependTo($ulWarp).css("left","0px").hide();
					rightBtn.appendTo($ulWarp).css("right","0px");
				}
				$(".ulBtn").css({
					"position":"absolute",
					"top":"0px",
					"cursor":"pointer",
					"padding":"11px 5px",
					"background":"rgba(0,0,0, .3)",
					"color":"#fff",
					"zIndex":10
				});
				rightBtn.on("click",function(){
					var ulMarginLeft=parseFloat($ul.css("marginLeft"));
					var hideWidth = liWidthSum-ulWarpWidth+ulMarginLeft;
					var skewingR = Math.abs(ulMarginLeft)+(hideWidth > ulWarpWidth?ulWarpWidth*0.5:hideWidth);
					$ul.animate({"marginLeft":-skewingR+"px"},1000,function(){
						ulMarginLeft=parseFloat($ul.css("marginLeft"));
						if(Math.abs(ulMarginLeft)+ulWarpWidth==liWidthSum)
							rightBtn.hide(); 					
					});
					if(leftBtn.css("display")=="none")
						leftBtn.show();
				});
				leftBtn.on("click",function(){
					var ulMarginLeft=parseFloat($ul.css("marginLeft"));
					var hideWidth = Math.abs(ulMarginLeft);
					var skewingL =  Math.abs(ulMarginLeft)-(hideWidth>ulWarpWidth?ulWarpWidth*0.5:hideWidth);
					$ul.animate({"marginLeft":-skewingL+"px"},1000,function(){
						if(parseFloat($ul.css("marginLeft"))==0)
							leftBtn.hide();
					});
					if(rightBtn.css("display")=="none")
						rightBtn.show();
				});
			}
			else{
				$(".fa-angle-left.ulBtn").length>0 && $(".fa-angle-left.ulBtn").remove();
				$(".fa-angle-right.ulBtn").length>0 && $(".fa-angle-right.ulBtn").remove();
				$ul.css("marginLeft","0px").width("auto");
			}
		}
	}	 
	return tabLoad;
})