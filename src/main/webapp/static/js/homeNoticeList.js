  


	
	/**
	 * 时间格式化
	 */
	function timeStamp2String(time){
	    var datetime = new Date();
	    datetime.setTime(time);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	    return year + "-" + month + "-" + date;
	}

	
	/**
	 * 执行 首页面
	 */
	getNoticeList();
	
	/**
	 * 首页面加载初始化
	 */
	function getNoticeList() {
		 var param = {};
		$.ajax({
			url: "/notice/getNoticeList/"+1+"/"+6+".do", //url  action是方法的名称
			type: 'POST',
			contentType:"application/json; charset=UTF-8",
			data: JSON.stringify(param),
			success : function(result){
				
				if(result.result == 0){
					console.error("查询失败,   "+result.message);
				}else{
					// 生成 dom 放到页面上   、
					var letterList =  result.message.list;
					if(letterList && letterList.length>0){
						
						var noticeList = $("#noticeList");
						$(letterList).each(function(index,ele) {
							var li = $("<li></li>");
							li.attr("class","ds-notice-li");
							
							var a  = $("<a></a>");
							a.attr("class","ds-text-nowrap ds-font-simsun");
							a.attr("href","/html/noticeInfo.html?id="+ele.notId);
							a.attr("target","_blank" );
							var span  = $("<span></span>");
							span.attr("class","text-red");
							span.html("[公告]");
							
							a.append(span);
							a.append(ele.notTitle);
							
							li.append(a);
							
							noticeList.append(li)
						})
					}
				}
			},
			error : function(XMLHttpRequest, textStatus) {
				console.error("获取数据失败!");
			}
		});
	}
	
	/**
	 * 跳转到公告列表页 
	 */
	function goToNoticeList (){
		
		window.location.href = "/html/noticeList.html";
	}
