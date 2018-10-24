require.config({
	baseUrl:"../js",
	paths:{
		"jquery":"../common/jquery-1.8.3.min",
		"service":"service/service",
		"header":"header",
		"city.select":"plugins/jquery.city.select.min",
		"valid-zh":"../common/jquery.validate/messages_zh",
		"valid":"../common/jquery.validate/jquery.validate",
		"encryption":"plugins/encryption.min",
		"valid-extend":"../common/jquery.validate/validate-method-define"
	}
});
require(["jquery","path","header","valid","valid-zh","valid-extend","animation","encryption"],function($,path,header,valid,validZh,validExtend,animation,strEnc){
	/**
	 * 判断协议是否同意
	 */
	var isPhoneCodePass=false;
	var isVerifyCodePass=false;
	
	var urlObj = {
		register:null,//注册接口
		phoneVerifyCode:null,//手机验证验证码接口
	}
	function isAccept(){
		if ($("#accept")[0].checked) {
			$("#register").removeAttr("disabled");
			$("#register").addClass("ds-btn-primary");
		}else{
			$("#register").attr({"disabled":"disabled"});
			$("#register").removeClass("ds-btn-primary");
		}
	}
		
	/**
	 * 提交数据
	 */
	function _submit(){
		var valid=$("#accountForm").valid();
		if(valid){
			$("#register").attr({"disabled":"disabled"});
			$("#register").removeClass("ds-btn-orange");
			var account=$("#accountForm").serialize();
			var param=account+"&"+user;
			$.ajax({
				url: '/userController/register.do', //url  action是方法的名称
				data: param,
				type: 'POST',
				dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
				//contentType: "application/json; charset=utf-8",
				success: function(data) {
					if(data.result==1){//注册成功
						console.info("注册成功!");
						location.href=himPath+"/login.html";
					}else{
						console.error(data.message);
						$("#register").removeAttr("disabled");
						$("#register").addClass("ds-btn-orange");
					}
				},
				error: function(msg) {
					console.error("注册失败!");
					$("#register").removeAttr("disabled");
					$("#register").addClass("ds-btn-orange");
				}
			});
		}
	}
	
	$(function(){
		$(".js-getPhoneCode").on("click",function(){
			var $thisBtn = $(this);
			var phoneNum=$("#loginPhone").val();
			if(phoneNum.length>0 && !isNaN(phoneNum) && phoneNum.length==11 && phoneNum.substring(0,1)==1){
				$thisBtn.addClass("btn-disabled").attr("disabled","disabled");
				$("#loginPhone").next(".ds-from-tips").html("");
				$.ajax({
					url: urlObj.phoneVerifyCode,
					data:{
						phoneNum:phoneNum
					},
					success:function(data){
						if(data.result=="success"){
							isPhoneCodePass=true;
						}
					}
				})	
				var time=5;
				var timer=setInterval(function(){
					if(time>=0){
						$thisBtn.html(time);
						time--;
					}
					if(time==-1){
						$thisBtn.removeClass("btn-disabled").html("重新发送");
						clearInterval(timer);
					}
				},1000)
			}
			else{
				$("#loginPhone").next(".ds-from-tips").html("请正确填写手机号码");
			}
		})
		$(".js-isAccept").on("click",function(){
			isAccept();
		})
		
		/*$(".js-sumbit").on("click",function(){
			_submit();
		})*/
		//注册成功提醒
		var registerTip="<div class='register-tips'>" +
					"<div class='clearfix'>" +
					"<div class='register-tips-icon'>✔</div>" +
					"<div class='register-tips-text'>" +
					"<span>注册成功</span>" +
					"<span>马上填写资料成为会员吧</span>" +
					"</div>"+
					"</div>"+
					"<div class='ds-btn-group clearfix'>" +
					"<a href='javascript:void(0);' class='ds-btn ds-btn-primary js-member-btn'>填写资料</a>" +
					"<a href='javascript:void(0);' class='ds-btn ds-btn-orange'>马上登陆</a>"+
					"</div>"+
					"</div>"
		$(".js-sumbit").on("click",function(){
			/*校验表单成功后ajax提交注册信息的回调函数，此处暂时省去校验表单以及提交数据步骤*/
			animation.load({
				container:$(".ds-user-main"),
				text:"注册中，请稍后"
			});
			/*setTimeout模拟提交数据后的回调函数*/
			setTimeout(function(){
				animation.destory({
					callback:function(){
						$(".progress-title .js-register").removeClass("active");
						$(".progress-title .js-register-tips").addClass("active");
						$(".ds-user-main").html(registerTip);
					}
				})
			},3000)
		})
		
		$(document).on("click",".js-member-btn",function(){
			animation.load({
				container:$(".ds-user-main"),
				text:"正在前往填写资料页面，请稍后",
				url:"beMember.html",
				callback:function(){
					$(".progress-title .js-register-tips").removeClass("active");
					$(".progress-title .js-member").addClass("active");
				},
				loadCallback:function(){
					
				}
			})				
		})
	})
})