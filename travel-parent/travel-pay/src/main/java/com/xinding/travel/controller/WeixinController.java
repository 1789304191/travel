package com.xinding.travel.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.xinding.travel.pojo.MessagePay;
import com.xinding.travel.pojo.PayOrder;
import com.xinding.travel.service.INotifyService;
import com.xinding.travel.service.IPayOrderService;
import com.xinding.travel.util.ArithUtil;
import com.xinding.travel.util.Constant;
import com.xinding.travel.util.DateUtil;
import com.xinding.travel.util.FileManagerUtil;
import com.xinding.travel.util.GenerateCode;
import com.xinding.travel.util.HttpRequestClient;
import com.xinding.travel.util.HttpSender;
import com.xinding.travel.util.JsonUtil;
import com.xinding.travel.util.Message;
import com.xinding.travel.util.NumberFormat;
import com.xinding.travel.util.ProjectCodeUtil;
import com.xinding.travel.util.PropertiesUtil;
import com.xinding.travel.util.UploadUtil;
import com.xinding.travel.util.XmlConverUtil;
import com.xinding.travel.vo.PropertiesVo;
import com.xinding.travel.weixin.common.Configure;
import com.xinding.travel.weixin.common.WxConstant;
import com.xinding.travel.weixin.common.RandomStringGenerator;
import com.xinding.travel.weixin.common.Signature;
import com.xinding.travel.weixin.common.WeixinPayBean;
import com.xinding.travel.weixin.util.WeixinUtil;

