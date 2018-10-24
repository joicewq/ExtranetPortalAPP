
require.config(config);
require(["jquery","validate","base","animation","layer","step","phoneVarifyCode","strength"],
	function($,validate,base,animation,layer,step,phoneVarifyCode,strength){
	var loginId="";
	
	function errorTip(text){
		layer.alert(text);
	}

	function showCode(){
		$("#verifyImg").attr("src","/verify/code?tm="+Math.random());
		$("#verifyCode").val("");
		$("#verifySpan").text("");
	}

	/**
	 * 提交数据
	 */
	function _submit(){
		var flag = true;
		$("#accountForm [name]").each(function() {
			if(!checkForm($(this))){
				$(this).focus();
				flag = false;
				return false;
			}
			switch ($(this).attr("id")) {
			case "phone":
				//手机验证码验证
/*				if($("#phone-verifyCode").val()==""){
					$("#phone-verifyCode").focus();
					flag = false;
					$("#verifyCode_error").html("请完善必填信息")
					return false;
				}*/
				break;
			case "loginPassword":
				if($("#loginPassword").val().indexOf(" ")>-1){
					$("#loginPassword").focus();
					flag = false;
					$("#loginPassword").parent().find(".ds-from-tips").html("密码不能包含空格")
					return false;
				}
				break;
			case "password":
				if($("#password").val().indexOf(" ")>-1){
					$("#password").focus();
					flag = false;
					$("#password").parent().find(".ds-from-tips").html("密码不能包含空格")
					return false;
				}
				break;

			default:
				break;
			}
		});
		if(flag){
			animation.load({
				container:$(".ds-user-main"),
				text:"注册中，请稍后"
			});
			var params=base.serializeObject($("#accountForm"));
			$.ajax({
				url: '/account/add',
				data: {params:JSON.stringify(params)},
				type: 'POST',
				dataType: "json",
				success: function(data) {
					if(data.result==1){//注册成功
						loginId=data.data;
						$("#lId").val(loginId);
						registerSuccess();
					}else{
						animation.destory({
							callback:function(){
								errorTip(data.message);
							}
						});
					}
				},
				error: function() {
					animation.destory({
						callback:function(){
							errorTip("注册失败！");
						}
					});
				}
			});
		}
	}

	/**
	 * 注册成功后的提醒
     */
	function registerSuccess(){
		animation.destory({
			callback:function(){
				var registerTip="<div class='register-tips'>" +
					"<div class='clearfix'>" +
					"<div class='register-tips-icon'>✔</div>" +
					"<div class='register-tips-text'>" +
					"<span>注册成功</span>" +
					"<span>您已成为该平台的会员</span>" +
					"</div>"+
					"</div>"+
					"<div class='ds-btn-group clearfix'>" +
					"<a href='javascript:void(0)' class='ds-btn ds-btn-primary js-member-btn'>填写资料认证</a>" +
					"<a href='/login/index' class='ds-btn ds-btn-orange'>登陆</a>"+
					"</div>"+
					"</div>";
				step.next();
				$(".ds-user-main").html(registerTip);
			}
		});
	}


	$(function(){
		//初始化step
		step.init({
			steps:["注册账号","注册成功"],
			container:$(".progress-title-container")
		})
		//调用密码强度插件
		$("#loginPassword").strength({
            strengthClass: 'strength',
            strengthMeterClass: 'strength_meter',
            strengthButtonClass: 'button_strength',
            strengthButtonText: 'Show Password',
            strengthButtonTextToggle: 'Hide Password',
            text:{
            	veryWeak:"非常弱",
            	weak:"弱",
            	medium:"中",
            	strong:"强"
            },
            isShowPassword:false,
            strength:"强度:"
        });
		/**
		 *失去交点验证
         */
		$("#accountForm [name]").blur(function(){
			if(!checkForm($(this))){
//  			    $(this).focus();//只能编辑本输入框
				flag = false;
				return false;
			}
			switch ($(this).attr("id")) {
			case "phone":
				//手机验证码验证
/*				if($("#phone-verifyCode").val()==""){
					$("#phone-verifyCode").focus();
					flag = false;
					$("#verifyCode_error").html("请完善必填信息")
					return false;
				}*/
				break;
			case "loginPassword":
				if($("#loginPassword").val().indexOf(" ")>-1){
					$("#loginPassword").focus();
					flag = false;
					$("#loginPassword").parent().find(".ds-from-tips").html("密码不能包含空格")
					return false;
				}
				break;
			case "password":
				if($("#password").val().indexOf(" ")>-1){
					$("#password").focus();
					flag = false;
					$("#password").parent().find(".ds-from-tips").html("密码不能包含空格")
					return false;
				}
				break;

			default:
				break;
			}
		});
		//手机验证码
		$("#phone-verifyCode").blur(function(){
			$("#verifyCode_error").html("");
			if($(this).val()==""){
				$("#verifyCode_error").html("请完善必填信息");
			}

		});

		/**
		 * 获取短信验证码
         */
		$(".js-getPhoneCode").on("click",function(){
			$("#verifyCode_error").html("");
			var $thisBtn = $(this);
			var phoneNum=$("#phone").val();
			if(phoneNum.length>0 && !isNaN(phoneNum) && phoneNum.length==11 && phoneNum.substring(0,1)==1){
				$.post("/login/sendMsg",{phone:phoneNum},function(result){
					if(result=="error"){
						$("#verifyCode_error").html("获取验证码失败");
					}else{
						$("#phone").attr("readonly","readonly");
						$(".changePhone").show();
						$("#verifyCode_error").html("验证码已发送");
					}
				});
			}
			else{
				$("#phone").next(".ds-from-tips").html("请正确填写手机号码");
			}
		})

		$("#showCode").on("click",function(){
			showCode();
		})

		/**
		 * 注册按钮点击事件
         */
		$(".js-submit").on("click",function(){
			_submit();
		 });
		$(".changePhone").on("click",function(){
			layer.confirm('更改手机需重新发送验证码', {
				  btn: ['确定', '取消'] //可以无限个按钮
				}, function(index, layero){
					$("#phone").removeAttr("readonly");
					$("#verifyCode_error").empty();
					$("#phone-verifyCode").val("");
					$.post("/login/disVerifyCode",{},function(){
						layer.close(index);
					});
				}, function(index){
					layer.close(index);
				});

		});
		$(document).on("click",".js-member-btn",function(){
			loadSupplierRegister();
		});

	})
	function loadSupplierRegister(){
		var loginId=$("#lId").val();
		$("#registerBox").load("/supplier/register?loginId="+loginId);
	}
})