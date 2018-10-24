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
<title>检索</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<body>
	<script src="/static/js/require.js" data-main="/static/model/home/serach.js"></script>
	<jsp:include page="/static/common/header.jsp" />
	<div class="ds-main">
		<div class="content">
			<div class="search-wrap">
				<form id="rf" action="" class="form-inline" onsubmit="return false;">
					<div class="tb_search">
						<div class="form-group">
							<label for="spec">日期查询：</label>
							<input type="text" name="startDTime" id="startDTime" value="${startDTime} " class="date-inp Wdate  required" onclick="WdatePicker()"> ~
							<input type="text" name="endDTime" id="endDTime" value="${endDTime }" class="date-inp Wdate required" onclick="WdatePicker()">
						</div>
						<div class="form-group">
							<label for="spec">标题查询：</label>
							<input type="text" name="title" id="title" value="${title }">
						</div>
						<a class="ds-btn ds-btn-small ds-btn-primary" id="queryBtn"><i class="fa fa-search"></i> 搜索</a>
					</div>
				</form>
			</div>
			<div class="content-list">
				<div class="content-header" style="height: 30px">
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

					{#if $T.totalRow != 0} 									
						{#foreach $T.data as row}
							<li>
								<a href="/salt/detail?id={$T.row.id}" target="_blank" class="content-item-title inline-block">{$T.row.title}</a>
								<span class="content-item-date inline-block">{$T.row.createDate}</span>
							</li>
						{#/for}
					{#else }
						<li class="text-center">
							无符合条件数据
						</li>
					{#/if}
				{#/template MAIN}
		]]>
	</textarea>
	
	<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>

</html>