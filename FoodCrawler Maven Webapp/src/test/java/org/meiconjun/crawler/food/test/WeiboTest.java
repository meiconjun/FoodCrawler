package org.meiconjun.crawler.food.test;

import java.util.HashMap;
import java.util.Map;

import org.meiconjun.crawler.food.dto.RetObj;
import org.meiconjun.crawler.food.util.CommonUtil;
import org.meiconjun.crawler.food.util.SendRequestUtil;
import org.meiconjun.crawler.food.util.WeiboUtil;
import com.google.gson.reflect.TypeToken;

/**
 * 微博爬虫测试
 * @author baosh
 *
 */
public class WeiboTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, String> condMap = new HashMap();
		condMap.put("entry", "weibo");
		condMap.put("client", "ssologin.js(v1.4.19)");
		condMap.put("callback", "sinaSSOController.preloginCallBack");
		condMap.put("su", WeiboUtil.base64Encrypt(CommonUtil.urlEncoding("baoshiki@vip.qq.com")));
		condMap.put("rsakt", "mod");
		condMap.put("_", CommonUtil.getCurTimeStampMsec());
		
		String preLoginUrl = "https://login.sina.com.cn/sso/prelogin.php";
		
		RetObj reObj = SendRequestUtil.doGet(preLoginUrl, null, condMap);
		System.out.println("retCode : " + reObj.getRetCode());
		Map retMap = reObj.getRetMap();
//		System.out.println(retMap.get("retStr"));
		String retStr = (String)retMap.get("retStr");
		retStr = retStr.substring(retStr.indexOf("preloginCallBack(") + 17, retStr.indexOf("})") + 1);
		System.out.println(retStr);
		Map mm = (Map) CommonUtil.jsonToObj(retStr, new TypeToken<Map<String, String>>(){}.getType());
		System.out.println(CommonUtil.objToJson(mm));
		String sp = WeiboUtil.rsaKeyEncrypt(mm, "24657321jikuy*");
		
		Map loginMap = new HashMap();
		loginMap.put("entry", "weibo");
		loginMap.put("gateway", "1");
		loginMap.put("from", "");
		loginMap.put("savestate", "7");
		loginMap.put("qrcode_flag", "false");
		loginMap.put("useticket", "1");
		loginMap.put("pagerefer", "https://login.sina.com.cn/crossdomain2.php?action=logout&r=https%3A%2F%2Fpassport.weibo.com%2Fwbsso%2Flogout%3Fr%3Dhttps%253A%252F%252Fweibo.com%26returntype%3D1");
		loginMap.put("vsnf", "1");
		loginMap.put("su", WeiboUtil.base64Encrypt(CommonUtil.urlEncoding("baoshiki@vip.qq.com")));
		loginMap.put("service", "miniblog");
		loginMap.put("servertime", mm.get("servertime"));
		loginMap.put("nonce", mm.get("nonce"));
		loginMap.put("pwencode", "rsa2");
		loginMap.put("rsakv", mm.get("rsakv"));
		loginMap.put("sp", sp);
		loginMap.put("sr", "1920*1080");
		loginMap.put("encoding", "UTF-8");
		loginMap.put("prelt", "109");
		loginMap.put("url", "https://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
		loginMap.put("returntype", "META");
		
		String loginUrl = "https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.19)";
		SendRequestUtil.setCharset("GBK");
		RetObj ro = SendRequestUtil.doPost(loginUrl, null, loginMap);
		Map reMap = ro.getRetMap();
		System.out.println(reMap.get("retStr"));
	}
	
}
