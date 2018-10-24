



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

 function getInfo (){
	   //  /html/noticeInfo.html/notId/  
	 // 截取 zifuchua
   var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++){
    	
      hash = hashes[i].split('=');
      // vars.push(hash[0]);
      vars[hash[0]] = hash[1];
    }
		    
	if(vars.id != null){
		
		$.ajax({
			url: "/notice/getNoticeInfo.do", //url  action是方法的名称
			type: 'GET',
//			contentType:"application/json; charset=UTF-8",
			data: {"noticeId":vars.id},
			success: function(msg) {
				if(msg.result =="0"){
					alert("查询失败! "+msg.message);
				}
				if(msg.result == "1"){
					if(msg.message){
						console.log(msg.message.notContent);
						$("#noticeTitle").html(msg.message.notTitle);
						$("#createDate").html(timeStamp2String(msg.message.createDate));
						$("#noticeContent").html(msg.message.notContent);
					}
				}
			},
			error: function(msg) {
				alert("查询失败!"+msg.statusText);
			}
		});
	}else{
		alert("没有公告id");
		return;
	};
 };
   
   
   /**
    * 执行查询  
    */
   getInfo();