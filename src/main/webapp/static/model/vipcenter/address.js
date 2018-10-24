/**
 * Created by lovercy on 1/12/2016.
 */
require.config(config);
require(["jquery","validate","base","animation","layer","jtemp","distpicker","ChineseDistricts"],function($,validate,base,animation,layer,distpicker,cityData){
    var layerIndex=null;
    var addrNum=0;
    function filterCityData(){
        var filterData={};
        for(prop in cityData){
            var cityObj=cityData[prop];
            for(code in cityObj){
                filterData[code]=cityObj[code];
            }
        }
        return filterData;
    }

    var dataList=filterCityData();

    function showData() {
        $.ajax({
            url: "/delivery/address/query",
            type: "get",
            dataType: "json",
            success: function (page) {
                if(page && page.data){
                    addrNum=page.data.length;
                }
                $("#addressList").setTemplateElement("tableTemplateTxt");
                $("#addressList").processTemplate(page);
            },
            error: function () {
                layer.msg("获取收货地址信息失败!", {icon: 2});
            }
        });
    }

    function showDetail(addressId){
        $.ajax({
            url:"/delivery/address/detail",
            type:"get",
            data:{addressId:addressId},
            dataType:"json",
            success:function (data) {
                if(data && data.result==1){
                    var item=data.data;
                    $("#addressId").val(item.addressId);//收货地址Id
                    $("#distpicker").distpicker({
                        province:item.province,
                        city:item.city,
                        district:item.area
                    });//省市县
                    $("#contact-address-edit").val(item.address);//地址
                    $("#contact-tel-postalcode").val(item.postCode);//邮编
                    $("#contact-name-edit").val(item.name);
                    $("#contact-tel-edit").val(item.phone);
                    $("#areaCode").val(item.areaCode);
                    $("#telephone").val(item.telephone);
                    if(item.isDefault && item.isDefault==1){
                        $("#isDefault")[0].checked=true;
                    }else{
                        $("#isDefault")[0].checked=false;
                    }
                }else{
                    layer.msg("获取收货地址失败!",{icon:2},function(){
                        layer.close(layerIndex);
                    })
                }
            }
        })
    }

    function _submitAddr(){
        var flag = true;
        $("#address-edit-form [name]").each(function() {
            if(!checkForm($(this))){
                $(this).focus();
                flag = false;
                return false;
            }
        });
        var phone=$("#contact-tel-edit").val();
        var telephone=$("#telephone").val();
        if((phone==undefined || phone==null || phone=="") && (telephone==undefined || telephone==null || telephone=="")){
            $("#phoneSpan").text("手机号码、电话号码必须选填一项");
            flag =false;
            return false;
        }else{
            $("#phoneSpan").text("");
        }
        if(flag){
            var params=base.serializeObject($("#address-edit-form"));
            var isDefault=0;
            if($("#isDefault")[0].checked){
                isDefault=1;
            };
            params.isDefault=isDefault;
            $.ajax({
                url:"/delivery/address/edit",
                type:"post",
                data:{params:JSON.stringify(params)},
                dataType:"json",
                success:function(data){
                    if(data && data.result==1){
                        layer.close(layerIndex);
                        layer.msg("操作成功!",{icon:1},function(){
                            showData();
                        });
                    }else{
                        layer.msg("操作失败!",{icon:2});
                    }
                },
                error:function(){
                    layer.msg("操作失败!",{icon:2});
                }
            })
        }
    }

    $(function(){
        showData();
        /**
         * 新增收货地址按钮
         */
        $("#add_btn").on("click",function(){
           if(addrNum>=5){
               layer.msg("每位会员只能添加五个收货地址!",{icon:0});
           }else{
               layerIndex=layer.open({
                   type: 1,
                   id: 1,
                   area: ["580px", "580px"],
                   content: $("#address-edit-wrap"),
                   success:function(){
                       $("#distpicker").distpicker();
                   },
                   scrollbar:false,
                   cancel :function(){
                       $("#distpicker").distpicker("destroy");
                   }
               });
           }
        });

        /**
         * 编辑按钮
         */
        $(document).on("click",".edit_btn",function(){
            var addressId=$(this).attr("data-value");
            layerIndex=layer.open({
                type: 1,
                id: 1,
                area: ["580px", "580px"],
                content: $("#address-edit-wrap"),
                success:function(){
                    showDetail(addressId);
                },
                scrollbar:false,
                cancel :function(){
                    $("#distpicker").distpicker("destroy");
                }
            });
        });

        /**
         * 设置为默认
         */
        $(document).on("click",".set_default",function() {
            var addressId = $(this).attr("data-value");
            var params={
                addressId:addressId,
                isDefault:1
            }
            $.ajax({
                url:'/delivery/address/default',
                type:'post',
                data:{params:JSON.stringify(params)},
                dataType:'json',
                success:function(data){
                    if(data && data.result==1){
                        layer.msg("操作成功!",{icon:1},function(){
                            showData();
                        })
                    }else{
                        layer.msg(data.message,{icon:2});
                    }
                },
                error:function(){
                    layer.msg("操作失败!",{icon:2});
                }
            })
        });
        /**
         * 失去交点验证
         */
        $("#address-edit-form [name]").blur(function(){
            checkForm($(this));
        });

        /**
         * 保存收货地址
         */
        $("#js-save-btn").on("click",function(){
            _submitAddr();
        })

    })

})
