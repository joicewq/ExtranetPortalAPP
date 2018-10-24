<%--
  Created by IntelliJ IDEA.
  User: lovercy
  Date: 22/11/2016
  Time: 下午 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 		prefix="fmt" %>
<script src="/static/model/member/supplierRegister.js"></script>
<script src="/static/model/member/fileUpload.js"></script>

<style>
.ds-from-group {
	height: 52px;
}
.input-file-supplier-parent{
	position: relative;
}
.input-file-supplier{
    position: absolute;
    top: 0;
    right: 51px;
    width: 50px;
    height: 35px;
    cursor: pointer;
    opacity: 0;
    overflow: hidden;
}
</style>
<c:if test="${not empty member.approvalStatus && (member.approvalStatus ne '11'&& member.approvalStatus ne '12')}">
<c:set var="showType" value="disabled"></c:set>
</c:if>
<body>
<form id="supplierForm"  method="post" enctype="multipart/form-data">
    <input type="hidden" id="loginId" name="loginId" value="${loginId}" />
    <input type="hidden" id="orderPrivce" name="orderPrivce" value="${member.orderPrivce}" />
    <input type="hidden" id="orderType" name="orderType" value="${member.orderType}" />
    <input type="hidden" id="orderNumber" name="orderNumber" value="${member.orderNumber}" />
    <input type="hidden" id="approvalStatus" name="approvalStatus" value="${member.approvalStatus}" />
	<div class="mt20">
		<h2 class="ds-text-blue font18 mb10">
		基本信息
		<c:choose>
		<c:when test="${member.approvalStatus eq '11'}">
		（审核通过）
		</c:when>
		<c:when test="${not empty member.approvalStatus &&(member.approvalStatus ne '11' && member.approvalStatus ne '12')}">
		（审核中）
		</c:when>
		<c:when test="${member.approvalStatus eq '12'}">
		（审核驳回）
		</c:when>
		</c:choose>
		</h2>
			<input type="hidden" id="companyId" name="companyId" value="${member.companyId}" />
			<input type="hidden" id="loginType" name="loginType" value="S" />
		<ul class="ds-user-from member-form clearfix">
			
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司名称:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required checkCompanyNameExist" data-companyid="${member.companyId}" maxDataLength="55" value="${member.companyName}" placeholder="请输入公司名称" type="text" id="companyName" name="companyName" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--公司名称校验:必填  -->
	            </div>
	        </li>
			<li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>法定代表姓名:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" maxDataLength="10" value="${member.legalRepresentative}" placeholder="请输入法定代表姓名" type="text"  name="legalRepresentative" id="legalRepresentative" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 法定代表人校验:必填,2—4位 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>联系人:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" maxDataLength="10" value="${member.contactPerson}" placeholder="请输入联系人姓名" type="text" id="contactPerson" name="contactPerson" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 联系人校验:必填,2-4位,没有空格 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>法人代表联系方式:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required mobilePhone" value="${member.legalPhone}" placeholder="请输入法人代表联系方式" type="text"  name="legalPhone" id="legalPhone" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 法人代表手机号码校验:必填,11位 -->
	            </div>
	        </li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>联系方式:</label>
				<div class="ds-from-group">
					<input class="ds-inp-control required mobilePhone" value="${member.contactPhone}" placeholder="请输入联系方式" type="text" id="contactPhone" name="contactPhone" ${showType }/>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 联系人校验:必填,2-4位,没有空格 -->
				</div>
			</li>
	        <li>
	            <label class="ds-from-left">法人邮箱:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control email" value="${member.legalEmail}" maxDataLength="50" placeholder="请输入法人代表邮箱" type="text"  name="legalEmail" id="legalEmail" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 邮箱校验:必填,2—4位 -->
	            </div>
	        </li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司所在区域:</label>
				<div class="ds-from-group">
					<select id="province" name="province" class="required" provinceId="${member.province}" ${showType }>
						<option value="">省份</option>
					</select>
					<select id="city" name="city" class="required" cityId="${member.city}" ${showType }>
						<option value="">市区</option>
					</select>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 银行账号校验:必填,至少7位 -->
				</div>
			</li>
<%-- 	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>银行账号:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required numerical" maxDataLength="20" value="${member.bankCode}" placeholder="请输入银行账号" type="text"  name="bankCode" id="bankCode" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 银行账号校验:必填,至少7位 -->
	            </div>
	        </li> --%>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司注册地址:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control required" maxDataLength="56" value="${member.address}" placeholder="请输入公司地址" type="text" id="address" name="address" ${showType }/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!--公司地址校验:必填,最少2位  -->
                </div>
            </li>
