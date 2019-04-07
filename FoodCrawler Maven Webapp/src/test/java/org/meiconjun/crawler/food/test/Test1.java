/**
 * @author meiconjun
 * @time 2019年4月6日下午4:59:25
 */
package org.meiconjun.crawler.food.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.meiconjun.crawler.food.dto.RetObj;
import org.meiconjun.crawler.food.util.SendRequestUtil;

/**
 * @author meiconjun
 * @time 2019年4月6日下午4:59:25
 */
public class Test1 {

	/**
	 * @author meiconjun
	 * @throws Exception 
	 * @time 2019年4月6日下午4:59:25
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String userAgent = UserAgentUtil.getRandomUserAgent();
//		System.out.println(userAgent);
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("aaa", "aaa"));
//		params.add(new BasicNameValuePair("bbb", "bbb"));
//		String s = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
//		System.out.println(s);
		SendRequestUtil.setCharset("gb2312");
		RetObj retObj = SendRequestUtil.doPost("http://www.w3school.com.cn/b.asp", null, null);
	}

}
