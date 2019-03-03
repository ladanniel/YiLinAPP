
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">

<meta charset="utf-8">

<title>易林物流平台</title> 
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/ucenter.css"/>
	<#import "/common/import.ftl" as import>
    <@import.systemImportCss/> 
    <@import.tableManagerImportScript/> 
    <link rel="stylesheet" href="${WEB_PATH}/resources/css/plugins/ladda/ladda-themeless.min.css">
   
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/layout_head2880f5.css"/>
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/base2d5cf1.css"/>
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/lib2968da.css"/>
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/check_email2957c2.css"/>
     
     
    <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
    
	<script src="${WEB_PATH}/resources/js/plugins/ladda/spin.min.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/ladda/ladda.min.js"></script>
	
	<script src="${WEB_PATH}/resources/js/account/reset.js"></script>
	
	<style type="text/css">
		.frm_label {
			  width: 4em;
		}
	</style>
    </head>
    <body class="zh_CN">
    <div class="head" id="header">

<div class="head_box">
    <div class="inner wrp">
        <h1 class="logo"><a href="#" title="易林物流平台"></a></h1>
        <div class="account">
                   <div class="account_meta account_faq">
                                                       <a href='${WEB_PATH}/system/user/logout.do'>登录</a><i class="account_line">|</i><a href="http://kf.qq.com/faq/120911VrYVrA1509086vyumm.html" target="_blank">使用帮助</a>
                        </div>                 
       </div>
</div>
                    
</div>

        </div>
        <div id="body" class="body page_simple reset_password">
            <div class="container_box">
                
<div class="main_hd">
    <h2>修改密码</h2>
</div>

<div class="processor_wrp js_process">
</div>
<div class="main_bd">
	<div class="main_bd">
    <form class="form email_form" id="js_form" method="POST"  action="${WEB_PATH}/account/register/restPas.do" novalidate="novalidate">
        <div class="email_form_inner">
            <p class="page_global_tips"></p>
            <div class="frm_control_group">
                <label class="frm_label" for="">密码</label>
                <div class="frm_controls">
                    <span>
                        <input type="password" class="form-control" id="password" name="password">
                    </span><p class="frm_msg fail" style="display: block;"><span for="js_email" class="frm_msg_content" style="display: inline;"></span></p>
                    <p class="frm_tips">
                      
                    </p>
                </div>
            </div>
            <div class="frm_control_group">
                <label class="frm_label" for="">确认密码</label>
                <div class="frm_controls">
                    <div id="js_verifycodeImgArea"><div class="verifycode">
	<span><input name="confirm_password"  type="password" value="" class="form-control"></span><p class="frm_msg fail" style="display: block;"><span for="imgcode" class="frm_msg_content" style="display: inline;"></span></p>
</div></div>
                </div>
            </div>
        </div>
        <div class="tool_bar border tc">
         <button class="btn btn-primary ladda-button" data-style="expand-right" type="button" onclick="submitForm()"><span class="ladda-label">   确认     </span></button>
         <script>
         	function submitForm() {
         		if($("#js_form").valid()) {
         			var action =  $("#js_form").attr("action");
					$.ajax({
						  type: 'POST',
						  url: action,  
						  data: $('#js_form').serialize(),
						  dataType: "json",
							 success:function(result){  
								if(result.success == true){
									swal("", result.msg, "success");
									setTimeout(function(){
										location.href = "${WEB_PATH }/system/user/logout.do";
									},3000);
								}else{
									location.href = "${WEB_PATH }".result.msg;
								}
							}
						});
         		}
         		
			}
         </script>
        </div>
    </form>
</div>
</div>

            </div>
        </div>
   <!-- 前台页面底部-->
    <@import.webFoot/> 
   <!-- 前台页面底部-->	
    </body>

</html>

