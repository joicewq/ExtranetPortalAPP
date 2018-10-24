<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统通知</title>
<script type="text/javascript" src="/static/model/vipcenter/system_index.js"></script>
</head>
<body>
	<div class="div-center">
		<span id="zwxx" style="display: none;">暂无消息</span>
		<ul class="sys-info-list" id="msgView">
		<!-- 分页填充 -->
		</ul>
		<div id="page" class="ds-page clearfix">
	    	<ul>
				<li><a href="javascript:void(0);" onclick="pageIndex">首页</a></li>
				<li><a href="javascript:void(0);" onclick="pageUp">上一页</a></li>
				<li><a class="active"><span id="page-index"></span></a></li>
				<li><a href="javascript:void(0);" onclick="pageNext">下一页</a></li>
				<li><a>尾页</a></li>
	        	<li>共<span id="page-sum"></span>页，到第</li>
	            <li><input type="text" id="pageNum" class="input-page"></li>
	            <li>页</li>
	            <li><a href="javascript:void(0);" class="active hover">确定</a></li>
	        </ul>
	    </div>
	</div>
	<script id="template" type="text/html">
		<li>
			<span class="sys-tip">[系统]</span>
			<span class="biaoti"></span>
			<span class="list-date font12 text-gray riqi"></span>
		</li>		
	</script>
</body>
</html>