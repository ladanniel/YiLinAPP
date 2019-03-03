 
<div data-role="page" class="jqm-demos ui-responsive-panel" id="panel-responsive-page1" data-title="欢迎使用雅酩酒业销售系统" data-url="panel-responsive-page1">
	<script src="http://api.map.baidu.com/components?ak=3cb84e2962257581676de834ef448e3b&v=1.0"></script>
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
		#golist {display: none;}
		@media (max-device-width: 800px){#golist{display: block!important;}}
	</style>
	<style>
		/*用户头像*/
		.ui-icon-user:after {
			background-image: url("${WEB_PATH }/resources/upload/images/headportrait/defult/user-small.png");
		}
		/* Listview with collapsible list items. */
	    .ui-listview > li .ui-collapsible-heading {
	      margin: 0;
	    }
	    .ui-collapsible.ui-li-static {
	      padding: 0;
	      border: none !important;
	    }
	    .ui-collapsible + .ui-collapsible > .ui-collapsible-heading > .ui-btn {
	      border-top: none !important;
	    }
	    /* Nested list button colors */
	    .ui-listview .ui-listview .ui-btn {
	    	background: #fff;
	    }
	    .ui-listview .ui-listview .ui-btn:hover {
	    	background: #f5f5f5;
	    }
	    .ui-listview .ui-listview .ui-btn:active {
	    	background: #f1f1f1;
	    }
	    /* 用户头像. */  
		.author-info {
			position: relative; 
			overflow: hidden;
			zoom: 1
		}
		.author-info .author-portrait {
			width: 60px;
			height: 60px;
			margin: 0
		}
		.author-info .author-portrait-container {
			position: relative;
			display: block;
			width: 60px;
			height: 60px
		}
		.author-info .portrait-cover {
			position: absolute;
			width: 60px;
			height: 60px;
			background: url("${WEB_PATH }/resources/style/images/fgdgdgd.png") no-repeat;
		_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="http://img.baidu.com/img/iknow/exp/uc/uc_avatar_masker_32.png");
			_background: 0
		}
		.author-info .info {
		    float:left;  
			margin-left: 126px;
			padding-right: 60px
		}
		.grid-731{
		   float:right; 
		} 
		
		
	</style>
	<script>
	$(document).ready(function() { 
		 $('#exituser').click(function(){
			location.href = "${WEB_PATH }/webapp/ymapp/logout.do";
		 });
	});
	</script> 
    <div data-role="header">
        <h1>欢迎使用雅酩酒业销售系统</h1>
        <a href="#nav-panel" data-icon="user" data-iconpos="notext">菜单</a> 
        <a href="#" class="ui-btn ui-shadow ui-corner-all ui-icon-delete ui-btn-icon-notext ui-btn-inline" id="exituser">退出</a>
    </div><!-- /header -->
    <div role="main" class="ui-content">  
		<h1>内容</h1>
        
    </div><!-- /content -->
    
    <div data-role="footer" data-position="fixed" data-fullscreen="true">
        <div data-role="navbar">
	      <ul>
	        <li><a href="#">消息</a></li>
	        <li><a href="#" class="ui-btn ui-shadow ui-corner-all ui-icon-arrow-u ui-btn-icon-top">联系人</a></li>
	        <li><a href="#">动态</a></li>
	      </ul>
	    </div>
    </div>
    <div data-role="panel" data-display="push" data-theme="a" id="nav-panel">
        <ul data-role="listview">
           <table>
				<tr>
					<td>
					<div class="grid-730 left author-info">
					  <div class="author-portrait left"> <a href="" class="author-portrait-container">-
					    <div class="portrait-cover"></div>
					    <img src="${WEB_PATH }/resources/upload/images/headportrait/defult/user-big.png" width="60px" height="60px" alt="ps_小p老师"> </a>  
						</div>
					</div>
					<td> 
					<td > 
					    <div style="font-size:20px;font-weight:bold;">${user.name}</div>
					    <div style="font-size:10px;">${user.roleNames!}</div> 
					</td>
				</tr>
			</table>
        	<#list moduleList as module>
				<li data-role="collapsible" data-inset="false" data-iconpos="right"> 
		          <h3>${module.text}</h3> 
		          <ul data-role="listview">
		            <#list module.pidList as modules>
		            <li><a href="#">${modules.text}</a></li> 
		            </#list> 
		          </ul>
	
		        </li>
			</#list> 
			
        </ul>
     </div> 
