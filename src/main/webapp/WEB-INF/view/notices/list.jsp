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
<title>天津市外宣门户</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/notice/noticeList.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	 <div class="ds-main" id="container">
			<div class="pull-left" style="width:200px">
				<div class="" id="menu"></div>
			</div>
			<div class="right-content content">
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
									<a onclick="itemClick(this)" type="{$T.row.forDepartment},{$T.row.forObject},{$T.row.paperCode},{new Date($T.row.startTime).Format("yyyy-MM-dd")},{new Date($T.row.endTime).Format("yyyy-MM-dd")},{$T.row.paperName},{$T.row.paperRemark},{$T.row.id}" target="_blank" class="content-item-title inline-block">{$T.row.paperName}</a>
									<span class="content-item-date inline-block">{new Date($T.row.startTime).Format("yyyy-MM-dd")} 至 {new Date($T.row.endTime).Format("yyyy-MM-dd")}</span>
								</li>
							{#/for}
						{#else}
							<li>
								<div class="text-center">无符合条件数据</div>
							</li>
						{#if}
					{#/template MAIN}
			]]>
		</textarea> 
		<jsp:include page="${ctx}/static/common/footer.jsp" />
</body>
</html>
<script type="text/javascript">
 function itemClick(obj){
	 var arrList = obj.type.split(",");
	 var questionnaireform={//forDepartment,forObject,paperCode,startTime,endTime,paperName,paperRemark
			 	forDepartment: encodeURI(arrList[0]),  //调研部门{$T.row.forDepartment},{$T.row.forObject},{$T.row.paperCode},{$T.row.startTime},{$T.row.endTime},{$T.row.paperName},{$T.row.paperRemark}
		        forObject: encodeURI(arrList[1]),   //调研对象
		        paperCode: arrList[2],    //问卷编码
		        startTime: arrList[3],    
		        endTime: arrList[4],
		        paperName: encodeURI(arrList[5]),   //问卷名称
				paperRemark: encodeURI(arrList[6])
	} 
	 window.location.href="/salt/querstionDetail?id=" + arrList[7]+"&questionnaireform="+JSON.stringify(questionnaireform)
}
</script>