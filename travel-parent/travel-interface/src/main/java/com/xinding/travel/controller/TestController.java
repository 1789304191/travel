package com.xinding.travel.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinding.travel.service.IRedisService;
import com.xinding.travel.service.ITestService;

@Controller
@RequestMapping("/jedis")
public class TestController{
	
	@Autowired
	private ITestService testService;
	
	@Autowired
	protected IRedisService redisService ;
	
	@RequestMapping(value="/set", method= RequestMethod.GET)
    @ResponseBody
    public void set() {
//		redisService.set("enable_store","5");
//		testService.updateStore1("2");
		
//		System.out.println(Integer.valueOf(null));
//		testService.updateStore("0");
		testService.updateStore2("0");
	}
	
	@RequestMapping(value="/set1", method= RequestMethod.GET)
    @ResponseBody
    public void set1() {
		redisService.set("enable_store","5");
	}
	
	@RequestMapping(value="/test", method= RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("all")
    public void test() {
		
		String product_id="";
		product_id = redisService.get("enable_store");
		int enable_store = Integer.valueOf((String) (product_id));
		if(enable_store==0){
			Map p=new HashMap();
			p.put("remark","库存不足");
			p.put("time",new Timestamp(System.currentTimeMillis()));
			testService.insert(p);
			return;
		}
		// *************加锁排队开始*********************
					// 超时时间20秒
		long timeout = 20000;
		boolean flag=false;
		boolean b = this.lock(timeout,redisService);
		if (b) {
			// 排队操作库存
			flag = checkStore(redisService, flag);
			// 库存满足，调用生成订单接口
			if (flag) {
				Map p=new HashMap();
				p.put("remark","购买成功");
				p.put("time", new Timestamp(System.currentTimeMillis()));
				testService.insert(p);
			}

		} else {
			Map p=new HashMap();
			p.put("remark","库存不足");
			p.put("time", new Timestamp(System.currentTimeMillis()));
			testService.insert(p);
		}
		// 释放锁
		this.unLock(redisService);
		return;
	}
	
	public boolean checkStore( IRedisService jedis,
			boolean flag) {
			String product_id = null;
			if (jedis.get("enable_store") == null) {
				product_id = "0";
			} else {
				product_id = jedis.get("enable_store");
			}
			int enable_store = Integer.valueOf((String) (product_id));
			if (enable_store >0) {

				jedis.set("enable_store",
						String.valueOf(enable_store - 1));
				// 修改商品规则中的库存
				testService.updateStore(
						enable_store - 1+"");
				flag=true;
			} else {
				flag=false;
			}
		return flag;
	}
	
	public boolean lock(long timeout, IRedisService jedis) {
		// 锁状态
		boolean lockSuccess = false;
		// 死锁失效时间
		int LOCKKEY_EXPIRE_TIME = 5;

		long start = System.currentTimeMillis();
		String lockKey = "lock_all";
		do {
			// setnx当且仅当 key 不存在，将 key 的值设为 value ，并返回1；若给定的 key 已经存在，则 SETNX
			// 不做任何动作，并返回0。
			long result = jedis.setnx(
					lockKey,
					String.valueOf(System.currentTimeMillis()
							+ LOCKKEY_EXPIRE_TIME * 1000 + 1));
			// 当result==1,表示当前无锁,则该线程通过
			if (result == 1) {
				lockSuccess = true;
				break;
			} else {
				// 当result!=1,表示当前有锁,则该线程去判断之前的线程锁是否失效
				String lockTimeStr = jedis.get(lockKey);
				// 如果key存在，锁存在
				if (StringUtils.isNumeric(lockTimeStr)) {

					long lockTime = Long.valueOf(lockTimeStr);
					// 锁已过期
					if (lockTime < System.currentTimeMillis()) {
						String originStr = jedis.getSet(
								lockKey,
								String.valueOf(System.currentTimeMillis()
										+ LOCKKEY_EXPIRE_TIME * 1000 + 1));
						// 表明锁由该线程获得
						if (StringUtils.isNotBlank(originStr)
								&& originStr.equals(lockTimeStr)) {
							lockSuccess = true;
							break;
						}
					}

				}

			}
			// 如果不等待，则直接返回
			if (timeout == 0) {
				break;
			}
			// 等待300ms继续加锁
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ((System.currentTimeMillis() - start) < timeout);

		return lockSuccess;

	}
	
	public void unLock(IRedisService jedis) {

		String lockKey = "lock_all";
		// 删除锁
		jedis.del(lockKey);

	}

}
