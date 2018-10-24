package cn.gov.tjp.app.ga.timer;

import org.springframework.beans.factory.InitializingBean;

import cn.gov.tjp.app.ga.timer.job.ScanExternalNewsJob;
import cn.gov.tjp.app.ga.utils.EnvUtil;

/**
 * 定时任务管理类
 */
public class JobManager implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		TimerManager.addJob("ScanExternalNewsJob", EnvUtil.getValue("SCAN_EXTERNAL_NEWS_JOB_TIMER"), ScanExternalNewsJob.class);
	}
}
