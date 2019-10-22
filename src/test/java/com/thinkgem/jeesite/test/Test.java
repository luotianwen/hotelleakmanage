package com.thinkgem.jeesite.test;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {



		// STEP1：获取应用基本信息
		private static String appId = "wbjCSin7MW9Fq8zDc2Rxq8";
		private static String appKey = "s2jrhpg23JAp6i2QeEl0LA";
		private static String masterSecret = "BPqibID0Z49nVcrewPRRH7";
		private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

	public static void main(String[] args) {
		IGtPush push = new IGtPush(url, appKey, masterSecret);

		List<String> appIds = new ArrayList<String>();
		appIds.add(appId);
       /* AppMessage message = new AppMessage();

        message.setData(notificationTemplateDemo(appId,appKey));
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);  // 时间单位为毫秒
*/
      /*  IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());*/

		ListMessage message = new ListMessage();
		message.setData(notificationTemplateDemo(appId,appKey));
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒
		message.setOfflineExpireTime(24 * 1000 * 3600);

		// 设置推送目标，填入appid和clientId
		List targets = new ArrayList();
		Target target1 = new Target();

		target1.setAppId(appId);
		target1.setClientId("cccb479eb0d7c797913f499ea6731229");
		// target2.setAlias(Alias2);
		targets.add(target1);

		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targets);
		System.out.println(ret.getResponse().toString());
	}
		public static void main1(String[] args) throws IOException {

			IGtPush push = new IGtPush(url, appKey, masterSecret);


			// STEP4：选择通知模板
		/*	NotificationTemplate template = new NotificationTemplate();
			template.setAppId(appId);
			template.setAppkey(appKey);
			template.setStyle(style);*/
			// STEP5：定义"AppMessage"类型消息对象,设置推送消息有效期等推送参数
			List<String> appIds = new ArrayList<String>();
			appIds.add(appId);
			AppMessage message = new AppMessage();
			//message.setData(template);
			message.setData(notificationTemplateDemo(appId,appKey));
			message.setAppIdList(appIds);
			message.setOffline(true);
			message.setOfflineExpireTime(1000 * 600);  // 时间单位为毫秒

			// STEP6：执行推送
			IPushResult ret = push.pushMessageToApp(message);
			System.out.println(ret.getResponse().toString());
		}
	public static NotificationTemplate notificationTemplateDemo(String appId, String appkey) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入您要透传的内容");

		Style0 style = new Style0();
		// 设置通知栏标题与内容

		style.setTitle("报警");
		style.setText("有误闯人员"+new Random().nextInt(100));
		// 配置通知栏图标
		//style.setLogo("icon.png");
		// 配置通知栏网络图标
		//style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);

		template.setStyle(style);

		// 设置定时展示时间，安卓机型可用
		// template.setDuration("2019-08-16 11:40:00", "2019-08-16 12:24:00");

		// 消息覆盖
		// template.setNotifyid(123); // 在消息推送的时候设置自定义的notifyid。如果需要覆盖此条消息，则下次使用相同的notifyid发一条新的消息。客户端sdk会根据notifyid进行覆盖。
		return template;
	}
	public static TransmissionTemplate transmissionTemplateDemo(String appId, String appKey){
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent("99999");
		template.setTransmissionType(2);

		Notify notify = new Notify();

		notify.setTitle("报警");
		notify.setContent("有误闯人员"+new Random().nextInt(100));
		//notify.setIntent("intent:#Intent;launchFlags=0x10000000;package=com.pp.yl;component=你的包名com.getui.demo.MainActivity;i.parm1=12;end");
		//notify.setType(GtReq.NotifyInfo.Type._intent);
		/*Style0 style = new Style0();
		// STEP2：设置推送标题、推送内容
		style.setTitle("报警");
		style.setText("有误闯人员"+new Random().nextInt(100));
		//style.setLogo("push.png");  // 设置推送图标
		// STEP3：设置响铃、震动等推送效果
		style.setRing(true);  // 设置响铃
		style.setVibrate(true);  // 设置震动*/
		//template.setr
		template.set3rdNotifyInfo(notify);//设置第三方通知
		return template;
	}
}
