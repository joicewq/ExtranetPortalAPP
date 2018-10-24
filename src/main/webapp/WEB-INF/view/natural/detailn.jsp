<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/static/common/jstl.jsp"%>
<%@ include file="/static/common/import.jsp"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
	<meta charset="utf-8">
    <title>产品备案</title>
    <link rel="Shortcut Icon" href="/static/images/jgqt.ico">
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script src="${ctx}/static/model/natural/detailn.js"></script>
    <style type="text/css" >
        
        .pages{
        	margin-bottom:0px;
        }
        .iframe-cot {
            border-radius: 0 4px 4px 4px;
        }
        #loaded1 > .clearfix, 
        #loaded2 > .clearfix,
        #loaded3 > .clearfix,
        #loaded4 > .clearfix,
        #loaded5 > .clearfix {
        	margin-left: 24px;
        	margin-top: -20px
        }
        #loaded1 > .clearfix .table-form,
        #loaded2 > .clearfix .table-form,
        #loaded3 > .clearfix .table-form,
        #loaded4 > .clearfix .table-form,
        #loaded5 > .clearfix .table-form {
        	width: 649px;
        }
        #loaded1 > br,
        #loaded2 > br,
        #loaded3 > br,
        #loaded4 > br,
        #loaded5 > br {
        	display: none;
        }
    </style>
</head>
<ul class="tabs">
	<li><a href="#tab1">产品信息</a></li>
	<li><a href="#tab2">审核记录</a></li>
