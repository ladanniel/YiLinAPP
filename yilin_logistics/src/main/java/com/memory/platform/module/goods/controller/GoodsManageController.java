package com.memory.platform.module.goods.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsAutLog;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsBasic.Status;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsAutLogService;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.goods.service.IGoodsDetailService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 上午10:11:25 
* 修 改 人： 
* 日   期： 
* 描   述： 货物管理控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/goods/goodsmanage")
public class GoodsManageController extends BaseController {

	@Autowired
	private IGoodsBasicService goodsBasicService;
	@Autowired
	private IGoodsDetailService goodsDetailService;
	@Autowired
	private IGoodsAutLogService logService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		model.addAttribute("userId", UserUtil.getUser().getId());
		model.addAttribute("admin", UserUtil.getUser().getAccount());
		model.addAttribute("roleName", UserUtil.getUser().getRole().getName());
		return "/goods/goodsmanage/index";
	}
	
	@RequestMapping(value = "/view/osindex", method = RequestMethod.GET)
	protected String osindex(Model model, HttpServletRequest request) {
		return "/goods/goodsmanage/osindex";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(GoodsBasic goodsBasic,HttpServletRequest request){
		return goodsBasicService.getOsPage(goodsBasic, getStart(request), getLimit(request));
	}
	
	@RequestMapping("/getRecordPage")
	@ResponseBody
	public Map<String, Object> getRecordPage(GoodsBasic goodsBasic,HttpServletRequest request){
		return goodsBasicService.getRecordPage(goodsBasic, getStart(request), getLimit(request));
	}
	
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, HttpServletRequest request,String weight) {
		model.addAttribute("weight", weight);
		return "/goods/goodsmanage/edit";
	}
	
	@RequestMapping(value = "/view/verify", method = RequestMethod.GET)
	protected String verify(Model model,String id, HttpServletRequest request) {
		GoodsBasic goods = goodsBasicService.getGoodsBasicById(id);
		
		Account user = UserUtil.getUser();
		if(!goods.getStatus().equals(Status.apply) && !goods.getStatus().equals(Status.lock)){
			throw new BusinessException("请选择申请发货或处理中已锁定的数据进行审核，其它状态下的数据无法执行锁定操作。");
		}
		
		if(goods.getHasLock()){
			if(!goods.getAuditPersonId().equals(user.getId())){
				throw new BusinessException("该信息已被其他管理员锁定，无法操作。");
			}
		}else{
			GoodsAutLog log = new GoodsAutLog();
			log.setCreate_time(new Date());
			log.setAfterStatus(goods.getStatus());
			log.setAuditPerson(user.getName());
			log.setAuditPersonId(user.getId());
			log.setBeforeStatus(Status.lock);
			log.setGoodsBasic(goods);
			log.setRemark("审核锁定。");
			log.setTitle("锁定");
			goods.setStatus(Status.lock);
			goods.setHasLock(true);
			goods.setAuditPersonId(user.getId());
			goods.setAuditPerson(user.getName());
			goodsBasicService.updateGoodsBasic(goods,log,null);
		}
		
		model.addAttribute("goods", goods);
		model.addAttribute("list", goodsDetailService.getListForMap(goods.getId()));
		model.addAttribute("list", logService.getListForMap(goods.getId())); 
		return "/goods/goodsmanage/verify";
	}
	
	/**
	* 功能描述： 管理员撤销锁定
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月22日下午5:38:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/closeLock")
	@ResponseBody
	public Map<String, Object> closeLock(String id){
		GoodsBasic goods = goodsBasicService.getGoodsBasicById(id);
		
		Account user = UserUtil.getUser();
		
		if(!user.getRole().getName().equals("管理员") && !user.getRole().getName().equals("易林管理员") && !user.getId().equals(goods.getAuditPersonId())){
			throw new BusinessException("您不是管理员或锁定该数据的用户，不能撤销该数据。");
		}
		
		goods.setHasLock(false);
		goods.setAuditPerson(null);
		goods.setAuditPersonId(null);
		
		GoodsAutLog log = new GoodsAutLog();
		log.setCreate_time(new Date());
		log.setAfterStatus(GoodsBasic.Status.apply);
		log.setAuditPerson(user.getName());
		log.setAuditPersonId(user.getId());
		log.setBeforeStatus(GoodsBasic.Status.apply);
		log.setGoodsBasic(goods);
		log.setRemark("锁定撤销。");
		log.setTitle("解锁");
		
		if(goods.getStatus().equals(Status.lock)){
			goods.setStatus(Status.apply);
		}
		
		goodsBasicService.updateGoodsBasic(goods,log,null);
		return jsonView(true, "锁定撤销成功");
	}
	
	/**
	* 功能描述： 审核发货
	* 输入参数:  @param goods
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月14日下午2:49:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/verify")
	@ResponseBody
	public Map<String, Object> verify(HttpServletRequest request,GoodsBasic goods,String arrayLog){
		String[] array = null;
		String logs = "";
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> temp = new HashMap<String,Object>();
		if(StringUtil.isNotEmpty(arrayLog)){
			array = arrayLog.split("#");
			for (String string : array) {
				String str[] = string.split("-");
				map.put(str[0]+"-"+str[1], str[2]+"-"+str[3]+"-"+str[4] + "-" + str[5]);
			}
			for (Map.Entry<String, Object> entry : map.entrySet()) { 
				String str[] = entry.getValue().toString().split("-");
				if(!str[0].equals(str[1])){
					temp.put(entry.getKey(), entry.getValue());
				}
			  
			} 
		}
		
		GoodsBasic prent = goodsBasicService.getGoodsBasicById(goods.getId());
		Account user = UserUtil.getUser();
		if(!prent.getAuditPersonId().equals(user.getId())){
			throw new BusinessException("该信息正在被其它管理员处理，你不能进行操作。");
		}
//		if(goods.getStatus().equals(Status.success)){
//			if(StringUtil.isEmpty(goods.getStockTypeNames()) || StringUtil.isEmpty(stockTypes)){
//				throw new BusinessException("请选择载货车辆类型，再执行审核功能。");
//			}
//			prent.setStockTypeNames(goods.getStockTypeNames());
//		}
		prent.setAuditCause(goods.getAuditCause());
		prent.setStatus(goods.getStatus());
		prent.setAuditTime(new Date());
		
		GoodsAutLog log = new GoodsAutLog();
		log.setCreate_time(new Date());
		log.setAfterStatus(prent.getStatus());
		log.setAuditPerson(user.getName());
		log.setAuditPersonId(user.getId());
		log.setBeforeStatus(goods.getStatus());
		log.setGoodsBasic(prent);
		log.setRemark(goods.getAuditCause());
		log.setTitle("【审核】");
		
		if(prent.getUnitPrice() != goods.getUnitPrice()){
			logs += "经管理员与商户协商一致，在原有的货物单价"+prent.getUnitPrice()+"元每吨调整为"+goods.getUnitPrice()+"元每吨。";
			prent.setUnitPrice(goods.getUnitPrice());
			prent.setTotalPrice(goods.getTotalPrice());
		}
		
		if(temp.size() != 0){
			for (Map.Entry<String, Object> entry : temp.entrySet()) { 
				String key[] = entry.getKey().split("-");
				String val[] = entry.getValue().toString().split("-");
				if(key[1].equals("weight")){
					logs += "【"+val[2]+"--"+val[3]+"】重量从原来的"+val[0]+"吨修改为"+val[1]+"吨;";
				}else if(key[1].equals("length")){
					logs += "【"+val[2]+"--"+val[3]+"】长度从原来的"+val[0]+"米修改为"+val[1]+"米;";
				}else if(key[1].equals("height")){
					logs += "【"+val[2]+"--"+val[3]+"】高度从原来的"+val[0]+"米修改为"+val[1]+"米;";
				}else if(key[1].equals("diameter")){
					logs += "【"+val[2]+"--"+val[3]+"】直径从原来的"+val[0]+"厘米修改为"+val[1]+"厘米;";
				}else if(key[1].equals("wingWidth")){
					logs += "【"+val[2]+"--"+val[3]+"】翼宽从原来的"+val[0]+"修改为"+val[1]+";";
				}
			} 
			log.setRemark(log.getRemark() + logs);
			prent.setGoodsDetail(goods.getGoodsDetail());
		}
		
		goodsBasicService.updateGoodsBasic(prent,log,temp);
		return jsonView(true, "审核成功");
	}
}
