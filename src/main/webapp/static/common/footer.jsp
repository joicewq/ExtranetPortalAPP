<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- <script src="/static/js/jquery-1.8.3.min.js"></script> -->
<script>
$(document).ready(function(){
	var stationId=0;
	var docUrl="/doc/download/";
	$.post("/portal/getEnv",{},
	function(data,status){	
		docUrl=data.docAppUrl+docUrl; 
	});	 	
	//请求模板	 
	$.post("/portal/queryTemplete",{
		testLink: window.location.origin
	},
	function(data,status){	
		var templeteInfo=[];
         if (data.message === '[]' || data.message === undefined) {
             templeteInfo = []
         } else {
             templeteInfo = data.message[0];
         }
         stationId=templeteInfo.stationId;     
         initLogoInfo();//加载站群对应的Logo信息存放localStorage 
	});	 
function initLogoInfo(){
	$.post("/portal/queryLogoInfo",{
		type: "1",//只要文字链接
		stationId:stationId,
	},
	function(data,status){	
	 var reslutData=data.data;		
	 $("#remarkTwo").html("单位地址："+reslutData.remarkTwo);
	 $("#remarkThr").html("邮政编码："+reslutData.remarkThr);
	 $("#remarkFour").html("技术支持："+reslutData.remarkFour);
	 $("#remarkFive").html("版权所有："+reslutData.remarkFive);	 
	 $("#twoCodeId").attr("src",docUrl+reslutData.twoCodeId);	 
	});	
}
	$.post("/portal/initBolgroll",{
			type: "1",//只要文字链接
			stationId:stationId,
		},
		function(data,status){	
		 var links=data.data;
		 var lis="";		 		
		 $.each(links,function(index,item){       	
		     lis+="<li><a href="+item.url+" target='_blank'>"+item.name+"</a></li>";   	     	    	
			}); 		
		 $(".footer-links").html(lis);        
		});	
   
});
</script>
<div class="ds-footer">
    <div class="ds-footer-main">
      <ul class="footer-links"></ul>
      <div class="ds-footer-nav">
        <div class="footer-website-info">
          <div class="footer-icon-wrap">
            <img  src="/static/images/footer-icon.png">
          </div>
          <div class="footer-info-wrap">
            <div id="remarkTwo" class="display-inline-block" >单位地址：</div><!--{{obj.adds}}-->
            <div id="remarkThr" class="display-inline-block ml15" >邮政编码：</div>
            <div id="remarkFour">技术支持：</div>
            <div id="remarkFive">版权所有：</div> 
          </div>
          <div class="ds-footer-code">
            <img id="twoCodeId" class="display-inline-block" style="width:90px;height:90px;"   src="" />
            官方微信
          </div>
      </div>
    </div>
</div>