import com.ha.facecamera.configserver.events.CameraConnectedEventHandler;
import com.ha.facecamera.configserver.events.CaptureCompareDataReceivedEventHandler;
import com.ha.facecamera.configserver.pojo.CaptureCompareData;
import com.ha.facecamera.configserver.pojo.Face;
import com.ha.facecamera.configserver.pojo.Time;

public class Test {
    private static final char last2byte = (char) Integer.parseInt("00000011", 2);
    private static final char last4byte = (char) Integer.parseInt("00001111", 2);
    private static final char last6byte = (char) Integer.parseInt("00111111", 2);
    private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
    private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
    private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
    private static final char[] encodeTable = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public static void main(String[] args) {

        com.ha.facecamera.configserver.ConfigServer cs = new com.ha.facecamera.configserver.ConfigServer();
        cs.start(10001, new com.ha.facecamera.configserver.ConfigServerConfig());
        com.ha.facecamera.configserver.DataServer ds = new com.ha.facecamera.configserver.DataServer();
        com.ha.facecamera.configserver.DataServerConfig dsc = new com.ha.facecamera.configserver.DataServerConfig();
        dsc.connectStateInvokeCondition = com.ha.facecamera.configserver.ConnectStateInvokeCondition.DeviceNoKnown;
        ds.start(10002, dsc);
        cs.onCameraConnected(new CameraConnectedEventHandler() {

            @Override
            public void onCameraConnected(final String val) {
                try {
                    System.out.println("设备已连接配置端口,设备sn: " + val);
                    try {
                        //重置设备当前时间（与服务端保持一致）
                        Time time = new Time();
                        //configServer.setTimeBySn(val,new Time(),5000);
                        Time initTime = cs.getTimeBySn(val,5000);
                        Face f = new Face();
                        f.setId("xiaohe");
                        f.setName("肖何");
                        f.setRole(1);
                        f.setCustom("sssss");
                        f.setJpgFilePath(new String[]{"D:\\face\\192.168.1.80\\2019-10-08\\截图\\17_35_00.803951.jpg"});
                        //cs.addFaceBySn()
                       /* if(cs.addFaceByDeviceNo("1",f,5000)){
                            System.out.println("添加人脸成功");
                        }*/
                       cs.deleteFace("01237B-358274-5159EE", f.getId(), 5000);
                        if(cs.addFace("01237B-358274-5159EE", f, 5000))
                            System.out.println("添加人脸成功");
                    } catch (Exception ex) {
                    }
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }

            }
        });
        ds.onCaptureCompareDataReceived(new CaptureCompareDataReceivedEventHandler() {
            @Override
            public void onCaptureCompareDataReceived(CaptureCompareData data) {
                try {
                    System.out.println("收到抓拍对比数据（取前200个字符）:" + data.toJson().substring(0,200) + "...");
                    //起线程出息接收到的抓拍对比数据
//                        new Thread(new Runnable() {
//                            public void run() {
                    try {

                        System.out.println("设备" + data.getCameraID() + "识别出人脸,员工号为：" + data.getPersonID());
                        //将人脸图byte[]字符转化为BASE64


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
        //Thread.sleep();
      /*  Face f = new Face();
        f.setId("xiaohe");
        f.setName("肖何");
        f.setRole(1);
        f.setJpgFilePath(new String[]{"D:\\face\\192.168.1.88\\2019-10-05\\截图\\22_54_32.183837.jpg"});
        //cs.addFaceBySn()
        if(cs.addFaceByDeviceNo("1",f,5000)){
            System.out.println("添加人脸成功");
        }
        if(cs.addFace("01237B-358274-5159EE", f, 5000))
            System.out.println("添加人脸成功");*/
        try {
            //Object[] tw = HaCamera.twistImage(Files.readAllBytes(new File("D:\\face\\192.168.1.88\\2019-10-05\\截图\\22_54_32.183837.jpg").toPath()));

            //System.out.println(twis);
           // String json = "{\"version\": \"0.9\",\"cmd\": \"add person\",\"id\": \"test\",\"name\": \"测试人员\",\"role\": 1,\"feature_unit_size\": 0,\"feature_num\": 0,\"feature_data\": [],\"image_num\": 1,\"reg_images\": [{\"format\": \"jpg\",\"image_data\":\"" + encode((byte[]) tw[0]) + "\"}],\"norm_image_num\": 1,\"norm_images\": [{\"width\": 150,\"height\": 150,\"image_data\":\"" + encode((byte[]) tw[0]) + "\"}],\"wg_card_id\": 0,\"term\": \"forever\", \"term_start\":\"useless\"}";

           // HaCamera.
           // Files.write(new File("D:/save/twis.txt").toPath(), json.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void iss(Boolean ss) {

        ss = true;
    }

    /**
     * Base64 encoding.	 * 	 * @param from	 *            The src data.	 * @return cryto_str
     */
    public static String encode(byte[] from) {
        StringBuilder to = new StringBuilder((int) (from.length * 1.34) + 3);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < from.length; i++) {
            num = num % 8;
            while (num < 8) {
                switch (num) {
                    case 0:
                        currentByte = (char) (from[i] & lead6byte);
                        currentByte = (char) (currentByte >>> 2);
                        break;
                    case 2:
                        currentByte = (char) (from[i] & last6byte);
                        break;
                    case 4:
                        currentByte = (char) (from[i] & last4byte);
                        currentByte = (char) (currentByte << 2);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & lead2byte) >>> 6;
                        }
                        break;
                    case 6:
                        currentByte = (char) (from[i] & last2byte);
                        currentByte = (char) (currentByte << 4);
                        if ((i + 1) < from.length) {
                            currentByte |= (from[i + 1] & lead4byte) >>> 4;
                        }
                        break;
                }
                to.append(encodeTable[currentByte]);
                num += 6;
            }
        }
        if (to.length() % 4 != 0) {
            for (int i = 4 - to.length() % 4; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }


}
