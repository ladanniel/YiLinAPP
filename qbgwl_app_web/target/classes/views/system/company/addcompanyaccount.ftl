<style>
    	.company:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "商户信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.company {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		}
		.account:after {
		    background-color: #F5F5F5;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px 0 4px 0;
		    color: #9DA0A4;
		    content: "账户信息";
		    font-size: 12px;
		    font-weight: bold;
		    left: -1px;
		    padding: 3px 7px;
		    position: absolute;
		    top: -1px;
		}
		.account {
		    margin-left: 0px;
		    padding:40px 15px 0px;
		    border: 1px solid #DDDDDD;
		    border-radius: 4px;
		    position: relative;
		    word-wrap: break-word;
		    margin-top: 10px;
		}
		 
    </style>
<div class="modal inmodal" id="userModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: block; padding-left: 6px;">
        <div class="modal-dialog" style="width: 85%;">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" style="margin-top: -20px;"><span aria-hidden="true" id="close-x">&times;</span><span class="sr-only" >关闭</span>
                    </button>
                    <h1 class="modal-title">商户信息添加</h1>
                    <div class="modal-body" style="max-height:450px;overflow-y: auto;"> 
                        <form class="m-t" id="addCompanyInfo">
	                        <div style="" class="company ui-sortable">
	                    		 <div class="row">
	                    		 	<div class="col-sm-4"> 
		                                <div class="form-group">
		                                    <label>商户名称：</label>
		                                    <input id="text" name="name" id="name" minlength="2" type="text"  class="form-control" placeholder="请输入商户名称">
		                                </div>
		                            </div>
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>商户类型：</label>
		                                    <select data-placeholder="选择商户类型" id="companyType" name="companyTypeId" class="chosen-select form-control" >
			                                    <@companyTypeInfo isreg = "true">
				                    	 			<#list companyTypeViews as companyType >
			                                    		<option value="${companyType.id}" hassubinfo="true">${companyType.name}</option>
			                                     	</#list>
									 			</@companyTypeInfo>
			                                </select>
		                                </div> 
		                            </div>
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>地区：</label>
		                                    <input id="areaFullName_id" name="areaFullName" class="form-control" readonly type="text" value="" >
											<input id="area_id_id" name="area_id" type="hidden" >
		                                </div>
		                            </div> 
		                         </div>
		                         <div class="row">
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>联系人：</label>
		                                    <input  name="contactName" id="contactName" minlength="2" type="text"  class="form-control" placeholder="请输入联系人">
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>联系电话：</label>
		                                    <input   name="contactTel" id="contactTel" minlength="2" type="text"  class="form-control" placeholder="请输入联系电话" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
		                                </div>
		                            </div> 
		                            <div class="col-sm-4">
		                                <div class="form-group">
		                                    <label>地址：</label>
		                                    <input  name="address" id="address" minlength="2" type="text"  class="form-control" placeholder="请输入地址">
		                                </div>
		                            </div>  
		                        </div>
		                    </div>
	                    </form> 
	                    <form class="m-t" id="addAccountInfo">
		                    <div style="" class="account ui-sortable">
	                    		 <div class="row">
		                            <div class="col-sm-3">
		                                <div class="form-group">
		                                    <label>账号：</label>
		                                    <input  name="account" id="account" minlength="2" type="text"  class="form-control" placeholder="请输入账号">
		                                </div> 
		                            </div>
		                             <div class="col-sm-3"> 
		                                <div class="form-group">
		                                    <label>密码：</label>
		                                    <input  name="password" id="password"  type="password"  class="form-control" placeholder="请输入密码">
		                                </div>
		                            </div>
		                            <div class="col-sm-3">
		                                <div class="form-group">
		                                    <label>确认密码：</label>
		                                    <input id="text" name="confirm_password" id="confirm_password"  type="password"  class="form-control" placeholder="请输入密码">
		                                </div>
		                            </div> 
		                            <div class="col-sm-3">
		                                <div class="form-group">
		                                    <label>手机号码：</label>
		                                    <input id="phone" name="phone"  type="text" class="form-control" placeholder="请输入手机号" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
		                                </div>
		                            </div>  
		                        </div>
		                    </div>
	                    </form> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary btn-outline" id="saveBut"><i class="fa fa-check"></i>&nbsp;&nbsp;保存</button>
                        <button type="button" class="btn btn-white btn-outline" data-dismiss="modal" id="close-but">取消</button>
                    </div>
                </div>
            </div>
        </div> 
    </div>
 <script>
 $(function(){ 
    $("#companyType").chosen({width:'100%'}); 
    $('#areaFullName_id').citypicker({areaId:'area_id_id'}); 
    $('#contactTel').blur(function() { 
		$("#phone").val($('#contactTel').val());
	}); 
    var $addCompanyInfo =  $('#addCompanyInfo').validate({
		  rules: {
		    name: {
		      required: true, 
		      minlength: 3,
		      maxlength:20,
		      remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/aut/authenticat/checkCompanyName.do" 
              }
		    },
		    companyType: {
		      required: true
		    },
		    contactName: {
		      required: true,
		      isChinese:true
		    },
		    contactTel: {
		      required: true,
		      isMobile: true,
		      maxlength:11,
		      remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/system/user/checkContactTel.do",
              }
		    },
		    areaFullName:{
		      required: true
		    },
		    address: {
		      required: true
		    }
		  },
		  messages: {
            name: {
                remote:e+"商户名已存在，请重新输入！"
            }
          },
		  errorPlacement: function(error, element) {
		  		if(element.get(0).id == "areaFullName_id"){
            		$('#areaFullName_id').citypicker("setError");
            	}
            	element.before(error) ;
          }
	});
	var $addAccountInfo =  $('#addAccountInfo').validate({
		  rules: {
		    account: {
		     	required: !0,
                userName:true,
                remote:{
                	  type:"POST",
                      url:"${WEB_PATH }/system/user/checkName.do",
                }
		    },
		    password: {
                required: !0,
                minlength: 6,
                maxlength:12,
                isPasword:true
            },
            confirm_password: {
                required: !0,
                minlength: 6,
                equalTo: "#password"
            },
		    phone: {
                required: !0,
                isMobile: !0,
                isInteger:true,
                remote:{
              	  type:"POST",
                  url:"${WEB_PATH }/system/user/checkPhone.do",
                }
            }
		  },
		  messages: {
            account: {
                required: e + "请输入您的用户名",
                minlength: e + "用户名必须6个字符以上",
                maxlength: e + "用户名必须小于12个字符",
                remote:"用户名重复"
            },
            password: {
                required: e + "请输入您的密码",
                minlength: e + "密码必须6个字符以上",
                maxlength: e + "密码必须小于12个字符",
            },
            confirm_password: {
                required: e + "请再次输入密码",
                minlength: e + "密码必须6个字符以上",
                equalTo: e + "两次输入的密码不一致"
            },
            phone: {
                required: e + "请输入您手机号",
                remote:"手机号已被注册"
            }
          },
          errorPlacement: function(error, element) {
            	element.before(error) ;
          }
          
	});
    //关闭层
    $('#close-but').click(function(){
   		$('#userModal').remove();
    }); 
    $('#close-x').click(function(){
   		$('#userModal').remove();
    }); 
    $('#saveBut').click(function(){  
	   	 var isfalse = validatCompanyAccountInfo();
	   	 if(isfalse){
	   	 	saveAccountCompany();
	   	 }
	}); 
	$('#noSaveBut').click(function(){  
	   	  var id = $("#company_id").val();  
		  layer.prompt({title: '请填写不通过原因', formType: 2}, function(text){
		  	  $('#noSaveBut').attr("disabled",true);  
		      saveAutCompany(id,false,text,$('#noSaveBut'))
		  }); 
	   	  
	}); 
	
});

