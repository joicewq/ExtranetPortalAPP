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
<title>企业信息公示</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<style type="text/css">
	.col-12{
		width: 100% !important;
	}
	.content-list .content-item-list li{
		height: auto;
	}
</style>
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/supplier/supplierDetail.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
		<div class="content">
			<div class="article-wrap hide" id="supplier-detail">
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
						<div class="content-list">
							<div class="content-header clearfix p0">
							 	 <div class="inline-block text-center col-1">序号</div>
							 	 <div class="inline-block text-center col-1">省(区、市)</div>
								 <div class="inline-block text-center col-2">食盐批发企业名称</div>
								 <div class="inline-block text-center col-1">法人/负责人</div>
								 <div class="inline-block text-center col-3">营业执照编号/统一社会信用代码</div>
								 <div class="inline-block text-center col-2">食盐批发许可证</div>
								 <div class="inline-block text-center col-2">企业类型</div>
								 <!--<div class="inline-block text-center col-1">审核结果</div> -->
							</div>
							<div id="policies-list">
								<ul id="policies-list-items" class="content-item-list p0">
									<li class="clearfix" v-for="(item,index) in items">
										<span class="text-center col-1">{{index+1}}</span>
										<span class="text-center col-1">{{item.province | statusFilter}}</span>
										<span class="text-center col-2">{{item.companyName}}</span>
										<span class="text-center col-1">{{item.legalRepresentative}}</span>
										<span class="text-center col-3">{{item.charterCode}}</span>
										<span class="text-center col-2">食盐批字第{{item.licenceWholesale}}号</span>
										<span class="text-center col-2">{{item.orderType | typeFilter}}</span>
										<!-- <span class=" text-center col-1">{{item.approvalStatus | statusFilter}}</span> -->
									</li>
								</ul> 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>