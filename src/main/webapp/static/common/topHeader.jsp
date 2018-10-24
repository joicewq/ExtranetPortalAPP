<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 无导航栏以及搜索框的公用顶部，适用于注册、登陆 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c"%>
<div class="header">     
	<div class="ds-top">                                                   
		<ul class="ds-top-text clearfix ds-width-1000">             
			<li class="pull-left">你好！欢迎来到广东省盐业监管服务平台。</li>    
			<li class="pull-right" id="notLogin">                          
				<a href="/login/index" class="ds-top-login">登录</a>  
			</li>
		</ul>              
	</div>                                                         
	<div class="ds-logo ds-width-1000">      
		<a href="${ctx}/salt/index">
			<img src="${ctx}/static/images/logo.png" alt="" class="ds-logo-img"/>                                                                                                             
		</a>                                                                     
	</div> 
</div>