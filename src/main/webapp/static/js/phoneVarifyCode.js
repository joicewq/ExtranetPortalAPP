;define(["jquery"],function($){
	/**
	var config={
		startBtn:$("#btn"),
		url:"/test/d",
		params:{},
		time:5,
		beforeSend:function(){}发送手机号码之前的回调函数
		success:function(data){}手机号码发送成功后的回调函数
	}
	**/
	function sendCode(config,callback){
		$.ajax({
			url:config.url,
			type:"POST",
			param:config.params,
			beforeSend:function(){
				config && config.beforeSend && config.beforeSend();
			},
			success:function(data){
				config && config.success && config.success(data);
				callback && callback(data);
			}
		})
	}
	
	function countDown(countDownTime){
		var time=countDownTime||5;
		this.addClass("btn-disabled").attr("disabled","disabled");
		var $this=this;
		var timer=setInterval(function(){
			if(time>=0){
				console.log("time>0");
				$this.html("有效期"+time+"秒");
				time--;
			}
			if(time==-1){
				console.log("time==-1");
				$this[0].removeAttribute("disabled");
				$this.removeClass("btn-disabled").html("重新发送");
				clearInterval(timer);
			}
		},1000);
	}
	
	function init(config){
		var $self=config.startBtn;
		if(!$self.hasClass("btn-disabbled") && $self.attr("disabled")!="disabled"){
			countDown.call($self,config.time);
			sendCode(config);				
		}
	}
	
	return {
		init:init
	}
})