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
<title>产品信息公示</title>
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<style type="text/css">
	.col-2{
		width: 11% !important;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		padding: 0 2px;
	}
	.content-list .content-item-list li{
		height: auto;
	}
</style>
</head>
<body>
	<script src="../static/js/require.js" data-main="/static/model/natural/naturalDetail2.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
		<div class="content">
			<div class="article-wrap hide" id="natural-detail" style="padding:35px 20px 70px 20px;">
				<div class="article-header">
					<h2 class="article-title">{{items[0].issueTitle}}</h2>
					<div class="article-info">
						<div class="inline-block">发布时间：<span id="filterPublishTime">{{modifyDate}}</span></div>
					</div>
				</div>
				<div class="article-content article-module">
					<div class="">
						{{items[0].issueContent}}
						<div class="content-list">
							<div class="content-header clearfix p0">
								<div class="inline-block text-center col-2">中盐/省份</div>
								<div class="inline-block text-center col-2">企业名称</div>
								<!-- <div class="inline-block text-center col-2">食盐种类</div> -->
								<div class="inline-block text-center col-2">食盐名称</div>
								<div class="inline-block text-center col-2">产品商标</div>
								<div class="inline-block text-center col-2">标准名称</div>
								<div class="inline-block text-center col-2">标准号</div>
								<!-- <div class="inline-block text-center col-2">食盐外观图案描述</div> -->
								<div class="inline-block text-center col-2">包装规格描述</div>
							</div>
							<div id="policies-list">
								<ul id="policies-list-items" class="content-item-list p0">
									<li class="clearfix" v-for="item in items">
										<span class=" text-center col-2" v-bind:title="item.province">{{item.province}}</span>
										<span class=" text-center col-2" v-bind:title="item.companyName">{{item.companyName}}</span>
									<!-- 	<span class=" text-center col-2" v-bind:title="item.kindes">{{item.kindes}}</span> -->
										<span class=" text-center col-2" v-bind:title="item.saltName">{{item.saltName}}</span>
										<span class=" text-center col-2" v-bind:title="item.tradeMark">{{item.tradeMark}}</span>
										<span class=" text-center col-2" v-bind:title="item.productId">{{item.productId}}</span>
										<span class=" text-center col-2" v-bind:title="item.standard">{{item.standard}}</span>
										<!-- <span class=" text-center col-2" v-bind:title="item.describeInfo">{{item.describeInfo}}</span> -->
										<span class=" text-center col-2" v-bind:title="item.cartonPackInfo">{{item.cartonPackInfo}}</span>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%-- <div class="ds-main">
		<div class="content">
			<div class="article-wrap hide" id="natural-detail">
				<div class="article-header">
					<h2 class="article-title">{{item.issueTitle}}</h2>
					<div class="article-info">
						<div class="inline-block">发布时间：<span id="filterPublishTime">{{modifyDate}}</span></div>
						<div class="inline-block ml40">浏览人数：<span>${PoliciesInfo.title}</span></div>
					</div>
				</div>
				<div class="article-content article-module">
					<div class="">
						{{content}}
						<div class="content-list">
							<table class="table data-table table-bordered table-fixed">
							    <tbody>
							    <tr>
							        <td class="ds-bg-gray text-right">中盐/省份</td>
							        <td>{{item.province}}</td>
							        <td class="ds-bg-gray text-right">企业名称</td>
							        <td>{{item.companyName}}</td>
							    </tr>
							    <tr>
							        <td class="ds-bg-gray text-right">食盐种类</td>
							        <td>{{item.kindes}}</td>
							        <td class="ds-bg-gray text-right">食盐名称</td>
							        <td>{{item.saltName}}</td>
							    </tr>
							    <tr>
							        <td class="ds-bg-gray text-right">产品商标</td>
							        <td>{{item.tradeMark}}</td>
							        <td  class="ds-bg-gray text-right">标准名称</td>
							        <td>{{item.productId}}</td>
							    </tr>
							    <tr>
							        <td class="ds-bg-gray text-right">标准号</td>
							        <td colspan="3">{{item.standard}}</td>
							    </tr>
							    <tr>
							        <td class="ds-bg-gray text-right">食盐外观图案描述</td>
							        <td colspan="3">{{item.describeInfo}}</td>
							    </tr>
							    <tr>
							        <td class="ds-bg-gray text-right">包装规格描述</td>
							        <td colspan="3">{{item.cartonPackInfo}}</td>
							    </tr>
							    </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> --%>
	<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>