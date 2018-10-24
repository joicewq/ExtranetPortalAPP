<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
	<meta charset="utf-8">
    <title>电子商务-后台管理-注册用户管理</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript" src="${ctx}/static/model/natural/edit.js"></script>
	<link rel="stylesheet" href="${ctx}/static/css/select2.css"/>
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
			<span class="sm-title"><span class="text-red mr5">*</span>省份/中盐：</span>
			<select id="province" name="province" class="required" provinceId="${NaturalInfo.supplier}" ${showType }>
						<option value="">省份</option>
			</select>
			<%-- <select id="province" name="province"  class="required" style="width: 180px;" provinceId="${NaturalInfo.supplier}"><option value="">省份</option></select> --%>
		  </div>
		</li>
			
		<li style="width:370px;">
			<div class="label">
				<span class="sm-title"><span class="text-red mr5">*</span>食盐种类：</span>
				<select name="naturalParent" id="naturalParent"  class="required" style="width: 180px;" >
					<option value="">请选择</option>
					<c:forEach var="natural" items="${naturalList}">
						<option value="${natural.id}" name="${natural.nameNo }" <c:if test="${NaturalInfo.typeName eq natural.name}">selected</c:if> >${natural.name}</option>
					</c:forEach>
				</select>	
				<!-- <select name="naturalNodechild" id="naturalNodechild" class="" style="width: 150px">
					<option value="">请选择</option>
				</select>
				<select name="naturalNodelist" id="naturalNodelist" class="" style="width: 150px">
					<option value="">请选择</option>
				</select> -->
			</div>
			 <input  type="hidden" id="saltName" name="saltName" value="${NaturalInfo.saltName}"/>
			<input  type="hidden" id="remark" name="remark" value="${NaturalInfo.remark}"/>
			<input  type="hidden" id="remark2" name="remark2" value="${NaturalInfo.remark2}"/>
			<p class="text-red form-list-tip hide">
				提示信息
			</p>
		</li>
		<li style="width:450px;">
		  <div class="label">
				<span class="sm-title" style="width: 100px;"><span class="text-red mr5">*</span>食盐名称：</span>
				<select name="naturalchild" id="naturalchild" class="select2 required"  saltNameId="${NaturalInfo.saltName}" style="width: 180px;" >
					<option value="">请选择</option>
				</select>
		  </div>
		</li>
			
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="text-red mr5"></span>食盐外观图案描述：</span>
				<textarea name="describeInfo"  title="食盐外观图案描述" id="describeInfo" rows="2">${NaturalInfo.describeInfo}</textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">	
				<label class="sm-title"><span class="text-red mr5">*</span>食盐外观图案(必须上传正反面图片)：</label>
				<div id="uploadDiv" class="clearfix" style="width:700px; margin-left:140px"></div>
				<input id="describeId" name="describeId"  class="required" type="hidden" value="${NaturalInfo.describeId}" />
			</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="text-red mr5">*</span>包装规格描述：</span>
				<textarea name="cartonPackInfo" title="包装规格描述" class="required" id="cartonPackInfo" rows="2">${NaturalInfo.cartonPackInfo}</textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">	
				<label class="sm-title">包装规格：</label>
				<div id="uploadDiv2" class="clearfix" style="width:700px; margin-left:140px"></div>
				<input id="cartonPackId" name="cartonPackId" type="hidden" value="${NaturalInfo.cartonPackId}" />
				</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="text-red mr5">*</span>防伪识别方法：</span>
				<textarea name="recognitionInfo" title="防伪识别方法" class="required" id="recognitionInfo" rows="2">${NaturalInfo.cartonPackInfo}</textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">	
				<label class="sm-title"><span class="text-red mr5">*</span>质检报告(第三方检测报告)：</label>
				<div id="uploadDiv3" class="clearfix" style="width:700px; margin-left:140px"></div>
				<input id="qualityReportId" name="qualityReportId" type="hidden" class="required" value="${NaturalInfo.qualityReportId}" style="margin-top:-65px;margin-left:190px;border-style:none;" value="" />
				</div>
		</li>
		<li class="li-per">
			<div class="label">
				<span class="sm-title"><span class="text-red mr5">*</span>产品商标：</span>
				<textarea name="tradeMark" class="required illegalValidatePlus" title="产品商标" id="tradeMark" rows="2">${NaturalInfo.tradeMark}</textarea>
			</div>
		</li>
		<li class="li-per">
			<div class="label">	
				<label class="sm-title">产品商标文件：</label>
				<div id="uploadDiv5" class="clearfix" style="width:700px; margin-left:140px"></div>
				<input id="tradeFile" name="tradeFile" type="hidden" value="${NaturalInfo.tradeFile}" />
				</div>
		</li>
		<li>
			<div class="label">
				<span class="sm-title"><span class="text-red mr5">*</span>标准名称：</span>
				<input type="text" name="productId" class="required illegalValidatePlus"  title="标准名称" id="productId" value="${NaturalInfo.productId}" />
			</div>
		</li>
		<li>
			<div class="label">
				<span class="sm-title"><span class="text-red mr5">*</span>标准号：</span>
				<input type="text" name="standard" class="required illegalValidatePlus" title="标准号" id="standard" value="${NaturalInfo.standard}" />
			</div>
		</li>
		<li class="li-per">
			<div class="label">	
				<label class="sm-title"><span class="text-red mr5">*</span>执行标准文件：</label>
				<div id="uploadDiv4" class="clearfix" style="width:700px; margin-left:140px"></div>
				<input id="standardFile" name="standardFile" type="hidden" value="${NaturalInfo.standardFile}" />
				<input id="standardFileId" name="standardFileId" type="hidden" class="required" value="" />
				</div>
		</li>
	</ul>
	<div class="text-center mt10">
		<button class="btn btn-primary" type="button" style="font-size:16px" id="save">保存</button>
		<button class="btn btn-default" id="process_btn" disabled="disabled" style="display:none;" onclick="return false;">
			<i class="fa fa-default"></i> 正在处理
		</button>
		<button class="btn btn-default" onclick='jumbto("/natural/naturalindex")' type="button" style="font-size:16px" >返回</button>
	</div>
</form>
		