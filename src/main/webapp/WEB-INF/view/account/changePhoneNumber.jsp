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
    <title>供应商修改手机号</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<link rel="stylesheet" href="${ctx}/static/js/layer/skin/default/layer.css"/>
    <script type="text/javascript" src="${ctx}/static/js/require.js" data-main="${ctx}/static/model/account/changePhoneNumber.js"></script>
</head>
<body>
	<jsp:include page="${ctx}/static/common/header.jsp" />
    <div class="ds-main ds-width-1000 progress-container" id="changePhoneNumber">
        <div class="progress-title-container">
            
        </div>
        <div class="ds-user-main">
            <form id="oldPhoneNumberForm">
                <ul class="ds-user-from register-form">
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入原手机号码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control ds-inp-min required mobilePhone" value="" placeholder="请输入原手机号码" type="text" 
                            	id="oldPhoneNumber" name="oldPhoneNumber"/>
                            <span class="btn-choose">
                                <a class="btn btn-min js-getPhoneCode mr0">获取验证码</a>
                            </span>
                            <span class="ds-form-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入验证码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required" value="" placeholder="请输入验证码" type="text" 
                            	id="verifyCode" name="verifyCode"/>
                            <span id="verifySpan" class="ds-form-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <div class="ds-from-group ds-btn-group-center">
                            <button class="ds-btn ds-btn-primary ds-btn-center js-submit" type="button"> 下一步 </button>
                        </div>
                    </li>
                </ul>
            </form>
            <form id="newPhoneNumberForm" class="hide">
            	<ul class="ds-user-from register-form">
	            	 <li>
		                 <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入新手机号码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control ds-inp-min required mobilePhone checkPhoneExist" value="" placeholder="请输入新手机号码" type="text" 
                            	id="newPhoneNumber" name="newPhoneNumber"/>
                            <span class="btn-choose">
                                <a class="btn btn-min js-getNewPhoneCode mr0 markPhoneCode">获取验证码</a>
                            </span>
                            <span class="ds-form-tips ds-text-red ml10"></span>
                        </div>
		             </li>
		             <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>输入验证码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required" value="" placeholder="请输入验证码" type="text" id="newVerifyCode" name="verifyCode"/>
                            <span id="newVerifySpan" class="ds-form-tips ds-text-red ml10"></span>
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