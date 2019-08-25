package org.meiconjun.crawler.food.test;

import org.meiconjun.crawler.food.util.CommonUtil;
import org.meiconjun.crawler.food.util.WeiboUtil;

/**
 * 微博爬虫测试
 * @author baosh
 *
 */
public class WeiboTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String loginPreUtil = "https://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=&rsakt=mod&client=ssologin.js(v1.4.19)&_=1566640406070";
		System.out.println(WeiboUtil.base64Encrypt("baoshiki@vip.qq.com"));
	}
	
}
