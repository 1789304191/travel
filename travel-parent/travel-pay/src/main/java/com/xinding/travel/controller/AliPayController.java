package com.xinding.travel.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.xinding.travel.alipay.config.AlipayConfig;
import com.xinding.travel.alipay.util.AlipayCore;
import com.xinding.travel.alipay.util.AlipaySubmit;
import com.xinding.travel.pojo.AlipayNotifyEntity;
import com.xinding.travel.pojo.MessagePay;
import com.xinding.travel.pojo.PayOrder;
import com.xinding.travel.service.INotifyService;
import com.xinding.travel.service.IPayOrderService;
import com.xinding.travel.util.ArithUtil;
import com.xinding.travel.util.Constant;
import com.xinding.travel.util.FileManagerUtil;
import com.xinding.travel.util.GenerateCode;
import com.xinding.travel.util.HttpSender;
import com.xinding.travel.util.Message;
import com.xinding.travel.util.ProjectCodeUtil;
import com.xinding.travel.util.PropertiesUtil;
import com.xinding.travel.util.UploadUtil;
import com.xinding.travel.vo.PropertiesVo;
import com.xinding.travel.weixin.common.WxConstant;

/**
 * TODO(支付宝支付接口)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dongjun,date:2016年6月14日 上午11:08:56,content:TODO
 * </p>
 * 
 * @author dongjun
 * @date 2016年6月14日 上午11:08:56
 * @since
 * @version
 */
@Controller
@RequestMapping("/aliPay")
public class AliPayController extends BaseController {
	private  String orderIdentify = "";
	@Autowired
	private IPayOrderService payOrderService;
	@Autowired
	private INotifyService notifyService;
	
	/**
	 * <p>支付宝支付</p> 
	 * @author dongjun
	 * @date 2016年6月23日 下午4:45:24
	 * @param p
	 * @return
	 * @see
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/aliPay", method = RequestMethod.POST)
	@ResponseBody
	public Message aliPay(@RequestBody Map p) {
		Message msg = new Message();
		try {

			/***************************验证短信验证码bengin****************************/
			List<MessagePay> msgList = payOrderService.msgList(p);
			if(msgList.isEmpty()){
				msg.setCode(Constant.INTEGER_NEG_ONE);
				msg.setRequestFlag(false);
				msg.setMesssage("验证码不正确");
				return msg;
			}
			// 系统当前时间
			long nowTime = System.currentTimeMillis() / 1000;
			// 验证码更新的最新时间
			long lastSendTime = msgList.get(0).getCreateTime().getTime()/1000;
			// 二者时间差
			long timeDif = nowTime - lastSendTime;
			// 30分钟失效
			if (1800 < timeDif) {
				msg.setCode(Constant.INTEGER_NEG_TWO);
				msg.setRequestFlag(false);
				msg.setMesssage("对不起，验证码已失效，请重新发送");
				return msg;
			}
			/***************************验证短信验证码end****************************/
			
			Map<String, Object> result = new HashMap<String, Object>();
			Long scenicProjectId = new Long(String.valueOf(p.get("id")));
			//验证项目id
			if(scenicProjectId == null){
				msg.setCode(Constant.INTEGER_NEG_THREE);
				msg.setRequestFlag(false);
				msg.setMesssage("项目Id不能为空");
				return msg;
			}
			
			/************************订单业务开始*****************************/
			PropertiesVo po = PropertiesUtil.getProperties();
			orderIdentify = po.getOrderIdentify();
			//查询商品信息
			List<Map> list = this.payOrderService.scenicDetail(scenicProjectId);
			//驻户Id
			Long customerId = Long.valueOf(String.valueOf(list.get(0).get("customerId")));
			// 数据库价格
			Double dbPrice = Double.valueOf(list.get(0).get("price").toString());
			//商品名称
			String scenicName = list.get(0).get("name").toString();
			// 数量
			Integer num = new Integer(p.get("num").toString());
			// 前台单价
			Double price = new Double(p.get("price").toString());
			
