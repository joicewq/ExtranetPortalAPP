<%--
  Created by IntelliJ IDEA.
  User: lovercy
  Date: 1/12/2016
  Time: 上午 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/model/vipcenter/address.js"></script>
    
    <div>
        <button class="btn btn-default" id="add_btn"><i class="fa fa-plus mr5"></i>新增收货地址</button>
    </div>
    <div id="addressList"></div>
      

    <div id="address-edit-wrap" class="layer-form" style="display: none">
        <div class="ds-user-main">
            <form id="address-edit-form" class="address-edit-form">
                <input type="hidden" name="addressId" id="addressId"/>
                <ul class="ds-user-from">
                    <li>
                        <label class="ds-from-left" style="line-height: 1px;">&nbsp;</label>
                        <span>手机号码、电话号码选填一项，其余均为必填项</span>
                    </li>
                    <li class="mt10">
                        <label class="ds-from-left">
                            <span><span class="ds-text-red mr5">*</span>所在地区:</span>
                        </label>
                        <div class="ds-from-group" id="distpicker">
                            <select class="required" id="province" name="province"></select>
                            <select class="required" id="city" name="city"></select>
                            <select class="required" id="district" name="area"></select>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                        </div>

                    </li>
                    <li>
                        <label class="ds-from-left">
                            <span><span class="ds-text-red mr5">*</span>详细地址:</span>
                        </label>
                        <div class="ds-from-group">
                            <textarea type="text" class="textarea-normal required" id="contact-address-edit" name="address"></textarea>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left">
                            <span><span class="ds-text-red mr5">*</span>邮政编码:</span>
                        </label>
                        <div class="ds-from-group">
                            <input type="text" class="ipt-small required numerical" id="contact-tel-postalcode" name="postCode"/>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left">
                            <span><span class="ds-text-red mr5">*</span>收货人姓名:</span>
                        </label>
                        <div class="ds-from-group">
                            <input type="text" class="ipt-small required" maxDataLength="10" id="contact-name-edit" name="name"/>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left">
                            <span>手机号码:</span>
                        </label>
                        <div class="ds-from-group">
                            <div class="phone-wrap">
                                <span class="phone-tips">+86</span>
                                <input type="text" class="ipt-small phone-num" maxDataLength="11" id="contact-tel-edit" name="phone"/>
                            </div>
                        </div>
                    </li>
                    <li>
                        <label class="ds-from-left">
                            <span>电话号码:</span>
                        </label>
                        <div class="ds-from-group">
                            <div class="phone-wrap">
                                <span class="phone-tips">+86</span>
                                <input type="text" class="ipt-min phone-num" id="areaCode" name="areaCode" />
                                -
                                <input type="text" class="ipt-samll phone-num" id="telephone" name="telephone" />
                            </div>
                            <span class="ds-from-tips ds-text-red ml10" id="phoneSpan"></span>
                        </div>
                    </li>
                    <li class="mt10">
                        <label class="ds-from-left" style="line-height: 20px">&nbsp;</label>
                        <div class="ds-btn-group">
                            <label>
                                <input type="checkbox" id="isDefault" /> 设为默认地址
                            </label>
                        </div>
                    </li>
                    <li class="mt10">
                        <label class="ds-from-left" style="line-height: 20px">&nbsp;</label>
                        <div class="ds-btn-group">
                            <a href="javascript:void(0);" id="js-save-btn" class="ds-btn ds-btn-orange">保存</a>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
    </div>

    <textarea id="tableTemplateTxt" style="display:none">
	<![CDATA[
        {#template MAIN}
            {#foreach $T.data as row}
                {#include ROW root=$T.row}
            {#/for}
		{#/template MAIN}
        {#template ROW}
        <div class="address-modal">
            <div class="mt15">
                <span class="address-info-title">收货人：</span>
                <span>
                    {#if $T.name==null}
						{#else }{$T.name}
					{#/if}
                </span>
            </div>
            <div class="mt15">
                <span class="address-info-title">收货地址：</span>
                <span>
                    {#if $T.province==null}
						{#else }{$T.province}
					{#/if}
                    {#if $T.city==null}
						{#else }{$T.city}
					{#/if}
                    {#if $T.area==null}
						{#else }{$T.area}
					{#/if}
                    {#if $T.address==null}
						{#else }{$T.address}
					{#/if}
                </span>
            </div>
            <div class="mt15">
                <span class="address-info-title">手机号码：</span>
                <span>
                    {#if $T.phone==null}
						{#else }{$T.phone}
					{#/if}
                </span>
            </div>
            <div class="mt15">
                <span class="address-info-title">固定电话：</span>
                <span>
                    {#if $T.telephone==null || $T.telephone==""}
                    {#elseif $T.areaCode==null || $T.areaCode==""}
                        {$T.telephone}
                    {#else}
                        {$T.areaCode}-{$T.telephone}
                    {#/if}
                </span>
            </div>
            <div class="mt15">
                <span class="address-info-title">邮政编号：</span>
                <span>
                   {#if $T.postCode==null}
                        {#else }{$T.postCode}
                   {#/if}
                </span>
            </div>
            {#if $T.isDefault==1}
            <span class="address-default"><span>默认</span></span>
            {#/if}
            <div class="set-default">
                {#if $T.isDefault==1}
                {#else}
                <a href="javascript:void(0)" class="set_default" data-value="{$T.addressId}">设为默认</a>
                {#/if}
                <a href="javascript:void(0)" class="edit_btn" data-value="{$T.addressId}">编辑</a>
            </div>
        </div>
        {#/template ROW}
	]]>
	</textarea>

