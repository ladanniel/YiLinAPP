 
package com.memory.platform.global;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialType;
import com.memory.platform.entity.order.RobOrderRecord;

public class StatusMap {
	private String[] companyType = {"易林","货主","车队", "个人司机"}; 
	
	/**
	* 功能描述： 获取抢单状态描述信息
	* 输入参数:  @param str
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月11日上午10:24:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	public static String getRobOrderStatusMsg(RobOrderRecord.Status status,Account account)  
    {  
		//0:提交申请  1:处理中   2:退回    3:成功    4:作废   5:撤回  6:结束
		String msg  = "";
		switch (status.ordinal()) {
		case 0:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "当前抢单正在等待货主审核，可以进行撤回修改或取消抢单操作！";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "当前抢单正在等待您的审核，若需要协商请点击退回，若报价合适请确认该抢单！";
			}
			break;
		case 1:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "当前抢单货主正在处理中，无法进行其他操作！";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "当前抢单正在处理中，若需要协商请点击退回，若报价合适请确认该抢单！";
			}
			break;
		case 2:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "当前抢单已被退回，可以进行编辑重提或销单处理！";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "当前抢单已退回抢单人，等待对方操作！";
			}
			break;
		case 3:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "恭喜您，抢单成功！请确认运输车辆并支付押金！";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "当前抢单正在等待抢单人的确认，请耐心等候！";
			}
			break;
		case 4:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "当前抢单已销单，无法进行其他操作！";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "当前抢单已销单，无法进行其他操作！";
			}
			break;
		case 5:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "当前抢单已被撤回，可以进行编辑重提或销单处理！";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "当前抢单被抢单人撤回，等待对方操作！";
			}
			break;
		}
		return msg;
	 
    }
	
	public static final String ORDER_DEAIL_MSG = "当前抢单正在等待您的审核，若需要协商请点击退回，若报价合适请确认该抢单";
	public static final String ORDER_DEAIL_BACK_MSG = "当前抢单已退回抢单人，等待对方操作。";
	public static final String ORDER_DEAIL_CANCEL_MSG = "当前抢单被抢单人撤回，等待对方操作";
	public static final String ORDER_DEAIL_WAIT_MSG = "当前抢单正在等待抢单人的确认，请耐心等候";
	public static final String ORDER_DEAIL_BACK_MANAGE_MSG = "当前抢单已被货主退回。";
	
	public static String getOrderStatusMsg(String status,Account account){
		if(status.equals("0")){
			if(account.getCompany().getCompanyType().getName().equals("货主")){
				return ORDER_DEAIL_MSG;
			}else if(account.getCompany().getCompanyType().getName().equals("司机") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				return "当前抢单已申请提交，等待货主审阅。";
			}
		}
		if(status.equals("1")){
			if(account.getCompany().getCompanyType().getName().equals("货主")){
				return ORDER_DEAIL_MSG;
			}else if(account.getCompany().getCompanyType().getName().equals("司机") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				return "当前抢单正在被货主审阅，请耐心等待。";
			}
		}
		if(status.equals("2")){
			if(account.getCompany().getCompanyType().getName().equals("货主")){
				return "当前抢单已退回。";
			}else if(account.getCompany().getCompanyType().getName().equals("司机") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				return "当前抢单已被货主退回，请编辑确认后再提交。";
			}
		}
		if(status.equals("3")){
			if(account.getCompany().getCompanyType().getName().equals("货主")){
				return "当前抢单已成功。";
			}else if(account.getCompany().getCompanyType().getName().equals("司机") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				return "当前抢单已审核成功，请确认信息处理该抢单。";
			}
		}
		if(status.equals("4")){
			return "当前抢单已作废。";
		}
		if(status.equals("5")){
			if(account.getCompany().getCompanyType().getName().equals("货主")){
				return ORDER_DEAIL_CANCEL_MSG;
			}else if(account.getCompany().getCompanyType().getName().equals("司机") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				return "已撤回该抢单，请操作。";
			}
		}
		return null;
	}
	
	
	/**
	* 功能描述： 获取抢单状态描述信息
	* 输入参数:  @param str
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月11日上午10:24:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	public static String getRobOrderConfirmMsg(RobOrderConfirm robOrderConfirm,Account account) 
    {  
		
		String msg  = "";
		
		if(robOrderConfirm.getSpecialType()!=null&&robOrderConfirm.getSpecialStatus()!=SpecialStatus.success){
			if(robOrderConfirm.getSpecialType().equals(SpecialType.emergency)){
				msg = account.getName()+"发起急救，该订单已被冻结，请等待易林人员进行处理";
			}
			
		}
		
		
		switch (robOrderConfirm.getStatus().ordinal()) {
		case 0:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "快快联系货主装货吧，装货完毕后请货主确认付款再出发哦";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "师傅正在赶来，装货完毕后确认付款师傅才能上路哦";
			}
			break;
		case 1:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "装货完毕,请联系货主确认付款";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "师傅已装货，确认后付款师傅才能上路哦";
			}
			break;
		case 2:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "货主已将运费存入系统，可以上路啦。卸货完毕后记得确认送达哦";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "师傅正在运输货物，有任何情况保持联系哦";
			}
			break;
		case 3:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "请委托快递公司送还回执，易林物流会将您的回执送还货主并为您结算运费";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "卸货已完成";
			}
			break;
		case 4:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "快递正在返回途中，易林物流会将您的回执送还货主并为您结算运费";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "回执正在返回途中，可以通过快递单号进行查询哦";
			}
			break;
		case 5:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "易林平台工作人员正在赶往货主门店的途中······";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "易林平台工作人员正在为您送还回执，请耐心等候";
			}
			break;
		case 6:
			if(account.getCompany().getCompanyType().getName().equals("车队") || account.getCompany().getCompanyType().getName().equals("个人司机")){
				msg = "恭喜您，该运单已完结";
			}else if(account.getCompany().getCompanyType().getName().equals("货主")){
				msg = "恭喜您，该运单已完结";
			}
			break;
		}
		
		
		return msg;
	 
    }
	
	/**
	* 功能描述： 获取抢单状态描述信息
	* 输入参数:  @param str
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月11日上午10:24:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	public static String getRobOrderConfirmSpecialMsg(RobOrderConfirm robOrderConfirm,Account account) 
    {  
		
		String msg  = "";
		
		if(robOrderConfirm.getSpecialType()!=null&&robOrderConfirm.getSpecialStatus()!=SpecialStatus.success){
			if(robOrderConfirm.getSpecialType().equals(SpecialType.emergency)){
				msg = account.getName()+"发起急救，该订单已被冻结，请等待易林人员进行处理";
			}
			if(robOrderConfirm.getSpecialType().equals(SpecialType.arbitration)){
				msg = account.getName()+"发起仲裁，该订单已被冻结，请等待易林人员进行处理";
			}
			
		}
		
		return msg;
	 
    }
	
	
	
	
	
	
}
 