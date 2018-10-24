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
<title>违纪举报</title>
<link rel="Shortcut Icon" href="/static/images/jgqt.ico">
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<style type="text/css">
#file_upload_div br,#file_list br{
	display: none;
}
</style>
</head>
<body>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/complaints/report.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
	<div class="ds-main">
			<div class="content">
				<div class="normal-wrap">
					<div class="normal-module bg-light-red">
						<p>如您发现广东省盐业监管服务平台和直属单位党员领导干部和工作人员有违纪行为，可通过下属渠道举报</p>
						<ul class="mt20 li-mt10">
							<li>
								<span class="text-blue mr5">●</span>来信举报：<span>广东省广州市中山四路18号，邮编：510055</span>
							</li>
							<li>
								<span class="text-blue mr5">●</span>举报电话：<span>020-83876533</span>
							</li>
							<li>
								<span class="text-blue mr5">●</span>举报信箱：<span>广东省越秀区中山四路178号盐业集团大厦21楼电梯口</span>
							</li>
							<li>
								<span class="text-blue mr5">●</span>举报邮箱：<span>gdsyyjt@163.com</span>
							</li>
						</ul>
					</div>
					<!-- 在线举报 -->
					<div class="row modules-banner">
						<img src="${ctx}/static/images/reportOnline.png" class="modules-banner-left">
						<div class="modules-banner-line"></div>
					</div>
					<div class="normal-module">
						<ul class="ds-user-from member-form normal-form clearfix form-list">
							<form id="report-form">
							    <li>
						            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>案件名称:</label>
						            <div class="ds-from-group">
						                <input class="ds-inp-control required" maxDataLength="50" value="" placeholder="请输入案件名称" type="text" id="caseName" name="caseName" />
						                <span class="ds-from-tips ds-text-red ml10"></span>
						            </div>
						        </li>
						        <li>
								     <label class="ds-from-left"><em class="ds-text-red mr5">*</em>区域:</label>
						             <div class="ds-from-group">
						              <select name="caseArea" id="caseArea" class="ds-inp-control se2 required">
										 <option value="">请选择</option>
									  </select>
									  <span class="ds-from-tips ds-text-red ml10"></span>
						      	     </div>
					      	     </li>
					      	     <li>
						            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>举报时间:</label>
						            <div class="ds-from-group">
						                <input class="ds-inp-control required Wdate" value="" placeholder="请选择" onclick="WdatePicker();" type="text" id="rcDate" name="rcDate" readonly="readonly"/>
						                <span class="ds-from-tips ds-text-red ml10"></span>
						            </div>
						        </li>
								<li>
						            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>举报人:</label>
						            <div class="ds-from-group">
						                <input class="ds-inp-control required" maxDataLength="50" value="" placeholder="请输入举报人" type="text" id="rcName" name="rcName" />
						                <span class="ds-from-tips ds-text-red ml10"></span>
						            </div>
						        </li>
						        <li>
						            <label class="ds-from-left">联系电话:</label>
						            <div class="ds-from-group">
						                <input class="ds-inp-control mobilePhone" value="" placeholder="请输入联系电话" type="text" id="mobile" name="mobile" />
						                <span class="ds-from-tips ds-text-red ml10"></span>
						            </div>
						        </li>
						        <li class="width-fixed">
						            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>举报内容:</label>
						            <div class="ds-from-group">
						            	<textarea rows="" cols="" class="required" maxDataLength="5000" placeholder="简明扼要记录、时间、地点、人物、违法事实" id="rcaseContent" name="rcaseContent"></textarea>
						                <span class="ds-from-tips ds-text-red ml10"></span>
						            </div>
						        </li>
							</form>
					        <li class="width-fixed">
					        	<label class="ds-from-left">上传附件:</label>
					        	<div id="file_upload_div" style="margin-left:150px;"></div>
					        </li>
					        <li class="width-fixed">
					            <div class="ds-btn-group ds-btn-group-center">
					            	<a href="javascript:void(0);" class="ds-btn ds-btn-primary" id="js-submit-btn">提交</a>
					            </div>
					        </li>
						</ul>
					</div>
				</div>
			</div>
		</div>
<jsp:include page="${ctx}/static/common/footer.jsp" />
<script type="text/javascript">
$(document).ready(function(){
	$.post("/claims/getAllArea",{},function(data){ 
		$.each(data,function(infoIndex,info){
				$("#caseArea").append("<option value="+info.areaCode+">"+info.areaName+"</option>");
		});
	});
});
</script>
</body>
</html>