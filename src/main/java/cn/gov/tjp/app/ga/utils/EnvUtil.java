package cn.gov.tjp.app.ga.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

/**
 * 环境配置文件操作工具类<br>
 * 默认是"env.properties"
 * 
 * @author Nansen
 *
 */
public class EnvUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(EnvUtil.class);
	private static int osType = 0;// 系统标识，默认为"0"，"Windows"系统为"1"，"Linux"系统为"2"

	/**
	 * 资源文件读取类
	 */
	private static Properties PRO = new Properties();

	/**
	 * 获取"env.properties"配置文件中的值
	 * 
	 * @param key
	 *            :参数
	 * @return 返回"key"对应的值
	 */
	public static String getValue(String key) {
		if (osType == 0) {
			loadEnv();
		}
		return (osType == 1) ? PRO.getProperty(key) : System.getenv(key);
	}

	/**
	 * 加载配置文件
	 */
	private static void loadEnv() {
		String osName = System.getProperty("os.name");// 获取当前系统名
        InputStream in = null;
		// 如果系统名称不为空并且以"win"开头，为"Windows"系统，否则为"Linux"系统
		if ((!CommonUtil.isEmpty(osName) && (osName.toLowerCase().startsWith("win")))) {
			try {
				in = new BufferedInputStream(new FileInputStream(getSourcePath()));
				PRO.load(new InputStreamReader(in, "utf-8"));
				//PRO.load(new FileInputStream(getSourcePath()));
				osType = 1;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}finally{
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    System.out.println(e.getMessage());
	                }
	            }
			}
		} else {
			osType = 2;
		}
	}

	/**
	 * 获取资源文件的路径
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年11月30日上午11:45:08
	 */
	private static String getSourcePath() {
		String configfile = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		configfile = configfile + File.separator + "WEB-INF" + File.separator + "config" + File.separator + "env.properties";
		return configfile;
	}
}
