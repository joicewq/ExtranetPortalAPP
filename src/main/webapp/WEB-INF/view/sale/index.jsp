<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
	<meta charset="utf-8">
    <title></title>
    <link rel="icon" href="/static/images/jyht.ico" type="image/x-icon"/>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript"  src="${ctx}/static/model/sale/index.js"></script>
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
<div class="tb_search">
	<form id="rf" action="" class="form-inline" onsubmit="return false;">	
		<div class="form-group-Wdate">
			<label for="classes">用户名称：</label>
			<input  type="text" name="userName" id="userName" />
		</div>
	</form>
	<button class="user-list-search" id="queryBtn"><i class="fa fa-search mr5"></i>搜索</button>
</div>
<div class="mt15">
			<button class="btn btn-default" id="addBtn"><i class="fa fa-edit mr5"></i>新增</button>
			<button class="btn btn-default" id="updateBtn"><i class="fa fa-edit mr5"></i>修改</button>
		   <button class="btn btn-default" id="deleteBtn"><i class="fa fa-edit mr5"></i>删除</button>
			<button class="btn btn-default" id="detailBtn"><i class="fa fa-edit mr5"></i>详情</button>
	
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
	<div class="" id="tableTemplate">		
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
					    <th><input onclick="base.selectAll(this,'stocked')" type="checkbox" /></th>
                        <th>编号</th>
                        <th>食盐零售商</th>
                        <th>用户名称</th>
                        <th>月用盐量</th>
                        <th>标题</th>
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
							<td colspan="6">无符合条件数据</td>
						{#/if}
					</tbody>
					</tbody>
				</table>
			{#/template MAIN}
			{#template ROW}
				<tr>                                                            
                	<td width="4%" class="text-center">
	                    <input class="stocked" name="{$T.id}" type="checkbox"/>
	                </td>
	                <td>{$P.index}</td>
					<td>{$T.saltRetailers}</td>
					<td>{$T.userName}</td>
					<td>{$T.monthSalt}</td>
					<td>{$T.title}</td>
				</tr>
			{#/template ROW}
	]]>
	</textarea>
</body>
</html>
