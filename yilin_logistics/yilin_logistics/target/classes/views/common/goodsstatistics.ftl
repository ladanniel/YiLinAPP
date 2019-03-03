<!-- 货物统计-->
<#macro goodscount >
<#if USER.userType == "company" && (USER.company.companyType.name == "货主" || USER.company.companyType.name == "系统"  || USER.company.companyType.name == "管理")>
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>货物统计</h5>
        </div>
        <div class="ibox-content">
            <div class="row">
                <div class="col-sm-7">
                     <div style="width: 100%;height:330px;padding-left:25px;" id="goods_count"></div>
                </div>
                <div class="col-sm-5">
                	<div style="width: 100%;height:330px;padding-left:25px;" id="goods_starte"></div>
                </div>
            </div>
        </div>

    </div>
</div>
<script>
	$(function(){
	    <@goodsBaseicCount type="index">
		option = {
		    title : {  
		        text: '平台货物状态比例统计',  
		        subtext: '',  
		        x:'center'  
		    },  
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"  
		    },
		    legend: {
		        orient: 'vertical',
		        x: 'left',
		        data:['待审核','正在处理','退回','通过','下线','上线']
		    },
		    series: [
		        {
		            name:'访问来源',
		            type:'pie',
		            selectedMode: 'single',
		            radius: [0, '30%'],
		
		            label: {
		                normal: {
		                    position: 'inner'
		                }
		            },
		            labelLine: {
		                normal: {
		                    show: false
		                }
		            },
		            itemStyle : {
		                normal : {
		                    label : {
		                        position : 'inner',
		                        formatter : function (params) { 
		                        	console.log(params); 
		                          return params.name+"\n("+(params.percent - 0).toFixed(0) +'%'+")"
		                        }
		                    },
		                    labelLine : {
		                        show : false
		                    }
		                },
		                emphasis : {
		                    label : {
		                        show : true,
		                        formatter : "{b}\n{d}%"
		                    }
		                }
		                
		            },
		            data:[
		                {value:${goodsCount.countMap.falseCount}, name:'下线'},
		                {value:${goodsCount.countMap.trueCount}, name:'上线'}
		            ],
		            color:['#ed5565', '#1a7bb9'] 
		        },
		        {
		            name:'货物比例',
		            type:'pie',
		            radius: ['40%', '55%'], 
		            data:[
		                {value:${goodsCount.countMap.applyCount}, name:'待审核' },
		                {value:${goodsCount.countMap.lockCount}, name:'正在处理'},
		                {value:${goodsCount.countMap.backCount}, name:'退回'},
		                {value:${goodsCount.countMap.successCount}, name:'通过'} 
		            ],
		            itemStyle:{ 
		                normal:{ 
		                  label:{ 
		                    show: true, 
		                    formatter: '{b} : {c} 单 \n ({d}%)' 
		                  }, 
		                  labelLine :{show:true} 
		                }  
		            }
		        }
		    ],
		    
		};
	    var l = echarts.init(document.getElementById("goods_starte"));
	    l.setOption(option);
	    $(window).resize(l.resize);
		option_1 = {
			 title : {
		        text: '近一周发货统计',
		        subtext: ''
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		       data:['发货单数','发货重量']
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
		            data : ${goodsCount.xAxis}
		        }
		    ],
		    yAxis : [
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
		    ],
		    series : [
		        {
		            name:'发货单数',
		            type:'bar',
		            data:${goodsCount.countData}
		        },
		        {
		            name:'发货重量',
		            type:'bar',
		            yAxisIndex: 1,
		            data:${goodsCount.weightData},
		        }
		    ]
		};
		 var cc = echarts.init(document.getElementById("goods_count"));
		 cc.setOption(option_1);
		 $(window).resize(cc.resize);
		 </@goodsBaseicCount>
	});
</script>
</#if>
</#macro>
<!-- 货物统计 -->
 
 