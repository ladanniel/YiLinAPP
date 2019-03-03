<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>易林物流平台 - 主页</title>
    <meta name="keywords" content="易林物流平台 ">
    <meta name="description" content="易林物流平台 ">
    <#import "/common/import.ftl" as import>
	<@import.systemImportCss/>
     
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper"> 
        <@import.leftMenuImportHtml/>  
         <!--右侧部分开始-->
         <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                       <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                       	<i class="fa fa-bars"></i> 
                       </a> 
                    </div> 
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" id="main_title" data-id="<#if user.authentication?string == "notAuth">${WEB_PATH }/system/admin/helpinfo.do<#else>${WEB_PATH }/system/admin/main.do</#if>"><#if user.authentication?string == "notAuth">系统介绍<#else>首页</#if></a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="${WEB_PATH }/system/user/logout.do" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="yiling_iframe" id="yiling_iframe" width="100%" height="100%" src="<#if user.authentication?string == "notAuth">${WEB_PATH }/system/admin/helpinfo.do<#else>${WEB_PATH }/system/admin/main.do</#if>" frameborder="0" data-id="<#if user.authentication?string == "notAuth">${WEB_PATH }/system/admin/helpinfo.do<#else>${WEB_PATH }/system/admin/main.do</#if>" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2014-2015 </div>
            </div>
        </div>
        <!--右侧部分结束--> 
        <@import.systemImportScript/>
    </div>
</body>

</html>