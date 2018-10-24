	/**
	 * 用户登录
	 */
	function login(){
		var loginName=$("#loginName").val();
		var loginPassword=$("#loginPassword").val();
		if(loginName.indexOf(" ")>-1 || loginPassword.indexOf(" ")>-1){
			alert("用户名或密码错误!")
		}else{
			var param="loginName="+loginName+"&loginPassword="+loginPassword;
			$.ajax({
		        url: "/loginController/login.do", //url  action是方法的名称
		        data: param,
		        type: 'POST',
		        dataType: "json", //可以是text，如果用text，返回的结果为字符串；如果需要json格式的，可是设置为json
		        //contentType: "application/json; charset=utf-8",
		        success: function(msg) {
		        	if(msg.result ==0){
						alert("出错了! "+msg.message);
					}
					if(msg.result == 1){
						location.href="/index.html";
					}
		        },
		        error: function(msg) {
		        	alert("登录失败!");
		        }
		    });
		}
	}
	/**
	 * 忘记密码
	 */
	function forgetPassword(){
		location.href=umPath+"/html/findPassword.html";
	}
