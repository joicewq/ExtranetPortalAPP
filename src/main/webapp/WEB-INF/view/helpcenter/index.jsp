<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助中心</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
</head>
<body>
	<script data-main="/static/model/helpcenter/help.js" src="${ctx}/static/js/require.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
		<div class="help-menu" id="help-menu"></div>
		<div class="helpcenter-content" id="helpcenter-content"></div>
	</div>
	<jsp:include page="/static/common/footer.jsp"></jsp:include>
</body>
</html>