require.config(config);
require(["jquery","Vue","base","animation"],function($,Vue,base,animation){
	var searchId = location.search.match(/\?id=([0-9a-zA-Z]*)/)[1];
	$(function(){
		animation.load({
			container:$(".ds-main .content")
		})
		$.ajax({
			url:"/spi/getNiById",
			type:"POST",
			data:{
				id:searchId
			},
			success:function(data){
				if(data){
					var content = data[0].issueContent;
					var title = data[0].issueTitle;
					var modifyDate = new Date(parseInt(data[0].issueCreateDate)).Format("yyyy-MM-dd");
					var naturalDetail = new Vue({
						el:"#supplier-detail",
						data:{
							items:data,
							title:title,
							content:content,
							modifyDate:modifyDate
						},
						mounted:function(){
							animation.destory({
								callback:function(){
									$("#supplier-detail").removeClass("hide");								
								}
							})
						},
						filters:{
							statusFilter:function(province){
								 var provCity="";
								 if(province){
									 for(var i=0,len=dataCity.length;i<len;i++){
										 var provinceObj=dataCity[i];
										 if(province==provinceObj["id"]){
											 provCity=provinceObj["name"];
										 }
									 }
								 }
								return provCity;
							},
							typeFilter:function(orderType){
								var orderUnitType="";
								if(orderType==1){
									orderUnitType="省级食盐批发企业";
								}else if(orderType==2){
									orderUnitType="食盐定点生产企业";
								}else if(orderType==3){
									orderUnitType="多品种食盐定点生产企业";
								}
								return orderUnitType;
							}
						}
					})
				}
			}
		})
	})
	//省份数据
	var dataCity=[{id:11,name:"北京"},{id:12,name:"天津"},{id:13,name:"河北"},{id:14,name:"山西"},{id:15,name:"内蒙古"},{id:21,name:"辽宁"},{id:22,name:"吉林"},{id:23,name:"黑龙江"},{id:31,name:"上海"},{id:32,name:"江苏"},{id:33,name:"浙江"},{id:34,name:"安徽"},{id:35,name:"福建"},{id:36,name:"江西"},{id:37,name:"山东"},{id:41,name:"河南"},{id:42,name:"湖北"},{id:43,name:"湖南"},{id:44,name:"广东"},{id:45,name:"广西"},{id:46,name:"海南"},{id:50,name:"重庆"},{id:51,name:"四川"},{id:52,name:"贵州"},{id:53,name:"云南"},{id:54,name:"西藏"},{id:61,name:"陕西"},{id:62,name:"甘肃"},{id:63,name:"青海"},{id:64,name:"宁夏"},{id:65,name:"新疆"},{id:71,name:"台湾",},{id:81,name:"香港"},{id:82,name:"澳门"},{id:100,name:"其他"},{id:400,name:"海外"}];
})