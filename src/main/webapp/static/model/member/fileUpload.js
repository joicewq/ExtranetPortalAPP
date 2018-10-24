/**
 * Created by lovercy on 24/11/2016.
 *//*

    //文件名称对象
    var supplierfile={
        logo_file:"",
        charter_file:"",
        business_file:"",
        wholesale_file:"",
    };
    //文件按钮控制
    var btnFlag={
        logo_btn:false,
        charter_btn:false,
        business_btn:false,
        wholesale_btn:false,
    };
    //组件的初始化方法
    var upload_global={
        //选择完成
        FilesAdded:function(files){
            var fileName=files[0].name;
            var oldId=files[0].id;
            if(btnFlag.charter_btn){
                $("#"+$("#charter_brower").attr("oId")).click();//删除自己之前的文件
                $("#charter_brower").attr("oId",oldId);//设置临时文件id
                $("#charter_name").val(fileName);//显示临时文件名称
                $("#charterUrl").val("");//选择文件后清空原来的ID
            }else if(btnFlag.logo_btn){
                $("#"+$("#logo_brower").attr("oId")).click();//删除自己之前的文件
                $("#logo_brower").attr("oId",oldId);//设置临时文件id
                $("#logo_name").val(fileName);//显示临时文件名称
                $("#logoUrl").val("");//选择文件后清空原来的ID
            }else if(btnFlag.business_btn){
                $("#"+$("#business_brower").attr("oId")).click();//删除自己之前的文件
                $("#business_brower").attr("oId",oldId);//设置临时文件id
                $("#business_name").val(fileName);//显示临时文件名称
                $("#licenceBusinessUrl").val("");//选择文件后清空原来的ID
            }else if(btnFlag.wholesale_btn){
                $("#"+$("#wholesale_brower").attr("oId")).click();//删除自己之前的文件
                $("#wholesale_brower").attr("oId",oldId);//设置临时文件id
                $("#wholesale_name").val(fileName);//显示临时文件名称
                $("#licenceWholesaleUrl").val("");//选择文件后清空原来的ID
            }
        },
        //上传完成
        FileUploaded:function(){
            //获取文件id
            var fileId=getNowId();
            if(btnFlag.charter_btn){
                //删除之前的
                $("#"+supplierfile.charter_file).click();
                //赋值,展示
                supplierfile.charter_file=fileId;
                $("#charterUrl").val(fileId);
            }else if(btnFlag.logo_btn){
                //删除之前的
                $("#"+supplierfile.logo_file).click();
                //赋值,展示
                supplierfile.logo_file=fileId;
                $("#logoUrl").val(fileId);
            }else if(btnFlag.business_btn){
                //删除之前的
                $("#"+supplierfile.business_file).click();
                //赋值,展示
                supplierfile.business_file=fileId;
                $("#licenceBusinessUrl").val(fileId);
            }else if(btnFlag.wholesale_btn){
                //删除之前的
                $("#"+supplierfile.wholesale_file).click();
                //赋值,展示
                supplierfile.wholesale_file=fileId;
                $("#licenceWholesaleUrl").val(fileId);
            }
            //上传成功后去掉遮罩,提示上传成功
            layer.msg("上传成功！",{icon:1});
        }
    };
    $(function() {
        var option={
            file_ids:"", //文件id集合
            multi_selection:false,	//对选功能
			max_file_size:"20mb",   //文件大小
            prevent_duplicates:false,//重复选择
            mime_types:"png,gif,jpg",//文件类型
            btn_class:"ds-btn ds-btn-default mr10 ds-btn-upload", //按钮样式
            table_class:"hidden"//table样式
        };
        $("#testDiv").load("/doc/index",option,function(){

            //自己的上传按钮
            $("#charter_upload").bind("click",function(){
                if($("#charter_name").val()!=""&&$("#charterUrl").val()==""){
                	$("#start_upload").click();
                	//点击上传后,需要一个遮罩效果
                	layer.msg("正在上传,请稍后",{icon:0});
                }
            });
            $("#logo_upload").bind("click",function(){
                if($("#logo_name").val()!=""&&$("#logoUrl").val()==""){
                	$("#start_upload").click();
                	//点击上传后,需要一个遮罩效果
                	layer.msg("正在上传,请稍后",{icon:0});
                }
            });
            $("#business_upload").bind("click",function(){
                if($("#business_name").val()!=""&&$("#licenceBusinessUrl").val()==""){
                	$("#start_upload").click();
                	//点击上传后,需要一个遮罩效果
                	layer.msg("正在上传,请稍后",{icon:0});
                }
            });
            $("#wholesale_upload").bind("click",function(){
                if($("#wholesale_name").val()!=""&&$("#licenceWholesaleUrl").val()==""){
                	$("#start_upload").click();
                	//点击上传后,需要一个遮罩效果
                	layer.msg("正在上传,请稍后",{icon:0});
                }
            });
        });
    });

*/

