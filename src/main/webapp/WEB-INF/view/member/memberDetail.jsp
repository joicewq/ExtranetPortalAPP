<%--
  Created by IntelliJ IDEA.
  User: lovercy
  Date: 6/12/2016
  Time: 上午 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/model/member/memberDetail.js"></script>

<div id="memberDetail">
    <h3 class="form-info-h3">
        <span class="text-blue">基本信息：</span>
        <c:if test="${member.loginType=='P'}">
        <button class="btn btn-default pull-right" id="edit_btn" data-value="${member.companyId}"><i class="fa fa-edit mr5"></i>编辑</button>
        </c:if>
    </h3>
    <ul class="form-list form-info">
        <li>
            <span class="sm-title">公司名称：</span>
            <span class="form-info-span">${member.companyName}</span>
        </li>
        <li>
            <span class="sm-title">法定代表人：</span>
            <span class="form-info-span">${member.legalRepresentative}</span>
        </li>
        <li>
            <span class="sm-title">联系人：</span>
            <span class="form-info-span">${member.contactPerson}</span>
        </li>
        <li>
            <span class="sm-title">法人代表手机号：</span>
            <span class="form-info-span">${member.contactPhone}</span>
        </li>
        <li>
            <span class="sm-title">公司所在区域：</span>
            <span class="form-info-span">${member.province}${member.city}</span>
        </li>
        <li>
            <span class="sm-title">法人邮箱：</span>
            <span class="form-info-span">${member.legalEmail}</span>
        </li>
        <li>
            <span class="sm-title">公司地址：</span>
            <span class="form-info-span">${member.address}</span>
        </li>
        <li>
            <span class="sm-title">银行账号：</span>
            <span class="form-info-span">${member.bankCode}</span>
        </li>
        <li>
            <span class="sm-title">传真：</span>
            <span class="form-info-span">${member.fixCode}</span>
        </li>
        <li>
            <span class="sm-title">开户户名：</span>
            <span class="form-info-span">${member.bankPeople}</span>
        </li>
        <li>
            <span class="sm-title">Q Q ：</span>
            <span class="form-info-span">${member.qq}</span>
        </li>
        <li>
            <span class="sm-title">开户行：</span>
            <span class="form-info-span">${member.bankName}</span>
        </li>
        <li>
            <span class="sm-title">固话：</span>
            <span class="form-info-span">${member.telephone}</span>
        </li>
        <li>
            <span class="sm-title">银行预留证件：</span>
            <span class="form-info-span">${member.credentialType}</span>
        </li>
        <li>
            <span class="sm-title">邮箱：</span>
            <span class="form-info-span">${member.email}</span>
        </li>
        <li>
            <span class="sm-title">预留证件号码：</span>
            <span class="form-info-span">${member.credentialCode}</span>
        </li>
        <li>
            <span class="sm-title">邮政编码：</span>
            <span class="form-info-span">${member.postCode}</span>
        </li>
        <li>
            <span class="sm-title">收票人电话：</span>
            <span class="form-info-span">${member.checkPhone}</span>
        </li>
        <li>
            <span class="sm-title">收票人姓名：</span>
            <span class="form-info-span">${member.checkTaker}</span>
        </li>
        <li>
            <span class="sm-title">收票地址：</span>
            <span class="form-info-span">${member.checkAddress}</span>
        </li>
        <c:if test="${member.loginType=='S'}">
        <li class="li-per">
            <span class="sm-title">公司logo：</span>
            <span class="form-info-span"><img src="${member.logoUrl}" alt="公司logo"></span>
        </li>
        <li class="li-per">
            <span class="sm-title">公司简介：</span>
            <span class="form-info-span">${member.companyIntro}</span>
        </li>
        </c:if>
    </ul>
    <h3 class="info-h3">
        <span class="text-blue">认证信息：</span>
    </h3>
    <ul class="form-list form-info">
        <li class="li-per">
            <span class="sm-title">工商营业执照号码：</span>
            <span class="form-info-span">${member.charterCode}</span>
        </li>
        <li class="li-per">
            <span class="sm-title">工商营业执照附件：</span>
            <span class="form-info-span"><img src="${member.charterUrl}" alt="执照图片缩略图"></span>
        </li>
    </ul>
</div>

