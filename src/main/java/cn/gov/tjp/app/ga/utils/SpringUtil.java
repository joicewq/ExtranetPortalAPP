package cn.gov.tjp.app.ga.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用于操作"Spring"的工具类
 */
public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
	}

	/**
	 * 根据类名来获取Spring中的对象
	 * 
	 * @author : Nansen
	 * @param beanName
	 * @return
	 * @date : 2017年9月22日下午2:44:03
	 */
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}

	/**
	 * 根据类来获取Spring中的对象
	 * 
	 * @author : Nansen
	 * @param _class
	 * @return
	 * @date : 2017年9月22日下午2:43:46
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getBean(Class _class) {
		return CONTEXT.getBean(_class);
	}
}
