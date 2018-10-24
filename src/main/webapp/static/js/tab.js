;define(["jquery"],function($){
	/**
	 * config
	 * */
	function tab(config){
		//Default Action
		$(".tab-content").hide(); //Hide all content
		$(".tabs").each(function(i){
			$(this).find("li:first").addClass("active").show()
		})
		$(".tab-contentmian").each(function(i){
			$(this).find(".tab-content:first").addClass("active").show()
		})
		
		//On Click Event
		$(".tabs li").bind("click",function() {
			$(this).parent().find("li").removeClass("active");
			$(this).addClass("active"); //Add "active" class to selected tab
			var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
			$(activeTab).siblings().hide();     //siblings()除了自己以外的同辈元素
			$(activeTab).fadeIn(); //Fade in the active content
			config.callback && typeof config.callback == "function" && config.callback($(this));
			return false;
		});
		
		//解除绑定按钮
		//$(".tabs li:not(:nth-child(1))").unbind("click");	
	}
	return tab
})