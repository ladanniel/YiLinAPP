package com.memory.platform.global;

public class StringFromateTemplate {
	StringBuilder sb=new StringBuilder();
	public StringFromateTemplate(String...args){
		for(String str : args){
			sb.append(str);
		}
	}
	@Override
	public String toString() {
		 return sb.toString();
	}
}