<%-- 	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>开户户名:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" maxDataLength="10" value="${member.bankPeople}" placeholder="请输入开户户名" type="text"  name="bankPeople" id="bankPeople" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 纳税人识别号校验:必填,位数待确定 -->
	            </div>
	        </li> --%>
            <li>
                <label class="ds-from-left">传真:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control isFax" value="${member.fixCode}" maxDataLength="20" placeholder="请输入传真号码" type="text" id="fixCode" name="fixCode" ${showType }/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 传真电话校验:必填,至少7位,没有空格 -->
                </div>
            </li>
<%--             <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>开户行:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control required" value="${member.bankName}" maxDataLength="20" placeholder="请输入开户银行名称" type="text"  name="bankName" id="bankName" ${showType }/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 开户银行名称:必填,至少2位 -->
                </div>
            </li> --%>
	        <li>
	            <label class="ds-from-left">QQ:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control isQQ" value="${member.qq}" maxDataLength="15" placeholder="请输入QQ" type="text"  name="qq" id="qq" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--QQ校验:必填,最少为5位  -->
	            </div>
	        </li>
<%--             <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>银行预留证件:</label>
                <div class="ds-from-group">
                    <select class="ds-select-control required" id="credentialType" name="credentialType" ${showType }>
                        <option <c:if test="${member.credentialType eq '1'}">selected='selected'</c:if> value="1">身份证</option>
                        <option <c:if test="${member.credentialType eq '2'}">selected='selected'</c:if> value="2">护照</option>
                        <option <c:if test="${member.credentialType eq '3'}">selected='selected'</c:if> value="3">港澳居民身份证</option>
                    </select>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 法定代表人校验:必填,2—4位 -->
                </div>
            </li> --%>
	        <li>
	            <label class="ds-from-left">固话:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control isTelephone" value="${member.telephone}" maxDataLength="20" placeholder="请输入固定电话" type="text" id="telephone" name="telephone" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 固定电话校验:必填,至少7位,没有空格 -->
	            </div>
	        </li>
<%--             <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>预留证件号码:</label>
                <div class="ds-from-group">
                        <c:if test="${member.credentialType eq '1'}"><c:set var="credentialType" value="isIDCard"></c:set></c:if>
                        <c:if test="${member.credentialType eq '2'}"><c:set var="credentialType" value="isPassPort"></c:set></c:if>
                        <c:if test="${member.credentialType eq '3'}"><c:set var="credentialType" value="isHKMacao"></c:set></c:if>
                    <input class="ds-inp-control required ${credentialType}" maxDataLength="20" value="${member.credentialCode}" placeholder="请输入法预留证件号码" type="text" id="credentialCode" name="credentialCode" ${showType }/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 证件号码校验:必填,2—4位 -->
                </div>
            </li> --%>
	        <li>
	            <label class="ds-from-left">邮箱:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control email" value="${member.email}" maxDataLength="50" placeholder="请输入邮箱" type="text" id="email" name="email" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--邮箱校验:必填,是否符合邮箱格式  -->
	            </div>
	        </li>
<%--             <li>
                <label class="ds-from-left">收票人姓名:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control" value="${member.checkTaker}" maxDataLength="10" placeholder="请输入收票人姓名" type="text" id="checkTaker" name="checkTaker" ${showType }/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!--收票人姓名校验:必填,2—4位，没有空格  -->
                </div>
            </li> --%>
	        <li>
	            <label class="ds-from-left">邮政编码:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control isPostCode" value="${member.postCode}" placeholder="请输入邮政编码" type="text" name="postCode" id="postCode" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 邮政编码校验:必填,至少7位 -->
	            </div>
	        </li>
<%-- 	        <li>
	            <label class="ds-from-left">收票人电话:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="${member.checkPhone}" maxDataLength="15" minDataLength="7" placeholder="请输入电话号码" type="text"  name="checkPhone" id="checkPhone" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 固定电话校验:必填,至少7位,没有空格 -->
	            </div>
	        </li> --%>
            <li>
                <label class="ds-from-left">公司Logo:</label>
                
                <div class="ds-from-group">
                    <div class="accessory-wrap input-file-supplier-parent">
                        <div class="file-wrap">
                            <input type="text" class="file-name" placeholder="公司Logo" id="logo_name" readonly="readonly"  value="${member.logoName}"  name="logoName" ${showType }/>
                            <input type="hidden" class="file-id" id="logoUrl" name="logoUrl"  value="${member.logoUrl}"/>
                            <a href="javascript:void(0);" class="ds-btn file-brower">浏览</a>
                            <a href="javascript:void(0);" class="ds-btn file-upload" <c:if test="${empty showType }">id="logo_upload"</c:if> >上传</a>
                            <span class="ds-from-tips ds-text-red ml10"></span>
					        	<input type="hidden" value="" />
                            	<input type="file" class="input-file-supplier" id="logo_brower" ${showType }/>
                            <!-- 组织机构代号校验:必填,待确认 -->
                        </div>
                    </div>
                </div>
            </li>
