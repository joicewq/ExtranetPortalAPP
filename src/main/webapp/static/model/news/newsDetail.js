require.config(config);
require(["jquery", "animation" ], function($,animation) {
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

	$(function() {
		var id1 = $("#accessory").val();
		var option = {
				is_shade: false,
				file_ids: id1,
				multi_selection: true,
				max_file_size: "20mb",
				prevent_duplicates: true,
				mime_types: "",
				btn_class: "hide",
				delete_class: "hide",
			};
		$("#filterPublishTime").text(timeStampToString(parseInt($("#publishTime").val()),1));
		if($(".article-accessory").length>0){
			animation.load({
				container:$(".article-accessory-list"),
				callback:function(animationWrap){
					animationWrap.find(".animation-spinner").css("margin","auto");
					$.get("/doc/index",option,function(data){
						$("#uploadDiv").html(data);
						var accessoryNum=$("#uploadTbody").find("tr").length;
						$.each($("#uploadTbody").find("tr"),function(i){
								var $li = $("<li></li>");
								var $a = $("<a></a>");
								$a.text($(this).children("td").eq(0).text()).attr("href","http://test.doc.qhgrain.com/doc/download/"+id1);
								$li.append($a).appendTo($(".article-accessory-list"));
						})
						animation.destory();
					});					
				}
			})			
		}
		
	})
})