			BigDecimal data1 = new BigDecimal(dbPrice);  
		    BigDecimal data2 = new BigDecimal(price); 
			//对比两个价格是否相等
			if(data1.compareTo(data2) !=0){
				msg.setCode(Constant.INTEGER_NEG_FOUR);
				msg.setRequestFlag(false);
				msg.setMesssage("两次价格不相等");
				return msg;
			}
			
			//总金额
			Double orderAmount = ArithUtil.mul(num, dbPrice);
			// 支付方式ID
			Integer payWayId = new Integer(p.get("payWayId").toString());
			// 当前时间
			Timestamp now = new Timestamp(System.currentTimeMillis());
			// 手机号
			String mobilePhone = p.get("phone").toString();
			// 项目编码
			String projectCode = ProjectCodeUtil.getProjectCode(p.get("projectCode").toString());
			// 订单号
			String orderSn = String.valueOf("A"+projectCode+orderIdentify + dateToString()
								+ GenerateCode.randomNumber(0, 8));
			
			PayOrder ord = new PayOrder();
			ord.setOrderSn(orderSn);
			ord.setStatus(new Integer(0));
			ord.setScenicProjectId(scenicProjectId);
			ord.setNum(num);
			ord.setPrice(price);
			ord.setOrderAmount(orderAmount);
			ord.setPayWayId(payWayId);
			ord.setCreateTime(now);
			ord.setMobilePhone(mobilePhone);
			ord.setCustomerId(customerId);
			ord.setProjectCode(p.get("projectCode").toString());
			this.payOrderService.savePayOrder(ord);
			
			/************************订单业务结束*****************************/
			
