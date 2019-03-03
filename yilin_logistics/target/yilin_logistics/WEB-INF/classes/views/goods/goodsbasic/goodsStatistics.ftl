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
    	<@goodsBaseicCount type="statistics">
        <!-- Panel Other -->
         <div class="row">
            <div class="col-sm-3">
                <div class="widget style1 red-bg">
                    <div class="row">
                        <div class="col-xs-4 text-center">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63d;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span> 今日发货</span>
                            <h2 class="font-bold">${goodsCount.goodscount.toDayCount?string(',###.##')}/单</h2>
                            <span>${(goodsCount.goodscount.toDayWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 navy-bg">
                    <div class="row">
                        <div class="col-xs-4">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63a;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span> ${goodsCount.goodscount.month}月发货 </span>
                            <h2 class="font-bold">${goodsCount.goodscount.thisMonthCount?string(',###.##')}/单</h2>
                            <span>${(goodsCount.goodscount.thisMonthWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 lazur-bg">
                    <div class="row">
                        <div class="col-xs-4">
                             <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63b;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span>本季度发货</span>
                            <h2 class="font-bold">${goodsCount.goodscount.quarterCount?string(',###.##')}/单</h2>
                            <span>${(goodsCount.goodscount.quarterWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="widget style1 yellow-bg">
                    <div class="row">
                        <div class="col-xs-4">
                            <i class="iconfont" style="font-size:4em;font-style:normal;" >&#xe63e;</i>
                        </div>
                        <div class="col-xs-8 text-right">
                            <span>总发货 </span>
                            <h2 class="font-bold">${goodsCount.goodscount.toolCount?string(',###.##')}/单</h2>
                            <span>${(goodsCount.goodscount.toolWeight!0)?string(',###.##')}/吨</span>
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
                        	<span>待审核 （${(goodsCount.goodscount.applyCount!0/goodsCount.goodscount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${goodsCount.goodscount.applyCount?string(',###.##')}/单</h3>
                            <span>${(goodsCount.goodscount.applyWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 navy-bg" style="background-color: #f8ac59;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;" >&#xe614;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>正在处理（${(goodsCount.goodscount.lockCount!0/goodsCount.goodscount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${goodsCount.goodscount.lockCount?string(',###.##')}/单</h3>
                            <span>${(goodsCount.goodscount.lockWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 navy-bg" style="background-color: #676a6c;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont"  style="font-size:3em;font-style:normal;" >&#xe615;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>退回 （${(goodsCount.goodscount.backCount!0/goodsCount.goodscount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${goodsCount.goodscount.backCount?string(',###.##')}/单</h3>
                            <span>${(goodsCount.goodscount.backWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" style="background-color: #00bb9c;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe639;</i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<span>通过（${(goodsCount.goodscount.successCount!0/goodsCount.goodscount.toolCount!0)?string.percent}） </span>
                            <h3 class="font-bold">${goodsCount.goodscount.successCount?string(',###.##')}/单</h3>
                            <span>${(goodsCount.goodscount.successWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 lazur-bg" style="background-color: #1a7bb9;">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe637;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>上线（${(goodsCount.goodscount.trueCount!0/goodsCount.goodscount.toolCount!0)?string.percent}） </span>
                            <h3 class="font-bold">${goodsCount.goodscount.trueCount?string(',###.##')}/单</h3>
                            <span>${(goodsCount.goodscount.trueWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="widget style1 red-bg">
                    <div class="row vertical-align">
                        <div class="col-xs-3">
                            <i class="iconfont" style="font-size:3em;font-style:normal;">&#xe636;</i> 
                        </div>
                        <div class="col-xs-9 text-right">
                            <span>下线（${(goodsCount.goodscount.falseCount!0/goodsCount.goodscount.toolCount!0)?string.percent}）</span>
                            <h3 class="font-bold">${goodsCount.goodscount.falseCount?string(',###.##')}/单</h3>
                            <span>${(goodsCount.goodscount.falseWeight!0)?string(',###.##')}/吨</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       </@goodsBaseicCount>
       <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>每月发货量</h5>
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
                          <div id="echarts-goods-month" class="echarts" style="height: 400px"></div>
                    </div>
                </div>
            </div>
            <#if USER.company.companyType.name == "系统" || USER.company.companyType.name == "管理">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>发货量排名</h5>
                        <div class="ibox-tools">
                        	请选择排名类型：
                        	<select style="width:80px;"  name="type" id="type">
                        			<option value="all" selected = "selected">全部</option>
                            		<option value="weight">货物重量</option>
                            		<option value="count">发货单数</option>
	                        </select>
	                        &nbsp;&nbsp;&nbsp;
                        	请选择排名数：
                        	<select style="width:60px;"  name="ranking" id="ranking">
                            		<option value="10" >10</option>
                            		<option value="20" >20</option>
	                        </select>
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div id="echarts-goods-ranking" class="echarts" style="height: 400px"></div>
                    </div>
                </div>
            </div>
            </#if>
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
          <#if USER.company.companyType.name == "系统" || USER.company.companyType.name == "管理">
          $('#ranking').change(function(){ 
          	 initGoodsRanking();
          });
          $('#type').change(function(){ 
          	 initGoodsRanking();
          });
          initGoodsRanking();
          </#if>
	});
	
	<#if USER.company.companyType.name == "系统" || USER.company.companyType.name == "管理">
	function initGoodsRanking(){
	      var echarts_goods_month = echarts.init(document.getElementById("echarts-goods-ranking"));
		  echarts_goods_month.showLoading({
                    text: "图表数据正在努力加载..."
          });
		  $.ajax({
			type: 'GET',
			url: '${WEB_PATH }/goods/goodsbasic/goodsRankingStatistics.do',  
			data: {"ranking":$("#ranking").val(),"type":$("#type").val()},
			dataType: "json",
			success:function(result){
				if(result.success){
					var echarts_goods_ranking = echarts.init(document.getElementById("echarts-goods-ranking"));
					var option = initOption(result.xAxis,result.counts,result.weights,"平台发货量统计排名",result.legendData);
					echarts_goods_ranking.setOption(option), 
        			$(window).resize(echarts_goods_ranking.resize);
				}else{
					layer.msg('<font color="red">统计数据出现问题，请稍后再试！。</font>', {icon: 5}); 
				}
			}
		  });   
	}
	</#if>
	
	
	function initGoodsMonth(){
	      var echarts_goods_month = echarts.init(document.getElementById("echarts-goods-month"));
		  echarts_goods_month.showLoading({
                    text: "图表数据正在努力加载..."
          });
		  $.ajax({
			type: 'GET',
			url: '${WEB_PATH }/goods/goodsbasic/goodsMonthStatistics.do',  
			data: {"year":$("#year").val()},
			dataType: "json",
			success:function(result){
				if(result.success){
					var echarts_goods_month = echarts.init(document.getElementById("echarts-goods-month"));
					var option = initOption(result.xAxis,result.counts,result.weights,$("#year").val()+"年每月的发货量统计");
					echarts_goods_month.setOption(option), 
        			$(window).resize(echarts_goods_month.resize);
				}else{
					layer.msg('<font color="red">统计数据出现问题，请稍后再试！。</font>', {icon: 5}); 
				}
			}
		  });   
	}
	function initOption(xAxis,counts,weights,title,legendData){
	    if(legendData == null){
	    	legendData = ['发货单数','发货重量'];
	    }
	    var yAxis_v = null
	    var series_v = null;
	    if(counts != null && weights != null){
	    	yAxis_v = [
			    {
		            type: 'value',
		            name: '发货单数', 
		            axisLabel: {
		                formatter: '{value}单'
		            }
		        },
		        {
		            type: 'value',
		            name: '发货重量', 
		            axisLabel: {
		                formatter: '{value}吨'
		            },
		            splitLine : {
		                show: false
		            }
		        }
		    ];
		    
		    series_v = [
		        {
		            name:'发货单数',
		            type:'bar',
		            data:counts,
		        },
		        {
		            name:'发货重量',
		            type:'bar',
		            yAxisIndex: 1,
		            data:weights
		        }
		    ];
	    }
	    if(counts != null && weights == null){
	    	yAxis_v = [
			    {
		            type: 'value',
		            name: '发货单数', 
		            axisLabel: {
		                formatter: '{value}单'
		            }
		        } 
		    ];
		    series_v = [
		        {
		            name:'发货单数',
		            type:'bar',
		            data:counts
		        } 
		    ];
	    }
	    
	    if(counts == null && weights != null){
	    	 yAxis_v = [
		        {
		            type: 'value',
		            name: '发货重量', 
		            axisLabel: {
		                formatter: '{value}吨'
		            },
		            splitLine : {
		                show: false
		            }
		        }
		    ];
		    
		    series_v = [
		        {
		            name:'发货重量',
		            type:'bar',
		            data:weights
		        }
		    ];
	    }
	    
		var option = {
		    title : {
		        text: title,
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		       data:legendData
		    },
		    toolbox: {
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']}, 
		            saveAsImage: {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : xAxis
		        }
		    ],
		    yAxis : yAxis_v,
		    series : series_v
		};
		
		return option;
	}
    </script>
</body>

</html>