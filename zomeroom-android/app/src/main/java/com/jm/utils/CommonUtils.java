package com.jm.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jm.base.Constants;
import com.jm.bean.AreaBean;
import com.jm.bean.CallInfo;
import com.jm.bean.PeopleBean;
import com.jm.bean.SeeDetailBean;
import com.jm.bean.ShopBean;
import com.jm.bean.TelDetailBean;
import com.jm.bean.UpTelAddBean;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

/**
 * Created by sky on 2017/8/7.
 * description: 公共工具类
 * changed: 创建
 *
 * @change author: wangdeshun
 * @change time: 2017/11/28 9:58
 * @change description:  添加手机号验证
 */

public class CommonUtils {

    private static Toast mToast;

    /**
     * 防止重复点击的短提示
     *
     * @param context 上下文
     * @param str     弹出信息
     */
    public static void showShortToast(Context context, final String str) {
        if (context != null) {
            if (mToast == null) {  //防止重复点击
                mToast = Toast.makeText(context.getApplicationContext(), str + "",
                        Toast.LENGTH_SHORT);
            } else {
                //mToast.setGravity(Gravity.CENTER,0,0);
                mToast.setText(str);
            }
            mToast.show();
        }
    }

    /**
     * 防止重复点击的长提示
     *
     * @param context 上下文
     * @param str     弹出信息
     */
    public static void showLongToast(Context context, String str) {
        if (mToast == null) {  //防止重复点击
            mToast = Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_LONG);
        } else {
            mToast.setText(str);
        }
        mToast.show();
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body, String path, String name) {
        try {
            File futureStudioIconFile = new File(path + name);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                Log.e("xxxxxx", "xxxx" + e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 得到uri所对应的文件的真实地址
     */
    public static String getFilePathByUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null,
                            null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 调用系统拨打电话
     *
     * @param tel      电话号码
     * @param activity 当前activity
     */
    public static void callTel(final String tel, final Activity activity) {
        //权限申请
        AndPermission.with(activity)
                .permission(
                        Permission.CALL_PHONE)
                .onGranted(new Action() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onAction(List<String> permissions) {
                        //用intent启动拨打电话
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                        activity.startActivity(intent);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtils.ToastCenter("权限被拒绝，请开启权限或手动拨打电话");
                    }
                })
                .start();
    }

    /**
     * 判断是否安装支付宝app
     *
     * @return 是否安装
     */
    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 判断是否安装微信app
     *
     * @return 是否安装
     */
    public static boolean checkWeChatInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                         en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                             enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()
                                    && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager =
                        (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24
                & 0xFF);
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file 文件
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }


    // 获得汉语拼音首字母
    public static String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "浏览历史";
        } else if (str.equals("1")) {
            return "热门";
        } else if (str.equals("2")) {
            return "全部";
        } else {
            return "#";
        }
    }

//    /**
//     * a-z排序
//     */
//    @SuppressWarnings("rawtypes")
//    Comparator comparator = new Comparator<City>() {
//        @Override
//        public int compare(City lhs, City rhs) {
//            String a = lhs.getPinyi().substring(0, 1);
//            String b = rhs.getPinyi().substring(0, 1);
//            int flag = a.compareTo(b);
//            if (flag == 0) {
//                return a.compareTo(b);
//            } else {
//                return flag;
//            }
//        }
//    };

    /**
     * 隐藏软键盘
     *
     * @param context 上下文
     */
    public static void hideSoft(Activity context) {
        //隐藏软键盘
        InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(context.findViewById(android.R.id.content).getWindowToken(), 0);
    }

    /**
     * 关闭软键盘 ,没有上下文view
     */
    public static void closeKeyBoard(Activity ctx) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {  // 关闭键盘
            View view = ctx.getWindow().peekDecorView();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * 手机号验证
     *
     * @param tel 手机号
     * @return true 符合
     */
    public static boolean phoneNum(String tel) {
        String regExp = "^((1[358][0-9]|(14[57])|(17[0678]))\\d{8})";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(tel);
        return m.find();
    }

    /**
     * 关闭界面提示，离开还是继续填写
     *
     * @param context  上下文
     * @param category 大类id
     */
//    public static AlertDialog closeReminder(final Context context, final int category) {
//        final AlertDialog builder = new AlertDialog.Builder(context).create();
//        View View1 = LayoutInflater.from(context).inflate(R.layout.item_dialogbuilder, null);
//        builder.setView(View1);
//        TextView textView1 = (TextView) View1.findViewById(R.id.confirmTv);
//        TextView textView2 = (TextView) View1.findViewById(R.id.fillTv);
//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity) context).finish();
//            }
//        });
//        textView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.dismiss();
//            }
//        });
//        builder.show();
//        return builder;
//    }

    /**
     * 根据传入的毫秒值 计算日期
     *
     * @return 返回格式  xx-xx 周x
     */
    public static String time2date(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(time));
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        c.add(Calendar.DAY_OF_MONTH, 1);
        String[] split = date.split("-");
        c.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]));
        String year = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String way = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(way)) {
            way = "天";
        } else if ("2".equals(way)) {
            way = "一";
        } else if ("3".equals(way)) {
            way = "二";
        } else if ("4".equals(way)) {
            way = "三";
        } else if ("5".equals(way)) {
            way = "四";
        } else if ("6".equals(way)) {
            way = "五";
        } else if ("7".equals(way)) {
            way = "六";
        }
        return month + "-" + day + " 周" + way;
    }

    /**
     * 检测传入的对象是否为空
     *
     * @param reference 对象
     * @param <T>       泛型
     * @return 对象
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * 关闭界面提示，离开还是继续填写
     *
     * @param context  上下文
     * @param category 大类id
     */
