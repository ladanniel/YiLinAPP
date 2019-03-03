/*
 * 
 * 
 * 
 */
package com.memory.platform.global;

//0：未认证通过  1:认证通过    2:未申请认证    3:等待审核  4：认证补录  5:补录等待审核

public enum Auth{
	notAuth,auth,notapply,waitProcess,supplement,waitProcessSupplement
}