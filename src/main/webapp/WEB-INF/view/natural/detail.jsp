<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<jsp:include page="/static/common/jstl.jsp" />
<jsp:include page="/static/common/import.jsp" />
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<title>检测结果公示</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<style type="text/css">
	.col-6{
		width: 50% !important;
	}
	.content-list .content-item-list li{
		height: auto;
	}
</style>
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/natural/naturalDetail.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
		<div class="content">
			<div class="article-wrap hide" id="natural-detail">
				<div class="article-header">
					<h2 class="article-title">{{title}}</h2>
					<div class="article-info">
						<div class="inline-block">发布时间：<span id="filterPublishTime">{{modifyDate}}</span></div>
						<%-- <div class="inline-block ml40">浏览人数：<span>${PoliciesInfo.title}</span></div> --%>
					</div>
				</div>
				<div class="article-content article-module">
					<div class="">
						{{content}}
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>