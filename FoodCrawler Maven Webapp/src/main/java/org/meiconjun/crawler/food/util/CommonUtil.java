package org.meiconjun.crawler.food.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
