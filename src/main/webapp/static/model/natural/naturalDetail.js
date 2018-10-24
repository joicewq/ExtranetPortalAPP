require.config(config);
require(["jquery","Vue","base","animation"],function($,Vue,base,animation){
	var searchId = location.search.match(/\?id=(\S*)/)[1];
	$(function(){
		animation.load({
			container:$(".ds-main .content")
		})
		$.ajax({
			url:"/ni/getPublishById",
			type:"POST",
			data:{
				id:searchId
			},
			success:function(data){
				if(data){
					var content = data[0].content;
					var title = data[0].title;
					var modifyDate = new Date(data[0].publishDate).Format("yyyy-MM-dd");
					var naturalDetail = new Vue({
						el:"#natural-detail",
						data:{
							items:data,
							title:title,
							content:content,
							modifyDate:modifyDate
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