<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/static/common/jstl.jsp"%>
<jsp:include page="${ctx}/static/common/import.jsp" />
<!DOCTYPE html>
<html lang="en-us">
<head>
	<meta charset="utf-8">
    <title>产品信息告知</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript" src="${ctx}/static/model/member/detail.js"></script>
	<%@ include file="/static/common/pager.jsp"%>
    <style type="text/css" >
        
        .tab-content{
            padding:0px;
        }
        .pages{
        	margin-bottom:0px;
        }
        .iframe-cot {
            border-radius: 0 4px 4px 4px;
        }
    </style>
</head>
<body>
<div class="iframe-cot">
	<div class="right-form mt0 p0">
		<form id="rf" action="" class="form-inline" onsubmit="return false;">
		<input type="hidden" value="${member.companyId}" id="supplierId" name="supplierId"/>
		</form>
    </div>

	<div id="tableTemplate">		
		<div id="view"  class="mt15">
			<!-- 内容填充位置 -->
		</div>
		<div id="pagination"></div>
	</div>

	<!--表格模版 -->
	<textarea id="tmpl" style="display:none">
	<![CDATA[
			{#template MAIN}
				 <table class="table-form table-bordered">
					<thead>
					<tr>
                        <th>提交时间</th>
                        <th>审核时间</th>
                        <th>审核用户</th>
                        <th>审核意见</th>
                        <th>审核结果</th>
					</tr>
					</thead>
					<tbody>
						{#param name=startNum value=($T.curPage - 1) * $T.pageLine }
						{#if $T.totalRow != 0} 									
							{#foreach $T.data as row}
								{#param name=index value=($P.startNum + $T.row$index + 1)}
								{#include ROW root=$T.row}
							{#/for}
						{#else }
							<td colspan="5">无符合条件数据</td>
						{#/if}
					</tbody>
					</tbody>
				</table>
			{#/template MAIN}
			{#template ROW}
				<tr>                                                            
	                <td>{$T.createDate}</td>
	                <td>{$T.auditorDate}</td>
					<td>{$T.auditor}</td>
					<td>{$T.auditorSuggestion}</td>
					<td>{pager.stautsChange($T.approvalStatus)}</td>
				</tr>
			{#/template ROW}
	]]>
	</textarea>
</body>
</html>

