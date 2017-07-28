package org.task.platform;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.system.message.Prompt;
import org.tools.mail.PushMail;
import org.tools.mail.TextMail;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年7月6日
 * @version 1.0
 * @description 定时邮件任务
 */
@Component
public class MailServiceTask {
	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @date 2017年7月27日
	 * @version 1.0
	 * @description 午餐点餐提醒
	 */
	@Scheduled(cron = "0 0 11 ? * MON-FRI")
	public void drinkTask() {
		PushMail.pushText(new TextMail(Prompt.bundle("system.mail.drink.user.list").split(","), "定时点餐提醒", "同志们你们滴外卖点了么？再不点十二点可能就送不到了哟"));
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @date 2017年7月27日
	 * @version 1.0
	 * @description 日报提醒
	 */
	@Scheduled(cron = "0 45 17 ? * MON-FRI")
	public void sendWorkPlanTask() {
		PushMail.pushText(new TextMail(Prompt.bundle("system.mail.send.work.plan.user.list").split(","), "定时工作计划提醒", "友情提示！离下班还有15分钟，工作日报写了么？"));
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @date 2017年7月27日
	 * @version 1.0
	 * @description 周报提醒
	 */
	@Scheduled(cron = "0 45 17 ? * FRI")
	public void sendWeeklyPlanTask() {
		PushMail.pushText(new TextMail(Prompt.bundle("system.mail.send.weekly.plan.user.list").split(","), "定时工作计划提醒", "友情提示！今天是周五哟，本周周报写了么？"));
	}

	@Scheduled(cron = "0 30 9-18/1 ? * MON-FRI")
	public void lovePushTask() {
		PushMail.pushText(new TextMail("1007372729@qq.com,912554737@qq.com".split(","), "给宝贝请安", "Hello 有认真喝水么？有每小时起来走一走么？"));
	}

}
