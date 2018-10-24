require.config(config);
require(["jquery","Vue","base","animation"],function($,Vue,base,animation){
	var searchId = location.search.match(/\?id=([0-9a-zA-Z]*)/)[1];
	$(function(){
		animation.load({
			container:$(".ds-main .content")
		})
		$.ajax({
			url:"/ni/getNiByIdNatual",
			type:"POST",
			data:{
				id:searchId
			},
			success:function(data){
				if(data){
					var content = data[0].issueContent;
					var title = data[0].issueTitle;
					var modifyDate = new Date(parseInt(data[0].createDateIssue)).Format("yyyy-MM-dd");
					var naturalDetail = new Vue({
						el:"#natural-detail",
						data:{
							items:data,
							modifyDate:modifyDate,
							content:content
						},
						mounted:function(){
							animation.destory({
								callback:function(){
									$("#natural-detail").removeClass("hide");								
								}
							})
						}
					})
				}
			}
		})
	})
})