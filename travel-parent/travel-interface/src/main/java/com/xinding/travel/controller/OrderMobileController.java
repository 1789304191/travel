package com.xinding.travel.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.controller.BaseController;
import com.xinding.travel.pojo.MessageMobile;
import com.xinding.travel.pojo.OrderMobile;
import com.xinding.travel.pojo.PagedResult;
import com.xinding.travel.service.IMessageMobileService;
import com.xinding.travel.service.IOrderMobileService;
import com.xinding.travel.util.Constant;
import com.xinding.travel.util.FileManagerUtil;
import com.xinding.travel.util.Message;
import com.xinding.travel.weixin.util.WeixinUtil;

@Controller
@RequestMapping("/orderMobile")
public class OrderMobileController extends BaseController {
	
	@Autowired
	private IOrderMobileService orderMobileService;
	@Autowired
	private IMessageMobileService messageMobileService;

	/**
	 * <p>根据手机号查询订单列表</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:12:01
	 * @param p
	 * @return
	 * @see
	 */
	@RequestMapping(value="/list", method= RequestMethod.POST)
    @ResponseBody
    public Message list(@RequestBody Map p) {
		Map<String, Object> data = new HashMap<String, Object>();
		Message msg = new Message();
		try {
			List<MessageMobile> listByToken = messageMobileService.listByToken(p);
			if(listByToken.isEmpty()){
				msg.setCode(Constant.INTEGER_NEG_ONE);
				msg.setMesssage("手机号或token不正确");
				msg.setRequestFlag(false);
				return msg;
			}
			// 系统当前时间
			long nowTime = System.currentTimeMillis() / 1000;
			// 验证码更新的最新时间
			long lastSendTime = listByToken.get(0).getCreateTime().getTime()/1000;
			// 二者时间差
			long timeDif = nowTime - lastSendTime;
			// 30分钟失效
			if (1800 < timeDif) {
				msg.setCode(Constant.INTEGER_NEG_TWO);
				msg.setMesssage("token已失效");
				msg.setRequestFlag(false);
				return msg;
			}
			
			PagedResult<OrderMobile> pageResult = orderMobileService.pages(p);
			for(OrderMobile om:pageResult.getDataList()){
				if(StringUtils.isNotBlank(om.getQrcodePic())){
					om.setQrcodePic(FileManagerUtil.getPicturePath(om.getQrcodePic(), "qrcode")+om.getQrcodePic());
				}
    	    }
			
			data.put("data",pageResult.getDataList());
			data.put("total",pageResult.getTotal());
			msg.setRequestFlag(true);
			msg.setResponseEntiy(data);
			msg.setMesssage("查询订单成功");
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return msg;
    }
	
	/**
	 * <p>支付完成详情</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:12:32
	 * @param orderSn
	 * @return
	 * @see
	 */
	@RequestMapping(value="/orderDetail", method= RequestMethod.GET)
    @ResponseBody
	public Message orderDetail(String orderSn){
		Map<String, Object> data = new HashMap<String, Object>();
		Message msg = new Message();
		try {
			OrderMobile order = new OrderMobile();
			order = orderMobileService.orderDetail(orderSn);
			if(order != null){
				order.setQrcodePic(FileManagerUtil.getPicturePath(order.getQrcodePic(), "qrcode")+order.getQrcodePic());
			}
			data.put("order", order);
			msg.setRequestFlag(true);
			msg.setMesssage("查询订单详情成功！");
			msg.setResponseEntiy(data);
		} catch (Exception e) {
			msg.setRequestFlag(false);
			msg.setMesssage("查询订单详情失败！");
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * <p>订单详情</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:14:42
	 * @param orderSn
	 * @return
	 * @see
	 */
	@RequestMapping(value="/orderDetailInfo", method= RequestMethod.GET)
    @ResponseBody
	public Message orderDetailInfo(String orderSn){
		Map<String, Object> data = new HashMap<String, Object>();
		Message msg = new Message();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			OrderMobile order = new OrderMobile();
			order = orderMobileService.orderDetail(orderSn);
			order.setQrcodePic(FileManagerUtil.getPicturePath(order.getQrcodePic(), "qrcode")+order.getQrcodePic());
			order.setBuyTime(df.format(order.getCreateTime()));
			data.put("order", order);
			msg.setRequestFlag(true);
			msg.setMesssage("查询订单详情成功！");
			msg.setResponseEntiy(data);
		} catch (Exception e) {
			msg.setRequestFlag(false);
			msg.setMesssage("查询订单详情失败！");
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * <p>根据微信openId查询手机号信息</p> 
	 * @author dongjun
	 * @date 2016年6月29日 下午2:06:20
	 * @param code
	 * @return
	 * @see
	 */
	@RequestMapping(value="/listByWxCode", method= RequestMethod.POST)
    @ResponseBody
	public Message listByWxCode(@RequestBody Map p){
		Message msg = new Message();
		//微信授权码
		String code = String.valueOf(p.get("code"));
		// 微信用户标识openid
		String openId = "";
		openId = WeixinUtil.getWeixinOpenId(code);
		p.put("openId", openId);			
		List<OrderMobile> list = this.orderMobileService.getMobilePhone(p);
		Map map = new HashMap();
		
		if(!list.isEmpty()){
			map.put("mobilePhone", list.get(0).getMobilePhone());
			msg.setRequestFlag(true);
			msg.setMesssage("获取手机号成功");
			msg.setResponseEntiy(map);
		}else{
			msg.setRequestFlag(false);
			msg.setMesssage("获取手机号失败");
		}
		return msg;
	}
	
	@RequestMapping(value="/orderCheck", method= RequestMethod.POST)
    @ResponseBody
	public Message doVerificate(@RequestBody Map p) {
		String veriNo = p.get("veriNo").toString();
		Message message = new Message();
		if(veriNo.indexOf("=") != -1) {
			veriNo = veriNo.split("=")[1];
			Long pdaUserId = Long.valueOf((String)p.get("pdaUserId"));
			String token = (String) p.get("token");
			message = orderMobileService.checkoutVeri(veriNo,pdaUserId,token);
		} else {
			message.setMesssage("二维码不正确");
			message.setRequestFlag(false);
		}
		return message;
	}
	
	@RequestMapping(value="/orderVerList")
    @ResponseBody
	public Message orderVerList(@RequestBody Map p) {
		Integer pdaUserId = Integer.valueOf((String) p.get("status"));
		Message message = orderMobileService.orderVerList(pdaUserId);	
		return message;
	}
	
	@RequestMapping(value="/orderVerDetail", method = RequestMethod.POST)
    @ResponseBody
	public Message orderVerDetail(@RequestBody Map p) {
		String veriNo = (String) p.get("veriNo");
		Message message = orderMobileService.orderVerDetail(veriNo);
		return message;
	}
	
}
