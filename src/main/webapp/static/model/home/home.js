require.config(config);
require(["jquery","flexslider","Vue","tab","animation"],function($,flexslider,Vue,tab,animation){
	var picPath="../images/";
	
	function dataTimeFilter(data,attr){
		var msToDay=60*60*24*1000;
		if(data){
			for(var i=0,len=data.length;i<len;i++){
				if(data[i][attr]){
					if(!isNaN(data[i][attr])){
						data[i]["isNewest"]=(Date.parse(new Date)-data[i][attr])/msToDay<5;						
					}
					else{
						data[i]["isNewest"]=(Date.parse(new Date)-Date.parse(data[i][attr]))/msToDay<5;
					}
					//data[i]["publishDate"]=timeStampToString(data[i]["publishDate"],1);					
					data[i].date=timeStampToString(data[i][attr],1);
				}				
			}
		}
		function timeStampToString(time,mode){
			var date={};
		    var datetime = new Date(time);
		    date.year = datetime.getFullYear();
		    date.month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
		    date.date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
		    date.hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
		    date.minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
		    date.second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
		    
		    if(typeof mode == "undefined" || mode==1){
		    	return date.year+"-"+date.month+"-"+date.date;
		    }
		}
	}
	
	function moduleLoad(ele,url,params,callback){
		var module=new Vue({
			el:ele,
			data:{
				items:{}
			},
			updated:function(){
				$(ele).find(".news-hot-icon").show();
				animation.destory({
					container:$(ele).find(".animation-wrap")
				})
			}
		})
		return $.ajax({
			url:url,
			data:params,
			type:"POST",
			beforeSend:function(){
				animation.load({
					container:$(ele)
				})
			},
			success:function(data){
				if(data && typeof data != "object" && data!="error"){
					data=JSON.parse(data);
				}
				var list = data.data;
				if(list && list["createDate"] && typeof list["createDate"]== "string"){
					for(var i=0,len=list.length;i<len;i++){
						list[i].createDate = new Date(list[i].createDate).valueOf(); 
					}
				}
				if(list && list[0] && list[0]["publishDate"]){
					dataTimeFilter(list,"publishDate");
				}
				else if(list && list[0] && list[0]["createDate"]){
					dataTimeFilter(list,"createDate");
				}
				module.$data.items=list
			}
		})
	}
	
	$(function(){
		//animation.pageLoad();
		
		//轮播
		function sliderModule(){
			return $.ajax({
				url:"/broadcast/queryAll",
				type:"POST",
				data:{
					pageNo:1,
					pageSize:10
				},
				success:function(data){
					if(data.code==0){
						new Vue({
							el:"#picplayer",
							data:{
								items:data.list							
							},
							mounted:function(){
								$("#picplayer").flexslider({
									animation : "slide",
									slideshowSpeed : 3000,
									slideshow : true,
									imgAlt:true,
									directionNav : true,
									itemWidth: 460,
									itemHeight: 340,
									controlNav:false
								});							
							}
						})					
					}
				}
			})			
		}
		
		//tab切换
		tab({
			callback:function(li){
				var url = li.find("a").text()=="政策法规"?"/policies/list":"/news/list";
				$(".index-right").find(".ds-more").attr("href",url);
			}
		});
		
		/**
		 * 加载顺序：
		 * 轮播、盐业动态→备案企业公示→通知公告→盐政执法、政策法规→检测合格结果公示、检测不合格结果公示
		 * 
		 * */
		$.when(moduleLoad("#dynamic-list","/article/query",{
			"columnName":"新闻动态",
			"pageNo":1,
			"pageSize":8,
			"type":0
		}),sliderModule()).then(function(data1,data2){
			$.when(moduleLoad("#company-publicity","/spi/query",{
				"pageNo":1,
				"pageSize":7
			}),moduleLoad("#notices","/article/query",{
				"columnName":"通知公告",
				"pageNo":1,
				"pageSize":7
			})).then(function(data1,data2){
				$.when(moduleLoad("#law-enforcement","/article/query",{
					"columnName":"新闻动态",
					"pageNo":1,
					"pageSize":8,
					"type":1
				}),moduleLoad("#policies","/article/query",{
					"columnName":"政策法规",
					"pageNo":1,
					"pageSize":8
				})).then(function(data1,data2){
					$.when(moduleLoad("#publicity-pass-list","/ni/query",{
						status:1,
						page:1,
						pageSize:8
					}),moduleLoad("#publicity-failed-list","/ni/naturalQuery",{
						"columnName":"检测公示",
						page:1,
						pageSize:8
					}))
				},function(){
					
				})
			})
		},function(data1,data2){
			
		})	
	})
	
})