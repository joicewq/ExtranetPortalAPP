<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<title>会员中心</title>
	<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
	<link rel="stylesheet" href="/static/css/layout.css"/>
	<link rel="stylesheet" href="/static/css/personal.css"/>
	<link rel="stylesheet" href="/static/css/fonts.min.css"/>
</head>
 <script type="text/javascript" src="/static/model/vipcenter/demand.js"></script>
<%@ include file="/static/common/pager.jsp"%>
</head>
<body>
		<form id="rf" action="" class="form-inline" onsubmit="return false;">
        	<div class="tb_search">
				<div class="form-group">
					<label for="name">产品名称：</label>
					<input type="text" name="productName" id="productName"/>
				</div>
				<div class="form-group">
					<label for="spec">截止日期：</label>
					<input type="text" name="startDTime" id="startDTime" class="date-inp Wdate  required" onClick="WdatePicker()" /> ~ 
					<input type="text" name="endDTime" id="endDTime" class="date-inp Wdate required" onClick="WdatePicker()" />
				</div>
				<button class="user-list-search" id=queryBtn><i class="fa fa-search"></i>搜索</button>
				
			</div>
			<div class="mt15">
				<button class="btn btn-default mr5" id="listedBtn"><i class="fa fa-cc-diners-club mr5"></i>挂牌</button>
				<button class="btn btn-default mr5" id="delistedBtn"><i class="fa fa-minus-circle mr5"></i>停牌</button>
				<button class="btn btn-default" id="delBtn"><i class="fa fa-trash mr5"></i>删除</button>
			</div>
        </form>
		
		<div id="view12" class="mt15">
			<!-- 内容填充位置 -->
		</div>
		<div id="pagination12"></div>
		<textarea id="tmpl12" style="display: none">
			<![CDATA[
				{#template MAIN}
					<table class="table-form table-bordered table-hover">
						<thead>
							<tr>
		                       <th><input onclick="base.selectAll(this,'category')" type="checkbox" /></th>
		                       <th>产品名称</th>
							   <th>规格</th>
							   <th>数量</th>
							   <th>报价（元/kg）</th>
							   <th>截止日期</th>
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
					<tr id="{$T.id}">
						<td width="4%" class="text-center">
		                    <input class="category" name="{$T.id}" sta="{$T.productState}" type="checkbox"/>
		                </td>
						<td>{$T.pIdName}</td>
						<td>{$T.productSpec}</td>
						<td>{$T.productNum}</td>
						<td>{$T.productPrice}</td> 
						<td>
							{#if $T.deadtime}{new Date($T.deadtime).Format('yyyy-MM-dd')}
							{#else }--
							{#/if}
						</td>
						<td>
							{#if $T.productState=='1'}审核中
							{#elseif $T.productState=='2'}已通过
							{#elseif $T.productState=='3'}不通过
							{#elseif $T.productState=='4'}挂牌
							{#elseif $T.productState=='5'}停牌
							{#else }--
							{#/if}
						</td>
						<td><a style="cursor:pointer;" id="showBtn">详情</a></td>
					</tr>
				{#/template ROW}
			]]>
		</textarea>
</body>
</html>