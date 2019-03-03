package com.memory.platform.global;

/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Java 5 enumeration of HTTP status codes.
 *
 * <p>The HTTP status code series can be retrieved via {@link #series()}.
 *
 * @author Arjen Poutsma
 * @author Sebastien Deleuze
 * @see HttpStatus.Series
 * @see <a href="http://www.iana.org/assignments/http-status-codes">HTTP Status Code Registry</a>
 * @see <a href="http://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes - Wikipedia</a>
 */
public enum ApiStatusCode {

	SUCCESS(0, "请求成功"),
	
	FAIL(-1,"系统繁忙，此时请开发者稍候再试"),
	
	LOGIN_CODE_10000(10000,"用户名或密码错误！"),
	
	LOGIN_CODE_10001(10001,"该用户已停用，无法登陆，请联系管理员！"),
	
	LOGIN_CODE_10002(10002,"该商户已注销，无法登陆，请联系管理员！");
	
	
	
	



	private final int value;

	private final String reasonPhrase;


	private ApiStatusCode(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return reasonPhrase;
	}


	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(value);
	}

}

