package com.memory.platform.core;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlCDataInt extends XmlAdapter<Integer,String>{

	@Override
	public String unmarshal(Integer v) throws Exception {
		// TODO Auto-generated method stub
		return v.toString();
	}

	@Override
	public Integer marshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return Integer.parseInt(v);
	}
}
