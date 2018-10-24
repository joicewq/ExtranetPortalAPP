require.config(config);
require(["jquery","validate","base","animation","pager","layer","select2","city.select"],function($,validate,base,animation,pager,layer,select2){
/*	var attachmentIds = $("#attachmentIds").val();
	var option = {
		is_shade: true,
		real_delete: false,
		file_ids: attachmentIds,
		multi_selection: true,
		max_file_size: "20mb",
		prevent_duplicates: true,
		mime_types: "",
		upload_index:1
	}*/
	function changeUpload(id,index){
		var option = {
				is_shade: true,
				real_delete: false,
				file_ids: id,
				multi_selection: true,
				max_file_size: "20mb",
				prevent_duplicates: true,
				mime_types: "",
				upload_index:index
			}
		return option;
	}

	//省份数据
	var data=[{id:1000,name:"中盐"},{id:1100,name:"北京市"},{id:1200,name:"天津市"},{id:13,name:"河北省"},{id:1400,name:"山西省"},{id:1500,name:"内蒙古自治区"},{id:2100,name:"辽宁省"},{id:2200,name:"吉林省"},{id:2300,name:"黑龙江省"},{id:3100,name:"上海市"},{id:3200,name:"江苏省"},{id:3300,name:"浙江省"},{id:3400,name:"安徽省"},{id:3500,name:"福建"},{id:3600,name:"江西省"},{id:3700,name:"山东省"},{id:4100,name:"河南省"},{id:4200,name:"湖北省"},{id:4300,name:"湖南"},{id:4400,name:"广东省"},{id:4500,name:"广西壮族自治区"},{id:4600,name:"海南省"},{id:5000,name:"重庆"},{id:5100,name:"四川"},{id:5200,name:"贵州省"},{id:5300,name:"云南省"},{id:5400,name:"西藏自治区"},{id:6100,name:"陕西省"},{id:6200,name:"甘肃省"},{id:6300,name:"青海省"},{id:6400,name:"宁夏回族自治区"},{id:6500,name:"新疆维吾尔自治区"},{id:7100,name:"台湾省"},{id:81,name:"香港特别行政区"},{id:8200,name:"澳门特别行政区"}];
	function save(){
		var flag = true;
		var describeId=getFileIds(1).join(',');
	      if(describeId==""){
	        	flag = false;
	        	/*layer.open({
	        		content: "请上传:质检报告(第三方检测报告)附件",
	        		scrollbar: false
	        	});*/
//	        	return false;
	        	$("#describeId").val("");
	        	$("#describeId").show();
	        }else{
	        	$("#describeId").val(describeId);
	        	$("#describeId").hide();
	        };
		
		var qualityReportId=getFileIds(3).join(',');
	      if(qualityReportId==""){
	        	flag = false;
	        	/*layer.open({
	        		content: "请上传:质检报告(第三方检测报告)附件",
	        		scrollbar: false
	        	});*/
//	        	return false;
	        	$("#qualityReportId").val("");
	        	$("#qualityReportId").show();
	        }else{
	        	$("#qualityReportId").val(qualityReportId);
	        	$("#qualityReportId").hide();
	        };
	        var standardFileId=getFileIds(4).join(',');
	      if(standardFileId==""){
	        	flag = false;
	        	/*layer.open({
	        		content: "请上传:执行标准文件附件",
	        		scrollbar: false
	        	});*/
//	        	return false;
	        	$("#standardFileId").val("");
	        	$("#standardFileId").show();
	        }else{
	        	$("#standardFileId").val(standardFileId);
	        	$("#standardFileId").hide();
	        };
	        
		$(".form-list [name]").each(function() {
			if(!checkForm($(this))){
				$(this).focus();
				flag = false;
				return false;
			}
		});
		  /*if($("#naturalParent[name='naturalParent']").find("option:selected").val()=="0"){
	        	flag = false;
	        	layer.open({
	        		content: "请选择食盐种类",
	        		scrollbar: false
	        	});
	        	return false;
	        };
	        if($("#naturalchild[name='naturalchild']").find("option:selected").val()=="1"){
	        	flag = false;
	        	layer.open({
	        		content: "请选择/新增食盐名称",
	        		scrollbar: false
	        	});
	        	return false;
	        };*/
	  
		if(flag){
			base.processStatus(1,'save','process_btn');
			var saltName = $("#saltName").val();		//食盐名称
			var remark = $("#remark").val();		//产品编号
			var remark2 = $("#remark2").val();		//产品主键ID
			var describeInfo = $("#describeInfo").val();		//食盐外观图案描述
			var cartonPackInfo = $("#cartonPackInfo").val();	//纸箱包装的样式描述
			var recognitionInfo = $("#recognitionInfo").val();	//防伪识别方法
			var productId = $("#productId").val();      //食盐产品生产标准
			var tradeMark = $("#tradeMark").val();      //产品商标
			var standard = $("#standard").val();      //执行标准
			
			//产品库需要字段
			var categoryId = $("#naturalParent[name='naturalParent']").find("option:selected").val();//产品库ID
			var nameNO=$("#naturalParent[name='naturalParent']").find("option:selected").attr("name");//产品库编号
			var province=$("#province[name='province']").find("option:selected").attr("name");//产品库编号
			var provinceName=$("#province[name='province']").find("option:selected").text();//产品库编号
			//end
			var saltNameChild="";
			if($("#saltNameChild")){
			  saltNameChild = $("#saltNameChild").val();
			}
			var id=$("#id").val();
			if(id==null || id==""){
				id=""
			}			
			var describeId = getFileIds(1).join(',');	//食盐外观图案Id集合
			var cartonPackId = getFileIds(2).join(',');	//纸箱包装的样式Id集合
			var qualityReportId = getFileIds(3).join(',');	//检验报告Id集合
			var standardFile = getFileIds(4).join(',');	//执行标准文件Id集合
			var tradeFile = getFileIds(5).join(',');	//产品商标文件
			var formDate = {
					id:id,//主键id
					saltName:saltName,	//名称
					province:province, //省份编号
					provinceName:provinceName,
					saltNameChild:saltNameChild,
					remark:remark,				//产品编号
					remark2:remark2,				//产品主键ID
					categoryId:categoryId,
					nameNO:nameNO,
					describeInfo:describeInfo,				//规格
					cartonPackInfo:cartonPackInfo,			//品质等级
					recognitionInfo:recognitionInfo,			//产品标准号
					describeId:describeId,	//食盐定点生产证号
					cartonPackId:cartonPackId,//产地
					qualityReportId:qualityReportId,	//有效期（从）
					productId:productId,
					productMark:"",
					tradeMark:tradeMark,
					standard:standard,
					standardFile:standardFile,
					tradeFile:tradeFile
			}
			var url = "/natural/save";
			$.ajax({
                url:url,
                data: {natural:JSON.stringify(formDate)},
                type: 'POST',
                dataType: "json",
                success: function(data) {
                    if(data.result==1){
                    	layer.msg('操作成功', {
    						icon: 1
    					});
                    	//layer.close(index);
                    	jumbto("/natural/naturalindex");
                    }else if(data.result==2){
                    	layer.msg('登录超时，请退出重新登录！', {
    						icon: 1
                    	});
                    }else{
                    	layer.msg('操作失败', {
						icon: 1
					});
                    }
                      
                },error:function(){
                	layer.msg('操作失败', {
						icon: 2
					});
                	base.processStatus(0,'save','process_btn');
                }
                
			});
			/*$.post(url,post,function(result){
				if(result=="success"){
					layer.msg('操作成功', {
						icon: 1
					});
					window.location.href="/prodlib/index"; 
				}else if(result=="error"){
					layer.msg('操作失败', {
						icon: 2
					});
				}
				base.processStatus(0,'save','process_btn');
			});*/
		}
	}
	
	$(function() {
		var id1 = $("#describeId").val();
		var id2 = $("#cartonPackId").val();
		var id3 = $("#qualityReportId").val();
		var id4 = $("#standardFile").val();
		var id5 = $("#tradeFile").val();
		$("#uploadDiv").load("/doc/index", changeUpload(id1,1));
		//option.upload_index=2;
		$("#uploadDiv2").load("/doc/index", changeUpload(id2,2));
		//option.upload_index=3;
		$("#uploadDiv3").load("/doc/index", changeUpload(id3,3));
		//option.upload_index=4;
		$("#uploadDiv4").load("/doc/index", changeUpload(id4,4));
		//option.upload_index=5;
		$("#uploadDiv5").load("/doc/index", changeUpload(id5,5));
		
		
		
		$(".form-list").on("blur", "input", function(){
			checkForm($(this));
		});
		
		
		 /**
         * 省市初始化
         */
        $("#province").citylist({
            data: data,
            id: "id",
            name: 'name',
            metaTag: 'name',
            selected:$("#province").attr("provinceId")//$("#province").attr("provinceId")
        });
        
    	
    	function cityChange(province){
    		 var provCity="";
    		 if(province){
    			 for(var i=0,len=data.length;i<len;i++){
    				 var provinceObj=data[i];
    				 if(province==provinceObj["name"]){
    					 provCity=provinceObj["id"];
    				 }
    			 }
    		 }
    		return provCity;
    	}
        var provinceId=$("#province").attr("provinceId");
        if(provinceId!=""){
        	var id = cityChange(provinceId);
            $("#province").find("option[name='"+id+"']").attr("selected","selected");
        }
       
        //编辑状态初始化食盐名称列表
        var nameno=$("#naturalParent[name='naturalParent']").val();
		if(nameno!="请选择" && nameno!=""){
			var province =$("#province[name='province']").find("option:selected").attr("name");
			var nameNO=$("#naturalParent[name='naturalParent']").find("option:selected").attr("name");
			var value;
			$.ajax({
				url: '/natural/caregary',
				type: "post",
				cache: false,
				data: {
					"nameNo": province+nameNO
				},
				dataType: "json",
				success: function(data) {
					if(data.status == 0) {
						$('#naturalchild[name="naturalchild"]').val("");
						var saltName = $("#naturalchild").attr("saltNameId");
						var naturalchild = data.naturalchild;
						var html;
						for(var i=0 ; i<naturalchild.length;i++){
							var name=naturalchild[i].name
							var id=naturalchild[i].id
							var nameNo=naturalchild[i].nameNo
							html+="<option value='"+id+"' name='"+nameNo+"'>"+name+"</option>";
							if(saltName==name){
								value=id;
							}
						}
						if($('#naturalParent[name="naturalParent"]').val()==""){
							var option="<option value=''>请选择</option>"
						}else{
							html+="<option value='0'>新增</option>"
							var option="<option value=''>请选择</option>"
						}
						$("#naturalchild").html(option+html).val(value);
						$("#naturalchild").select2();
			/*			var obj=getLastSelect();
						if(obj!=""){
							var id = obj.find("option:selected").val();
							var nameNO=obj.find("option:selected").attr("name");
							var saltName=obj.find("option:selected").text();
							$("#saltName").val(saltName);
							$("#remark").val(nameNO);
							$("#remark2").val(id);
						}else{
							$("#saltName").val("");
							$("#remark").val("");
							$("#remark2").val("");
						}*/
						if($("#saltNameChild")){
							$("#saltNameChild").remove();
						}
					} else {
						$("input[name='model']").val("");
						$("input[name='equipmentName']").val("");
						layer.msg('获取失败,联系超管', {
							icon: 2
						});
					}
				},
				error: function(XMLHttpRequest, textStatus) {
					layer.msg('操作失败', {
						icon: 2
					});
				}
			});
		}
		
		
		$("#save").click(save);
	
		$('#naturalParent[name="naturalParent"]').change(function() {
			var province =$("#province[name='province']").find("option:selected").attr("name");
			var nameNO=$(this).find("option:selected").attr("name");
			$.ajax({
				url: '/natural/caregary',
				type: "post",
				cache: false,
				data: {
					"nameNo": province+nameNO
				},
				dataType: "json",
				success: function(data) {
					if(data.status == 0) {
						$('#naturalchild[name="naturalchild"]').val("");
						var naturalchild = data.naturalchild;
						var html;
						for(var i=0 ; i<naturalchild.length;i++){
							var name=naturalchild[i].name
							var id=naturalchild[i].id
							var nameNo=naturalchild[i].nameNo
							html+="<option value='"+id+"' name='"+nameNo+"'>"+name+"</option>"
						}
						if($('#naturalParent[name="naturalParent"]').val()==""){
							var option="<option value=''>请选择</option>"
						}else{
							html+="<option value='0'>新增</option>"
							var option="<option value=''>请选择</option>"
						}
						
						$("#naturalchild").html(option+html);
						$("#naturalchild").select2();
						var obj=getLastSelect();
						if(obj!=""){
							var id = obj.find("option:selected").val();
							var nameNO=obj.find("option:selected").attr("name");
							var saltName=obj.find("option:selected").text();
							$("#saltName").val(saltName);
							$("#remark").val(nameNO);
							$("#remark2").val(id);
						}else{
							$("#saltName").val("");
							$("#remark").val("");
							$("#remark2").val("");
						}
						if($("#saltNameChild")){
							$("#saltNameChild").remove();
						}
					} else {
						$("input[name='model']").val("");
						$("input[name='equipmentName']").val("");
						layer.msg('获取失败,联系超管', {
							icon: 2
						});
					}
				},
				error: function(XMLHttpRequest, textStatus) {
					layer.msg('操作失败', {
						icon: 2
					});
				}
			});
		});

		$('#naturalchild[name="naturalchild"]').change(function() {
			var $this = $(this);
			var val = $this.val();
			if(val == 0){
				var input = $('<input type="text" style="width: 150px; vertical-align: top;" class="required illegalValidate" title="食盐名称"  name="saltNameChild" id="saltNameChild" />');
				$(this).parent().append(input)
			}else{
				var obj=getLastSelect();
				if(obj!=""){
					var id = $(this).find("option:selected").val();
					var nameNO=$(this).find("option:selected").attr("name");
					var saltName=$(this).find("option:selected").text();
					$("#saltName").val(saltName);
					$("#remark").val(nameNO);
					$("#remark2").val(id);
				}else{
					$("#saltName").val("");
					$("#remark").val("");
					$("#remark2").val("");
				}
				if($("#saltNameChild")){
					$("#saltNameChild").remove();
				}
			}
			
		});
		
		
		/*$('#naturalchild[name="naturalchild"]').change(function() {
			var obj=getLastSelect();
			if(obj!=""){
				var id = obj.find("option:selected").val();
				var nameNO=obj.find("option:selected").attr("name");
				var saltName=obj.find("option:selected").text();
				$("#saltNameChild").val(saltName);
				$("#remark").val(nameNO);
				$("#remark2").val(id);
			}else{
				$("#saltNameChild").val("");
				$("#remark").val("");
				$("#remark2").val("");
			}

		});*/
		
		$('#province[name="province"]').change(function() {
			//$('#naturalParent[name="naturalParent"]').find("option:selected").val();
			$('#naturalchild[name="naturalchild"]').empty().prepend("<option value=''>请选择</option>")
			$('#naturalParent[name="naturalParent"]').val("");
		});
	/*	$('#naturalNodechild[name="naturalNodechild"]').change(function() {
			var id = $(this).find("option:selected").val();
			var nameNO=$(this).find("option:selected").attr("name");
			var saltName=$(this).find("option:selected").text();
			$.ajax({
				url: '/natural/caregary',
				type: "post",
				cache: false,
				data: {
					"id": id
				},
				dataType: "json",
				success: function(data) {
					if(data.status == 0) {
						$("#naturalNodelist").show();
						var naturalchild = data.naturalchild;
						var html;
						for(var i=0 ; i<naturalchild.length;i++){
							var name=naturalchild[i].name
							var nameNo=naturalchild[i].nameNo
							var id=naturalchild[i].id
							html+="<option value='"+id+"' name='"+nameNo+"'>"+name+"</option>"
						}
						var option="<option value=''>请选择</option>"
						$("#naturalNodelist").html(option+html);
						var obj=getLastSelect();
						if(obj!=""){
							var id = obj.find("option:selected").val();
							var nameNO=obj.find("option:selected").attr("name");
							var saltName=obj.find("option:selected").text();
							$("#saltName").val(saltName);
							$("#remark").val(nameNO);
							$("#remark2").val(id);
						}else{
							$("#saltName").val("");
							$("#remark").val("");
							$("#remark2").val("");
						}
					} else {
						$("input[name='model']").val("");
						$("input[name='equipmentName']").val("");
						layer.msg('获取失败,联系超管', {
							icon: 2
						});
					}
				},
				error: function(XMLHttpRequest, textStatus) {
					layer.msg('操作失败', {
						icon: 2
					});
				}
			});
		});
		$('#naturalNodelist[name="naturalNodelist"]').change(function() {
			var obj=getLastSelect();
			if(obj!=""){
				var id = obj.find("option:selected").val();
				var nameNO=obj.find("option:selected").attr("name");
				var saltName=obj.find("option:selected").text();
				$("#saltName").val(saltName);
				$("#remark").val(nameNO);
				$("#remark2").val(id);
			}else{
				$("#saltName").val("");
				$("#remark").val("");
				$("#remark2").val("");
			}
		});
		*/
		
	});
	function getLastSelect(){
		if($("#naturalNodelist").val()!=""){
			return $("#naturalNodelist");
		}else{
			if($("#naturalNodechild").val()!=""){
				return $("#naturalNodechild");
			}else{
				if($("#naturalchild").val()!=""){
					return $("#naturalchild");
				}else{
					if($("#naturalParent").val()!=""){
						return $("#naturalParent");
					}else{
						return "";
					}
				}
			}
		}
	}
 });