package org.meiconjun.crawler.food.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * 微博爬虫相关工具类
 * @author baosh
 *
 */
public class WeiboUtil {
	private static Logger logger = Logger.getLogger(WeiboUtil.class);
	
	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");//脚本引擎
	private static Invocable iv = null;
	static {
		//TODO  此处获取js文件路径方式有待优化，用ClassPathResource从相对路径获取
		Resource aesJs = new FileSystemResource("E:\\git\\FoodCrawler\\FoodCrawler Maven Webapp\\src\\main\\webapp\\javaScript\\util\\weiboUtil.js");
		try {
			engine.eval(new FileReader(aesJs.getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iv = (Invocable) engine;
	}
	/**
	 * 对用户名进行base64加密
	 * @param str
	 * @return
	 */
	public static String base64Encrypt(String str) {
		String retStr = "";
		try {
			
			retStr = (String) iv.invokeFunction("base64Encrypt", CommonUtil.urlEncoding(str));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行js脚本出错:" + e.getMessage());
		}
		return retStr;
	}
	
	public static String rsaKeyEncrypt(Map me, String str) {
		String retStr = "";
		try {
			retStr = (String) iv.invokeFunction("rsaKeyEncrypt", me, str);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行js脚本出错:" + e.getMessage());
		}
		return retStr;
	}
}
