<%--
  Created by IntelliJ IDEA.
  User: lovercy
  Date: 22/11/2016
  Time: 下午 4:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<jsp:include page="${ctx}/static/common/jstl.jsp" />
<jsp:include page="${ctx}/static/common/import.jsp" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>供应商忘记密码</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<link rel="stylesheet" href="${ctx}/static/js/layer/skin/default/layer.css"/>
    <script type="text/javascript" src="${ctx}/static/js/require.js" data-main="${ctx}/static/model/account/forgetPassword.js"></script>
</head>
<body>
	<jsp:include page="${ctx}/static/common/header.jsp" />
    <div class="ds-main ds-width-1000 progress-container" id="registerBox">
        <div class="progress-title-container">
            
        </div>
        <div class="ds-user-main">
            <form id="varityCodeForm">
                <ul class="ds-user-from register-form">
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入用户名:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control ds-inp-min" placeholder="请输入用户名" type="text" id="loginNamee" />
                            <span class="btn-choose">
                                <a class="btn btn-min mr0" id="js-getPhoneVerifyCode">获取验证码</a>
                            </span>
                            <span class="ds-form-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入验证码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required" value="" placeholder="请输入验证码" type="text" id="verifyCode" name="verifyCode"/>
                            <span id="verifySpan" class="ds-form-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li class="mt0">
                        <label class="ds-from-left">&nbsp;</label>
                        <div class="ds-from-group ds-text-gray font14">
                            	（验证码将发送至注册账户时填写的手机）
                        </div>
                    </li>
                    <li>
                        <div class="ds-from-group ds-btn-group-center">
                            <button class="ds-btn ds-btn-primary ds-btn-center js-submit" type="button"> 下一步 </button>
                        </div>
                    </li>
                </ul>
            </form>
            <form id="newPasswordForm" class="hide">
            	<ul class="ds-user-from register-form">
	            	 <li>
		                 <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入新密码:</label>
		                 <div class="ds-from-group">
		                     <input class="ds-inp-control" value="" placeholder="请输入新密码" type="password" id="loginPassword" />
		                     <span id="newVerifySpan" class="ds-form-tips ds-text-red ml10" ></span>
		                 </div>
		             </li>
		             <li>
		                 <label class="ds-from-left"><span class="ds-text-red mr5">*</span>确认新密码:</label>
		                 <div class="ds-from-group">
		                     <input class="ds-inp-control required" value="" placeholder="请确认新密码" type="password" id="password" name="verifyCode"/>
		                     <span id="verifySpan" class="ds-form-tips ds-text-red ml10"></span>
		                 </div>
		             </li>
		             <li>
		                 <div class="ds-from-group ds-btn-group-center">
		                     <button class="ds-btn ds-btn-primary js-submit" type="button"> 下一步 </button>
		                 </div>
		             </li>
	             </ul>
            </form>
        </div>
    </div>
    <jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>