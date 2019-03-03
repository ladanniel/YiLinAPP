
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
	
	<script src="${WEB_PATH}/resources/js/account/resetpwd.js"></script>
	
	
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
    <h2>找回密码</h2>
</div>

<div class="processor_wrp js_process">
</div>
<div class="main_bd">
	<div class="main_bd">
    <form class="form email_form" id="js_form" action="return:false;" novalidate="novalidate">
        <div class="email_form_inner">
            <p class="page_global_tips">您正在使用手机验证身份，请完成以下操作</p>
            <div class="frm_control_group">
                <label class="frm_label" for="">手机号</label>
                <div class="frm_controls">
                    <span>
                        <input type="text" class="form-control" id="phone" name="phone">
                    </span><p class="frm_msg fail" style="display: block;"><span for="js_email" class="frm_msg_content" style="display: inline;"></span></p>
                    <p class="frm_tips">
                      
                    </p>
                </div>
            </div>
                 <div class="frm_control_group">
              	 <label class="frm_label" for=""></label>
                <div id="code_controls" class="frm_controls">
                    <div id="js_verifycodeImgArea"><div class="verifycode">
						<span><button type="button" id="send" class="btn btn-primary" data-url="${WEB_PATH}">获取短信验证码</button></span><div class="ui-form-explain"></div>
					</div></div>
                </div>
            </div>
            <div class="frm_control_group">
                <label class="frm_label" for="">验证码</label>
                <div class="frm_controls">
                    <div id="js_verifycodeImgArea"><div class="verifycode">
						<span><input id="code" name="code" maxlength="6" type="text" value="" class="form-control" style="width:40%" ></span><p class="frm_msg fail" style="display: block;"><span for="imgcode" class="frm_msg_content" style="display: inline;"></span></p>
					</div></div>
                </div>
            </div>
            
            <div class="frm_control_group">
                <label class="frm_label" for=""></label>
                <div class="verifycode" style="" id="verifyDiv">
	                <span class="frm_input_box" style="border:none;width:40%;">
	                    <input id="code-img" class="form-control" name="iamgeCode" style="width:100%;margin-left:-10px;">
	                </span>
	               <img id="verifyImg" src="${WEB_PATH }/commonFtl/image.jsp" style="width: 90px;">
	               <!-- <a href="javascript:;" id="verifyChange">换一张</a>-->
	            </div>
            </div>
            
            
        </div>
        <div class="tool_bar border tc">
         <button class="btn btn-primary ladda-button" data-style="expand-right" type="submit"><span class="ladda-label">   下一步     </span></button>
            <a href="${WEB_PATH}/system/user/logout.do" id="js_return" class="btn btn_default">返回</a>
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

