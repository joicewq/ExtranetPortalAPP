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
<title>防伪追溯查询</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<style>
.report-module-h2{
	font-size: 18px;
	padding-bottom: 10px;
	margin-bottom: 15px;
	border-bottom: 1px solid #c2c2c2;
}
</style>
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/trace/search.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
			<div class="content">
				<div class="normal-wrap">
					<div class="row modules-banner">
						<img src="${ctx}/static/images/trace.png" class="modules-banner-left">
						<div class="modules-banner-line"></div>
					</div>
					<div class="bg-light-blue normal-module">
						<ul class="tabs clearfix">
							<li>产品二维码编码</li>
						</ul>
						<div class="tab-contentmian">
							<div class="tab-content" id="search-form">
								<div class="inline-block">
									<input type="text" id="qrCode" name="qrCode" class="ipt-normal require" maxDataLength="20" placeholder="请输入产品包装袋上的溯源码">
									<span class="ds-from-tips ds-text-red"></span>
								</div>
								<div class="inline-block">
									<input type="text" class="ipt-min matchVerify require" name="verifyCodeIpt" id="verifyCodeIpt" placeholder="验证码">
									<span class="ds-from-tips ds-text-red"></span>
								</div>
								<a href="javascript:void(0);" class="inline-block">
									<img src="/verify/code" class="verifyImg" id="verifyCode">
								</a>
								<div class="pull-right">
									<a href="javascript:void(0);"  class="ds-btn ds-btn-primary" id="js-search-btn">
										<i class="iconfont icon-search"></i> 查询
									</a>
								</div>
							</div>
						</div>
					</div>
					
					<div id="info_detail_div">
					
					</div>
					
					
					
				</div>
			</div>
		</div>
		<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>


</html>