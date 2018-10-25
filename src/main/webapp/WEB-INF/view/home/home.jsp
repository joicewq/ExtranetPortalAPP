<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<jsp:include page="${ctx}/static/common/jstl.jsp" />
<jsp:include page="${ctx}/static/common/import.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>广东省盐业监管服务平台</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">

<!-- 未读消息图标css -->
<style>
	.info-badge{
		display: inline-block;
		min-width: 10px;
		padding: 3px 7px;
		font-size: 12px;
		font-weight: 700;
		line-height: 1;
		color: #fff;
		text-align: ce	nter;
		white-space: nowrap;
		vertical-align: middle;
		background-color: #ff7800;
		border-radius: 10px;
		margin-left:-8px;
	}
	.entry-box>a{
		height: 57px;
	}
	.ds-footer {
		margin-top: 0;
	}
	.slidess,.section {
		height:100%;
	}
	#picplayer,.slidess {
		position: relative;
	}
	.section {
		background-color: #000;
		background-size: cover;
		background-position: 50% 50%;
		text-align: center;
		color: white;
	}
</style> 
</head>

<body>	
	<script src="/static/js/require.js" data-main="/static/model/home/home.js"></script>
 
	<jsp:include page="/static/common/header.jsp" />
	<div class="ds-main container" id="homeBox">
		<div class="row clearfix">
			<div class="index-left">
				<div class="entry-box" id="linksId">
				
				</div>
				<div id="picplayer" class="slider">
					<div class="slides clearfix sections">
						<%--  <li class="" v-for="item in items">
							<a v-bind:href="'/broadcast/detail?id='+item.id" target="_blank" class="">
								<img v-bind:alt="item.advTitle" v-bind:src="'${upload_url}/doc/download/'+item.fileIds"/>
							</a>
						</li>  --%>
					</div>
				</div>
			</div>
			<div class="index-right">
                <a href="/news/list" class="ds-more pull-right" style="margin-top: 10px;margin-right: 10px;">更多>></a>
				<ul class="tabs clearfix">
                    <li class="active"><a href="#dynamic-list">盐业动态</a></li>
                    <li><a href="#law-enforcement">盐政执法</a></li>
                    <li><a href="#policies">政策法规</a></li>
                </ul>
                <div class="tab-contentmian">
                    <div class="tab-content nopadding" id="dynamic-list">
                        <ul class="news-list">
                        	<li v-for="item in items">
                        		<a v-bind:href="'/news/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
                        		<span class="news-hot-icon" v-if="item.isNewest==true"></span>
								<span class="news-date" v-html="item.date"></span>                       		
                        	</li>
                        </ul>
                    </div>
                    <div class="tab-content nopadding" id="law-enforcement">
                    	<ul class="news-list">
                        	<li v-for="item in items">
                        		<a v-bind:href="'/news/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
                        		<span class="news-hot-icon" v-if="item.isNewest==true"></span>
								<span class="news-date" v-html="item.date"></span>                       		
                        	</li>
                        </ul>
                    </div>
                    <div class="tab-content nopadding" id="policies">
                    	<ul class="news-list">
                        	<li v-for="item in items">
                        		<a v-bind:href="'/policies/detail?id='+item.id" target="_blank" v-bind:title="item.title" class="news-title" v-html="item.title"></a>
                        		<span class="news-hot-icon" v-if="item.isNewest==true"></span>
								<span class="news-date" v-html="item.date"></span>                       		
                        	</li>
                        </ul>
                    </div>
                </div>
			</div>
		</div>
		
		<div id="newsLatest" class="row clearfix">			 
		</div>

		<div id="columnOne" class="row clearfix">			 
		</div>
		<div id="columnThr" class="row clearfix">
		</div>
				
		 
		
		
	</div>

	<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
<script type="text/javascript">
 $(function(){ 
		init();
	 	$("#picplayer").PageSwitch({
			direction:'horizontal',
			easing:'ease-in',
			duration:1000,
			autoPlay:true,
			loop:'false'
		});
		cache()
});
 //查询轮播图信息
function init(){
	$.ajax({
		url:"/broadcast/queryAll",
		type:"POST",
		data:{
			pageNo:1,
			pageSize:10
		},
		success:function(data){
			if(data.code==0){
				if(data.list!==null){
					var size = data.list.length;
					var temp="" ;
					for(var i =0; i<size;i++){
						console.log(data.list[i].advTitle)							
						temp+="<div class='section'><a href><img"
						temp+=" alt='"+data.list[i].advTitle+"'";
						temp+=" src='http://docapplive.gdyyjg.cn/doc/download/"+data.list[i].fileIds+"'";
						temp+="></img></a></div>"; 
					}
					console.log("html",temp);
					$(".slides").html(temp);
					$(".horizontal").html("<li></li><li></li><li></li>");
				}			
			}
		}
	})	
}
//获取缓存信息
function cache(){
	$.post("/portal/getEnv",{},
	function(data,status){	
		if(data!=null){
			var temp="";
			$.each($.parseJSON(data.onlineService),function(index,ele){
				if(typeof ele.aName!="undefined"){
					temp+="<a href='"+ele.aName+"' class='no-border' target='_blank'>";
					temp+="<div class='entry-icon'><i class='iconfont icon-company'></i></div>";
					temp+="<div class='entry-title-wrap'><span class='entry-title'>"+ele.name+"</span></div></a>";
				}else{
					temp+="<a href='"+ele.columnCode+"'>";
					temp+="<div class='entry-icon'><i class='iconfont icon-company'></i></div>";
					temp+="<div class='entry-title-wrap'><span class='entry-title'>"+ele.name+"</span></div>";
				}
				
			});
			$("#linksId").html(temp);
		}
	});	 
}
 
</script>
</html>