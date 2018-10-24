<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c"%>
<header class="header">
<script src="/static/model/home/nav.js"></script>
	<div class="ds-top">                                                                                         
		<ul class="ds-top-text clearfix ds-width-1000">                                                               
			<li class="pull-left">你好！欢迎来到广东省盐业监管服务平台。</li>                                                                  
			<c:choose>
			<c:when test="${empty saltUserInfo}">                                                                
			<li class="pull-right" id="notLogin">                                                                         
				<a href="/login/index" class="ds-top-login">登录</a>                                        
					<span class="ds-fc-c0">|</span>                                                                               
				<a href="/account/register" class="ds-top-login">注册</a>    
			</li>        
			</c:when>
			<c:otherwise>
			<li class="pull-right" id="isLogin">     
				<input type="hidden" value="${saltUserInfo.loginName }" id="loginId" />     
				<a href="/vipcenter/index?showPage=0_0" class="ds-top-login" id="userName">${saltUserInfo.loginName }</a>                                          
				<!-- 					<a  href="javascript:void(0);"><span id="letterNum" class="info-badge">abc</span></a> -->                       
					<span class="ds-fc-c0">|</span>                                                                               
				<a href="/login/logout" class="ds-top-login"><i class="fa fa-sign-out"></i> 退出</a>                      
			</li>
			</c:otherwise> 
			</c:choose>                                                                                                          
		</ul>                                                                                                         
	</div>                                                                                                        
	<div class="ds-logo ds-width-1000">     
		<a href="${ctx}/salt/index">
			<img src="${ctx}/static/images/logo.png" alt="" class="ds-logo-img"/>
		</a>                                                  
		<div class="ds-header-search pull-right">                                                                     
			<div class="ds-search-w clearfix">                                                                            	                                                                                                              
				<div class="ds-search-input-div pull-right ml10 mb10">                                                         
					<span class="ds-ico-search"></span>        
					<form action="/salt/serach" method="post">                                                                  
						<input class="ds-search-input" type="text" id="keyName" name="keyName" placeholder="请输入文章标题" value="${keyName}"/>                                  
						<button class="ds-search-btn" id="serach">搜 索</button>  
					</form>                                                       
				</div>  																			
			</div>                                                                                                        
			<p class="ds-quick-seacrh mt5" id="hotSpot"></p>                                                                                                          
		</div>                                                                                                        
	</div>                                                                                                        
	<div class="ds-nav">                                                                                          
		<ul class="ds-nav-ul ds-width-1000" id="nav"></ul>                                                                                                         
	</div>
</header>