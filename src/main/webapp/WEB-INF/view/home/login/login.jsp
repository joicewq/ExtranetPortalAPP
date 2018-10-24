<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/static/common/jstl.jsp" />
<jsp:include page="/static/common/import.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/css/login.css"/>
<title>广东省盐业监管服务平台登录</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<style type="text/css">
.ds-footer{
	background: #fff;
	margin-top: 0;
	padding-bottom: 0;
}
.ds-footer .ds-main{
	margin-top: 0;
}
.ds-footer-nav{
	color: #666;
	padding-top: 20px;
}
.ds-footer-nav a{
	color: #666;
}
</style>
</head>
<script src="/static/js/require.js" data-main="/static/model/home/login.js"></script>
<body>
<div class="ds-login-warp">
	<div class="header">     
		<div class="ds-top">                                                   
			<ul class="ds-top-text clearfix ds-width-1000">             
				<li class="pull-left">你好！欢迎来到广东省盐业监管服务平台。</li> 
			</ul>              
		</div>                                                         
		<div class="ds-logo ds-width-1000">      
			<a href="${ctx}/salt/index">
				<img src="${ctx}/static/images/logo.png" alt="" class="ds-logo-img"/>                                                                                                             
			</a>                                                                     
		</div> 
	</div>
	<div class="ds-login-cent-qh">
		<div class="ds-login-center">
			<h2 class="ds-login-title">广东省盐业监管服务平台</h2>
	    	<div class="ds-login-left">
	        	
	        </div>
	        <div class="ds-login-right">
	        	<form id="loginForm">
		            <p class="ds-right-title mt15"><span>会员中心</span></p>
		            <div class="ds-right-from-number mt30">
		                <img src="/static/images/ds-right-numb.png"/>
		                <input class="ds-right-number" placeholder="用户名" name="loginName" id="loginName"/>
		                <div class="form-tips text-red" ></div>
		            </div>
		            <div class="ds-right-from-password mt30">
		                <img src="/static/images/ds-right-password.png"/>
		                <input type="password" class="ds-right-password" placeholder="密码" name="loginPassword" id="loginPassword"/>
		                <div class="form-tips text-red" ></div>
		            </div>
		            <div class="ds-right-remember mt20">
		                <span class="ds-right-number">&nbsp;</span>
		                <a class="ds-right-password" href="javascript:void(0);" id="js-forget-password">忘记密码？</a>
		            </div>
		        </form>
	            <a class="ds-right-button mt20" href="javascript:void(0);" id="js-login-btn">登  录</a>
	            <a class="pull-right text-gray mt20" href="/account/register" id="">注册</a>
	        </div>
	    </div>
	</div>
	<jsp:include page="/static/common/footer.jsp" />
</div>
</body>
</html>