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
<title>政策法规</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/article/detail.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
			<div class="content">
				<div class="article-wrap">
					<div class="article-header">
						<h2 class="article-title">${PoliciesInfo.title}</h2>
						<input type="hidden" value="${upload_url }" id="accessory_url">
						<div class="article-info">
							<div class="inline-block">发布时间：<span id="filterPublishTime"></span></div>
							<input type="hidden" value="${PoliciesInfo.publishDate}" id="publishTime" />
							<%-- <div class="inline-block ml40">浏览人数：<span>${PoliciesInfo.title}</span></div> --%>
						</div>
					</div>
					<div class="article-content article-module">
						<div class="font-special">
							${PoliciesInfo.content}
						</div>
					</div>
					<c:if test="${PoliciesInfo.uploadIds != ''}">
						<div class="article-accessory article-module">
							<input type="hidden" value="${PoliciesInfo.uploadIds}" id="accessory" />	
							<div class="article-accessory-title"><i class="iconfont icon-fujian"></i> 附件下载</div>
							<div id="uploadDiv" class="clearfix hide"></div>
							<ul class="article-accessory-list">
							</ul>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>