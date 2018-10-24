<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站统计</title>
</head>
<body>
	<script data-main="/static/model/helpcenter/help.js" src="${ctx}/static/js/require.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main content">
		<div class="statistics">
			<img class="statistics-img" src="/static/images/helpcenter/statistics.png" />
			<span class="underline ml100"></span>
			<div class="statistics-center">
				<span class="bar bar1"></span>
				<span class="bar bar2"></span>
				<span class="bar bar3"></span>
				<span class="bar bar4"></span>
				<span class="bar bar5"></span>
				<div class="statistics-text">网站访问量： <span class="statistics-num">${accessNum}</span> 次</div>
				<jsp:useBean id="accessDate" class="java.util.Date" />
				<p class="text-gray text-center">（统计截止至：<fmt:formatDate value="${accessDate }" pattern="yyyy-MM-dd HH:mm:ss" />）</p>
			</div>
		</div>
	</div>
	<jsp:include page="/static/common/footer.jsp"></jsp:include>
</body>
</html>