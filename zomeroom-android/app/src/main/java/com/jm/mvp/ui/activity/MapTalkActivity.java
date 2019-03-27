package com.jm.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.base.LocationAddressInfoBean;
import com.jm.mvp.ui.adapter.LocationListAdapter;
import com.jm.utils.ToastUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.com.yktour.network.base.IPresenter;

/**
 * 地图操作activity
 */
public class MapTalkActivity extends BaseActivity implements View.OnClickListener, PoiSearch.OnPoiSearchListener, LocationSource, AMapLocationListener {

    private ImageView iv_title_back;
    private TextView tv_title;
    private TextView tvTitleSecond;
    private TextView tv_map_longitude;
    private TextView tv_map_latitude;
    private MapView mv_map;
    private AMap aMap;
    private EditText et_map;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private RecyclerView mRvMap;
    private String keyWord = "";// 要输入的poi搜索关键字
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch       poiSearch;// POI搜索
    private Double Loggitude=0.0;// POI搜索
    private Double Latitude=0.0;// POI搜索
    ArrayList<LocationAddressInfoBean> data = new ArrayList<>();//自己创建的数据集合
    private LocationListAdapter listAdapter;
    private String context="";
    private String title="";
    /**
     * 可以获取appSHA1秘钥 与开发平台进行对比
     * sHA1(this);
     */
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_talk);
        initView();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }else {
            //监听EditText输入
            initListener();
        }
        mv_map = (MapView) findViewById(R.id.mv_map);
        mv_map.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        if (aMap == null) {
            aMap = mv_map.getMap();
        }
        initMap();
    }


    private void initView() {
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        tvTitleSecond = (TextView) findViewById(R.id.tv_title_second);
        tvTitleSecond.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_map_longitude = (TextView) findViewById(R.id.tv_map_longitude);
        tv_map_latitude = (TextView) findViewById(R.id.tv_map_latitude);
        et_map = (EditText) findViewById(R.id.et_map);
        mRvMap = (RecyclerView) findViewById(R.id.rv_map);
        mRvMap.setLayoutManager(new LinearLayoutManager(this));
        tv_title.setText("分店经纬度查询");
        iv_title_back.setOnClickListener(this);
        tvTitleSecond.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv_map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mv_map.onSaveInstanceState(outState);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_title_second:
                if (tv_map_latitude.getText().length() > 0 && tv_map_longitude.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.LONGITUDE, tv_map_longitude.getText());
                    intent.putExtra(Constants.LATITUDE, tv_map_latitude.getText());
                    if (Latitude==0.0 ||Loggitude==0.0){
                        ToastUtils.ToastCenter("查找失败");
                    }else {
                        intent.putExtra(Constants.LATITUDE, tv_map_latitude.getText());
                        intent.putExtra(Constants.LATITUDE, tv_map_latitude.getText());
                        intent.putExtra(Constants.Latitude, Latitude);
                        intent.putExtra(Constants.Loggitude, Loggitude);
                        intent.putExtra(Constants.CONTEXT, context);
                        intent.putExtra(Constants.TITLE, title);
                        setResult(Constants.DD, intent);
                        finish();
                    }

                } else {
                    ToastUtils.ToastCenter("请输入搜索的分店名称");
                }
                break;
        }
    }


    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                ToastUtils.ToastCenter("正在定位中...");
            }
        }
    }


    @Override
    public void onPoiSearched(PoiResult result, int rCode) {

        //rCode 为1000 时成功,其他为失败
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            // 解析result   获取搜索poi的结果
            if (result != null && result.getQuery() != null) {
                if (result.getQuery().equals(query)) {  // 是否是同一条
                    poiResult = result;
                    List<PoiItem> poiItems = poiResult.getPois();
                    //解析获取到的PoiItem列表
                    data.clear();
                    for(PoiItem item : poiItems){
                        //获取经纬度对象
                        LatLonPoint llp = item.getLatLonPoint();
                        String businessArea = item.getBusinessArea();//区
                        String cityName = item.getCityName();
                        //返回POI的名称
                        String title = item.getTitle();
                        //返回POI的地址
                        String text = item.getSnippet();
                        data.add(new LocationAddressInfoBean(title,cityName+"  "+businessArea+"  "+text,llp.getLongitude(), llp.getLatitude()));
                    }
                    listAdapter = new LocationListAdapter(data);
                   if (data.size()>0){
                       mRvMap.setVisibility(View.VISIBLE);
                   }else {
                       mRvMap.setVisibility(View.GONE);
                   }
                    mRvMap.setAdapter(listAdapter);
                    listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {



                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            /**
                             * 进行火星坐标转换
                             */
                            tv_map_longitude.setText(String.valueOf(data.get(position).getLongitude()));
                            tv_map_latitude.setText(String.valueOf(data.get(position).getLatitude()));
                            Loggitude=data.get(position).getLongitude();
                            Latitude=data.get(position).getLatitude();
                            context = data.get(position).getContext();
                            title = data.get(position).getTitle();
                            mRvMap.setVisibility(View.GONE);
                        }
                    });
                }
            } else {
                Toast.makeText(MapTalkActivity.this,"无搜索结果",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MapTalkActivity.this,"错误码"+rCode,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (requestCode == 1) {
            initMap();
        }
    }

    private void initMap() {
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //myLocationStyle.showMyLocation(true);
        aMap.setLocationSource(this);
        myLocationStyle.interval(1000000);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    private void initListener() {
        et_map.addTextChangedListener(new TextWatcher() {

            private String keyWord;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                keyWord = String.valueOf(editable);
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       if ("".equals(keyWord)) {
                           Toast.makeText(MapTalkActivity.this,"请输入搜索关键字",Toast.LENGTH_SHORT).show();
                           return;
                       } else {
                           doSearchQuery(keyWord);
                       }
                   }
               });
            }
        });
    }
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String key) {
        currentPage = 0;
        //不输入城市名称有些地方搜索不到
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(key, "", "010");
        // 设置每页最多返回多少条poiitem
        query.setPageSize(10);
        // 设置查询页码
        query.setPageNum(currentPage);

        //构造 PoiSearch 对象，并设置监听
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        //调用 PoiSearch 的 searchPOIAsyn() 方法发送请求。
        poiSearch.searchPOIAsyn();
    }
}
