;define(["jquery","animation"],function($,animation){
	
	/**
	 * config={
	 *	container:$("#container");
	 * 	steps:["步骤一","步骤二","步骤三"]
	 * }
	 * */
	
	var step={};
	step.init=function(config){
		animation.load({
			container:$(".progress-container")
		});
		if(config && config.steps){
			var stepNum=config.steps.length;
			var stepTextArray=config.steps;
		}
		var $ul=$("<ul></ul>").addClass("progress-title clearfix").appendTo(config.container);
		for(var i=0;i<stepNum;i++){
			var $li=$("<li></li>");
			if(i==0){
				$li.addClass("active");
			}
			$li.addClass("font18").html("<span class='progress-text'>"+stepTextArray[i]+"</span>").attr("id","step-"+(i+1)).width(1/stepNum*100+"%");			
			var span=$("<span class='progress-num'></span>").text(i+1).prependTo($li);
			$ul.append($li);
		}
		animation.destory();
	}
	/*
	 *step.next(config){}跳到下一个步骤
	 * config={
	 * 	callback:function(){}//步骤跳转完成后的回调函数
	 *  animationCallback:function(){}//过渡动画消失后的回调函数
	 * }
	 * */
	step.next=function(config){
		var curStepLi=$("li[id^=step].active");
		curStep=curStepLi.attr("id").match(/^step-(\d+)/)[1];
		curStepLi.removeClass("active");
		curStep++;
		$("#step-"+curStep).addClass("active");
		animation.destory({
			callback:function(){
				config && config.animationCallback && config.animationCallback();
			}
		});
		config && config.callback && typeof config.callback=="function" && config.callback();
	}
	
	return step;
})