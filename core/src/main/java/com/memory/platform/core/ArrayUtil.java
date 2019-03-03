package com.memory.platform.core;

import java.util.List;

 
import com.memory.platform.global.StringUtil;

public class ArrayUtil {
	
	public static interface  IJoinCallBack<T> {
		public <T>String join(T obj,int idx);

	 
	}
	
	public  static <T> String joinListArray(List<T> lst,String joinFlag, IJoinCallBack<T> callback) {
		StringBuilder sbBuilder = new StringBuilder();
		for(int i =0 ;i<lst.size(); i++ ){
			String info =  callback.join(lst.get(i),i);
			if(StringUtil.isNotEmpty(info)){
				sbBuilder.append(info);
				if(i< lst.size()-1) {
					sbBuilder.append(joinFlag);
				}
				
			}
			
		}
			return sbBuilder.toString();
	}
}
