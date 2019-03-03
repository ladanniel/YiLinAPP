<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>银行卡管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>银行卡管理</h5>
                <div class="ibox-tools"> 
                </div>
            </div>
        </div>
        <!-- End Panel Other -->
    </div>
    <div class="wrapper wrapper-content animated fadeInRight" style="margin-top: -20px;">
        <div class="row">
            <#list list as vo>
            	<div class="col-sm-3">
	            	<div class="ibox">
	                    <div class="ibox-title" style="background:rgba(113, 140, 22, 0.46)">
	                    	<a href="javascript:void(0)" onclick="delCard('${vo.id}')"><span class="label pull-right">x</span></a>
	                    	<#if vo.hasDefault == 1>
	                    		<span class="label label-primary pull-right">默认</span>
	                    	</#if>
	                        <h5>${vo.bankName}</h5>
	                    </div>
	                    <div class="ibox-content"></br>
	                       <h4>${(vo.bankCard?substring(0,4))!}************${(vo.bankCard?substring(vo.bankCard?length-3,vo.bankCard?length))!}(<#if vo.bankType == "debitCard">储蓄卡</#if><#if vo.bankType == "creditCard">信用卡</#if>)</h4>
	                       </br>
	                    </div>
	                </div>
	            </div>
            </#list>
            <div class="col-sm-3">
                <div class="ibox">
                    <div class="ibox-title" style="background:rgba(113, 140, 22, 0.46)">
                        <h5>添加银行卡</h5>    
                    </div>
                    <div class="ibox-content" style="height:102px;text-align:center"></br>
                    	 <button class="btn btn-default btn-circle" type="button" id="addCard">
                    	 <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                         </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="win_add"></div>
    <@import.tableManagerImportScript/> 
    <script >
        //加载添加页面
        $('#addCard').click(function(){
        	if("${auth}" == "yes"){
        		$("#win_add").load("${WEB_PATH }/capital/bankcard/view/add.do");
        	}else{
        		swal("", "请实名认证后再绑定银行卡！", "error");
        	}
        });
        
        function delCard(id){
	        swal({
					    title: "删除银行卡",
					    text: "你是否要删除该绑定银行卡？",
					    type: "warning",
					    showCancelButton: true,
					    confirmButtonColor: "#DD6B55",
					    confirmButtonText: "是的，我要删除！",
					    cancelButtonText: "我点错了…",
					    closeOnConfirm: false,
					    closeOnCancel: false
				},function(isConfirm) {
				    if(isConfirm) { 
						$.ajax({
								  type: 'POST',
								  url: '${WEB_PATH }/capital/bankcard/remove.do',  
								  data: {"id":id},
								  dataType: "json",
								  success:function(result){  
										if(result.success == true){
											$('#userModal').remove();
											swal("", result.msg, "success");
											setTimeout(function () { 
										        window.location.reload();
										    }, 1000);
										}
									},
									error:function(data){
				                        alert(data.responseText);
			                       }
						});
					}else {
			            swal("已取消", "您取消了操作！", "error")
			        }
				});
        }
    </script>
</body>

</html>