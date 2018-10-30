require.config(config);
require(["jquery", "animation","layer" ], function($,animation,layer) {
	var question="";
	var questionnaireform="";
	function getQueryString(name) {
       var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");       
       var r = window.location.search.substr(1).match(reg);           
         if(r!=null){
         // alert(unescape(r[2]));
            return  unescape(decodeURI(r[2]))
         }
        return null;
     }
	function int2cn(num) {
        var nums = ["零","一","二","三","四","五","六","七","八","九"];
        return nums[parseInt(num)];;
      }
	//根据需求调研ID查询问题列表
	function qustionListInfo(questionId){
		$.post("/portal/questionById?id="+questionId,{},function(result){
			  var data = result.data;
	          var listInfo= "";
	          if(result.errorCode==0){
	        	  var tempQuestion=result.data;
	        	  localStorage.setItem( "questionList", JSON.stringify(tempQuestion) );
	        	  if(tempQuestion.length>0){
	        		  var number=0;
	        		  $.each(tempQuestion,function(index,element){
	        			  if(index==0){
	        				  listInfo+="<li class='questions-wrap' id='"+element.id+"'"
	        				  number= index+1;
	        				  var maxNumber = int2cn(index+1);
	        				  listInfo+="<h1 class='question-type' id='"+element.id+"'>"+maxNumber+"、"+element.questionType+"</h1>"
	        			  }else if(tempQuestion[index-1].questionType!=element.questionType){
	        				  listInfo+="<li class='questions-wrap' id='"+element.id+"'"
	        				  var maxNumber = int2cn(number+1);
	        				  number=number+1
	        				  listInfo+="<h1 class='question-type' id='"+element.id+"'>"+maxNumber+"、"+element.questionType+"</h1>"
	        			  }
	        			  var list =element.questionContentVoList;
	        			  if(list.length>0){
	        				  listInfo+="<li id='"+element.id+"'>"
	        				  listInfo+="<div class='question-name' id='"+element.id+"'>"+element.questtionName+"</div>"
	        				  $.each(element.questionContentVoList,function(i,ele){
	        					  var type=element.questionChoose;
	        					  if(type=="0"){
	        						  listInfo+="<ul class='option-list'>"
	        						 
	        						  listInfo+="<li id='"+ele.id+"'>"
	        						  listInfo+="<input type='radio' id='"+ele.id+"' name='questionOption-"+ele.pid+"' value='"+ele.chooseName+"'>"+ele.chooseName+"</input></li></ul>"
	        					  }else{
	        						  listInfo+="<ul class='option-list'>"	 	        						 
	        						  listInfo+="<li id='"+ele.id+"'>"
	        						  listInfo+="<input type='checkbox' id='"+ele.id+"' name='questionOptionCheckbox-"+ele.pid+"' value='"+ele.chooseName+"'>"+ele.chooseName+"</input></li></ul>"
	        					  }
	        					 
	        					  
	        					  listInfo+="</li>"
	        				  })
	        				  
	        			  }
	        			  listInfo+="</li>"
	        		  })
	        		  
	        	  }
	              $("#question").html(listInfo);
	          }
		})
		
		
	}

	function save(){
		var arrQuestion = JSON.parse(localStorage.getItem( "questionList"))
		if(arrQuestion.length>0){
			$.each(arrQuestion,function(index,element){
				if(element.questionChoose==1){	
					var id_array=new Array(); 
					$("input[name='questionOptionCheckbox-"+element.id+"']:checked").each(function(){
						id_array.push($(this).val()) 
					})
					element["questionOption"]=id_array;
				}else{
					var text =  $("input[name='questionOption-"+element.id+"']:checked").val()
					element["questionOption"]=text;
				}
				
			})
		}
		
		$.post("/portal/saveQuestion?surveyId="+question,{"params":JSON.stringify(arrQuestion)},function(result){
			if(result.code==="1"){
				layer.msg('操作成功', {
					icon: 1
				});
            	jumbto("/salt/questionlist");
            }else{
            	layer.msg('操作失败', {
					icon : 2
				});
            }
		})
	}
	function formInfo(questionnaireform){
		var obj = JSON.parse(questionnaireform);
		$("#forDepartment").val(obj["forDepartment"]);
		$("#paperCode").val(obj["paperCode"]);
		$("#startTime").val(obj["startTime"]);
		$("#endTime").val(obj["endTime"]);
		$("#paperName").val(obj["paperName"]);
		$("#paperRemark").val(obj["paperRemark"]);
		$("#forObject").val(obj["forObject"]);
		console.log("obj",obj);
	}
	$(function() {
		question = getQueryString("id")
		questionnaireform = getQueryString("questionnaireform")
		qustionListInfo(question);
		formInfo(questionnaireform);
		console.log("question",question)
		$("#save").click(save);
	})
})