;define(["jquery"],function($){
	
	function isSupportAnimationFn(){
		   var div = document.createElement('div'),
		      vendors = 'Ms O Moz Webkit'.split(' '),
		      len = vendors.length;
		 
		   return (function(prop) {
		      if ( prop in div.style ) return true;
		 
		      prop = prop.replace(/^[a-z]/, function(val) {
		         return val.toUpperCase();
		      });
		 
		      while(len--) {
		         if ( vendors[len] + prop in div.style ) {
		            return true;
		         } 
		      }
		      return false;
		   })("animation");
	}
	
	function createAnimationWrap(config){
		var isSupportAnimation=false;
		var $wrap = $("<div class='animation-wrap'></div>");
		var $spinner = $("<div class='animation-spinner'></div>");
		if(localStorage){
			if(localStorage.getItem("isSupportAnimation")){
				isSupportAnimation=localStorage.getItem("isSupportAnimation")=="true"?true:false;
			}
			else{
				isSupportAnimation=isSupportAnimationFn();
				localStorage.setItem("isSupportAnimation",isSupportAnimation);
			}			
		}
		else{
			isSupportAnimation=isSupportAnimationFn();
		}
		if(isSupportAnimation==true){
			var rectDiv = "";
			for(var i=1;i<6;i++){
				rectDiv += "<div class=rect"+i+"></div>";
			}
			$spinner.html(rectDiv).appendTo($wrap);
			if(config && config.text){
				var $span = $("<span class='animation-text'></span>").html(config.text);
				$wrap.addClass("has-text").append($span);
			}						
		}
		else{
			var $img = $("<img>");
			$img.attr("src","/static/images/loading.gif");
			$spinner.append($img).appendTo($wrap);
		}
		return $wrap;
	}
	
	/**
	 * function pageLoad(config){}页面打开时执行的过渡动画
	 * config{
	 * 	callback:
	 * }
	 * */
	function pageLoad(config){
		var $wrap=createAnimationWrap(config);
		$wrap.addClass("fixed");
		$("body").append($wrap);
	}
	
	/**
	 * function load(config){}创建一个动画插入指定div
	 * config:{
	 * container:指定动画要出现的容器
	 * callback:动画插入后的回调函数
	 * url:动画插入后要加载的页面
	 * loadCallback:url指定页面加载完成后的回调函数
	 * }
	 * */
	function load(config){
		var $ele = config.container;
		if($ele.length>0){
			var $wrap=createAnimationWrap(config);
			$ele.height()==0 && $ele.css("min-height",260);
			$ele.css("position","relative").append($wrap);
			config.callback && typeof config.callback == "function" && config.callback($wrap);
			if(config.url){
				$.ajax({
					url: config.url,
					type:"get" || config.type,
					dataType: "html",
					data:{}||config.params,
					success:function(data){
						$ele.html(data);
						config.loadCallback && typeof config.loadCallback == "function" && config.loadCallback($ele,data);
					}
				})
			}
		}
	}
	
	/**
	 * config:{
	 * 	container:指定元素，否则移除全部$(".animation-wrap")
	 * 	callback:function(){}
	 * }*/
	function destory(config){
		var $ele = $(".animation-wrap");
		if(config){
			if(config.container){
				$ele = config.container;
			} 
		}
		$ele.remove();
		config && config.callback && typeof config.callback == "function" && config.callback();
	}

	var animation={
		pageLoad:pageLoad,
		load:load,
		destory:destory
	}
	window.animation=animation;
	return animation
});