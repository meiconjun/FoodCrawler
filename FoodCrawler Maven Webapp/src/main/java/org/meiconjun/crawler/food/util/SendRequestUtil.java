package org.meiconjun.crawler.food.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.meiconjun.crawler.food.dto.RetObj;

/**
 * 发送http请求工具
 * @author meiconjun
 * @time 2019年4月6日下午5:05:25
 */
public class SendRequestUtil {
	/** 日志对象 **/
	private static Logger logger = Logger.getLogger(SendRequestUtil.class);
	/** HttpClient实例 **/
	private static HttpClient client = HttpClients.custom().build();
	/** http响应 **/
	private static HttpResponse response = null;
	/** 网页字符集 **/
	private static String charSet = "utf-8";
	/** 从连接池中获取连接的超时时间 **/
	private static String requestTimeout = "10000";
	/** 建立连接的超时时间 **/
	private static String connectTimeout = "10000";
	/** 请求获取数据的超时时间 **/
	private static String socketTimeout = "10000";
	/**
	 * 执行get请求
	 * @author meiconjun
	 * @time 2019年4月6日下午5:24:02
	 * @param url 请求地址
	 * @param headers 请求头信息列表
	 * @param paramMap 表单参数
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RetObj doGet(String url, Map<String, String> headers, Map<String, String> paramMap)throws Exception {
		RetObj retObj = new RetObj();
		//设置表单参数
		if (null != paramMap) {
			logger.debug("----------------doGet:设置表单参数----------------");
			//将参数转为aaa=aaa&bbb=bbb字符串形式
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String key : paramMap.keySet()) {
				params.add(new BasicNameValuePair(key, paramMap.get(key)));
			}
			String paramStr = "?" + EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
			url += paramStr;
		}
		HttpGet httpGet = new HttpGet(url);
		//请求头配置
		//设置随机user-agent，防反爬
		httpGet.setHeader("User-Agent", UserAgentUtil.getRandomUserAgent());
		//响应体
		HttpEntity httpEntity = null;
		if (null != headers) {
			logger.debug("----------------doGet:设置请求头----------------");
			for (String key : headers.keySet()) {
				httpGet.setHeader(key, headers.get(key));
			}
		}
		try {
			logger.debug("----------------doGet:发送请求----------------");
			response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (200 == statusCode) {
				httpEntity = response.getEntity();
				//请求成功
//				String charset = CommonUtil.getStreamCharset(httpEntity.getContent());
//				logger.debug("-----------字符集：" + charset);
				String contCharSet = getEntityContEncoding(httpEntity);
				logger.debug("-----------字符集：" + contCharSet);
				String entityStr = EntityUtils.toString(httpEntity, CommonUtil.isStrBlank(contCharSet) ? charSet : contCharSet);//获取网页编码的问题有待解决
				logger.debug("doGet:请求成功，响应体内容：" + entityStr);
				retObj.setRetCode("0");
				HashMap retMap = new HashMap();
				retMap.put("retStr", entityStr);
				retObj.setRetMap(retMap);
			} else {
				logger.error("doGet:请求失败，返回报文：" + EntityUtils.toString(response.getEntity()));
				logger.error("doGet:请求失败，状态码：" + statusCode);
				retObj.setRetCode("1");
				retObj.setRetMsg("状态码：" + statusCode);
			}
		} catch(IOException e) {
			logger.error("----------------doGet:发送请求出现异常----------------");
			e.printStackTrace();
			throw e;
		} finally {
			//关闭资源
			if (null != httpEntity) {
				EntityUtils.consume(httpEntity);
			}
			httpGet.abort();
		}
		return retObj;
	}
	
	/**
	 * 执行post请求
	 * @author meiconjun
	 * @time 2019年4月7日下午3:50:10
	 * @param url 请求地址
	 * @param headers 请求头信息列表
	 * @param paramMap 表单参数
	 * @throws IOException 
	 */
	@SuppressWarnings({  "rawtypes", "unchecked" })
	public static RetObj doPost(String url, Map<String, String> headers, Map<String, String> paramMap) throws IOException {
		RetObj retObj = new RetObj();
		HttpPost httpPost = new HttpPost(url);
		if (null != paramMap){
			logger.debug("----------------doPost:设置表单参数----------------");
			//表单数组
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (String key : paramMap.keySet()) {
				nvps.add(new BasicNameValuePair(key, paramMap.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		}
		//请求头配置
		//设置随机user-agent，防反爬
		httpPost.setHeader("User-Agent", UserAgentUtil.getRandomUserAgent());
		//响应体
		HttpEntity httpEntity = null;
		if (null != headers) {
			logger.debug("----------------doPost:设置请求头----------------");
			for (String key : headers.keySet()) {
				httpPost.setHeader(key, headers.get(key));
			}
		}
		try {
			logger.debug("----------------doPost:发送请求----------------");
			response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (200 == statusCode) {
				httpEntity = response.getEntity();
				//请求成功
//				String charset = CommonUtil.getStreamCharset(httpEntity.getContent());
//				logger.debug("-----------字符集：" + charset);
				String contCharSet = getEntityContEncoding(httpEntity);
				logger.debug("-----------字符集：" + contCharSet);
				String entityStr = EntityUtils.toString(httpEntity, CommonUtil.isStrBlank(contCharSet) ? charSet : contCharSet);//获取网页编码的问题有待解决
				logger.debug("doPost:请求成功，响应体内容：" + entityStr);
				retObj.setRetCode("0");
				HashMap retMap = new HashMap();
				retMap.put("retStr", entityStr);
				retObj.setRetMap(retMap);
			} else {
				logger.error("doPost:请求失败，返回报文：" + EntityUtils.toString(response.getEntity()));
				logger.error("doPost:请求失败，状态码：" + statusCode);
				retObj.setRetCode("1");
				retObj.setRetMsg("状态码：" + statusCode);
			}
		} catch(IOException e) {
			logger.error("----------------doPost:发送请求出现异常----------------");
			e.printStackTrace();
			throw e;
		} finally {
			//关闭资源
			if (null != httpEntity) {
				EntityUtils.consume(httpEntity);
			}
			httpPost.abort();
		}
		return retObj;
	}

	/**
	 * 从响应头中获取响应体编码
	 * @param he
	 * @return
	 */
	private static String getEntityContEncoding(HttpEntity he) {
		Header header = he.getContentType();
		HeaderElement[] hes = header.getElements();
		for (HeaderElement e : hes) {
			for (NameValuePair p : e.getParameters()) {
				if ("charset".equals(p.getName())) {
					return p.getValue();
				}
			}
		}
		return null;
	}
	/**
	 * @return the charset
	 */
	public static String getCharset() {
		return charSet;
	}

	/**
	 * @param charset the charset to set
	 */
	public static void setCharset(String charset) {
		SendRequestUtil.charSet = charset;
	}

	/**
	 * @return the requestTimeout
	 */
	public static String getRequestTimeout() {
		return requestTimeout;
	}

	/**
	 * @param requestTimeout the requestTimeout to set
	 */
	public static void setRequestTimeout(String requestTimeout) {
		SendRequestUtil.requestTimeout = requestTimeout;
	}

	/**
	 * @return the connectTimeout
	 */
	public static String getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public static void setConnectTimeout(String connectTimeout) {
		SendRequestUtil.connectTimeout = connectTimeout;
	}

	/**
	 * @return the socketTimeout
	 */
	public static String getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * @param socketTimeout the socketTimeout to set
	 */
	public static void setSocketTimeout(String socketTimeout) {
		SendRequestUtil.socketTimeout = socketTimeout;
	}
	
}
