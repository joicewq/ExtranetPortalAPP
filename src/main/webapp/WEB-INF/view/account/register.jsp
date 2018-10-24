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

<jsp:include page="${ctx}/static/common/import.jsp" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>用户注册</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
    <script type="text/javascript" src="${ctx}/static/js/require.js" data-main="${ctx}/static/model/account/register.js"></script>
</head>
<body>
	<jsp:include page="${ctx}/static/common/topHeader.jsp" />

    <input type="hidden" id="lId" value="" />
    <div class="ds-main ds-width-1000 progress-container" id="registerBox">
        <div class="progress-title-container"></div>
        <div class="ds-user-main">
            <form id="accountForm">
				<input type="hidden" name="loginType" id="loginType" value="S"/>
				<input type="hidden" name="postId" id="postId" value="${post_id}"/>
                <ul class="ds-user-from register-form">
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>用户名:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required checkUserName loginNameExist" maxDataLength="100" value="" placeholder="请输入用户名" type="text" id="loginName" name="loginName" />
                            <span class="ds-from-tips ds-text-red ml10"></span><!--用户名校验:必填,是否被占用,只包含数字/字母/下划线,最大长度28  -->
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>密码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required" minDataLength="6" maxDataLength="20" value="" placeholder="请输入密码" type="password" id="loginPassword" name="loginPassword"/>
                            <span class="ds-from-tips ds-text-red ml10"></span><!-- 登录密码校验:必填,6-12位,没有空格 -->
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>再次输入密码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required" equalTo="#loginPassword" value="" placeholder="请确认密码" type="password" id="password" name="password"/>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <%--<li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>邮箱:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required email" maxDataLength="20" value="" placeholder="请输入邮箱" type="text" id="email" name="email" />
                            <span class="ds-from-tips ds-text-red ml10"></span><!--邮箱校验:必填,是否符合邮箱格式  -->
                        </div>
                    </li>--%>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>手机号码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control required mobilePhone checkPhoneExist" value="" placeholder="请输入手机号码" type="text" id="phone" name="phone" />
                           <!--  <span class="btn-choose">
                                <a class="btn btn-min changePhone" style="display: none">更换手机</a>
                            </span> -->
                            <span class="ds-from-tips ds-text-red ml10"></span><!--手机号码校验:必填,是否被占用,只包含数字/字母/下划线,最大长度28  -->
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>手机验证码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control ds-inp-min" value="" placeholder="请输入手机验证码" type="text" id="phone-verifyCode" name="phone-verifyCode"/>
                            <span class="btn-choose">
                                <a class="btn btn-min markPhoneCode js-getPhoneCode">获取验证码</a>
                            </span>
                            <span class="ds-from-tips ds-text-red ml10" id="verifyCode_error"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left"><span class="ds-text-red mr5">*</span>验证码:</label>
                        <div class="ds-from-group">
                            <input class="ds-inp-control ds-inp-min required matchVerify" value="" placeholder="请输入验证码" type="text" id="verifyCode" name="verifyCode"/>
                            <span>
                                <img id="verifyImg" class="verifyImg" src="/verify/code" />
                                <a id="showCode" style="color:#134ea5;font-size:12px;" href="javascript:void(0)">看不清？换一张</a>
                            </span>
                            <span id="verifySpan" class="ds-from-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left">&nbsp;</label>
                        <div class="ds-from-group">
                            <button class="ds-btn ds-btn-primary mr20 js-submit" type="button" id="register"> 确认注册 </button>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
    </div>
    <jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>