@Controller
@RequestMapping("/weixinPay")
public class WeixinController extends BaseController {
	private static Logger log = Logger.getLogger(WeixinController.class);
	private  String orderIdentify = "";
	@Autowired
	private IPayOrderService payOrderService;
	@Autowired
	private INotifyService notifyService;

	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value="/weixinPay", method= RequestMethod.POST)
    @ResponseBody
	public Message weixinPay(@RequestBody Map p){
		Message msg = new Message();
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			
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
			
			Long scenicProjectId = new Long(String.valueOf(p.get("id")));
			//验证项目id
			if(scenicProjectId == null){
				msg.setCode(Constant.INTEGER_NEG_THREE);
				msg.setRequestFlag(false);
				msg.setMesssage("项目Id不能为空");
				return msg;
			}
			
			/******************************************订单业务***************************************************/
			PropertiesVo po = PropertiesUtil.getProperties();
			orderIdentify = po.getOrderIdentify();
			//查询商品信息
			List<Map> list1 = this.payOrderService.scenicDetail(scenicProjectId);
			//驻户Id
			Long customerId = Long.valueOf(String.valueOf(list1.get(0).get("customerId")));
			// 数据库价格
			Double dbPrice = Double.valueOf(list1.get(0).get("price").toString());
			
			// 数量
			Integer num = new Integer(p.get("num").toString());
			// 单价
			Double price = new Double(p.get("price").toString());
			//总金额
			Double orderAmount = ArithUtil.mul(num, price);
			// 支付方式ID
			Integer payWayId = new Integer(p.get("payWayId").toString());
			// 当前时间
			Timestamp now = new Timestamp(System.currentTimeMillis());
			// 手机号
			String mobilePhone = p.get("phone").toString();

			// APP和网页支付提交用户端ip
			String spbill_create_ip = p.get("spbill_create_ip").toString();
			// 用户同意授权，获取code
			String code = p.get("code").toString(); 
			// 项目编码
			String projectCode = ProjectCodeUtil.getProjectCode(p.get("projectCode").toString());
			// 订单号
			String orderSn = String.valueOf("W"+projectCode+orderIdentify + dateToString()
								+ GenerateCode.randomNumber(0, 8));
			
			BigDecimal data1 = new BigDecimal(dbPrice);  
		    BigDecimal data2 = new BigDecimal(price); 
			//对比两个价格是否相等
			if(data1.compareTo(data2) !=0){
				msg.setCode(Constant.INTEGER_NEG_FOUR);
				msg.setRequestFlag(false);
				msg.setMesssage("两次价格不相等");
				return msg;
			}
			
			// 验证code是否为空
			if (StringUtils.isBlank(code)) {
				msg.setCode(Constant.INTEGER_NEG_FIVE);
				msg.setRequestFlag(false);
				msg.setMesssage("用户同意授权code为空!");
				return msg;
			}
			
			
			List<PayOrder> list = this.payOrderService.payOrderListByCode(code);
			// 用户标识openid
			String openId = "";
			// 验证list是否为空
			if (list.isEmpty()) {
				openId = WeixinUtil.getWeixinOpenId(code);
			} else {
				openId = list.get(0).getOpenId();
			}

			PayOrder ord = new PayOrder();
			ord.setOrderSn(orderSn);
			ord.setStatus(new Integer(0));
			ord.setOpenId(openId);
			ord.setScenicProjectId(scenicProjectId);
			ord.setNum(num);
			ord.setPrice(price);
			ord.setOrderAmount(orderAmount);
			ord.setPayWayId(payWayId);
			ord.setCreateTime(now);
			ord.setMobilePhone(mobilePhone);
			ord.setCode(code);
			ord.setCustomerId(customerId);
			ord.setProjectCode(p.get("projectCode").toString());
			this.payOrderService.savePayOrder(ord);
			
			/***************************************************支付业务*********************************************************/
			log.info("微信用户openid===============" + openId);
			// 订单总金额（单位：分）
			Integer total_fee = (int) (orderAmount * 100);

			/**
			 * 微信支付准备step1,获取签名
			 **/

			WeixinPayBean bean = new WeixinPayBean();
			// 公众账号ID
			bean.setAppid(Configure.getAppid());
			// 附加数据
			bean.setAttach("购买商品");
			// 商品描述
			bean.setBody("购买商品");
			// 商户号
			bean.setMch_id(Configure.getMchid());
			// 随机字符串
			bean.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			// 通知地址
			bean.setNotify_url(po.getWxpay_notify_url());
			// 用户标识openid
			bean.setOpenid(openId);
			// 商户订单号
			bean.setOut_trade_no(orderSn);
			// 终端IP
			bean.setSpbill_create_ip(spbill_create_ip);
			// 总金额(单位：分)
			bean.setTotal_fee(total_fee);
			// 交易类型(公众号支付)
			bean.setTrade_type("JSAPI");
			// 支付准备签名
			String sign = Signature.getSign(bean);
			// 签名
			bean.setSign(sign);
			
			/**
			 * 微信支付准备step2，Javabean转xml，调起统一下单接口并获取返回结果转json
			 **/
			// 解决XStream对出现双下划线的bug
			XStream xs = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
			// javabean转xml
			String xmlObj = xs.toXML(bean);
			xmlObj = xmlObj.replace("<com.xinding.travel.weixin.common.WeixinPayBean>", "<xml>");
			xmlObj = xmlObj.replace("</com.xinding.travel.weixin.common.WeixinPayBean>", "</xml>");
			log.info("=====微信支付参数转xmlObj=======" + xmlObj);

			// 微信支付调用统一下单接口
			String returnResult = HttpRequestClient.sendPost(WxConstant.WX_UNIFIEDORDER, xmlObj);
			log.info("=====微信支付统一下单返回结果returnResult=======" + returnResult);
			// xml转json字符串
			String jsonString = "[" + XmlConverUtil.xmltoJson(returnResult) + "]";
			log.info("=====微信支付统一下单返回结果转xmlToJson=======" + jsonString);

			/**
			 * 微信支付准备step3，获取微信预付单返回信息并返回前台
			 **/
			String appId = JsonUtil.jsonToValue(jsonString, "appid", null).toString();
			// 时间戳
			Long timeStamp = DateUtil.getDatelineLong();
			String nonceStr = JsonUtil.jsonToValue(jsonString, "nonce_str", null).toString();
			String wxPackage = "prepay_id="
					+ JsonUtil.jsonToValue(jsonString, "prepay_id", null).toString();
			String trade_type = JsonUtil.jsonToValue(jsonString, "trade_type", null).toString();
			String return_msg = JsonUtil.jsonToValue(jsonString, "return_msg", null).toString();
			String result_code = JsonUtil.jsonToValue(jsonString, "result_code", null).toString();
			String mch_id = JsonUtil.jsonToValue(jsonString, "mch_id", null).toString();
			String return_code = JsonUtil.jsonToValue(jsonString, "return_code", null).toString();

			// 验证return_code和result_code是否都SUCCESS
			if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
				Map<String, Object> returnParams = new HashMap<String, Object>();
				// appid
				returnParams.put("appId", appId);
				// 时间戳
				returnParams.put("timeStamp", timeStamp.toString());
				// 随机字符串
				returnParams.put("nonceStr", nonceStr);
				// 打包信息prepay_id
				returnParams.put("package", wxPackage);
				// 签名方式
				returnParams.put("signType", "MD5");
				// 微信支付完成后 重新签名返还前台
				String paySign = Signature.getSign(returnParams);
				result.put("appId", appId);
				result.put("timeStamp", timeStamp);
				result.put("nonceStr", nonceStr);
				result.put("package", wxPackage);
				result.put("signType", "MD5");
				result.put("paySign", paySign);
				// 非微信字段（自定义字段）
				// 日期
				result.put("date", DateUtil.dateToString("yyyy-MM-dd"));
				// 订单号
				result.put("orderSn", orderSn);

				msg.setRequestFlag(true);
				msg.setCode(Constant.INTEGER_ZERO);
				msg.setResponseEntiy(result);
				msg.setMesssage("获取微信预支付信息成功！");
				log.info("============resq value:" + msg);
			} else {
				msg.setRequestFlag(false);
				msg.setCode(Constant.INTEGER_NEG_SIX);
				msg.setResponseEntiy(result);
				msg.setMesssage("获取微信预支付信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}
	
	
	/**
	 * 
	 * <p>
	 * TODO(微信支付成功之后调用异步通知接口更新订单的状态)
	 * </p>
	 * （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
	 * @author dongjun
	 * @date 2016年3月28日 上午9:53:49
	 * @return
	 * @see
	 */
	@RequestMapping(value="/weixinPayStatusNotify", method= RequestMethod.POST)
    @ResponseBody
	public void weixinPayStatusNotify() {
		String text = "FAIL";
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			// log.info("~~~~~~~~~微信付款成功~~~~~~~~~~~");
			outStream.close();
			inStream.close();
			// 从输出流中获取返回xml结果
			String result = new String(outStream.toByteArray(), "utf-8");
			log.info("~~~~~~~~~~~~~~微信支付异步通知返回结果~~~~~~~~~~~~~~~" + result);
			// xml转json字符串
			String jsonString = "[" + XmlConverUtil.xmltoJson(result) + "]";
			log.info("~~~~~~~~~微信支付异步通知返回结果转xmlToJson~~~~~~~~~" + jsonString);

			// 公众账号ID
			String appid = JsonUtil.jsonToValue(jsonString, "appid", null).toString();
			// 付款银行
			String bank_type = JsonUtil.jsonToValue(jsonString, "bank_type", null).toString();
			// 是否关注公众账号
			String is_subscribe = JsonUtil.jsonToValue(jsonString, "is_subscribe", null).toString();
			// 商户号
			String mch_id = JsonUtil.jsonToValue(jsonString, "mch_id", null).toString();
			// 随机字符串
			String nonce_str = JsonUtil.jsonToValue(jsonString, "nonce_str", null).toString();
			// 用户标识
			String openid = JsonUtil.jsonToValue(jsonString, "openid", null).toString();
			// 商户订单号
			String out_trade_no = JsonUtil.jsonToValue(jsonString, "out_trade_no", null).toString();
			// 业务结果
			String result_code = JsonUtil.jsonToValue(jsonString, "result_code", null).toString();
			// 返回状态码
			String return_code = JsonUtil.jsonToValue(jsonString, "return_code", null).toString();
			// 签名
			String sign = JsonUtil.jsonToValue(jsonString, "sign", null).toString();
			// 支付完成时间yyyyMMddHHmmss 转yyyy-MM-dd HH:mm:ss
			String time_end = DateUtil.formatString(JsonUtil.jsonToValue(jsonString, "time_end", null).toString());
			// 总金额(单位：分 to 元)
			Double total_fee = NumberFormat
					.numberFormat(JsonUtil.jsonToValue(jsonString, "total_fee", null).toString());

			// 交易类型
			String trade_type = JsonUtil.jsonToValue(jsonString, "trade_type", null).toString();
			// 微信支付订单号
			String transaction_id = JsonUtil.jsonToValue(jsonString, "transaction_id", null).toString();
			// 验证微信支付是否成功
			if (result_code.equalsIgnoreCase("SUCCESS")) {
				text = "SUCCESS";
				//查询最新订单信息
				PayOrder pd = this.payOrderService.getTopPayOrder();
				// 短信验证码/核销码
				String verificateNo = "";
				//如果没有核销码就new一个
				if(StringUtils.isBlank(pd.getVerificateNo())){
					verificateNo = out_trade_no.substring(1, 3)+"000000001";
				}else{
					verificateNo = out_trade_no.substring(1, 3)+newVerificateNo(pd.getVerificateNo());
				}
				
				// 二维码字符串
				String qrcodeStr = String.valueOf(WxConstant.QRCODE_URL) + "?verificateNo=" + verificateNo;
				// 保存二维码并获取二维码文件名称
				String fileName = UploadUtil.doQrcodeUpload(qrcodeStr);
				
				String codeUrl = "";
				// 二维码地址
				codeUrl = FileManagerUtil.getPicturePath(fileName, "qrcode") + fileName;
				
				Map p = new HashMap();
				p.put("qrcodeStr", qrcodeStr);
				p.put("verificateNo", verificateNo);
				p.put("qrcodePic", fileName);
				p.put("orderSn", out_trade_no);
				//更新订单状态
				this.payOrderService.updateOrderByOrderSn(p);
				
				//根据订单号查询订单信息
				PayOrder pOrd = this.payOrderService.getPayOrder(out_trade_no);
				//查询商品信息
				List<Map> list = this.payOrderService.scenicDetail(pOrd.getScenicProjectId());
				//商品名称
				String scenicName = list.get(0).get("name").toString();
				// 修改销量
				this.payOrderService.updateScenicSalesNum(pOrd.getScenicProjectId());
				//发送短信二维码
				String sendStatus = HttpSender.sendMessage(pOrd.getMobilePhone(), "您已经通过微信支付购买了【"+scenicName+"】，核销码："+verificateNo+"，二维码地址为："+codeUrl);
				log.info("~~~~~~~~~~~~~~短信发送结果~~~~~~~~~~~~~~~" + sendStatus);
			}
			
			Map recordMap = new HashMap();
			recordMap.put("appid", appid);
			recordMap.put("bank_type", bank_type);
			recordMap.put("is_subscribe", is_subscribe);
			recordMap.put("mch_id", mch_id);
			recordMap.put("nonce_str", nonce_str);
			recordMap.put("openid", openid);
			recordMap.put("out_trade_no", out_trade_no);
			recordMap.put("result_code", result_code);
			recordMap.put("return_code", return_code);
			recordMap.put("sign", sign);
			recordMap.put("time_end", time_end);
			recordMap.put("total_fee", total_fee);
			recordMap.put("trade_type", trade_type);
			recordMap.put("transaction_id", transaction_id);
			//保存微信支付记录
			this.notifyService.saveWxNotify(recordMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(text);
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
	
	
//	public static void main(String[] args) {
//		double a = 0.0001;  
//	    double b = 0.00001;  
//	    BigDecimal data1 = new BigDecimal(a);  
//	    BigDecimal data2 = new BigDecimal(b);  
//	    System.out.print(data1.compareTo(data2) !=0 );  
//	}
}
