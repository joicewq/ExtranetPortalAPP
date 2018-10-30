<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
	<meta charset="utf-8">
    <title>调查问卷</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="${ctx}/static/css/select2.css"/>
	<script src="../static/js/require.js" data-main="${ctx}/static/model/notice/noticeDetail.js"></script>
	<jsp:include page="${ctx}/static/common/header.jsp" />
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
        #loaded1 > .clearfix, #loaded2 > .clearfix,#loaded3 > .clearfix,#loaded4 > .clearfix,#loaded5 > .clearfix{
        	margin-left: 24px;
        }
            #loaded1 > br, #loaded2 > br,#loaded3 > br,#loaded4 >br,#loaded5 >br{
        	display: none;
        }
        .form-list > li .ds-from-tips{
        	left:150px;
        }
    </style>
</head>
<form id="supply-form">
	<input type="hidden" id="id" value="${NaturalInfo.id}">
	<ul class="form-list">
		<li class="li-per">
			<div class="label">
				<span class="sm-title">调研部门</span>
				<textarea name="forDepartment"  id="forDepartment" rows="2" disabled="disabled"></textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">问卷编码：</span>
				<textarea name="paperCode"  id="paperCode" rows="2" disabled="disabled"></textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">开始时间：</span>
				<textarea name="startTime"  id="startTime" rows="2" disabled="disabled"></textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">结束时间：</span>
				<textarea name="endTime"  id="endTime" rows="2" disabled="disabled"></textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">问卷名称：</span>
				<textarea name="paperName"   id="paperName" rows="2" disabled="disabled"></textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">问卷说明：</span>
				<textarea  name="paperRemark" id="paperRemark" rows="2" disabled="disabled" ></textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title">调研对象：</span>
				<textarea  name="forObject" id="forObject" rows="2" disabled="disabled" ></textarea>
			</div>
		</li>
			
	</ul>
	<div id="question" style="margin-left:300px;"></div>
	<div class="text-center mt10">
		<button class="btn btn-primary" type="button" style="font-size:16px" id="save">保存</button>
		<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
			<i class="fa fa-default"></i> 正在处理
		</button>
		<button class="btn btn-default" onclick='javascript:history.go(-1)' type="button" style="font-size:16px" >返回</button>
	</div>
</form>
<jsp:include page="${ctx}/static/common/footer.jsp" />
		