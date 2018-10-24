require.config(config);
require(["jquery","animation","validate"],function($,animation,validate){
	$(function(){
		$("#stopbrand").on("click",function(){
			jumbto("supplyedit.html")
		});
	})
})