<%-- 	        <li>
	            <label class="ds-from-left">收票地址:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="${member.checkAddress}" maxDataLength="255" placeholder="请输入收票人地址" type="text"  name="checkAddress" id="checkAddress" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 收票人地址校验:至少2位,没有空格 -->
	            </div>
	        </li> --%>
	        <li class="width-fixed block">
	            <label class="ds-from-left">公司简介:</label>
	            <div class="ds-from-group" style="height: auto;">
	                <textarea rows="" cols="" class="" placeholder="公司简介" maxDataLength="500" name="companyIntro" id="companyIntro" ${showType }>${member.companyIntro}</textarea>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 组织机构代号校验:必填,待确认 -->
	            </div>
	        </li>
		</ul>
	</div>
	<div class="mt10">
		<h2 class="ds-text-blue font18 mb10">认证信息</h2>
	    <ul class="ds-user-from member-form clearfix">
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>工商营业执照编号:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required checkCharterCodeExist" value="${member.charterCode}" data-companyid="${member.companyId}" maxDataLength="40" placeholder="请输入工商营业执照编号" type="text"  name="charterCode" id="charterCode" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 营业执照号校验:必填,待确认 -->
	            </div>
	        </li>

	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>附件:</label>
	            <div class="ds-from-group">
	                <div class="accessory-wrap input-file-supplier-parent">
	                	<div class="file-wrap">
		                	<input type="text" class="file-name required" placeholder="工商营业执照" readonly="readonly" id="charter_name"  value="${member.charterName}" name="charterName" ${showType }>
							<input type="hidden" class="file-id" name="charterUrl" id="charterUrl"  value="${member.charterUrl}"/>
		                	<a href="javascript:void(0);" class="ds-btn file-brower" >浏览</a>
	                		<a href="javascript:void(0);" class="ds-btn file-upload" <c:if test="${empty showType }">id="charter_upload"</c:if> >上传</a>
							<span class="ds-from-tips ds-text-red ml10"></span>
					        <input type="hidden" value="">
                            <input type="file" class="input-file-supplier" id="charter_brower" ${showType }>
						</div>
	                </div>
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left">是否长期</label>
	            <div class="ds-from-group">
				<input type="radio" name="isLongTerm" value="0" <c:if test="${member.isLongTerm ne 1}">checked="checked"</c:if> ${showType} />是
				<input type="radio" name="isLongTerm" value="1" <c:if test="${member.isLongTerm eq 1}">checked="checked"</c:if> ${showType} />否
	            </div>
	        </li>
	        <li id="isLongTermLi"  <c:if test="${member.isLongTerm ne 1}">style="visibility: hidden;"</c:if>>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>有效期:</label>
	            <div class="ds-from-group">
 				<c:if test="${not empty member.charterStartDate}">
	            <jsp:useBean id="charterStartDate" class="java.util.Date" />
				<jsp:setProperty name="charterStartDate" property="time" value="${member.charterStartDate}"/>
				</c:if>
				<c:if test="${not empty member.charterEndDate}">
	            <jsp:useBean id="charterEndDate" class="java.util.Date" />
				<jsp:setProperty name="charterEndDate" property="time" value="${member.charterEndDate}"/> 
				</c:if>
	                <input class="ds-inp-control ds-inp-control-min" value="<fmt:formatDate value='${charterStartDate}' pattern='yyyy-MM-dd' />" placeholder="请选择起始时间" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'charterEndDate\')}',dateFmt:'yyyy-MM-dd'})" name="charterStartDate" id="charterStartDate" ${showType }/>
	                	至
	                <input class="ds-inp-control ds-inp-control-min" value="<fmt:formatDate value='${charterEndDate}' pattern='yyyy-MM-dd' />" placeholder="请选择终止时间" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'charterStartDate\')}',dateFmt:'yyyy-MM-dd'})" name="charterEndDate" id="charterEndDate" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10" id="sp"></span>
	                <!-- 营业执照号校验:必填,待确认 -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left" style="line-height: 22px;">食品流通许可证编号/食品经营许可证编号:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control"  placeholder="请输入食品流通许可证编号" maxDataLength="40" type="text"  name="licenceBusiness" id="licenceBusiness"  value="${member.licenceBusiness}" ${showType }/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                </div>
            </li>

            <li>
                <label class="ds-from-left">附件:</label>
                <div class="ds-from-group">
                    <div class="accessory-wrap input-file-supplier-parent">
                        <div class="file-wrap">
                            <input type="text" class="file-name" placeholder="食品流通许可证" readonly="readonly" id="business_name" name="licenceBusinessName" value="${member.licenceBusinessName}" ${showType }/>
                            <input type="hidden" class="file-id" name="licenceBusinessUrl" id="licenceBusinessUrl" value="${member.licenceBusinessUrl}"/>
                            
                            <a href="javascript:void(0);" class="ds-btn file-brower">浏览</a>
                            <a href="javascript:void(0);" class="ds-btn file-upload" <c:if test="${empty showType }">id="business_upload"</c:if>>上传</a>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                            <input type="hidden" value="">
                            <input type="file" class="input-file-supplier" id="business_brower" ${showType }>
                            <!-- 组织机构代号校验:必填,待确认 -->
                    </div>

                    </div>
                </div>
            </li>
            <li class="width-fixed">
	            <label class="ds-from-left">有效期:</label>
				<c:if test="${not empty member.businessStartDate}">
	            <jsp:useBean id="businessStartDate" class="java.util.Date" />
				<jsp:setProperty name="businessStartDate" property="time" value="${member.businessStartDate}"/>
				</c:if>
				<c:if test="${not empty member.businessEndDate}">
	            <jsp:useBean id="businessEndDate" class="java.util.Date" />
				<jsp:setProperty name="businessEndDate" property="time" value="${member.businessEndDate}"/>
				</c:if>
 	            <div class="ds-from-group">
	                <input class="ds-inp-control ds-inp-control-min" value="<fmt:formatDate value="${businessStartDate }" pattern="yyyy-MM-dd" />" placeholder="请选择起始时间" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'businessEndDate\')}',dateFmt:'yyyy-MM-dd'})" name="businessStartDate" id="businessStartDate" ${showType }/>
	                	至
	                <input class="ds-inp-control ds-inp-control-min" value="<fmt:formatDate value="${businessEndDate}" pattern="yyyy-MM-dd" />" placeholder="请选择终止时间" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'businessStartDate\')}',dateFmt:'yyyy-MM-dd'})" name="businessEndDate" id="businessEndDate" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 营业执照号校验:必填,待确认 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>食盐批发许可证编号:</label>
	            <div class="ds-from-group">
	            	<span>食盐批字第</span>
	                <input class="ds-inp-control required" style="width:130px;" maxDataLength="40" placeholder="请输入食盐批发许可证编号" type="text"  name="licenceWholesale" id="licenceWholesale"  value="${member.licenceWholesale}" ${showType }/>
	                <span>号</span>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 组织机构代号校验:必填,待确认 -->
	            </div>
	        </li>

	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>附件:</label>
	            <div class="ds-from-group">
	                <div class="accessory-wrap input-file-supplier-parent">
	                	<div class="file-wrap">
		                	<input type="text" class="file-name required" placeholder="食盐批发许可证" readonly="readonly" id="wholesale_name" name="licenceWholesaleName"  value="${member.licenceWholesaleName}" ${showType } />
							<input type="hidden" class="file-id " name="licenceWholesaleUrl" id="licenceWholesaleUrl" value="${member.licenceWholesaleUrl}"/>
		                	<a href="javascript:void(0);" class="ds-btn file-brower">浏览</a>
							<a href="javascript:void(0);" class="ds-btn file-upload"  <c:if test="${empty showType }">id="wholesale_upload"</c:if>>上传</a>
							<span class="ds-from-tips ds-text-red ml10"></span>
					        <input type="hidden" value="">
                            <input type="file" class="input-file-supplier" id="wholesale_brower" ${showType }>
							<!-- 组织机构代号校验:必填,待确认 -->
						</div>
					</div>

	            </div>
	        </li>
	        <li class="width-fixed">
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>有效期:</label>
	            <c:if test="${not empty member.wholesaleStartDate}">
	            <jsp:useBean id="wholesaleStartDate" class="java.util.Date" />
				<jsp:setProperty name="wholesaleStartDate" property="time" value="${member.wholesaleStartDate}"/>
				</c:if>
				<c:if test="${not empty member.wholesaleEndDate}">
	            <jsp:useBean id="wholesaleEndDate" class="java.util.Date" />
				<jsp:setProperty name="wholesaleEndDate" property="time" value="${member.wholesaleEndDate}"/>
	            </c:if>
 	            <div class="ds-from-group">
	                <input class="ds-inp-control ds-inp-control-min required" value="<fmt:formatDate value="${wholesaleStartDate }" pattern="yyyy-MM-dd" />" placeholder="请选择起始时间" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'wholesaleEndDate\')}',dateFmt:'yyyy-MM-dd'})" name="wholesaleStartDate" id="wholesaleStartDate" ${showType }/>
	                	至
	                <input class="ds-inp-control ds-inp-control-min required" value="<fmt:formatDate value="${wholesaleEndDate}" pattern="yyyy-MM-dd" />" placeholder="请选择终止时间" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'wholesaleStartDate\')}',dateFmt:'yyyy-MM-dd'})" name="wholesaleEndDate" id="wholesaleEndDate" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 营业执照号校验:必填,待确认 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left">食盐定点许可证编号:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" maxDataLength="40" placeholder="请输入食盐定点许可证编号" type="text"  name="fixedPoint" id="fixedPoint"  value="${member.fixedPoint}" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 组织机构代号校验:必填,待确认 -->
	            </div>
	        </li>

	        <li>
	            <label class="ds-from-left">附件:</label>
	            <div class="ds-from-group">
	                <div class="accessory-wrap input-file-supplier-parent">
	                	<div class="file-wrap">
		                	<input type="text" class="file-name" placeholder="食盐定点许可证" readonly="readonly" id="fixedPoint_name" name="fixedPointName"  value="${member.fixedPointName}" ${showType } />
							<input type="hidden" class="file-id " name="fixedPointUrl" id="fixedPointUrl" value="${member.fixedPointUrl}"/>
		                	<a href="javascript:void(0);" class="ds-btn file-brower">浏览</a>
							<a href="javascript:void(0);" class="ds-btn file-upload"  <c:if test="${empty showType }">id="fixedPoint_upload"</c:if>>上传</a>
							<span class="ds-from-tips ds-text-red ml10"></span>
					        <input type="hidden" value="">
                            <input type="file" class="input-file-supplier" id="fixedPoint_brower" ${showType }>
							<!-- 组织机构代号校验:必填,待确认 -->
						</div>
					</div>

	            </div>
	        </li>
	       <li class="width-fixed">
	            <label class="ds-from-left">有效期:</label>
 	            <c:if test="${not empty member.fixedPointStartDate}">
	            <jsp:useBean id="fixedPointStartDate" class="java.util.Date" />
				<jsp:setProperty name="fixedPointStartDate" property="time" value="${member.fixedPointStartDate}"/>
				</c:if>
				<c:if test="${not empty member.fixedPointEndDate}">
	            <jsp:useBean id="fixedPointEndDate" class="java.util.Date" />
				<jsp:setProperty name="fixedPointEndDate" property="time" value="${member.fixedPointEndDate}"/>
	            </c:if>
 	            <div class="ds-from-group">
	                <input class="ds-inp-control ds-inp-control-min" value="<fmt:formatDate value="${fixedPointStartDate }" pattern="yyyy-MM-dd" />" placeholder="请选择起始时间" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'fixedPointEndDate\')}',dateFmt:'yyyy-MM-dd'})" name="fixedPointStartDate" id="fixedPointStartDate" ${showType }/>
	                	至
	                <input class="ds-inp-control ds-inp-control-min" value="<fmt:formatDate value="${fixedPointEndDate}" pattern="yyyy-MM-dd" />" placeholder="请选择终止时间" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'fixedPointStartDate\')}',dateFmt:'yyyy-MM-dd'})" name="fixedPointEndDate" id="fixedPointEndDate" ${showType }/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 营业执照号校验:必填,待确认 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left">&nbsp;</label>
	            <div class="ds-from-group">
	            	<c:if test="${empty member.approvalStatus}">
	            		<button type="button" class="ds-btn mr20 ds-btn-orange js-save" id="save"> 保存 </button>
	            	</c:if>
	            	<c:if test="${member.approvalStatus ne '10'}">
	                	<button type="button" class="ds-btn mr20 ds-btn-primary js-submit" id="submit"> 提交 </button>
	                </c:if>
	            </div>
	        </li>
	    </ul>
	</div>
	<div id="testDiv" style="display:none;"></div>
</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	if($("#approvalStatus").val()=="11"){
		
	}
});
</script>