require.config(config);
require(["jquery", "animation" ], function($,animation) {
	var newsInfo={ //新闻信息	
	    };
	var docUrl=""; //文档服务地址
	var iframeSrc=""; //pdf文档显示地址
	var pdfUrl = "/pdf/web/viewer.html";
	
	/**
	 * 判断一个对象是否为空，其中字段串""也算是空
	 */
	function isEmpty(object) {
		var flag = false;

		switch(typeof(object)) {
		case("undefined"):
		{
			flag = true;
			break;
		}
		case("number"):
		{
			break;
		}
		case("string"):
		{
			if(object == "") {
				flag = true;
			}
			break;
		}
		case("boolean"):
		{
			break;
		}
		case("object"):
		{
			if(object == null) {
				flag = true;
			}
			break;
		}
		case("function"):
		{
			break;
		}
		default:
		{
			flag = true;
			break;
		}
		}
		return flag;
	}
	
	function getQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");       
       var r = window.location.search.substr(1).match(reg);           
         if(r!=null){
         // alert(unescape(r[2]));
            return  unescape(r[2])
         }
        return null;
     }
	
	 function setPDFURL() { //获取PDF请求地址
		if(!isEmpty(newsInfo.pdfId)) { //当不为空时才配置pdf展示
			var _url = pdfUrl + "?file=";			
			_url = _url + "/portal/getPDFByID/" + newsInfo.pdfId;
			iframeSrc = _url;
		}
	}
	 
	 function initDocUrl() { //初始化文档服务路径方法
		 $.post("/portal/getDocUrl",{},function(data,status){				 
				docUrl = data;
			});	
		}
	 /**
	  * 格式时间(yyyy-MM-dd)
	  */
	 function formatDate_date(stamp) {
	 	let date = new Date(stamp);
	 	let year = date.getFullYear();
	 	let month = date.getMonth() + 1;
	 	let day = date.getDate();
	 	return year + "-" + (month > 9 ? month : ("0" + month)) + "-" + (day > 9 ? day : ("0" + day));
	 }
	 /**
	  * 格式时间(yyyy-MM-dd HH-mm-ss)
	  */
	 function formatDate_time(stamp) {
	 	let date = new Date(stamp);
	 	let hour = date.getHours();
	 	let minute = date.getMinutes();
	 	let second = date.getSeconds();
	 	return formatDate_date(stamp) + " " + (hour > 9 ? hour : ("0" + hour)) + ":" + (minute > 9 ? minute : ("0" + minute)) + ":" + (second > 9 ? second : ("0" + second));
	 }
	  function initDate(_date) { //格式时间方法
		let result = "";
			//如果传入的参数不为空就格式
			if(!isEmpty(_date)) {
				result = formatDate_time(_date);
			}
			return result;
	 }
	
	
	function getData(){
		var id=getQueryString("id");
		$.post("/portal/queryDetail",{
			id:id	
		},function(data,status){				 
			var tempData = data;
			if(!isEmpty(tempData) && tempData.code == "1") {
				var data = tempData.data[0];
				    newsInfo=data;
				/*for(var key in newsInfo) {
					
					newsInfo[key] = data[key];
				}*/
				setPDFURL();
				initDocUrl();
				iniPageDate();
			} else {
				self.$message.error("初始化失败：" + tempData.msg);
			}
		});	
		
	}
	function iniPageDate(){
		var lastPage="<a href='/salt/index'>返回</a>";		
		
		console.info("columnId:",getQueryString("columnId"));
		if(!isEmpty(getQueryString("columnId")))
			lastPage="<a href='/news/list?id="+getQueryString("columnId")+"'>返回</a>";
		console.info("lastPage:",lastPage);
		$(".article-title ").html(newsInfo.title);
		$("#filterPublishTime ").html(initDate(newsInfo.updateTime));
		$("#contentFrom ").html(initDate(newsInfo.cfrom));
		$("#contentData ").html(newsInfo.content);
		$("#lastPage ").html(lastPage);
		
		if(!isEmpty(newsInfo.pdfId)){
			var docUrl="/doc/download/";
			$.post("/portal/getEnv",{},
			function(data,status){	
				docUrl=data.docAppUrl+docUrl; 
				var docDown="<a href='"+docUrl+newsInfo.pdfId+"'>附件下载</a>";
				$("#docDown ").html(docDown);
			});				
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
	

	$(function() {
//		alert("id:"+getQueryString("id"));
		getData();
		$(".content").css("minHeight",$(window).height()-$(".header").height()-$(".ds-footer").height());
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
							$a.text($(this).children("td").eq(0).text()).attr("href",$("#accessory_url").val()+"/doc/download/"+$(this).children("td").eq(2).children(".hide").attr("id"));
							$li.append($a).appendTo($(".article-accessory-list"));
						})
						animation.destory();
					});					
				}
			})			
		}
		
	})
})