<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<jsp:include page="${ctx}/static/common/jstl.jsp" />
<jsp:include page="${ctx}/static/common/import.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>个人中心</title>
	<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
	<link rel="stylesheet" href="/static/css/layout.css"/>
	<link rel="stylesheet" href="/static/css/personal.css"/>
	<link rel="stylesheet" href="/static/css/fonts.min.css"/>
	<link rel="stylesheet" href="/static/css/main.css"/>
	<link rel="stylesheet" href="/static/js/layer/skin/default/layer.css"/>
</head>
<body>
	
	<script src="${ctx}/static/js/requireJSConfig.js"></script>
	<script src="${ctx}/static/js/require.js" data-main="/static/model/account/personalIndex.js"></script>
	<script src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
	<jsp:include page="/static/common/header.jsp" />

	<div class="ds-main">
		<div class="user-main mt10 clearfix">
			<div class="menu-list" id="menu">
			<input id="showPage" type="hidden" value="${param.showPage}">
			<h2 class="menu-h2">个人中心</h2>
				<ul>
					<li>
						<a class="menu-a" href="javascript:void(0)">资格认证<i class="fa fa-sort-down" style="top: 12px;"></i></a>
						<ul class="menu-list-second" style="overflow: hidden;">
							<li><a class="menu-a" id="zhxx" href="javascript:void(0)" data-showpage="0_0" data-href="/vipcenter/mycenter?loginId=${loginId}&loginType=S">企业信息告知</a></li>
							<li><a class="menu-a" id="zhxx" href="javascript:void(0)" data-showpage="0_1" data-href="/vipcenter/registerDetail?loginId=${loginId}&loginType=S">企业审核记录</a></li>
						</ul>
					</li>
				<li>
				<a class="menu-a" id="" href="javascript:void(0)" data-showpage="1" data-href="/natural/naturalindex">产品信息告知</a>
				</li>
				<li>
				<a class="menu-a" href="javascript:void(0)" data-showpage="2" data-href="/stocked/index">储备管理</a>
				</li>
				<li>
				<a class="menu-a" href="javascript:void(0)" data-showpage="3" data-href="/account/safe/index">账户安全</a>
				</li>

<!-- 				<li>
				<a class="menu-a" href="javascript:void(0)" data-href="userInfo.html">修改密码</a>
				</li> -->
				<li>
				<a class="menu-a" href="javascript:void(0)">我的消息<i class="fa fa-sort-down" style="top: 5px;">
				</i>
				</a><ul class="menu-list-second" style="overflow: hidden;">
				<li>
				<a class="menu-a" href="javascript:void(0)" data-showpage="4_0" data-href="/vipcenter/systemIndex">系统通知
				</a>
				</li>
				</ul>
				</li>
				</ul>
			</div>
			<div class="user-right" id="rightContent">

			</div>
		</div>
	</div>

	<jsp:include page="${ctx}/static/common/footer.jsp" />

</html>