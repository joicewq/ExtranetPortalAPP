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
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/js/layer/skin/default/layer.css"/>
<script type="text/javascript" src="${ctx}/static/model/member/providerRegister.js"></script>
<script type="text/javascript" src="${ctx}/static/model/member/fileUpload.js"></script>
<form id="providerForm">
	<div class="mt20">
		<h2 class="ds-text-blue">基本信息</h2>
		<ul class="ds-user-from member-form clearfix">
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司名称:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" maxDataLength="56" value="" placeholder="请输入公司名称" type="text" id="companyName" name="companyName" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--公司名称校验:必填  -->
	            </div>
	        </li>
			<li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>法定代表姓名:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" maxDataLength="10" value="" placeholder="请输入法定代表姓名" type="text"  name="legalRepresentative" id="legalRepresentative" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 法定代表人校验:必填,2—4位 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>联系人:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" maxDataLength="10" value="" placeholder="请输入联系人姓名" type="text" id="contactPerson" name="contactPerson" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 联系人校验:必填,2-4位,没有空格 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>法人代表联系方式:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required mobilePhone" value="" placeholder="请输入法人代表联系方式" type="text"  name="legalPhone" id="legalPhone" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 法人代表手机号码校验:必填,11位 -->
	            </div>
	        </li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>联系方式:</label>
				<div class="ds-from-group">
					<input class="ds-inp-control required mobilPhone" value="" placeholder="请输入联系方式" type="text" id="contactPhone" name="contactPhone" />
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 联系人校验:必填,2-4位,没有空格 -->
				</div>
			</li>
	        <li>
	            <label class="ds-from-left">法人邮箱:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入法人代表邮箱" type="text"  name="legalEmail" id="legalEmail" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 邮箱校验:必填,2—4位 -->
	            </div>
	        </li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司所在区域:</label>
				<div class="ds-from-group">
					<select id="province" name="province" class="required">
						<option>省份</option>
					</select>
					<select id="city" name="city" class="required">
						<option>市区</option>
					</select>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 银行账号校验:必填,至少7位 -->
				</div>
			</li>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>银行账号:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required numerical" value="" placeholder="请输入银行帐号" type="text"  name="bankCode" id="bankCode" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 银行账号校验:必填,至少7位 -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司注册地址:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control required" maxDataLength="56" value="" placeholder="请输入公司地址" type="text" id="address" name="address" />
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!--公司地址校验:必填,最少2位  -->
                </div>
            </li>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>开户户名:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control required" maxDataLength="10" value="" placeholder="请输入开户户名" type="text"  name="bankPeople" id="bankPeople" />
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 纳税人识别号校验:必填,位数待确定 -->
                </div>
            </li>
	        <li>
	            <label class="ds-from-left">传真:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入传真号码" type="text" id="fixCode" name="fixCode"/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 传真电话校验:必填,至少7位,没有空格 -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>开户行:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control required" value="" placeholder="请输入开户银行名称" type="text"  name="bankName" id="bankName" />
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 开户银行名称:必填,至少2位 -->
                </div>
            </li>
	        <li>
	            <label class="ds-from-left">QQ:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入QQ" type="text"  name="qq" id="qq" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--QQ校验:必填,最少为5位  -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>银行预留证件:</label>
                <div class="ds-from-group">
                    <select class="ds-select-control required" id="credentialType" name="credentialType">
                        <option value="1">身份证</option>
                        <option value="2">护照</option>
                        <option value="3">港澳居民身份证</option>
                    </select>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 法定代表人校验:必填,2—4位 -->
                </div>
            </li>
	        <li>
	            <label class="ds-from-left">固话:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入固定电话" type="text" id="telephone" name="telephone" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 固定电话校验:必填,至少7位,没有空格 -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>预留证件号码:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control required isIDCard" value="" placeholder="请输入法预留证件号码" type="text" id="credentialCode" name="credentialCode"/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 证件号码校验:必填,2—4位 -->
                </div>
            </li>
	        <li>
	            <label class="ds-from-left">邮箱:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入邮箱" type="text" id="email" name="email"/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--邮箱校验:必填,是否符合邮箱格式  -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left">收票人电话:</label>
                <div class="ds-from-group">
                    <input class="ds-inp-control" value="" placeholder="请输入电话号码" type="text"  name="checkPhone" id="checkPhone"/>
                    <span class="ds-from-tips ds-text-red ml10"></span>
                    <!-- 固定电话校验:必填,至少7位,没有空格 -->
                </div>
            </li>
	        <li>
	            <label class="ds-from-left">邮政编码:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入邮政编码" type="text" name="postCode" id="postCode"/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 邮政编码校验:必填,至少7位 -->
	            </div>
	        </li>
	        <li>
	            <label class="ds-from-left">收票人姓名:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入收票人姓名" type="text" id="checkTaker" name="checkTaker"/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!--收票人姓名校验:必填,2—4位，没有空格  -->
	            </div>
	        </li>
            <li>
                <label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司Logo:</label>
                <div class="ds-from-group">
                    <div class="accessory-wrap">
                        <div class="file-wrap">
                            <input type="text" class="file-name" placeholder="公司Logo" id="logo_name" readonly="readonly" />
                            <input type="hidden" class="file-id required" id="logoUrl" name="logoUrl"/>
                            <a href="javascript:void(0);" class="ds-btn file-brower" id="logo_brower">浏览</a>
                            <a href="javascript:void(0);" class="ds-btn file-upload" id="logo_upload">上传</a>
                            <span class="ds-from-tips ds-text-red ml10"></span>
                            <!-- 组织机构代号校验:必填,待确认 -->
                        </div>
                    </div>
                </div>
            </li>
	        <li>
	            <label class="ds-from-left">收票地址:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control" value="" placeholder="请输入收票人地址" type="text"  name="checkAddress" id="checkAddress" />
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 收票人地址校验:必填,至少2位,没有空格 -->
	            </div>
	        </li>
			<li class="width-fixed block">
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>公司简介:</label>
				<div class="ds-from-group">
					<textarea rows="" cols="" class="required" placeholder="公司简介" id="companyIntro" name="companyIntro"></textarea>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 组织机构代号校验:必填,待确认 -->
				</div>
			</li>
	        <%--<li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>购销盐产品种类:</label>
	            <div class="ds-from-group">
	                <select class="ds-select-control" name="goodsType">
	                    <option value="">请选择种类</option>
	                </select>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 收票人地址校验:必填,至少2位,没有空格 -->
	            </div>
	        </li>--%>
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>月购销盐产品用量:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required numerical_decimal" value="" placeholder="请输入月购销盐产品用量" type="text"  name="goodsNum" id="goodsNum" />
	               	（单位:kg）
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 收票人地址校验:必填,至少2位,没有空格 -->
	            </div>
	        </li>
	        <%--<li>
	            <label class="ds-from-left">&nbsp;</label>
	            <div class="ds-from-group">
	                <a href="javascript:void(0);" class="js-addtype">新增种类</a>
	            </div>
	        </li>--%>
		</ul>
	</div>
	<div class="mt40">
		<h2 class="ds-text-blue">认证信息</h2>
	    <ul class="ds-user-from member-form clearfix">
	        <li>
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>工商营业执照编号:</label>
	            <div class="ds-from-group">
	                <input class="ds-inp-control required" value="" placeholder="请输入营业执照编号" type="text"  name="charterCode" id="charterCode"/>
	                <span class="ds-from-tips ds-text-red ml10"></span>
	                <!-- 营业执照号校验:必填,待确认 -->
	            </div>
	        </li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>有效期:</label>
				<div class="ds-from-group">
					<input class="ds-inp-control ds-inp-control-min required" value="" placeholder="请选择起始时间" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="charterStartDate" id="charterStartDate"/>
					至
					<input class="ds-inp-control ds-inp-control-min required" value="" placeholder="请选择终止时间" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="charterEndDate" id="charterEndDate"/>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 营业执照号校验:必填,待确认 -->
				</div>
			</li>
	        <li class="width-fixed">
	            <label class="ds-from-left"><span class="ds-text-red mr5">*</span>附件:</label>
	            <div class="ds-from-group">
	                <div class="accessory-wrap">
	                	<div class="file-wrap">
		                	<input type="text" class="file-name" readonly="readonly" id="charter_name" placeholder="工商营业执照"/>
							<input type="hidden" class="file-id required" name="charterUrl" id="charterUrl"/>
		                	<a href="javascript:void(0);" class="ds-btn file-brower" id="charter_brower">浏览</a>
	                		<a href="javascript:void(0);" class="ds-btn file-upload" id="charter_upload">上传</a>
							<span class="ds-from-tips ds-text-red ml10"></span>
							<!-- 组织机构代号校验:必填,待确认 -->
						</div>
					</div>
	            </div>
	        </li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>食品流通许可证编号:</label>
				<div class="ds-from-group">
					<input class="ds-inp-control required" value="" placeholder="请输入食品流通许可证编号" type="text"  name="licenceBusiness" id="licenceBusiness"/>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 营业执照号校验:必填,待确认 -->
				</div>
			</li>
			<li>
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>有效期:</label>
				<div class="ds-from-group">
					<input class="ds-inp-control ds-inp-control-min required" value="" placeholder="请选择起始时间" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="businessStartDate" id="businessStartDate"/>
					至
					<input class="ds-inp-control ds-inp-control-min required" value="" placeholder="请选择终止时间" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="businessEndDate" id="businessEndDate"/>
					<span class="ds-from-tips ds-text-red ml10"></span>
					<!-- 营业执照号校验:必填,待确认 -->
				</div>
			</li>
			<li class="width-fixed">
				<label class="ds-from-left"><span class="ds-text-red mr5">*</span>附件:</label>
				<div class="ds-from-group">
					<div class="accessory-wrap">
						<div class="file-wrap">
							<input type="text" class="file-name" placeholder="食品流通许可证" readonly="readonly" id="business_name"/>
							<input type="hidden" class="file-id required" name="licenceBusinessUrl" id="licenceBusinessUrl" />
							<a href="javascript:void(0);" class="ds-btn file-brower" id="business_brower">浏览</a>
							<a href="javascript:void(0);" class="ds-btn file-upload" id="business_upload">上传</a>
							<span class="ds-from-tips ds-text-red ml10"></span>
							<!-- 组织机构代号校验:必填,待确认 -->
						</div>
					</div>
				</div>
			</li>
	        <li>
	            <label class="ds-from-left">&nbsp;</label>
	            <div class="ds-from-group">
	                <a class="ds-btn mr20 ds-btn-orange js-save"> 保存 </a>
	                <a class="ds-btn mr20 ds-btn-primary js-submit"> 提交 </a>
	            </div>
	        </li>
	    </ul>
	</div>
    <input type="hidden" id="loginId" name="loginId" value="${loginId}" />
	<div id="testDiv" style="display:none;"></div>
</form>