			/************************支付业务开始*****************************/
			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.service);
			sParaTemp.put("partner", AlipayConfig.partner);
			sParaTemp.put("seller_id", AlipayConfig.seller_id);
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", AlipayConfig.payment_type);
			sParaTemp.put("notify_url", po.getAlipay_notify_url());
			sParaTemp.put("return_url", AlipayConfig.return_url+"_"+orderSn);
			sParaTemp.put("out_trade_no", orderSn);
			sParaTemp.put("subject", "购买商品");
			sParaTemp.put("total_fee", orderAmount.toString());
			sParaTemp.put("body", "购买商品");

			// 除去数组中的空值和签名参数
			Map<String, String> sPara = new HashMap<String, String>();
			sPara = AlipaySubmit.buildRequestPara(sParaTemp);
			
			Map<String, String> params = new HashMap<String, String>();
			String reqParams = AlipayCore.createLinkString(sPara);
			params.put("reqParams", reqParams);


			msg.setRequestFlag(true);
			msg.setResponseEntiy(params);
			msg.setMesssage("获取支付请求参数成功");
		} catch (Exception e) {
			msg.setRequestFlag(false);
			msg.setResponseEntiy(null);
			msg.setMesssage("获取支付请求参数失败");
			e.printStackTrace();
		}
		return msg;
	}

	
	/**
	 * 支付宝异步通知支付结果 如果反馈给支付宝的不是“success”，支付宝服务器会在25小时内重复发送8次通知，
	 * 通知的间隔频率：2m,10m,10m,1h,2h,6h,15h
	 * @author dongjun
	 * @date 2016年6月23日 下午4:45:11
	 * @see
	 */
	@RequestMapping(value = "/alipaySyncNotify", method = RequestMethod.POST)
	@ResponseBody
	public void alipaySyncNotify() {
		logger.info("=====支付宝异步通信=======");
		String result = "fail";
		try {
			AlipayNotifyEntity alipayNotifyEntity = notifyService.handleNotify(
					request, response);
			// 支付完成
			if (alipayNotifyEntity != null
					&& (alipayNotifyEntity.getTrade_status().equals(
							"TRADE_SUCCESS") || alipayNotifyEntity
							.getTrade_status().equals("TRADE_FINISHED"))) {
				logger.info("支付宝支付完成异步通信， alipayNotifyStr: "
						+ alipayNotifyEntity);
				String out_trade_no = alipayNotifyEntity.getOut_trade_no();
				logger.info("支付宝支付完成异步通信, out_trade_no: " + out_trade_no);
				
				//订单号
				alipayNotifyEntity.setOrderSn(out_trade_no);
				if (this.payFinish(alipayNotifyEntity)) {
					result = "success";
					logger.info("支付宝支付完成异步通信, result====" + result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(result);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}

	}
	
	
	/**
	 * 支付完成后操作 1. 支付记录支付状态更新为2 2. 更新订单支付状态 3. 支付记录支付状态更新为3
	 * 
	 * @Description: TODO
	 * @author zcc
	 * @date 2015年5月19日 上午8:27:32
	 * @throws
	 */
	private boolean payFinish(AlipayNotifyEntity alipay) {
		// 支付完成后续业务是否执行完成
		boolean flag = false;
		try {
			//查询最新订单信息
			PayOrder pd = this.payOrderService.getTopPayOrder();
			// 短信验证码/核销码
			String verificateNo = "";
			//如果没有核销码就new一个
			if(StringUtils.isBlank(pd.getVerificateNo())){
				verificateNo = alipay.getOrderSn().substring(1,3)+"000000001";
			}else{
				verificateNo = alipay.getOrderSn().substring(1,3)+newVerificateNo(pd.getVerificateNo());
			}
			
			// 二维码字符串
			String qrcodeStr = String.valueOf(WxConstant.QRCODE_URL) + "?verificateNo=" + verificateNo;
			// 保存二维码并获取二维码文件名称
			String fileName = UploadUtil.doQrcodeUpload(qrcodeStr);
			
			// 服务器地址
//			String basePath = request.getScheme() + "://"
//							+ request.getServerName() + ":" + request.getServerPort();
			String codeUrl = "";
			// 二维码地址
			codeUrl = FileManagerUtil.getPicturePath(fileName, "qrcode") + fileName;
			
			Map p = new HashMap();
			p.put("qrcodeStr", qrcodeStr);
			p.put("verificateNo", verificateNo);
			p.put("qrcodePic", fileName);
			p.put("orderSn", alipay.getOrderSn());
			//更新订单状态
			this.payOrderService.updateOrderByOrderSn(p);
			
			//根据订单号查询订单信息
			PayOrder pOrd = this.payOrderService.getPayOrder(alipay.getOrderSn());
			//查询商品信息
			List<Map> list = this.payOrderService.scenicDetail(pOrd.getScenicProjectId());
			//商品名称
			String scenicName = list.get(0).get("name").toString();
			//发送短信二维码
			String sendStatus = HttpSender.sendMessage(pOrd.getMobilePhone(), "您已经通过支付宝购买了【"+scenicName+"】，核销码："+verificateNo+"，二维码地址为："+codeUrl);
			logger.info("~~~~~~~~~~~~~~短信发送结果~~~~~~~~~~~~~~~" + sendStatus);
			// 修改销量
			this.payOrderService.updateScenicSalesNum(pOrd.getScenicProjectId());
			//保存支付宝支付记录
			this.notifyService.saveAliNotify(alipay);
			
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	private static String newVerificateNo(String verificateNo){
		
		String digestStr = verificateNo.substring(2, 11);
		//每次加1
		Integer target = Integer.valueOf(digestStr) + 1;
		int length = target.toString().length(); 
		String targetStr = "";
		if(length == 1){
			targetStr = "00000000"+target.toString();
		}else if(length == 2){
			targetStr = "0000000"+target.toString();
		}else if(length == 3){
			targetStr = "000000"+target.toString();
		}else if(length == 4){
			targetStr = "00000"+target.toString();
		}else if(length == 5){
			targetStr = "0000"+target.toString();
		}else if(length == 6){
			targetStr = "000"+target.toString();
		}else if(length == 7){
			targetStr = "00"+target.toString();
		}else if(length == 8){
			targetStr = "0"+target.toString();
		}else if(length == 9){
			targetStr = target.toString();
		}
		return targetStr;
	}
	/**
	 * 格式化时间
	 * 
	 * @return
	 */
	public String dateToString() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String dateString = sf.format(new Date());
		return dateString;
	}
}
