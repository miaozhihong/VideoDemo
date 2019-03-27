package com.jm.base;

import com.jm.helper.SampleApplicationContext;
import com.jm.utils.AppUtils;

/**
 * @author sky
 * @date 2017/8/7
 * description: 常量集合
 * changed: 创建
 */

public class Constants {

    public static final String SUBWAY = "subway";
    public static final int TEL_DETAIL = 605;
    public static final String SUBWAY_ID = "subway_id";
    public static final String WHICH = "which";
    public static final int SEE_DETAIL = 606;
    public static final int PWD = 607;
    public static final int AREA = 666;
    public static final int PEOPLE = 667;
    public static final String LONGITUDE = "经度";
    public static final String LATITUDE = "纬度";
    public static final String Latitude = "Latitude";
    public static final String Loggitude = "Loggitude";
    public static final String CONTEXT = "context";
    public static final String TITLE = "title";
    public static final String TALK = "talk";
    public static final int DD = 668;//地图
    public static final int CONTRACT = 669;//合同
    public static final String CONTRACT2 = "合同";
    public static final int TYPE_OUTSIDE = 1;
    public static final int TYPE_PUBLIC = 2;
    public static final int TYPE_ROOM = 3;
    public static final int TYPE_ROOM1 = 5;
    public static final int TYPE_ROOM2 = 6;
    public static final int TYPE_ROOM3 = 7;
    public static final int TYPE_ROOM4 = 8;
    public static final int TYPE_ROOM5 = 9;
    public static final int UPDATA = 2;
    public static final int ADDDATA = 1;
    public static final int DETAIL_IMAGE = 118;
    public static final int DETAIL_MOTH = 119;


    /**
     * 线上
     */
    public static String NET = "//118.190.83.174:8080";
    //public static String NET = "//192.168.31.116:8080";
    //public static String NET = "//192.168.31.215:8080";
    public static String HOST = "http:" + NET;
    public static String WEB_SOCKET = "ws:" + NET + "/app/websocket";
    public static final int NET_SUCCESS = 20000;
    public static final int NO_USE = 4044;
    public static final int NO_CRAME = 4045;

    public static int SUB = 10;
    public static int OUTSIDE1 = 1;
    public static int OUTSIDE2 = 2;
    public static int OUTSIDE3 = 3;
    public static int PUBLIC1 = 4;
    public static int PUBLIC2 = 5;
    public static int PUBLIC3 = 6;
    public static int ROOM1 = 7;
    public static int ROOM2 = 8;
    public static int ROOM3 = 9;
    public static int ROOM4 = 10;
    public static int ROOM5 = 11;
    public static int ROOM6 = 12;
    public static int ROOM7 = 13;
    public static int ROOM8 = 14;
    public static int ROOM9 = 15;
    public static int ROOM10 = 16;
    public static int ROOM11 = 17;
    public static int ROOM12 = 18;
    public static int ROOM13 = 19;
    public static int ROOM14 = 20;
    public static int ROOM15 = 21;
    public static int ROOM16 = 22;
    public static int ROOM17 = 23;
    public static int ROOM18 = 24;
    public static final int PHOTO_REQUEST_GALLERY1 = 25;
    public static final int PHOTO_REQUEST_GALLERY2 = 26;
    public static final int PHOTO_REQUEST_GALLERY3 = 27;
    public static final int PHOTO_REQUEST_GALLERY4 = 28;
    public static final int PHOTO_REQUEST_GALLERY5 = 29;
    public static final int PHOTO_REQUEST_GALLERY6 = 30;
    public static final int PHOTO_REQUEST_GALLERY7 = 31;
    public static final int PHOTO_REQUEST_GALLERY8 = 32;
    public static final int PHOTO_REQUEST_GALLERY9 = 33;
    public static final int PHOTO_REQUEST_GALLERY10 = 34;
    public static final int PHOTO_REQUEST_GALLERY11 = 35;
    public static final int PHOTO_REQUEST_GALLERY12 = 36;
    public static final int PHOTO_REQUEST_GALLERY13 = 37;
    public static final int PHOTO_REQUEST_GALLERY14 = 38;
    public static final int PHOTO_REQUEST_GALLERY15 = 39;
    public static final int PHOTO_REQUEST_GALLERY16 = 40;
    public static final int PHOTO_REQUEST_GALLERY17 = 41;
    public static final int PHOTO_REQUEST_GALLERY18 = 42;
    public static final int PHOTO_REQUEST_GALLERY19 = 43;
    public static final int PHOTO_REQUEST_GALLERY20 = 44;
    public static final int PHOTO_REQUEST_GALLERY21 = 45;
    public static final int PHOTO_REQUEST_GALLERY22 = 46;
    public static final int PHOTO_REQUEST_GALLERY23 = 47;
    public static final int PHOTO_REQUEST_GALLERY24 = 48;
    public static final String ARRAY = "url";
    public static final int ALBUM = 50;
    /**
     * 是否使用缓存
     */
    public static boolean Cache = true;


