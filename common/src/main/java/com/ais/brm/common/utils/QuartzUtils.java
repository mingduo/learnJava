package com.ais.brm.common.utils;

import java.util.Date;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz工具类
 * Created by xuechen on 2016-10-31.
 *
 * @author xuechen
 */
public class QuartzUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzUtils.class);

    /**
     * 获取调度器
     *
     * @return
     * @description
     */
    public static Scheduler getScheduler() {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (Exception e) {
            LOGGER.error("获取Quartz调度器出错", e);
        }
        return scheduler;
    }

    /**
     * @return
     * @description
     */
    public static void start(Scheduler scheduler) {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            LOGGER.error("启动调度器出错", e);
        }
    }

    /**
     * @return
     * @description
     */
    public static void start(Scheduler scheduler, JobListener jobListener) {
        try {
            scheduler.getListenerManager().addJobListener(jobListener);
            scheduler.start();
        } catch (SchedulerException e) {
            LOGGER.error("启动调度器出错", e);
        }
    }

    /**
     * @return
     * @description
     */
    public static Date scheduleJob(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) {
        Date date = null;
        try {
            date = scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("Quartz调度任务出错", e);
        }
        return date;
    }

    /**
     * @return
     * @description
     */
    public static void scheduleJob(Scheduler scheduler, JobDetail jobDetail,
                                   Set<? extends Trigger> triggers, boolean isReplaceIfExist) {
        try {
            scheduler.scheduleJob(jobDetail, triggers, isReplaceIfExist);
        } catch (SchedulerException e) {
            LOGGER.error("Quartz调度任务出错", e);
        }
    }

    /**
     * @return
     * @description
     */
    public static boolean checkExists(Scheduler scheduler, String name) {
        boolean exists = false;
        try {
            exists = scheduler.checkExists(new JobKey(name));
        } catch (SchedulerException e) {
            LOGGER.error("判断Quartz中是否存在任务[" + name + "]出错", e);
        }
        return exists;
    }

    /**
     * @return
     * @description
     */
    public static boolean deleteJob(Scheduler scheduler, String name) {
        boolean deleted = false;
        try {
            deleted = scheduler.deleteJob(new JobKey(name));
        } catch (SchedulerException e) {
            LOGGER.error("Quartz删除调度任务[" + name + "]出错", e);
        }
        return deleted;
    }
}
