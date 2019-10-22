package com.thinkgem.jeesite.modules.hotel.face;

import com.ha.facecamera.configserver.ConfigServer;
import com.ha.facecamera.configserver.DataServer;
import com.ha.facecamera.configserver.DataServerConfig;
import com.ha.facecamera.configserver.events.CameraConnectedEventHandler;
import com.ha.facecamera.configserver.events.CaptureCompareDataReceivedEventHandler;
import com.ha.facecamera.configserver.pojo.CaptureCompareData;
import com.ha.facecamera.configserver.pojo.Face;
import com.ha.facecamera.configserver.pojo.FacePage;
import com.ha.facecamera.configserver.pojo.ListFaceCriteria;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hotel.dao.device.HotelDeviceDao;
import com.thinkgem.jeesite.modules.hotel.dao.log.HotelLogDao;
import com.thinkgem.jeesite.modules.hotel.entity.device.HotelDevice;
import com.thinkgem.jeesite.modules.hotel.entity.log.HotelLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FaceUtils {
    public static Log log = LogFactory.getLog(FaceUtils.class);
    public static ConfigServer configServer;
    public static DataServer dataServer;
    private static HotelLogDao logDao = SpringContextHolder.getBean(HotelLogDao.class);
    private static HotelDeviceDao hotelDeviceDao = SpringContextHolder.getBean(HotelDeviceDao.class);
    private static Map<String, HotelDevice> hm = new HashMap();

    public synchronized static void initHd() {
        // synchronized ()
        hm.clear();
        List<HotelDevice> hds = hotelDeviceDao.findAllList();
        for (HotelDevice hd : hds
                ) {
            hm.put(hd.getIp(), hd);
        }
    }

    public static void init() {
        log.info("摄像头初始化");
        initHd();

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


        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        BASE64Encoder encoder = new BASE64Encoder();
        dataServer.onCaptureCompareDataReceived(new CaptureCompareDataReceivedEventHandler() {
            @Override
            public void onCaptureCompareDataReceived(CaptureCompareData data) {
                try {
                    log.info(data.toJson());
                    // System.out.println("收到抓拍对比数据（取前200个字符）:" + data.toJson());
                    //起线程出息接收到的抓拍对比数据
                    cachedThreadPool.execute(new Runnable() {
                        public void run() {
                            try {
                                if (StringUtils.isEmpty(data.getPersonID())) {


                                    //System.out.println("设备" + data.getCameraID() + "识别出人脸,员工号为：" + data.getPersonID());
                                    HotelLog hl = new HotelLog();
                                    hl.setPto("data:image/png;base64," + encoder.encode(data.getFeatureImageData()));
                                    HotelDevice hd1 = hm.get(data.getCameraID());
                                    hl.setD(hd1);
                                    hl.setH(hd1.getH());
                                    hl.setContent("误闯人员");
                                    hl.setState("0");
                                    hl.setType("1");
                                    hl.setStartDate(new Date());
                                    hl.preInsert();
                                    logDao.insert(hl);
                                    PushUtils.push();
                                }
                            } catch (Exception ex) {
                                // System.out.println("处理抓拍到的人脸数据时发生异常：" + ex);
                                log.error(ex.getMessage());
                            }
                        }
                    });
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    public static boolean addFace(Face f, String deviceId) {
        return (configServer.addFace(deviceId, f, 5000));
    }

    public static boolean modifyFace(Face f, String deviceId) {
        return (configServer.modifyFace(deviceId, f, 5000));
    }

    public static boolean deleteFace(String id, String deviceId) {

        return (configServer.deleteFace(deviceId, id, 5000));
    }

    public static boolean listFace(String id, String deviceId) {
        ListFaceCriteria criteria = new ListFaceCriteria();
        criteria.getRestrictions().idEq(id);

        FacePage fp = configServer.listFace(deviceId, criteria, 15000);
        return null!=fp&&fp.getTotal() > 0 ? true : false;
    }

}
