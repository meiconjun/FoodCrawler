package org.meiconjun.crawler.food.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

/**
 * 公共工具类
 * 
 * @author meiconjun
 * @time 2019年4月7日上午1:02:03
 */
public class CommonUtil {
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isStrBlank(String str) {
		if (null == str || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 获取当前毫秒时间戳
	 * @return
	 */
	public static String getCurTimeStampMsec() {
		return String.valueOf(System.currentTimeMillis());
	}
	/**
	 * 获取当前秒时间戳
	 * @return
	 */
	public static String getCurTimeStampSec() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	/**
	 * 对URL进行特殊字符编码
	 * @param url
	 * @return
	 */
	public static String urlEncoding(String url) {
		return URLEncoder.encode(url, Charset.forName("utf8"))
				.replace("\\+", "%20").replace("\\!", "%21").replace("\\'", "%27")
				.replace("\\(", "%28").replace("\\)", "%29").replace("\\~", "%7E");
	}
	/**
	 * 对编码后的URL进行解码
	 * @param url
	 * @return
	 */
	public static String urlDecoding(String url) {
		return URLDecoder.decode(url, Charset.forName("utf8"));
	}
	/**
	 * 检测流的字符集
	 * 
	 * @author meiconjun
	 * @time 2019年4月7日上午1:02:57
	 */
	public static String getStreamCharset(InputStream is) throws Exception {
		byte[] b = input2byte(is);
		ByteArrayInputStream bais = new ByteArrayInputStream(b);

		CodepageDetectorProxy cdp = CodepageDetectorProxy.getInstance();
		cdp.add(JChardetFacade.getInstance());// 依赖jar包 antlr.jar&chardet.jar
		cdp.add(ASCIIDetector.getInstance());
		cdp.add(UnicodeDetector.getInstance());
		cdp.add(new ParsingDetector(false));
		cdp.add(new ByteOrderMarkDetector());

		Charset charset = null;
		try {
			charset = cdp.detectCodepage(bais, 2147483647);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			bais.close();
		}
		return charset == null ? null : charset.name().toLowerCase();
	}

	public static InputStream byte2Input(byte[] buf) {
		return new ByteArrayInputStream(buf);
	}

	public static byte[] input2byte(InputStream inStream) throws Exception{
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		byte[] in2b = null;
		try {
			while ((rc = inStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			in2b = swapStream.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			swapStream.close();
		}
		return in2b;
	}
}
