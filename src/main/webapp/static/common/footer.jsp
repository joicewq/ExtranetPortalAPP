<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/static/js/jquery-1.8.3.min.js"></script>
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
		let templeteInfo;
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
		let reslutData=data.data;		
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
		 let links=data.data;
		 let lis="";		 		
		 links.forEach((item,index,arr)=>{       	
		     lis+="<li><a href="+item.url+" target='_blank'>"+item.name+"</a></li>";   	     	    	
			}); 		
		 $("ul").html(lis);		        
		    
		});	
   
});
</script>
<div class="ds-footer">
    <div class="ds-main ds-width-1000 ">
      <ul class="footer-links">
         
      </ul>
        <div class="ds-footer-nav">
         <div class="footer-website-info">
        <div class="row">
          <div class="percent90">
            <div class="clearfix">
              <div class="footer-icon-wrap">
                <img  src="/static/images/footer-icon.png">
              </div>
              <div class="footer-info-wrap">
                <div>
                  <div id="remarkTwo" class="display-inline-block" >单位地址：{{remarkTwo}}</div><!--{{obj.adds}}-->
                  <div id="remarkThr" class="display-inline-block ml15" >邮政编码：{{remarkThr}}</div>
                </div>
                <div id="remarkFour">技术支持：{{remarkFour}}</div>
                <div id="remarkFive">版权所有：{{remarkFive}}</div>
              </div>
            </div>
          </div>
          <div class="percent10">
            <div class="text-center">
              <img id="twoCodeId" class="display-inline-block" style="width:50px;height:50px;"   src="" />
            </div>
            <div class="font14 text-center">官方微信</div>
          </div>
        </div>
      </div>
           <!-- <div class="text-center mb20">
           		<a href="/help/index?id=register" target="_blank">帮助中心&nbsp;&nbsp;|&nbsp;</a>
           		<a href="/help/statistics" target="_blank">网站统计</a>
           </div>
           <div class="text-center mb10">
           		<div class="inline-block">版权所有：<span>广东省盐务局</span></div>
           		<div class="inline-block">粤ICP备13056293号</div>
           </div>
           <div class="text-center mb10">
           		<div class="inline-block">地址：<span>广东省广州市中山四路18号</span></div>
           		<div class="inline-block">邮编：<span>510055</span></div>
           		<div class="inline-block">电话：<span>020-83876533</span></div>
           </div> -->
        </div>
    </div>
</div>