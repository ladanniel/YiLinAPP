<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>发货管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
</head>

<body > 
<@goodsBaseicCount  type="statistics">
    <div style="border: 1px solid #f1f1f1;margin-bottom: 10px;border-radius: 4px;">
	<div class="forum-title" style="text-align: center;">
        <h2 style="color: red;"><i class="iconfont" style="font-size:20px;font-style:normal;" >&#xe638;</i>&nbsp;&nbsp;总货物（${goodsCount.goodscount.toolCount}）</h2>
    </div>
    <div class="forum-item active" style="border-bottom: 0px solid #f1f1f1;">
        <div class="row">
            <div class="col-sm-2 forum-info" style="color:red;font-size:14px;font-style:normal;">
                <span class="views-number">
                    ${goodsCount.goodscount.applyCount}
                </span>
                <div >
                    <i class="iconfont">&#xe618;</i>&nbsp;待审核
                </div>
            </div>
            <div class="col-sm-2 forum-info" style="color:#f8ac59;font-size:14px;font-style:normal;">
                <span class="views-number">
                    ${goodsCount.goodscount.lockCount}
                </span>
                <div >
                    <i class="iconfont" >&#xe614;</i>&nbsp;正在处理
                </div>
            </div>
            <div class="col-sm-2 forum-info" style="font-size:14px;font-style:normal;">
                <span class="views-number">
                    ${goodsCount.goodscount.backCount}
                </span>
                <div >
                    <i class="iconfont"  >&#xe615;</i>&nbsp;退回
                </div>
            </div>
            <div class="col-sm-6" >
            	<div class="col-sm-4 forum-info" style="color:#00bb9c;font-size:14px;font-style:normal;">
                    <span class="views-number">
                        ${goodsCount.goodscount.successCount}
                    </span>
                    <div>
                        <i class="iconfont" >&#xe639;</i>&nbsp;通过 
                    </div>
                </div>
                <div class="col-sm-1 forum-info">
                    <span style="font-size: 30px;">=</span>
                </div>
                <div class="col-sm-3 forum-info" style="color:#1a7bb9;font-size:14px;font-style:normal;">
                    <span class="views-number">
                        ${goodsCount.goodscount.trueCount}
                    </span>
                    <div>
                        <i class="iconfont" >&#xe637;</i>&nbsp;上线
                    </div>
                </div>
                <div class="col-sm-1 forum-info">
                    <span style="font-size: 30px;">+</span>
                </div>
                <div class="col-sm-3 forum-info" style="color:red;font-size:14px;font-style:normal;">
                    <span class="views-number">
                        ${goodsCount.goodscount.falseCount}
                    </span>
                    <div>
                         <i class="iconfont" >&#xe636;</i>&nbsp;下线 
                    </div>
                </div>
            </div>
        </div>
	</div>
	</div>
</@goodsBaseicCount>
                        		 
</body>

</html>