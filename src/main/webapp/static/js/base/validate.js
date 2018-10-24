	
/**
 * 通用表单验证javascript
 * @author:hehuajun
 * @param element
 * @returns {Boolean}
 * 必填：required
 * 数字：numerical
 * 整数或小数:numerical_decimal
 * 手机号：mobilePhone
 * 邮件：email
 * 非法字符校验：illegalValidate
 * 验证码：matchVerify
 * 用户名是否被占用：loginNameExist
 * equalTo：添加属性equalTo="" 例如 equalTo="#loginPassword"
 * 最小长度：minDataLength 添加属性 minDataLength="" 例如 minDataLength="2"
 * 最大长度：maxDataLength 同上
 * 身份证:isIDCard
 * 护照:isPassPort
 * QQ号码:isQQ,4-12位
 * 微信号:isWechat,4-20位，以字母开头，可包含数字、字母、下划线以及中划线
 * 港澳居住证:
 */

function checkForm(element) {
	var bb = true;
	if ($(element).is('.required')) {
		if(required(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.numerical') && bb) {
		if(numerical(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.numerical_decimal') && bb) {
		if(numerical_decimal(element) == false){
			bb=false;
		}
	}
	if ($(element).is('.mobilePhone') && bb) {
		if(mobilePhone(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.email') && bb) {
		if(email(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.illegalValidate') && bb) {
		if(illegalValidate(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.illegalValidatePlus') && bb) {
		if(illegalValidatePlus(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.matchVerify') && bb){
		if(matchVerify(element) == false){
			bb = false;
		}
	}
	if ($(element).is('.checkUserName') && bb){
	    if(checkUserName(element) == false){
	        bb=false;
	    }
	}
	if ($(element).is('.loginNameExist') && bb){
		if(loginNameExist(element) == false){
			bb = false;
		}
	}
	if (typeof($(element).attr("equalTo")) != "undefined" && bb){
		if(equalTo(element) == false){
			bb = false;
		}
	}
	if (typeof($(element).attr("minDataLength")) != "undefined" && bb){
		if(minLength(element) == false){
			bb=false;
		}
	}
	if (typeof($(element).attr("maxDataLength")) != "undefined" && bb){
		if(maxLength(element) == false){
			bb=false;
		}
	}
	if ($(element).is('.isIDCard') && bb){
	    if(isIDCard(element) == false){
	        bb=false;
        }
    }
    if ($(element).is('.isPassPort') && bb){
        if(isPassPort(element) == false){
            bb=false;
        }
    }
    if ($(element).is('.isHKMacao') && bb){
        if(isHKMacao(element) == false){
            bb=false;
        }
    }
    if ($(element).is('.isQQ') && bb){
        if(isQQ(element) == false){
            bb=false;
        }
    }
    if ($(element).is('.isWechat') && bb){
        if(isWechat(element) == false){
            bb=false;
        }
    }
    if ($(element).is('.isTelephone') && bb){
    	if(isTelephone(element,"固话") == false){
    		bb=false;
    	}
    }
    if ($(element).is('.isFax') && bb){
    	if(isTelephone(element,"传真") == false){
    		bb=false;
    	}
    }
    if ($(element).is('.isPostCode') && bb){
    	if(isPostCode(element) == false){
    		bb=false;
    	}
    }
    if ($(element).is('.checkPhoneExist') && bb){
		if(checkPhoneExist(element) == false){
			bb = false;
		}
	}
    //企业名称唯一
    if ($(element).is('.checkCompanyNameExist') && bb){
		if(checkCompanyNameExist(element) == false){
			bb = false;
		}
	}
    //工商执照编号
    if ($(element).is('.checkCharterCodeExist') && bb){
		if(checkCharterCodeExist(element) == false){
			bb = false;
		}
	}
	return bb;
}

/**
 * 用户名校验
 * */
function checkUserName(eleObj){
	var bb=true;
    var reg = /^[\-_@a-zA-Z0-9]+$/;
    if(!reg.test($(eleObj).val())){
        var $required = $("<span class='ds-from-tips ds-text-red ml10'>用户名只可包括英文、数字、_、-、@</span>");
        bb = false;
        $(eleObj).addClass("input-danger");
        if($(eleObj).parent().find(".ds-text-red").length==0){
            $(eleObj).after($required);
        } else {
            $(eleObj).parent().find(".ds-text-red").text("只可输入英文、数字、_、-、@");
        }
    }else {
        $(eleObj).removeClass("input-danger");
        $(eleObj).parent().find(".ds-text-red").empty();
    }
    return bb;
}
function required(eleObj) {
	var bb = true;
	if (!$(eleObj).val()) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请完善必填信息</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请完善必填信息");
		}
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
function numerical(eleObj) {
	var bb = true;
	if (!/^[0-9]+$/.test($(eleObj).val())) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的整数</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的整数");
		}
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
function numerical_decimal(eleObj) {
	var bb = true;
	if (!/^([1-9]\d*|0)(\.\d{1,2})?$/.test($(eleObj).val())) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的数字</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的数字");
		}
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
function mobilePhone(eleObj) {
	var bb = true;
	var phoneReg = /^((1[3-9]{1})+\d{9})$/;/*/^();/^(((13[0-9]{1})|(17[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;*/
	if ($(eleObj).val() && !phoneReg.test($(eleObj).val())) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确手机号</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确手机号");
		}
		$(".markPhoneCode").attr("style","visibility:hidden");
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
		$(".markPhoneCode").attr("style","");
	}
	return bb;
}
function email(eleObj) {
	var bb = true;
	if($(eleObj).val()==""){
		return bb;
	}
	var phoneReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (!phoneReg.test($(eleObj).val())) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的邮箱</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的邮箱");
		}
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}	
function illegalValidate(eleObj) {
	var str = $(eleObj).attr("title");
	var bb = true;
	var phoneReg = /^[\-a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (!phoneReg.test($(eleObj).val())) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的"+str+"</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的"+str);
		}
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
function illegalValidatePlus(eleObj) {
	var str = $(eleObj).attr("title");
	var bb = true;
	var phoneReg =/[(\`)(\~)(\!)(\@)(\<)(\>)(\#)(\$)(\%)(\^)(\&)(\*)]+/;
	if (phoneReg.test($(eleObj).val())) {
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的"+str+"</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required); 
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的"+str);
		}
	} else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
/**
 * 图片验证码
 * @param eleObj
 * @returns {boolean}
 */
function matchVerify(eleObj){
	var bb=true;
	if(!matchVerifyCode($(eleObj).val())){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的验证码</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的验证码");
		}
	}else{
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
/**
 * 用户名是否被占用
 * @param eleObj
 * @returns {boolean}
 */
function loginNameExist(eleObj){
	var bb=true;
	var pattern = new RegExp("[\u4e00-\u9fa5]+")
	if(pattern.test($(eleObj).val())){
		$(eleObj).focus();
		bb = false;
		$(eleObj).parent().find(".ds-text-red").text("用户名只能输入数字或字母")
	}else{
		if(!matchLoginName($(eleObj).val())){
			var $required = $("<span class='ds-from-tips ds-text-red ml10'>该用户名已被占用</span>");
			bb = false;
			$(eleObj).addClass("input-danger");
			if($(eleObj).parent().find(".ds-text-red").length==0){
				$(eleObj).after($required);
			} else {
				$(eleObj).parent().find(".ds-text-red").text("该用户名已被占用");
			}
		}else{
			$(eleObj).removeClass("input-danger");
			$(eleObj).parent().find(".ds-text-red").empty();
		}
	}

	return bb;
}
/**
 * equalTo校验
 * @param eleObj
 * @returns {boolean}
 */
function equalTo(eleObj){
	var compareObj=$(eleObj).attr("equalTo");
	var bb=true;
	if($(eleObj).val()!==$(compareObj).val()){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>两次输入内容不一致</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("两次输入内容不一致");
		}
	}else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
/**
 * 最小长度
 * @param eleObj
 * @returns {boolean}
 */
function minLength(eleObj){
	var num=$(eleObj).attr("minDataLength");
	var bb=true;
	var data=$(eleObj).val();
	if(data && data.replace(/[^x00-xff]/g,"01").length<num){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>长度不能小于"+num+"个字符</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("长度不能小于"+num+"个字符");
		}
	}else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
/**
 * 最大长度
 * @param eleObj
 * @returns {boolean}
 */
function maxLength(eleObj){
	var num=$(eleObj).attr("maxDataLength");
	var bb=true;
	var data=$(eleObj).val();
	if(data && data.replace(/[^x00-xff]/g,"01").length>num){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>长度不能大于"+num+"个字符</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("长度不能大于"+num+"个字符");
		}
	}else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
/**
 * 身份证号
 * @param eleObj
 * @returns {boolean}
 */
function isIDCard(eleObj){
    var bb=true;
    var idCardReg=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
    if (!idCardReg.test($(eleObj).val())) {
        var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的证件号</span>");
        bb = false;
        $(eleObj).addClass("input-danger");
        if($(eleObj).parent().find(".ds-text-red").length==0){
            $(eleObj).after($required);
        } else {
            $(eleObj).parent().find(".ds-text-red").text("请输入正确的证件号");
        }
    } else {
        $(eleObj).removeClass("input-danger");
        $(eleObj).parent().find(".ds-text-red").empty();
    }
    return bb;
}
/**
 * 护照
 * @param eleObj
 * @returns {boolean}
 */
function isPassPort(eleObj){
    var bb=true;
    var portReg1 = /^[a-zA-Z]{5,17}$/;
    var portReg2 = /^[a-zA-Z0-9]{5,17}$/;
    if(!portReg1.test($(eleObj).val()) && !portReg2.test($(eleObj).val())){
        var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的证件号</span>");
        bb = false;
        $(eleObj).addClass("input-danger");
        if($(eleObj).parent().find(".ds-text-red").length==0){
            $(eleObj).after($required);
        } else {
            $(eleObj).parent().find(".ds-text-red").text("请输入正确的证件号");
        }
    }else {
        $(eleObj).removeClass("input-danger");
        $(eleObj).parent().find(".ds-text-red").empty();
    }
    return bb;
}
/**
 * 港澳居住证
 * @param eleObj
 * @returns {boolean}
 */
function isHKMacao(eleObj) {
    var bb=true;
    var reg = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/;
    if(!reg.test($(eleObj).val())){
        var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的证件号</span>");
        bb = false;
        $(eleObj).addClass("input-danger");
        if($(eleObj).parent().find(".ds-text-red").length==0){
            $(eleObj).after($required);
        } else {
            $(eleObj).parent().find(".ds-text-red").text("请输入正确的证件号");
        }
    }else {
        $(eleObj).removeClass("input-danger");
        $(eleObj).parent().find(".ds-text-red").empty();
    }
    return bb;
}
/**
 * QQ号码校验
 * */
function isQQ(eleObj) {
    var bb=true;
	if($(eleObj).val()==""){
		return bb;
	}
    var reg = /^[1-9][0-9]{4,11}$/;
    if(!reg.test($(eleObj).val())){
        var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的QQ号</span>");
        bb = false;
        $(eleObj).addClass("input-danger");
        if($(eleObj).parent().find(".ds-text-red").length==0){
            $(eleObj).after($required);
        } else {
            $(eleObj).parent().find(".ds-text-red").text("请输入正确的QQ号");
        }
    }else {
        $(eleObj).removeClass("input-danger");
        $(eleObj).parent().find(".ds-text-red").empty();
    }
    return bb;
}

/**
 * 微信号校验
 * */
function isWechat(eleObj) {
    var bb=true;
    var reg = /^[a-zA-Z][\w-]{3,19}$/;
    if(!reg.test($(eleObj).val())){
        var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的微信号</span>");
        bb = false;
        $(eleObj).addClass("input-danger");
        if($(eleObj).parent().find(".ds-text-red").length==0){
            $(eleObj).after($required);
        } else {
            $(eleObj).parent().find(".ds-text-red").text("请输入正确的微信号");
        }
    }else {
        $(eleObj).removeClass("input-danger");
        $(eleObj).parent().find(".ds-text-red").empty();
    }
    return bb;
}
/**
 * 邮编校验
 * */
function isPostCode(eleObj) {
	var bb=true;
	if($(eleObj).val()==""){
		return bb;
	}
	if(isNaN($(eleObj).val())||$(eleObj).val().length!=6){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的邮编</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的邮编");
		}
	}else {
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
	}
	return bb;
}
/**
 * 固话/传真校验
 * */
function isTelephone(eleObj,str) {
	var bb=true;
	if($(eleObj).val()==""){
		return bb;
	}
	var arr=$(eleObj).val().split("-");
	if(arr.length!=2||arr[0].length<3||arr[0].length>5||arr[1].length<7||arr[1].length>8){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的"+str+"</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("请输入正确的"+str);
		}
		return bb;
	}else{
		var arr1=arr[0].split(".");
		var arr2=arr[1].split(".");
		if(isNaN(arr[0])||isNaN(arr[1])||arr1.length==2||arr2.length==2){
			var $required = $("<span class='ds-from-tips ds-text-red ml10'>请输入正确的"+str+"</span>");
			bb = false;
			$(eleObj).addClass("input-danger");
			if($(eleObj).parent().find(".ds-text-red").length==0){
				$(eleObj).after($required);
			} else {
				$(eleObj).parent().find(".ds-text-red").text("请输入正确的"+str);
			}
			return bb;
		}
	}
	$(eleObj).removeClass("input-danger");
	$(eleObj).parent().find(".ds-text-red").empty();
	return bb;
}

//====================需请求后台的校验=====================
/**
 * 校验验证码是否输入正确-后台请求
 */
function matchVerifyCode(value) {
	var result=false;
	var verifyCode = value;
	$.ajax({
		url: '/verify/compare?verifyCode=' + verifyCode, //url  action是方法的名称
		type: 'GET',
		dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
		async: false,
		success: function (data) {
			result = data;
		}
	});
	return result;
};

/**
 * loginName是否被占用-后台请求
 * @param value
 * @returns {boolean}
 */
function matchLoginName(value){
	var result=false;
	$.ajax({
		url: '/account/exist?loginName='+value, //url  action是方法的名称
		type: 'GET',
		dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
		async:false,
		success: function(data) {
			result=data;
		}
	});
	return result;
}

function checkPhoneExist(eleObj){
	
	var bb=true;
	if(!matchPhone($(eleObj).val())){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>该手机号码已被注册</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("该手机号码已被注册");
		}
		$(".markPhoneCode").attr("style","visibility:hidden");
	}else{
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
		$(".markPhoneCode").attr("style","");
	}
	return bb;
}
function checkCompanyNameExist(eleObj){
	var bb=true;
	if(!checkIsExits($(eleObj).val(),$(eleObj).attr("data-companyid"))){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>该企业名称已被注册</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("该企业名称已被注册");
		}
	}else{
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
		$(".markPhoneCode").attr("style","");
	}
	return bb;
}
function checkCharterCodeExist(eleObj){
	var bb=true;
	if(!charterCodeExist($(eleObj).val(),$(eleObj).attr("data-companyid"))){
		var $required = $("<span class='ds-from-tips ds-text-red ml10'>该工商营业执照编号已被注册</span>");
		bb = false;
		$(eleObj).addClass("input-danger");
		if($(eleObj).parent().find(".ds-text-red").length==0){
			$(eleObj).after($required);
		} else {
			$(eleObj).parent().find(".ds-text-red").text("该工商营业执照编号已被注册");
		}
	}else{
		$(eleObj).removeClass("input-danger");
		$(eleObj).parent().find(".ds-text-red").empty();
		$(".markPhoneCode").attr("style","");
	}
	return bb;
}
/**
 * 手机号码是否已注册-后台请求
 * @param value
 * @returns true	表示没有注册	可用
 * 			false	表示已注册	不可用
 */
function matchPhone(value,companyId){
	var flag = false;
	var url = "/account/phoneIsExit";
	$.ajax({
		url: url, 
		type: 'POST',
		data: {phone:value,companyId:companyId},
		async:false,
		success: function(result) {
			flag = result;
		}
	});
	return flag;
}
//失去焦点验证
/*$(function(){
	$("form [name]").blur(function(){
		checkForm($(this));
	});
})*/
/**
 * 企业名称是否已注册-后台请求
 * @param value
 * @returns true	表示没有注册	可用
 * 			false	表示已注册	不可用
 */
function checkIsExits(value,companyId){
	var flag = false;
	var url = "/supplier/checkIsExit";
	$.ajax({
		url: url, 
		type: 'POST',
		data: {companyName:value,companyId:companyId},
		async:false,
		success: function(result) {
			flag = result;
		}
	});
	return flag;
}
/**
 * 公司营业执照编号是否已注册-后台请求
 * @param value
 * @returns true	表示没有注册	可用
 * 			false	表示已注册	不可用
 */
function charterCodeExist(value,companyId){
	var flag = false;
	var url = "/supplier/checkIsExit";
	$.ajax({
		url: url, 
		type: 'POST',
		data: {charterCode:value,companyId:companyId},
		async:false,
		success: function(result) {
			flag = result;
		}
	});
	return flag;
}
	

