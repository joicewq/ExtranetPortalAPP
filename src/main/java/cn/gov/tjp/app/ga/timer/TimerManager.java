package cn.gov.tjp.app.ga.timer;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时器管理类
 */
public class TimerManager {
	private static SchedulerFactory SF = new StdSchedulerFactory();

	/**
	 * 返回一个"Scheduler"
	 * 
	 * @author : Nansen
	 * 
	 * @return
	 * @throws SchedulerException
	 * @date : 2017年10月10日下午3:22:10
	 */
	private static synchronized Scheduler getScheduler() throws SchedulerException {
		return SF.getScheduler();
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @author : Nansen
	 * 
	 * @param scheduler
	 *            :调度器
	 * @param jobName
	 *            :任务名
	 * @param time
	 *            :时间
	 * @date : 2017年10月10日下午3:17:13
	 */
	@SuppressWarnings("rawtypes")
	public static void addJob(String jobName, String time, Class jobClass) {
		addJob(null, jobName, time, jobClass);
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @author : Nansen
	 * 
	 * @param groupName
	 *            :任务组名
	 * @param scheduler
	 *            :调度器
	 * @param jobName
	 *            :任务名
	 * @param time
	 *            :时间
	 * @date : 2017年10月10日下午3:17:13
	 */
	@SuppressWarnings("rawtypes")
	public static void addJob(String groupName, String jobName, String time, Class jobClass) {
		try {
			Scheduler scheduler = getScheduler();
			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(jobName, groupName);

			// 如果该任务不存在才新建
			if (cronTrigger == null) {
				cronTrigger = new CronTrigger(jobName);// 触发器
				JobDetail jobDetail = new JobDetail(jobName, jobClass);// 任务

				cronTrigger.setCronExpression(time);// 配置时间
				scheduler.scheduleJob(jobDetail, cronTrigger);// 加载

				// 启动
				if (!scheduler.isShutdown()) {
					scheduler.start();
				}
			} else {
				throw new RuntimeException("指定任务已经存在");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 根据任务名称去修改时间
	 * 
	 * @author : Nansen
	 * 
	 * @param jobName
	 *            :任务名称
	 * @param newTime
	 *            :新的时间
	 * @date : 2017年10月10日下午3:52:54
	 */
	public static void setJobTime(String jobName, String newTime) {
		setJobTime(null, jobName, newTime);
	}

	/**
	 * 根据任务组、任务名称去修改时间
	 * 
	 * @author : Nansen
	 * @param groupName
	 *            :任务组名
	 * @param jobName
	 *            :任务名称
	 * @param newTime
	 *            :新的时间
	 * @date : 2017年10月10日下午3:52:54
	 */
	@SuppressWarnings("rawtypes")
	public static void setJobTime(String groupName, String jobName, String newTime) {
		try {
			Scheduler scheduler = getScheduler();
			CronTrigger cronTrigger = (CronTrigger) (scheduler.getTrigger(jobName, groupName));

			if (cronTrigger != null) {
				String oldTime = cronTrigger.getCronExpression();

				// 新的时间和旧的时间不一致
				if (newTime != null && !(newTime.equals(oldTime))) {
					JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
					Class jobClass = jobDetail.getJobClass();

					removeJob(groupName, jobName);
					addJob(groupName, jobName, newTime, jobClass);
				}
			} else {
				throw new RuntimeException("指定任务不存在");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * 根据任务名删除一个任务
	 * 
	 * @author : Nansen
	 * @param jobName
	 *            :任务名称
	 * @date : 2017年10月10日下午3:45:47
	 */
	public static void removeJob(String jobName) {
		removeJob(null, jobName);
	}

	/**
	 * 根据任务组、任务名删除一个任务
	 * 
	 * @author : Nansen
	 * @param groupName
	 *            :任务组名称
	 * @param jobName
	 *            :任务名称
	 * @date : 2017年10月10日下午3:45:47
	 */
	public static void removeJob(String groupName, String jobName) {
		try {
			Scheduler scheduler = getScheduler();

			scheduler.pauseJob(jobName, groupName);// 停止触发器
			scheduler.unscheduleJob(jobName, groupName);// 移除触发器
			scheduler.deleteJob(jobName, groupName);// 删除任务
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