$(document).ready(function(){
	$.post("/doc/index");
    //选完文件后处理
    $("#charter_brower").bind("change",function(){
    	var fileName=$(this).val();
    	fileName=fileName.replace("C:\\fakepath\\","");
    	var nameArr=fileName.split(".");
    	var dataId=$("#loginId").val();
    	$("#charter_name").val(fileName);
    	$("#charterUrl").val("");
    	$(this).prev().val("{'directory':'/gdsalt/supplier/"+dataId+"','fileName':'charter_brower."+nameArr[1]+"'}");
    });
    $("#logo_brower").bind("change",function(){
    	var fileName=$(this).val();
    	fileName=fileName.replace("C:\\fakepath\\","");
    	var nameArr=fileName.split(".");
    	var dataId=$("#loginId").val();
    	$("#logo_name").val(fileName);
    	$("#loginUrl").val("");
    	$(this).prev().val("{'directory':'/gdsalt/supplier/"+dataId+"','fileName':'logo_brower."+nameArr[1]+"'}");
    });
    $("#business_brower").bind("change",function(){
    	var fileName=$(this).val();
    	fileName=fileName.replace("C:\\fakepath\\","");
    	var nameArr=fileName.split(".");
    	var dataId=$("#loginId").val();
    	$("#business_name").val(fileName);
    	$("#licenceBusinessUrl").val("");
    	$(this).prev().val("{'directory':'/gdsalt/supplier/"+dataId+"','fileName':'business_brower."+nameArr[1]+"'}");
    });
    $("#wholesale_brower").bind("change",function(){
    	var fileName=$(this).val();
    	fileName=fileName.replace("C:\\fakepath\\","");
    	var nameArr=fileName.split(".");
    	var dataId=$("#loginId").val();
    	$("#wholesale_name").val(fileName);
    	$("#licenceWholesaleUrl").val("");
    	$(this).prev().val("{'directory':'/gdsalt/supplier/"+dataId+"','fileName':'wholesale_brower."+nameArr[1]+"'}");
    });
    $("#fixedPoint_brower").bind("change",function(){
    	var fileName=$(this).val();
    	fileName=fileName.replace("C:\\fakepath\\","");
    	var nameArr=fileName.split(".");
    	var dataId=$("#loginId").val();
    	$("#fixedPoint_name").val(fileName);
    	$("#fixedPointUrl").val("");
    	$(this).prev().val("{'directory':'/gdsalt/supplier/"+dataId+"','fileName':'fixedPoint_brower."+nameArr[1]+"'}");
    });
    //自己的上传按钮
    $("#charter_upload").bind("click",function(){
    	supplierUploadFile($(this));
    });
    $("#logo_upload").bind("click",function(){
    	supplierUploadFile($(this));
    });
    $("#business_upload").bind("click",function(){
    	supplierUploadFile($(this));
    });
    $("#wholesale_upload").bind("click",function(){
    	supplierUploadFile($(this));
    });
    $("#fixedPoint_upload").bind("click",function(){
    	supplierUploadFile($(this));
    });
});
function supplierUploadFile(obj){
	var fileName=obj.prev().prev().prev().val();
 	if(fileName!=""){
 		var file = fileName;
 		var sss = file.split(".");
 		var zf = sss[sss.length-1].toUpperCase();
 		if(zf=="PNG"||zf=="JPG"||zf=="PDF" || zf=="DOC" || zf=="DOCX"){
 			layer.msg("正在上传,请稍后",{icon:0});
 			$("input[name='custom_upload_data']").removeAttr("name");
 			$("input[name='file']").removeAttr("name");
 			obj.next().next().next().attr("name","file");
 			obj.next().next().attr("name","custom_upload_data");
 			var options = {
 			    url: '/doc/uploadFile',
 			    clearForm:false,
 			    resetForm:false,
 			    success: function(result) {
 			    	if(result!="error"){
     			    	var json=parseJsonPre(result);
     			    	layer.msg("上传成功！",{icon:1});
     			    	obj.prev().prev().val(json.now_id);
 			    	}else{
 			    		layer.msg("上传失败！",{icon:2});
 			    	}

 			    } 
 			};
 			
 			$("#supplierForm").ajaxSubmit(options); 
 		}else{
		    	layer.msg("附件格式只能是png或jpg", {
			            icon: 0
			        });
 		}
 	}

}

function parseJsonPre(str){
 	var startPre=str.substring(0,5)
 	if(startPre.toLocaleUpperCase()=="<pre>".toLocaleUpperCase()){
 		str=str.substring(5,str.length-6);
 	}
 	str=jQuery.parseJSON(str);
 	return str;
 }