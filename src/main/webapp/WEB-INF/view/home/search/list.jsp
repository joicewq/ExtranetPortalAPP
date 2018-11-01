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
	<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script> 
	<script src="/static/js/require.js" data-main="/static/model/home/serach.js"></script>
	<jsp:include page="/static/common/header.jsp" />
	<div class="ds-main">
		<div class="content">
			<div class="search-wrap">
				<form id="rf" action="" class="form-inline" onsubmit="return false;">
					<div class="tb_search">
						<div class="form-group">
							<label for="spec">标题/内容查询：</label>
							<input type="text" name="title" id="title" value="${title }">
						</div>
						<a class="ds-btn ds-btn-small ds-btn-primary" id="queryBtn"><i class="fa fa-search"></i> 搜索</a>
					   <table style="width:100%;">
					   <tr>
					     <td>
					        <label for="spec">搜索结果：包含以下全部的关键词</label>	
					     </td>
					     <td>
					        <input type="text" name="allKey" id="allKey" value="">
					      </td>
					   </tr>
					   <tr>
					     <td>
					        <label for="spec">包含以下完整的关键词</label>	
					     </td>
					     <td>
					        <input type="text" name="completeKey" id="completeKey" value="">
					      </td>
					   </tr>
					   <tr>
					     <td>
					        <label for="spec">包含以下任意一个的关键词</label>	
					     </td>
					     <td>
					        <input type="text" name="eitherKey" id="eitherKey" value="">
					      </td>
					   </tr>
					   <tr>
					     <td>
					        <label for="spec">不包含以下关键词</label>	
					     </td>
					     <td>
					        <input type="text" name="excludeKey" id="excludeKey" value="">
					      </td>
					   </tr>
					    <tr>
					     <td>
					        <label for="spec">时间：限定要搜索网页的时间是</label>	
					     </td>
					     <td>
					         <input  type="radio" id="updateTimeFlag" name="updateTimeFlag" value="1">任何时间
					         <input  type="radio" id="updateTimeFlag" name="updateTimeFlag" value="2">最近一天
					         <input  type="radio" id="updateTimeFlag" name="updateTimeFlag"	value="3">最近一周
					         <input  type="radio" id="updateTimeFlag" name="updateTimeFlag"	value="4">最近一月
					         <input  type="radio" id="updateTimeFlag" name="updateTimeFlag"	value="5">最近一年
					         <input  type="radio" id="updateTimeFlag" name="updateTimeFlag"	value="6">自定义
					         <input class="ds-inp-control ds-inp-control-min" placeholder="请选择起始时间" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="updateTime1" id="updateTime1" />
			                	至
			                 <input class="ds-inp-control ds-inp-control-min" placeholder="请选择终止时间" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"name="updateTime2" id="updateTime2"/>
					         <!-- <input type="text" name="updateTime1" id="updateTime1" value="2018-10-1" > ~
							 <input type="text" name="updateTime2" id="updateTime2" value="2018-10-10"  >
							 <input type="text" onfocus="WdatePicker({minDate:'2013-09-14', maxDate:'2013-9-20'})"/>   -->  
					      </td>
					   </tr>
					   <tr>
					     <td>
					        <label for="spec">文档格式：搜索网页格式是</label>	
					     </td>
					     <td>
					         <input  type="checkbox" id="filetype" name="filetype" value=".doc">.doc
					         <input  type="checkbox" id="filetype" name="filetype" value=".docx">.docx
					         <input  type="checkbox" id="filetype" name="filetype" value=".xls">.xls
					         <input  type="checkbox" id="filetype" name="filetype" value=".xlsx">.xlsx
					         <input  type="checkbox" id="filetype" name="filetype" value=".ppt">.ppt
					         <input  type="checkbox" id="filetype" name="filetype" value=".pptx">.pptx
					         <input  type="checkbox" id="filetype" name="filetype" value=".pdf">.pdf
					         <input  type="checkbox" id="filetype" name="filetype" value=".txt">.txt
					         <input  type="checkbox" id="filetype" name="filetype" value=".rar">.rar
					         <input  type="checkbox" id="filetype" name="filetype" value=".zip">.zip
					         <input  type="checkbox" id="filetype" name="filetype" value=".7z">.7z
					      </td>
					   </tr>
					   <tr>
					     <td>
					        <label for="spec">排序方式：搜索排序方式是</label>	
					     </td>
					     <td>
					         <input  type="radio" id="sort" name="sort" value="1">按相关度排序
					         <input  type="radio" id="sort" name="sort" value="2">按时间排序					        
					      </td>
					   </tr>
					   <tr>
					     <td>
					        <label for="spec">关键词位置：查询关键词位于</label>	
					     </td>
					     <td>
					         <input  type="radio" id="keyLocation" name="keyLocation" value="1">网页任何位置
					         <input  type="radio" id="keyLocation" name="keyLocation" value="2">网页标题中
					         <input  type="radio" id="keyLocation" name="keyLocation" value="3">网页正文中
					         <input  type="radio" id="keyLocation" name="keyLocation" value="4">网页网址
					      </td>
					   </tr>
					   <tr>
					     <td>
					       <a class="ds-btn ds-btn-small ds-btn-primary" id="superQueryBtn"><i class="fa fa-search"></i> 高级搜索</a>
					     </td>
					     <td>
					      </td>
					   </tr>
					   </table>
					   
						
					</div>
				</form>
			</div>
			<div class="content-list" style="margin-top:250px;">
				<div class="content-header" style="height: 30px">
					<div class="inline-block content-header-title">标题</div>
					<div class="inline-block content-header-date">发布时间</div>
				</div>
				<div id="policies-list">
					<ul id="policies-list-items" class="content-item-list">
					</ul>
					<div id="pagination">
						<jsp:include page="${ctx}/static/common/searchPager.jsp" />
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
							<li id="solrTitle{$T.row.id}"  style="height:auto">
								<a  href="/news/detail?id={$T.row.id}" target="_blank" class="content-item-title inline-block">{$T.row.title}</a>
								来源：
								{#if $T.row.cfrom}
								   {$T.row.cfrom}
								{#/if}
								 <span class="content-item-date inline-block">{$T.row.updateTime}</span>
								 <br/>	
								{#if $T.row.content}
								  <span>															    
							        	{$T.row.content}
								  </span>
								{#/if}
								
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