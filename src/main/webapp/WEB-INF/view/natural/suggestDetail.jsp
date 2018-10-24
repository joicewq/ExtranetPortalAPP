<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->
<title>电子商务-后台管理-用户添加</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<!-- Basic Styles -->
<link rel="stylesheet" href="../css/layout.css">
<link rel="stylesheet" href="../css/graindepot.css">
</head>
<body>
	<div class="iframe-cot pt0" style="height:100%">
		<div class="right-form mt0">
			<form>
				<input type="hidden" name="id" id="id" value="${id }"/>
				 <ul class="form-list">
				 		   <li>
				 		   		<div class="label">
				 		   		<span class="sm-title">审核人：</span>
				 		   		<input class=""  value="${natrualInfo.approvalPerson}" readonly="readonly">
				 		   		</div>
							</li>
							<li>
								<div class="label">
				 		   		<span class="sm-title">审核日期：</span>
				 		   		<input class="Wdate" id="auditorDate" name=auditorDate value="${natrualInfo.modifyDate}" readonly="readonly">
				 		   		</div>
							</li>
							<li class="li-per">
								<div class="label">
									<span class="sm-title">审核意见：</span>
									<textarea class="" style="width:70%" type="text" rows="5" readonly="readonly" value="${natrualInfo.approvalSuggestion}">${natrualInfo.approvalSuggestion}</textarea>
								</div>
							</li>
				 </ul>
			</form>
		</div>
	</div>  
</body>
</html>
<script type="text/javascript">
		function formatDateFromMilliseconds(milliseconds, pattern){
			if($.trim(milliseconds) == "")
				return "";
			var d = new Date(parseInt(milliseconds));
			return d.Format(pattern);
		}
		$(function() {
			var element = $("input[name='auditorDate']");
			var dateStr = formatDateFromMilliseconds(element.val(), "yyyy-MM-dd");
			element.val(dateStr);
			$("input[name='auditorDate']").text(dateStr);
			});

	</script>
