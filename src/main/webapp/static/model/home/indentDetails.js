require.config({
	baseUrl:"../js",
	paths:{
		"jquery":"../common/jquery-1.8.3.min",
		"Vue":"../common/vue",
		"path":"path",
		"animation":"animation",
		"validator":"../common/vue-validator.min",
		"tab":"tab"
	}
});
require(["jquery","path","Vue","animation","validator","tab"],function($,path,Vue,animation,validator,tab){
	var picPath=path.picPath;
	
	$(function(){
		animation.pageLoad({});//预留动画过渡，后期优化
		$.ajax({
			url:"../data/indentDetail.json",
			type: 'POST',
            dataType: "json",
            success:function(data){
            	var contactItems=data.contact;
            	var contact = new Vue({
            		el:"#contact-info",
            		data:{
            			items:contactItems
            		},
            		filters:{
            			telFilter:function(val){
            				return val.substring(0,3)+"****"+val.substring(6,10);
            			}
            		},
            		methods:{
            			editContact:function(item){
            				layer.open({
            					
            				})
            			},
            			delContact:function(item){
            				/*$.ajax({
            					url:null,
            					type:"POST",
            					param:{contactId:item.id},
            					success:function(data){
            						
            					}
            				})*/
            				removeItem(contactItems,item.id,"id");
            				function removeItem(array,itemId,attrName){
            					if(array){
            						var index=-1;
            						for(var i=0,len=array.length;i<len;i++){
            							if(array[i][attrName]==itemId){
            								index=i;
            							}
            						}
            						if(index!=-1){
            							array.splice(index,1);
            						}
            					}
            				}
            			}
            		}
            	})
            }
		})
	})
})