function saveAccountCompany(){
    layer.msg('加载中', {icon: 16});
    var addCompanyInfo = $("#addCompanyInfo").serializeJson();
	var addAccountInfo = $("#addAccountInfo").serializeJson();
	accountCompanyInfo=new Object();
	accountCompanyInfo.company_info= JSON.stringify(addCompanyInfo);
	accountCompanyInfo.account_info=JSON.stringify(addAccountInfo);
	accountCompanyInfo.type = "save";
	$.yilinAjax({
	  	 type:'POST',
	  	 url:'${WEB_PATH }/system/company/accountCompanySave.do',
	  	 data:accountCompanyInfo,
		 errorcallback:null,
		 btn:null,
		 successcallback:success
	 });
}  
function success(result){  
	layer.closeAll(); 
	if(result.success == true){
		$('#userModal').remove();
    	layer.confirm( result.msg, {
    	  icon: 3, 
    	  title:'信息提示',
		  btn: ['增加认证信息','关闭'] //按钮
		}, function(){
		  	$("#company_win").load("${WEB_PATH}/system/company/addcompanyaudtinfo.do?companyId="+result.companyId);
		  	layer.closeAll(); 
		}, function(){
		  	$("#exampleTableEvents").bootstrapTable('refresh');
		});
    	
	}else{
		swal("操作失败", result.msg, "error");
		$('#saveBut').attr("disabled",false);  
	}
} 
function validatCompanyAccountInfo(){
	var $valid_addCompanyInfo = $("#addCompanyInfo").valid();
	var $valid_addAccountInfo = $("#addAccountInfo").valid();
	if($valid_addCompanyInfo){
		if($valid_addAccountInfo){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
	 
}
  </script>   
