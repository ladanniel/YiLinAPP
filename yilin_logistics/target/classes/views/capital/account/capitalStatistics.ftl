<!DOCTYPE html>
<html> 
<head> 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>发货管理</title> 
    <link rel="shortcut icon" href="favicon.ico">
	<#import "/common/import.ftl" as import>
    <@import.tableManagerImportCss/> 
    <link href="${WEB_PATH}/resources/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <style>
    .widget.style1 h2 { 
	    font-size: 40px;
	}
	.widget.style1 h3{
	    font-size: 24px;
	}
	</style>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <!-- Panel Other -->
         <div class="row">
            <div class="col-sm-3">
                <div class="widget style1 lazur-bg">
                    <div class="row">
                        <div class="col-xs-4 text-center">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63e;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                        <#if type="system">
                        	<span>平台总资金</span>
                        	<span><#if capital.totalCapital.total?? || "" == capital.totalCapital.total>0<#else>${capital.totalCapital.total}</#if>元</span>
                        <#else>
                        	<span>总资金</span>
                        	<span><#if capital.totalCapital.total?? || "" == capital.totalCapital.total>0<#else>${capital.totalCapital.total}</#if>元</span>
                        </#if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 navy-bg">
                    <div class="row">
                        <div class="col-xs-4">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe650;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                        	<span>总充值</span>
	                        <span><#if capital.totalCapital.totalrecharge?? || "" == capital.totalCapital.totalrecharge>0<#else>${capital.totalCapital.totalrecharge}</#if>元</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 red-bg">
                    <div class="row">
                        <div class="col-xs-4">
                             <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe652;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                        	<span>总提现</span>
	                        <span><#if capital.totalCapital.totalcash??>0<#else>${capital.totalCapital.totalcash}</#if>元</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 yellow-bg">
                    <div class="row">
                        <div class="col-xs-4">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe651;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span>总转账</span>
	                        <span><#if capital.totalTransfer.money?? || "" == capital.totalTransfer.money>0<#else>${capital.totalTransfer.money}</#if>元</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-2">
                <div class="widget style1 red-bg">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;" >&#xe618;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>提现待处理</span>
	                        <span><#if capital.cashWaitProcess.total?? || "" == capital.cashWaitProcess.total>0<#else>${capital.cashWaitProcess.total}</#if>笔</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 navy-bg" style="background-color: #f8ac59;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;" >&#xe658;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>提现成功</span>
	                        <span><#if capital.cashSuccess.total?? || "" == capital.cashSuccess.total>0<#else>${capital.cashSuccess.total}</#if>笔</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 navy-bg" style="background-color: #676a6c;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont"  style="font-size:3em;font-style:normal;" >&#xe654;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>提现失败</span>
	                        <span><#if capital.cashFail.total?? || "" == capital.cashFail.total>0<#else>${capital.cashFail.total}</#if>笔</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" style="background-color: #00bb9c;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe657;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>本月充值</span>
	                        <span><#if "" == capital.monthRecharge.total || capital.monthRecharge.total??>0<#else>${capital.monthRecharge.total}</#if>笔</span>
	                        <span><#if "" == capital.monthRecharge.money || capital.monthRecharge.money??>0<#else>${capital.monthRecharge.money}</#if>元</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" style="background-color: #1a7bb9;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe656;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>本月提现</span>
	                        <span><#if "" == capital.monthCash.total || capital.monthCash.total??>0<#else>${capital.monthCash.total}</#if>笔</span>
	                       	<span><#if "" == capital.monthCash.money || capital.monthCash.money??>0<#else>${capital.monthCash.money}</#if>元</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 red-bg">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe655;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>本月转账</span>
	                       	<span><#if "" == capital.monthTransfer.total || capital.monthTransfer.total??>0<#else>${capital.monthTransfer.total}</#if>笔</span>
	                        <span><#if "" == capital.monthTransfer.money || capital.monthTransfer.money??>0<#else>${capital.monthTransfer.money}</#if>元</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>每月交易统计</h5>
                        <div class="ibox-tools">
                        	请选择年份：
                        	<select style="width:60px;"  name="year" id="year">
                        		<#list years as year>
                            		<option value="${year.id}" hassubinfo="true" <#if year.id == yearDay>selected = "selected"</#if>>${year.text}</option>
                            	</#list>
	                        </select>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                          <div id="echarts-moneyrecord-month" class="echarts" style="height: 400px"></div>
                    </div>
                </div>
            </div>
        </div>
       <!-- End Panel Other -->
    </div>
    <@import.tableManagerImportScript/> 
	<script src="${WEB_PATH}/resources/js/plugins/echarts/echarts.min.js"></script>
	<script>
	var echarts_goods_month = null;
	$(function(){
		  initGoodsMonth();
          $('#year').change(function(){ 
          	 initGoodsMonth();
          });
	});
	
	
	function initGoodsMonth(){
	      var echarts_goods_month = echarts.init(document.getElementById("echarts-moneyrecord-month"));
		  echarts_goods_month.showLoading({
                    text: "图表数据正在努力加载..."
          });
		  $.ajax({
			type: 'GET',
			url: '${WEB_PATH }/capital/account/capitalMonthStatistics.do',  
			data: {"year":$("#year").val()},
			dataType: "json",
			success:function(result){
				if(result.success){
					initOption(result.xAxis,result.recharge,result.cash,result.transfer,result.income,result.paid,result.transportSection,$("#year").val()+"年每月的交易记录");
				}else{
					layer.msg('<font color="red">统计数据出现问题，请稍后再试！。</font>', {icon: 5}); 
				}
			}
		  });   
	}
	function initOption(xAxis,recharge,cash,transfer,income,paid,transportSection,title){
		var reports_moneyrecord_month = echarts.init(document.getElementById("echarts-moneyrecord-month")),
		     option_moneyrecord_month = {
		     	 title : {
					text: title,
		            subtext: ''
				},
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['充值','提现','转账','收款','运输款','仲裁赔付']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : xAxis
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'充值',
			            type:'bar',
			            data:recharge
			        },
			        {
			            name:'提现',
			            type:'bar',
			            data:cash
			        },
			        {
			            name:'转账',
			            type:'bar',
			            data:transfer
			        },
			        {
			            name:'收款',
			            type:'bar',
			            data:income
			        },
			        {
			            name:'运输款',
			            type:'bar',
			            data:paid
			        },
			        {
			            name:'仲裁赔付',
			            type:'bar',
			            data:transportSection
			        }
			    ]
			};
		    reports_moneyrecord_month.setOption(option_moneyrecord_month), 
	        $(window).resize(reports_moneyrecord_month.resize);
	}
    </script>
</body>

</html>