package com.thinkgem.jeesite.modules.hotel.face;

import com.ha.facecamera.configserver.ConfigServer;
import com.ha.facecamera.configserver.DataServer;
import com.ha.facecamera.configserver.DataServerConfig;
import com.ha.facecamera.configserver.events.CameraConnectedEventHandler;
import com.ha.facecamera.configserver.events.CaptureCompareDataReceivedEventHandler;
import com.ha.facecamera.configserver.pojo.CaptureCompareData;
import com.ha.facecamera.configserver.pojo.Face;
import com.thinkgem.jeesite.common.config.Global;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FaceUtils {
    public static Log log= LogFactory.getLog(FaceUtils.class);
    public static   ConfigServer configServer;
    public static   DataServer dataServer;

 public static void init(){
     log.info("摄像头初始化");
     configServer = new ConfigServer();
     dataServer = new DataServer();
     configServer.start(Integer.parseInt(Global.getConfig("cs.port")), new com.ha.facecamera.configserver.ConfigServerConfig());
     DataServerConfig dsc = new com.ha.facecamera.configserver.DataServerConfig();
     dsc.connectStateInvokeCondition = com.ha.facecamera.configserver.ConnectStateInvokeCondition.DeviceNoKnown;
     dataServer.start(Integer.parseInt(Global.getConfig("ds.port")), dsc);
     configServer.onCameraConnected(new CameraConnectedEventHandler() {
         @Override
         public void onCameraConnected(final String val) {
             try {
                 log.info("设备已连接配置端口,设备sn: " + val);
                 try {
                     //重置设备当前时间（与服务端保持一致）
                      //configServer.setTimeBySn(val,new Time(),5000);
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             } catch (Exception ex) {
                 // TODO Auto-generated catch block
                 ex.printStackTrace();
             }

         }
     });
     dataServer.onCaptureCompareDataReceived(new CaptureCompareDataReceivedEventHandler() {
         @Override
         public void onCaptureCompareDataReceived(CaptureCompareData data) {
             try {
                 log.info(data.toJson());
                 System.out.println("收到抓拍对比数据（取前200个字符）:" + data.toJson());
                 //起线程出息接收到的抓拍对比数据
//                        new Thread(new Runnable() {
//                            public void run() {
                             try {

                                 System.out.println("设备" + data.getCameraID() + "识别出人脸,员工号为：" + data.getPersonID());



                             } catch (Exception ex) {
                                 System.out.println("处理抓拍到的人脸数据时发生异常：" + ex);
                             }
//                            }
//                        }).start();
             } catch (Exception e1) {
                 // TODO Auto-generated catch block
                 e1.printStackTrace();
             }
         }
     });
 }
     public boolean addFace( Face f,String deviceId){
         return (configServer.addFace(deviceId, f, 5000));
     }
     public boolean modifyFace( Face f,String deviceId){
         return (configServer.modifyFace(deviceId, f, 5000));
     }
    public boolean modifyFace(String id,String deviceId){
        return (configServer.deleteFace(deviceId, id, 5000));
    }

}