    /**
     * 是否需要录音
     */
    public static String isVoicePath = "http://118.190.83.174:8080/app/user/recording/";
    public static String upFilePath = "http://118.190.83.174:8080/";
    public static String upFilePath_Config = "app/user/fileUpload/";

    /**
     * 上传Token给服务器
     */
    public static String sendToken = "app/user/addToken/";// app/user/addToken/{id}/{token}
    /**
     * 通过id获取对应发件人
     */
    public static String byShopName = "app/user/shopName/";

    /**
     * 通过id获取内务详情
     */
    public static String getDetailList = "/app/modelData/details";
    /**
     * 通过id获取内务详情
     */
    public static String getRequestDetailList = "/app/modelData/transfer";

    /**
     * 获取用户时长信息
     */
    public static String getOnlineTiem = "app/user/status";

    /**
     * 回访数据上传
     */
    public static String upLoadTalkData = "/app/roomOrigin/add";
    /**
     * 回访数据修改
     */
    public static String upLoadTalkUPData = "/app/roomOrigin/update";
    /**
     * 获取回访数据
     */
    public static String getTalkData = "/app/roomOrigin/list";
    /**
     * 同意请求
     */
    public static String AreggRequst = "/app/modelData/assent";
  /**
     * 同意请求
     */
    public static String NotAreggRequst = "/app/modelData/noAssent";

    /**
     * 上传图片
     */
    public static String upLoadPhotos = "/app/roomOrigin/fileUpload";
    /**
     * 上传视频
     */
    public static String upLoadMovice = "/app/modelData/videoUpload";
    /**
     * 获取统计数据
     */
    public static String getStaticeData = "/app/roomOrigin/statistics";
    /**
     * 友盟分享网址
     */
    public static String talkShareUrl = "http://xs.zomeroom.com:8080/app/enjoy/roomOrigin/";
    /**
     * 友盟分享网址
     */
    public static String Warehou = "/app/modelData/storage";

    /**
     * 详情
     */
    public static String details = "/app/roomOrigin/details";
    /**
     * 拨打电话
     */
    public static String call = "/app/roomOrigin/callPhone";

    /**
     * 是否需要录音(本地)
     //     */
//    public static String isVoicePath="http://192.168.1.2:8080/app/user/recording/";
//    public static String upFilePath="http://192.168.1.2:8080/";
//    public static String upFilePath_Config="app/user/fileUpload/";

    /**
     * 上拉加载
     */
    public static final int UP_LOAD = 500;
    /**
     * 下拉刷新
     */
    public static final int DOWN_REFRESH = 501;

    /**
     * 地铁
     */
    public static String SUBWAY_TALK = "/app/dictionary/findStation";

    /**
     * 区域
     */
    public static String AREA_TALK = "/app/roomOrigin/area";
 /**
     * 获取调拨请求数据
     */
    public static String GETREQUEST = "/app/modelData/transferList";

    /**
     * 开发负责人
     */
    public static String PEOPLE_TALK = "/app/roomOrigin/findUser";
    /**
     * 取消调拨
     */
    public static String CANALE = "/app/modelData/cancel";

    /**
     * 上传打完电话的状态
     */
    public static String upCalId = "/app/roomOrigin/callPhoneUpdate";
    /**
     * 开发系统历史删除接口
     */
    public static String upDelet = "/app/roomOrigin/delete";
  /**
     * 内务详情更新
     */
    public static String upLoadDetail = "/app/modelData/update";

    /**
     * 删除图片
     */
    public static String deletPhotos = "/app/roomOrigin/deleteFile";

    /**
     * 获得APP版本号
     */
    public static int APP_VERSION_NAME = AppUtils.getVersionCode(SampleApplicationContext.context);
    public static String MOBILE_SYS_VERSION = String.valueOf(android.os.Build.VERSION.SDK_INT);
    public static final long TWO_HOURS = 7200000;
    public static final int AD = 600;
    public static final int PUSH_SHOP = 601;
    public static final int MINE_SHOP = 602;
    public static final String[] YES_OR_NO = new String[]{"否", "是"};
    public static final String[] FLLOW_OR_NO = new String[]{"未关注", "已关注"};
    public static final String[] INTENTION = new String[]{"跟进", "pass", "成交", "与销售无关", "转进店"};
    public static final String[] INTENTION_TALK = new String[]{"跟进", "pass", "签约"};
    public static final String[] INTENTION_MODE = new String[]{"直营店", "平台管理"};
    public static final String[] FLOOR = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    public static final int TEL = 603;
    public static final int SEE = 604;
    public static final int WEEK = 1;
    public static final int HALF = 2;
    public static final int MOTH = 3;

    public static final String SHOP_ID = "shop_id";
    public static final String DATA_ID = "id";
    public static final String CHAT_ID = "ID";
    public static final String DETALI_ID = "ids";
    public static final String DETALI_TIME = "time";


}
