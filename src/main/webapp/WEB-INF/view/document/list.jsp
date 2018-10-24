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
<title>资料下载</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<body>
	<script src="${ctx}/static/js/require.js" data-main="${ctx}/static/model/document/documentList.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
			<div class="" id="menu"></div>
			<div class="right-content content">
				<div class="search-wrap">
					<div class="tb_search">
						<!-- <div class="form-group">
							<label for="spec">日期查询：</label>
							<input type="text" name="startDTime" id="startDTime" class="date-inp Wdate  required" onclick="WdatePicker()"> ~
							<input type="text" name="endDTime" id="endDTime" class="date-inp Wdate required" onclick="WdatePicker()">
						</div> -->
						<div class="form-group">
							<label for="spec">标题查询：</label>
							<input type="text" name="spec" id="spec">
						</div>
						<a class="ds-btn ds-btn-small ds-btn-primary" id="queryBtn"><i class="fa fa-search"></i> 搜索</a>
					</div>
				</div>
				<div class="content-list">
					<div class="content-header">
						<div class="inline-block content-header-title">标题</div>
						<div class="inline-block content-header-date">下载</div>
					</div>
					<div id="policies-list">
						<ul id="policies-list-items" class="content-item-list">
						</ul>
						<div id="pagination" class="mr30">
							<jsp:include page="${ctx}/static/common/pager.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<textarea id="tmpl" style="display: none">
			<![CDATA[
					{#template MAIN}
						{#if $T.data.length>0}
							{#foreach $T.data as row}
								<li>
									<a href="javascript:void(0);" class="content-item-title inline-block">{$T.row.dTitle}</a>
									<a href="javascript:void(0);" data-docId="{$T.row.docId}" data-id="{$T.row.id}" class="content-item-date text-center js-download-btn inline-block">
										<i class="block fa fa-download" style="line-height:40px;"></i> 
									</a>
								</li>
							{#/for}
						{#else}
							<li>
								<div class="text-center">无符合条件数据</div>
							</li>
						{#/if}
					{#/template MAIN}
			]]>
		</textarea>
		<input type="hidden" value="${upload_url}" id="accessory_url" />
		<iframe id="download-iframe" class="hide"></iframe>
		<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>