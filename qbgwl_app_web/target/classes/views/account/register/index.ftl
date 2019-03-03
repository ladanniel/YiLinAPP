
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

<div class="head_box">
    <div class="inner wrp">
        <h1 class="logo"><a href="#" title="易林物流平台">易林物流平台</a></h1>
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
    <h2>用户注册</h2>
</div>

<div class="processor_wrp js_process">
</div>
<div class="main_bd">
 <div class="middle-box text-center loginscreen   animated fadeInDown">
           <form id="regForm" class="form-horizontal" action="add.do" method="POST">
                        <div class="form-group">
                                    <label class="col-sm-3 control-label">商户类型：</label>
                                    
                                    <div class="col-sm-8">
                                       <select class="form-control m-b" name="companyType">
                                            <option value="">请选择</option>
                                            <#list typeList as type>
                                                <option value="${type.id}">${type.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户名：</label>

                                <div class="col-sm-8">
                                    <input name="account" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" placeholder="用户名"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-3 control-label">密码：</label>

                                <div class="col-sm-8">
                                    <input id="password" name="password"  type="password" placeholder="密码" class="form-control">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">确认密码：</label>

                                <div class="col-sm-8">
                                    <input name="confirm_password" type="password" class="form-control">
                                </div>
                            </div>
                       
                            
                           <div class="form-group">
                                <label class="col-sm-3 control-label">手机号码：</label>

                                <div class="col-sm-8">
                                    <input id="phone" name="phone"  type="tel" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label"></label>
                                <div class="col-sm-4">
                                    <input id="code" name="code"  type="number"  maxlength="6"  class="form-control">
                                </div>
                                <div class="col-sm-1">
                                    <button type="button" id="send" class="btn btn-primary" data-url="${WEB_PATH}">获取短信验证码</button>
                                </div>
                            </div>
                            
                             <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" class="checkbox" id="agree" name="agree" style="margin-top:5px;">我同意并遵守<a href="${WEB_PATH}/account/register/readtemplate.do" target="_blank" data-spm-anchor-id="a2145.7275745.0.0">《易林物流平台使用协议》</a>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-8">
                                
                                    <button id="saveBtn" class="btn btn-primary ladda-button" data-style="expand-right" type="submit"><span class="ladda-label">   注     册     </span></button>
                                </div>
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

