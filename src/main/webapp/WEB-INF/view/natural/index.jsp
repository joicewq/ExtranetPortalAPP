<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
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
	<script type="text/javascript" src="${ctx}/static/model/natural/index.js"></script>
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
    <script type="text/javascript">
    	var approvalStatus="${approvalStatus}";
    </script>
</head>
<body>
<div class="tb_search">
	<input value="${approvalStatus}" type="hidden" id="approval-status" />
	<form id="rf" action="" class="form-inline" onsubmit="return false;">	
		<div class="form-group">
			<label for="classes">创建日期：</label>
			<input class="Wdate" type="text" placeholder="请选择" name="startDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',readOnly:true})" id="startDate" /> 至
			<input class="Wdate" type="text" placeholder="请选择" name="endDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',readOnly:true})" id="endDate" />
		</div>
		<div class="form-group">
			<label for="companyName">食盐名称：</label>
			<input type="text" name="saltName" id="saltName"/>
		</div>
	</form>
	<button class="user-list-search" id="queryBtn"><i class="fa fa-search mr5"></i>搜索</button>
</div>
<div class="mt15">
           <c:if test="${approvalStatus eq '11'}">
		   <button class="btn btn-default" id="addBtn"><i class="fa fa-plus mr5"></i>新增</button>
		   <button class="btn btn-default" id="editBtn"><i class="fa fa-plus mr5"></i>编辑</button>
		   <button class="btn btn-default" id="deleteBtn"><i class="fa fa-times mr5"></i>删除</button>
		   <button class="btn btn-default" id="detailBtn"><i class="fa fa-info mr5"></i>详情</button>
 </c:if>
</div>
<!--  <div class="iframe-cot">
	<div>
		<div class="right-form mt0">
			<form id="rf" action="" class="form-inline" onsubmit="return false;">
				<div class="tb_search">
					<div class="form-group-Wdate">
						<label for="classes">创建日期：</label>
						<input class="Wdate" type="text" placeholder="请选择" name="startDate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\')}',readOnly:true})" id="startDate" />至
						<input class="Wdate" type="text" placeholder="请选择" name="endDate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',readOnly:true})" id="endDate" />
					</div>
					<div class="form-group">
						<label for="companyName">食盐名称：</label>
						<input type="text" name="saltName" id="saltName"/>
					</div>
				</div>
			</form>
         </div>
		<div class="line-e6e"></div>
		<div class="form-top">
			<div class="pull-left">
				<button class="btn btn-default" id="add"><i class="fa fa-edit mr5"></i>新增</button>
				<button class="btn btn-default" id="delete"><i class="fa fa-edit mr5"></i>删除</button>

			</div>
			<div class="pull-right">
			   <button class="btn btn-orange" id=queryBtn><i class="fa fa-search"></i>查询</button>
			   <button class="btn btn-blue" id="resetBtn"><i class="fa fa-trash-o"></i> 清空</button>
			</div>
			
		</div>
	
 	</div>
 </div>
 -->
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
				 <table class="table-form table-bordered table-ellipsis table-fixed">
					<thead>
					<tr>
					    <th width="4%"><input onclick="base.selectAll(this,'associator')" type="checkbox" /></th>
                        <th width="10%">创建日期</th>
                        <th width="10%">食盐名称</th>
                        <th width="10%">商标</th>
                        <th width="10%">标准名称</th>
                        <th width="10%">质检报告</th>
                        <th width="12%">执行标准文件</th>
                        <th width="10%">审核结果 </th>
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
							<td colspan="8">无符合条件数据</td>
						{#/if}
					</tbody>
					</tbody>
				</table>
			{#/template MAIN}
			{#template ROW}
				<tr>                                                            
                	<td class="text-center">
	                    <input class="associator" name="{$T.id}" type="checkbox"/>
	                </td>
	                <td>{$T.createDate}</td>
					<td title='{$T.saltName}'>{$T.saltName}</td>
					<td title='{$T.tradeMark}'>{$T.tradeMark}</td>
					<td title='{$T.productId}'>{$T.productId}</td>
					<td title='{pager.status($T.qualityReportId)}'>{pager.status($T.qualityReportId)}</td>
					<td title='{pager.status($T.standardFile)}'>{pager.status($T.standardFile)}</td>
					<td id='{$T.approvalStatus}' title='{strChange($T.approvalStatus)}'>{strChange($T.approvalStatus)}</td>
				</tr>
			{#/template ROW}
	]]>
	</textarea>
</body>
<script type="text/javascript">
//列表审核结果状态转换
function strChange(approvalStatus){
	var stauts="";
	if(approvalStatus==11){
		return stauts="审核通过";
	}else if(approvalStatus==12){
		return stauts="审核驳回";
	}else{
		$('#auditOpinion').html("");
		$('#auditOpinion').attr('title','');
		return stauts="审核中";
	}
}
//列表审核意见控制
function auditOpinion(approvalStatus,approvalSuggestion){
	var stauts="";
	if(approvalStatus==11||approvalStatus==12){
		return approvalSuggestion;
	}else{
		$('#auditOpinion').html("");
		$('#auditOpinion').attr('title','');
	}
}
</script>
</html>
