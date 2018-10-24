require.config(config);
require(["jquery","layer","animation","validate"],function($,layer,animation,validate){
	$(function(){
		// 我的合同列表删除提示
		$("#delContract").on("click",function(){
			var index = layer.confirm('确定要删除合同吗？', {
				icon: 3,
				btn: ['确定','取消'] //按钮
			}, function(){
				//点确定执行
				layer.close(index);
			}, function(){
				//点取消执行
			});
		})

		// // 采购页面点击我要供货弹出信息框 改为页面跳转
		// $(".product-modal-btn").on("click",function(){
		// 	layer.open({
		// 		type: 2,
		// 		title: "<i class='fa fa-ambulance mr5'></i>我要供货",
		// 		content: "/html/supply.html",
		// 		area: ["800px","460px;"]
		// 	})
		// })

		// 购销合同选择交货方式
		$("#since").change(function(){
			if($(this).attr("checked")){
				$("#distribution").attr("checked",false);
				$(".distribution-box").hide();
				$(".since-box").show();
			}else{
				$(".since-box").hide();
			}
		});
		$("#distribution").change(function(){
			if($(this).attr("checked")){
				$("#since").attr("checked",false);
				$(".since-box").hide();
				$(".distribution-box").show();
			}else{
				$(".distribution-box").hide();
			}
		});

		// 选择文件
		$(".file").on("change","input[type='file']",function(){
		    var filePath=$(this).val();
		    if(filePath.indexOf("jpg")!=-1 || filePath.indexOf("png")!=-1){
		        var arr=filePath.split('\\');
		        var fileName=arr[arr.length-1];
		        $(this).next(".showFileName").val(fileName);
		        console.log(fileName);
		    }
		})

		
	})
	
})