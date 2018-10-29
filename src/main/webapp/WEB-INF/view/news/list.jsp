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
<title>外宣门户</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="icon" href="/static/images/jyht.ico" type="image/x-icon"/>
 
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/news/newsList.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<span id="breadcrumb"></span>
	<div class="ds-main" id="container">
			<div class="pull-left" style="width:200px">
				<div class="" id="menu"></div>
			</div>
			<div class="right-content content">
				<!-- <div class="search-wrap">
					<div class="tb_search">
						<div class="form-group">
							<label for="spec">日期查询：</label>
							<input type="text" name="startDTime" id="startDTime" class="date-inp Wdate  required" onclick="WdatePicker({maxDate:'%y-%M-%d'})"> 
							至
							<input type="text" name="endDTime" id="endDTime" class="date-inp Wdate required" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDTime\')}',maxDate:'%y-%M-%d'})">
						</div>
						<div class="form-group">
							<label for="spec">标题查询：</label>
							<input type="text" name="spec" id="spec">
						</div>
						<a class="ds-btn ds-btn-small ds-btn-primary" id="queryBtn"><i class="fa fa-search"></i> 搜索</a>
					</div>
				</div> -->
				<div class="content-list">
					<div class="content-header">
						<div class="inline-block content-header-title">标题</div>
						<div class="inline-block content-header-date">发布时间</div>
					</div>
					<div id="policies-list">
						<ul id="policies-list-items" class="content-item-list">
						</ul>
						<div id="pagination">
							<jsp:include page="${ctx}/static/common/pager.jsp" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<textarea id="tmpl" style="display: none">
			<![CDATA[
					{#template MAIN}
						{#if $T.totalRow>0}
							{#foreach $T.data as row}
								<li>
									<a href="/news/detail?id={$T.row.id}&columnId={$T.columnId}" target="_blank" class="content-item-title inline-block">{$T.row.title}</a>
									<span class="content-item-date inline-block">{$T.row.createTime}</span>
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
		<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>