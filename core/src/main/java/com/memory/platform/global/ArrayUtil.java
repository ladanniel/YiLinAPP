package com.memory.platform.global;

import java.util.Calendar;
import java.util.List;
import java.util.Stack;

import com.memory.platform.core.AppUtil;
 

public class ArrayUtil {
	
	private Stack<Object> st = new Stack<Object>();
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
	/**
	 * 将传入数组，按顺序排列
	 * @param data 对像数组
	 * @param num 数量
	 * @param split 分隔符
	 * @param list 返回数组
	 * @return
	 */
	public List<Object> arrayToString(Object[] data, int num, String split, List<Object> list) {
		if (num > data.length || num <= 0) {
			return list;
		}
		if (num == 1) {
			for (int i = 0; i < data.length; i++) {
				StringBuffer sb = new StringBuffer();
				st.push(data[i]);
				for (int j = 0; j < st.size(); j++) {
					if (sb.length() > 0) {
						sb.append(split);
					}
					sb.append(st.get(j));
				}
				System.out.println();
				list.add(sb.toString());
				st.pop();
			}
			return list;
		}
		for (int i = 0; i < data.length - num + 1; i++) {
			st.push(data[i]);
			Object[] newStr = new Object[data.length - i - 1];
			int k = 0;
			for (int j = i + 1; j < data.length; j++) {
				newStr[k++] = data[j];
			}
			arrayToString(newStr, num - 1, split,  list);
			st.pop();
		}
		return list;
	}
	
	
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		String ps = AppUtil.getrandom(c).toString();
		System.out.println(ps);
	}

}
