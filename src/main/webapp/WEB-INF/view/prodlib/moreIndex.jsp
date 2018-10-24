<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<title>产品发布更多</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script type="text/javascript" src="${ctx}/static/js/require.js" data-main="${ctx}/static/model/prodlib/moreIndex.js"></script>
<%@ include file="/static/common/pager.jsp"%>
</head>
<body>
	<div class="user-right">
		<form id="rf" action="" class="form-inline" onsubmit="return false;">
        	<div class="tb_search">
					<div class="form-group">
						<label for="name">名称：</label>
						<input type="text" name="name" id="name"/>
					</div>
					<div class="form-group">
						<label for="spec">规格：</label>
						<input type="text" name="spec" id="spec"/>
					</div>
					<button class="user-list-search">搜索</button>
					
					<button class="btn btn-default pull-right" id="delContract"><i class="fa fa-trash mr5"></i>删除</button>
<!-- 					<button class="btn btn-default pull-right mr5"><i class="fa fa-edit mr5"></i>编辑</button> -->
					<button class="btn btn-default pull-right mr5" id="delContract"><i class="fa fa-minus-circle mr5"></i>停牌</button>
					<button class="btn btn-default pull-right mr5"><i class="fa fa-cc-diners-club mr5"></i>挂牌</button>
				</div>
        </form>
		
		<div class="table-scroll">
			<div id="view">
				<!-- 内容填充位置 -->
			</div>
			<div id="pagination"></div>
		</div>
	</div>
	<textarea id="tmpl" style="display: none">
		<![CDATA[
			{#template MAIN}
				<table class="table-form table-bordered table-hover">
					<thead>
						<tr>
	                       <th><input onclick="base.selectAll(this,'category')" type="checkbox" /></th>
	                       <th>名称</th>
						   <th>规格</th>
						   <th>数量</th>
						   <th>报价（元/kg）</th>
						   <th>品级</th>
						   <th>产品标准号</th>
						   <th>有效期</th>
						   <th>状态</th>
						   <th>详情</th>
	                   </tr>
					</thead>
					<tbody>
						<!-- 增加序号定义
							$P startNum 定义每页开始行号
							$P index 定义行索引
						-->
						{#param name=startNum value=($T.curPage - 1) * $T.pageLine }
						{#if $T.totalRow != 0} 									
							{#foreach $T.data as row}
								{#param name=index value=($P.startNum + $T.row$index + 1)}
								{#include ROW root=$T.row}
							{#/for}
						{#else }
							<td colspan="10">无符合条件数据</td>
						{#/if}
					</tbody>
				</table>
			{#/template MAIN}
			
			{#template ROW}
				<tr>
					<td width="4%" class="text-center">
	                    <input class="category" name="{$T.id}" type="checkbox"/>
	                </td>
					<td >{$T.name}</td>
					<td>{$T.spec}</td>
					<td>{$T.number}</td>
					<td>{$T.price}</td>
					<td >{$T.grade}</td>
					<td>{$T.stdNum}</td>
					<td>{$T.listedDate}</td>
					<td>{$T.sta}</td>
					<td>详情</td>
				</tr>
			{#/template ROW}
		]]>
	</textarea>
</body>

</html>