</ul>
<div class="tab-contentmian">
	<div class="tab-content" id="tab1">
		<form id="supply-form">
			<ul class="form-list">
				<li class="">
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>省份：</span>
						<input  type="text" value="${NaturalInfo.supplier }" id="supplier" name="supplier" readonly="readonly"/>
				</li>
					<li class="">
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>食盐种类：</span>
						<input  type="text" value="${NaturalInfo.typeName }" id="typeName" name="typeName" readonly="readonly"/>
				</li>
				<li class="li-per">
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>食盐名称：</span>
						<input  type="text" value="${NaturalInfo.saltName }" id="saltName" name="saltName" readonly="readonly"/>
					<p class="text-red form-list-tip hide">
						提示信息
					</p>
				</li>
				<li class="li-per">
					<div class="label">
						<span class="sm-title">食盐外观图案描述：</span>
						<textarea name="describeInfo" id="describeInfo" rows="2" readonly="readonly">${NaturalInfo.describeInfo}</textarea>
					</div>
				</li>
				<li class="li-per">
					<div class="label">	
						<label class="sm-title"><span class="text-red mr5">*</span>食盐外观图案：</label>
						<div id="uploadDiv1" class="clearfix" style="width:700px; margin-left:140px"></div>
						<input id="describeId" name="describeId" type="hidden" value="${NaturalInfo.describeId}" />
					</div>
				</li>
				<li class="li-per">
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>包装规格描述：</span>
						<textarea name="cartonPackInfo" id="cartonPackInfo" rows="2" readonly="readonly">${NaturalInfo.cartonPackInfo}</textarea>
					</div>
				</li>
				<li class="li-per">
					<div class="label">	
						<label class="sm-title">包装规格：</label>
						<div id="uploadDiv2" class="clearfix" style="width:700px; margin-left:140px" ></div>
						<input id="cartonPackId" name="cartonPackId" type="hidden" value="${NaturalInfo.cartonPackId}" />
						</div>
				</li>
				<li class="li-per">
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>防伪识别方法：</span> 
						<textarea name="recognitionInfo" class="required" id="recognitionInfo" rows="2" readonly="readonly">${NaturalInfo.recognitionInfo }</textarea>
					</div>
				</li>
				<li class="li-per">
					<div class="label">	
						<label class="sm-title"><span class="text-red mr5">*</span>质检报告(第三方检测报告)：</label>
						<div id="uploadDiv3" class="clearfix" style="width:700px; margin-left:140px"></div>
						<input id="qualityReportId" name="qualityReportId" type="hidden" value="${NaturalInfo.qualityReportId }" />
						</div>
				</li>
				<li class="li-per">
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>产品商标：</span>
						<textarea name="tradeMark" class="required" id="tradeMark" rows="2" readonly="readonly">${NaturalInfo.tradeMark}</textarea>
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
						<input type="text" name="productId" class="required" id="productId" value="${NaturalInfo.productId}" readonly="readonly"/>
					</div>
				</li>
				<li>
					<div class="label">
						<span class="sm-title"><span class="text-red mr5">*</span>标准号：</span>
						<input type="text" name="standard" class="required" id="standard" value="${NaturalInfo.standard}" readonly="readonly"/>
					</div>
				</li>
				<li class="li-per">
					<div class="label">	
						<label class="sm-title"><span class="text-red mr5">*</span>执行标准文件：</label>
						<div id="uploadDiv4" class="clearfix" style="width:700px; margin-left:140px"></div>
						<input id="standardFile" name="standardFile" type="hidden" value="${NaturalInfo.standardFile}" />
						</div>
				</li>
			</ul>
		</form>
	</div>
	<div class="tab-content p10" id="tab2">
		<div id="tableTemplate">	
		<form id="rf" action="" class="form-inline" onsubmit="return false;">	
		<input value="${id}" type="hidden" name="id" id="id"/>
	    </form>	
			<div id="view"  class="">
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
                        <th>审核人</th>
                        <th>审核时间</th>
                        <th>审核环节</th>
                        <th>审核意见</th>
                        <th>审核结果</th>
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
							<td colspan="5">无符合条件数据</td>
						{#/if}
					</tbody>
					</tbody>
				</table>
			{#/template MAIN}
			{#template ROW}
				<tr>                                                            
	                <td>{$T.approvalPerson}</td>
					<td>{auditDate($T.auditDate)}</td>
					<td>{getRecord($T.approvalStatus,$T.result)}</td>
					<td>{$T.approvalSuggestion}</td>
					<td>{str($T.approvalStatus,$T.result)}</td>
				</tr>
			{#/template ROW}
	]]>
	</textarea>
	</div>
</div>
<div class="text-center mt15">
	<button class="btn btn-default" onclick='jumbto("/natural/naturalindex")'>返回</button>
</div>
<script type="text/javascript">
function str(approvalStatus,result){
	var str="";
	if(result=="0"){
		if(approvalStatus=="1"){
			str="科长同意待处长审核";
		}else if(approvalStatus=="2"){
			str="处长同意待局长审核";
		}else if(approvalStatus=="3"){
			str="局长同意待科长告知";
		}else{
			str="已告知";
		}
	}else if(result=="1"){
		if(approvalStatus=="12"){
			str="科长驳回";
		}else if(approvalStatus=="10"){
			str="处长驳回待科长处理";
		}else{
			str="局长驳回待处长处理";
		}
	}else{
		if(approvalStatus=="10"){
			str="科长撤回";
		}else if(approvalStatus=="1"){
			str="处长撤回";
		}else{
			str="局长撤回";
		}
	}
	return str;
}
function getRecord(approvalStatus,result){
	var procedure="";
	if(result=="0"){
		if(approvalStatus=="1"){
			procedure="待科长审核";
		}else if(approvalStatus=="2"){
			procedure="待处长审核";
		}else if(approvalStatus=="3"){
			procedure="待局长审核";
		}else{
			procedure="待科长告知";
		}
	}else if(result=="1"){
		if(approvalStatus=="12"){
			procedure="待科长审核";
		}else if(approvalStatus=="10"){
			procedure="待处长审核";
		}else{
			procedure="待局长审核";
		}
	}else{
		if(approvalStatus=="10"){
			procedure="待处长审核";
		}else if(approvalStatus=="1"){
			procedure="待局长审核";
		}else{
			procedure="待科长告知";
		}
	}
	return procedure;
}
function auditDate(auditDate){
    var date= new Date(auditDate);
	var dateStr=date.format("yyyy-MM-dd hh:mm:ss");
	return dateStr;
}

//扩展Date的format方法
Date.prototype.format = function (format) {
var o = {
"M+": this.getMonth() + 1,
"d+": this.getDate(),
"h+": this.getHours(),
"m+": this.getMinutes(),
"s+": this.getSeconds(),
"q+": Math.floor((this.getMonth() + 3) / 3),
"S": this.getMilliseconds()
}
if (/(y+)/.test(format)) {
format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
}
for (var k in o) {
if (new RegExp("(" + k + ")").test(format)) {
format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
      }
}
return format;
} 
</script>

		