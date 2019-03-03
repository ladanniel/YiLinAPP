
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">

<meta charset="utf-8">

<title>易林物流平台</title> 

	<#import "/common/import.ftl" as import>
    <@import.systemImportCss/> 
    <@import.tableManagerImportScript/> 
    <link rel="stylesheet" href="${WEB_PATH}/resources/css/plugins/ladda/ladda-themeless.min.css">
     
     
    <script src="${WEB_PATH}/resources/js/plugins/layer/layer.js"></script> 
    <script src="${WEB_PATH}/resources/js/account/register.js"></script>
    
	<script src="${WEB_PATH}/resources/js/plugins/ladda/spin.min.js"></script>
	<script src="${WEB_PATH}/resources/js/plugins/ladda/ladda.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/layout_head2880f5.css"/>
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/base2d5cf1.css"/>
	<link rel="stylesheet" type="text/css" href="${WEB_PATH}/resources/css/web/lib2968da.css"/>

    </head>
    <body class="zh_CN">
    <div class="head" id="header">
    
    <style>
    	.page_msg .msg_content {
		  padding-top: 100px;
		}
    </style>

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
    <h2>注册成功</h2>
</div>

<div class="processor_wrp js_process">
</div>
<div class="main_bd">
 <div class="regist_box mail_active" id="step2">
        <div class="page_msg simple default">
            <div class="inner">
                <div class="msg_icon_wrp"> </i>
                </div>
                <div class="msg_content">
                    <h4>
                       注册成功，欢迎注册易林物流 :
                        <span id="emailTxt"></span>
                    </h4>
                    <p>立即完善您的详细资料有助于您得到更好的服务！。</p>
                    <p class="spacing">
                    <a href="${WEB_PATH }/system/user/logout.do" id="goEmail" class="btn btn_primary">登录</a>
                    </p>
                    <p></p>
                    <ul>
                        <li>
                        </li>
                        <li>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

            </div>
        </div>
   <!-- 前台页面底部-->
    <@import.webFoot/> 
   <!-- 前台页面底部-->	
    </body>

</html>