//    public static AlertDialog closeReminder(final Context context, final int category) {
//        final AlertDialog builder = new AlertDialog.Builder(context).create();
//        View View1 = LayoutInflater.from(context).inflate(R.layout.item_dialogbuilder, null);
//        builder.setView(View1);
//        TextView textView1 = (TextView) View1.findViewById(R.id.confirmTv);
//        TextView textView2 = (TextView) View1.findViewById(R.id.fillTv);
//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity) context).finish();
//            }
//        });
//        textView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.dismiss();
//            }
//        });
//        builder.show();
//        return builder;
//    }

    /**
     * 如果金额是1.0或者是1.00类型的显示为1，如果是1.1或1.01类型的原样显示
     *
     * @param money 传入的钱数
     */
    public static String getPriceValue(String money) {
        if (TextUtils.isEmpty(money)) {
            return "0";
        }
        Double d = Double.parseDouble(money);
        String string = money;
        if (money.contains(".")) {
            string = money.substring(0, money.indexOf("."));
        }
        int i = Integer.parseInt(string);
        String str = "";
        if (d == i) {
            str = String.valueOf(i);
        } else {
            str = String.valueOf(d);
        }
        return str;
    }

    /**
     * 返回拼接好的标题
     *
     * @param festivalName 节日名称
     * @param nameFir      第一个名字
     * @param nameLast     最后一个名字
     * @return
     */
    public static String jointTitle(String festivalName, String nameFir, String nameLast) {
        if (!TextUtils.isEmpty(festivalName)) {
            festivalName = "[" + festivalName + "]";
        }
        if (!TextUtils.isEmpty(nameFir)) {
            nameFir = "<" + nameFir + ">";
        }
        return festivalName + nameFir + nameLast;
    }

    /**
     * 获取edittext的值
     *
     * @param et 输入
     * @return 输入的字
     */
    public static String getEditText(EditText et) {
        return et.getText().toString().trim();
    }

    /**
     * 控制背景变灰的方法
     *
     * @param activity 将context强转即可
     * @param bgAlpha  灰度
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String formatDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 根据返回值 设置意向
     *
     * @param intention
     * @return
     */
    public static String getIntention(int intention) {
        String intentionTemp = "";
        switch (intention) {
            case 0:
                break;
            case 1:
                intentionTemp = "跟进";
                break;
            case 2:
                intentionTemp = "pass";
                break;
            case 3:
                intentionTemp = "成交";
                break;
            case 4:
                intentionTemp = "与销售无关";
                break;
            // TODO: 2018/6/23 添加意向
            case 5:
                intentionTemp = "";
                break;
            case 6:
                intentionTemp = "转进店";
                break;
            default:
                break;
        }
        return intentionTemp;
    }

    public static String[] toStringArray(List list, int type, int which) {
        CommonUtils.checkNotNull(list);
        if (list == null || list.isEmpty()) {
            return new String[]{"请选择"};
        }
        String[] array;
        switch (type) {
            case Constants.AD:
                array = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    if (which == Constants.TEL) {
                        array[i] = ((TelDetailBean.DataBean.ChannelListBean) list.get(i)).getChannelName();
                    } else {
                        array[i] = ((SeeDetailBean.DataBean.ChannelListBean) list.get(i)).getChannelName();
                    }
                }
                return array;
            case Constants.PUSH_SHOP:
                array = new String[list.size() + 1];
                array[0] = "请选择";
                for (int i = 0; i < list.size(); i++) {
                    if (which == Constants.TEL) {
                        array[i + 1] = ((TelDetailBean.DataBean.PushListBean) list.get(i)).getShopName();
                    } else {
                        array[i + 1] = ((SeeDetailBean.DataBean.PushListBean) list.get(i)).getShopName();
                    }
                }
                return array;
            case Constants.MINE_SHOP:
                array = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    if (which == Constants.TEL) {
                        array[i] = ((TelDetailBean.DataBean.ShopListBean) list.get(i)).getShopName();
                    } else {
                        array[i] = ((SeeDetailBean.DataBean.ShopListBean) list.get(i)).getShopName();
                    }
                }
                return array;

            case Constants.AREA:
                array = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    if (which == Constants.TEL) {
                        array[i] = ((AreaBean.DataBean) list.get(i)).getName();
                    } else {
                        array[i] = ((AreaBean.DataBean) list.get(i)).getName();
                    }
                }
                return array;

            case Constants.PEOPLE:
                array = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    if (which == Constants.TEL) {
                        array[i] = ((PeopleBean.DataBean) list.get(i)).getName();
                    } else {
                        array[i] = ((PeopleBean.DataBean) list.get(i)).getName();
                    }
                }
                return array;

            default:
                break;
        }
        return new String[0];
    }

    public static int[] toIntTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int[] timeArray = new int[5];
        //日历对象
        Calendar parse = Calendar.getInstance();
        try {
            Date date = sdf.parse(time);
            parse.setTime(date);
            int year = parse.get(Calendar.YEAR);
            int month = parse.get(Calendar.MONTH) + 1;
            int day = parse.get(Calendar.DATE);
            int hours = parse.get(Calendar.HOUR_OF_DAY);
            int minutes = parse.get(Calendar.MINUTE);
            timeArray[0] = year;
            timeArray[1] = month;
            timeArray[2] = day;
            timeArray[3] = hours;
            timeArray[4] = minutes;
        } catch (ParseException e) {
            e.printStackTrace();
            Date date = new Date();
            parse.setTime(date);
            timeArray[0] = parse.get(Calendar.YEAR);
            timeArray[1] = parse.get(Calendar.MONTH) + 1;
            timeArray[2] = parse.get(Calendar.DATE);
            timeArray[3] = parse.get(Calendar.HOUR_OF_DAY);
            timeArray[4] = parse.get(Calendar.MINUTE);
        }
        return timeArray;
    }

    public static List<UpTelAddBean> toUpAddTelList(List<CallInfo> list) {
        List<UpTelAddBean> telList = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (!temp.contains(list.get(i).number)) {
                    UpTelAddBean bean = new UpTelAddBean();
                    bean.setTel(list.get(i).number.replace(" ", ""));
                    bean.setCreateTimeStr(formatDate(list.get(i).date));
                    telList.add(bean);
                    temp.add(list.get(i).number);
                }
            }
        }
        return telList;
    }

    public static List<UpTelAddBean> toUpAddTelList(List<CallInfo> list, int num) {
        List<UpTelAddBean> telList = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < num; i++) {
                if (!temp.contains(list.get(i).number)) {
                    UpTelAddBean bean = new UpTelAddBean();
                    bean.setTel(list.get(i).number);
                    SPUtils.put("tel", list.get(0).number);
                    bean.setCreateTimeStr(formatDate(list.get(i).date));
                    telList.add(bean);
                    temp.add(list.get(i).number);
                }
            }
        }
        return telList;
    }

    /**
     * 获取 3 天前的时间
     *
     * @return
     */
    public static String getLast3Day() {
        //日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        long millis = calendar.getTimeInMillis();
        return String.valueOf(millis);
    }

    public static List<String> toShopList(List<ShopBean.DataBean> data) {
        List<String> list = new ArrayList<>();
        list.add("全部");
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getShopName());
        }
        return list;
    }

    public static ArrayList<String> getIntentionList() {
        ArrayList<String> list = new ArrayList<>();
        // TODO: 2018/6/23 意向
        Collections.addAll(list, "全部", "跟进", "pass", "成交", "与销售无关", "转进店");
        return list;
    }

    public static ArrayList<String> getTalkIntentionList() {
        ArrayList<String> list = new ArrayList<>();
        // TODO: 2018/6/23 意向
        Collections.addAll(list, "全部", "跟进", "pass", "签约");
        return list;
    }
}
