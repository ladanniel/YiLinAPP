package com.memory.platform.global;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.memory.platform.exception.BusinessException;

public class DoubleHelper {
	public static double round(Double value) {
		
		BigDecimal bigDecimal  =  new BigDecimal(Math.abs( value));
		
		
		double d =  bigDecimal .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		double d2 = bigDecimal.subtract(new BigDecimal( d )).setScale(3,BigDecimal.ROUND_HALF_UP ).doubleValue();
		double d3 = 0;
		if(d2 >= 0.001) {
			d3 = 0.01;
		}
		 
		return   new BigDecimal( (d +d3) * ((value <0)?-1:1)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() ;
	 
	}
	public static void  main(String[] args) {
		double d =  19.994*555555510;
		d =  round(-2199.951);
		System.out.print(d+"\n");;
		
		
		BigDecimal count = new BigDecimal(0.00);
		DecimalFormat format = new DecimalFormat("#0.00");
		double data =0;
	
		for (int i = 0; i < 1000000; i++) {
			int randomD = (int) (Math.random()*10%10);
			double random =randomD*0.01;
	
			count = count.subtract(new BigDecimal(random));
		//	data = count.setScale(2).doubleValue();
			data = count.doubleValue() ;
			
			data = DoubleHelper.round(data);
			String str = data+ "";
			
			int  dotCount  = str.length() -1 - str.indexOf(".");
			if(dotCount>2) {
				throw new BusinessException("111");
			}
 		}
	}
}
