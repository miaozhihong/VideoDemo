package com.jm.helper;

import com.jm.bean.BlackListBean;
import com.jm.bean.CommonBean;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.JmModelDataListBean;
import com.jm.bean.LoginBean;
import com.jm.bean.ModelDataDTOBean;
import com.jm.bean.RemarkBean;
import com.jm.bean.SeeDetailBean;
import com.jm.bean.SeeListBean;
import com.jm.bean.ShopBean;
import com.jm.bean.SubwayBean;
import com.jm.bean.TelDetailBean;
import com.jm.bean.TelListBean;
import com.jm.bean.UserBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sky on 2017/8/8.
 * description: Retrofit 联网类
 * changed: 创建
 */
public interface API {

    /**
     * 查询黑名单列表 或 单一数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("app/black/findMyBlack")
    Observable<BlackListBean> getBlackList(@Body RequestBody body);

    /**
     * 删除黑名单
     *
     * @return
     */
    @POST("/app/black/delBlack/{id}")
    Observable<CommonBean> delBlackTel(@Path("id") String id);

    /**
     * 添加黑名单
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/black/addBlack")
    Observable<CommonBean> addBlack(@Body RequestBody body);

    /**
     * 电话咨询列表 包含筛选
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/consult/list")
    Observable<TelListBean> getTelList(@Body RequestBody body);

    /**
     * 电话咨询详情
     *
     * @return
     */
    @POST("/app/consult/query/{id}")
    Observable<TelDetailBean> getTelDetail(@Path("id") String id);

    /**
     * 内务咨询详情
     * @return
     */
    @GET("/app/modelData/details/{id}")
    Observable<JmModelDataListBean> getDataListDetail(@Path("id") String id);

    /**
     * 电话咨询详情更新
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/consult/update")
    Observable<CommonBean> updateTelDetail(@Body RequestBody body);
    /**
     * 业务详情
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/modelData/update")
    Observable<CommonBean> updateDetail(@Body RequestBody body);


    /**
     * 拨打电话
     *
     * @return
     */
    @POST("/app/consult/callPhone/{id}")
    Observable<CommonBean> callTel(@Path("id") String id);

    /**
     * 转进店
     *
     * @return
     */
    @POST("/app/consult/enterShop/{id}")
    Observable<CommonBean> telToShop(@Path("id") String id);

    /**
     * 转进店
     *
     * @return
     */
    @POST("/app/enter/cancel/{id}")
    Observable<CommonBean> cancelToShop(@Path("id") String id);

    /**
     * 模糊地铁搜索
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/app/dictionary/findStation")
    Observable<SubwayBean> searchSubway(@Field("name") String name);

    /**
     * 导入电话 多个
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/consult/add")
    Observable<CommonBean> addTel(@Body RequestBody body);

    /**
     * 导入电话 单个
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/consult/input")
    Observable<CommonBean> addSingleTel(@Body RequestBody body);

    /**
     * 进店咨询详情
     *
     * @return
     */
    @POST("/app/enter/query/{id}")
    Observable<SeeDetailBean> getSeeDetail(@Path("id") String id);

    /**
     * 进店咨询详情更新
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/enter/update")
    Observable<CommonBean> updateSeeDetail(@Body RequestBody body);

    /**
     * 进店拨打电话
     *
     * @return
     */
    @POST("/app/enter/callPhone/{id}")
    Observable<CommonBean> enterCallTel(@Path("id") String id);

    /**
     * 电话添加咨询备注
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/consult/addremark")
    Observable<CommonBean> addRemark(@Body RequestBody body);

    /**
     * 电话备注列表
     *
     * @return
     */
    @POST("/app/consult/findRemarkList")
    Observable<RemarkBean> remarkList(@Query("id") String id);

    /**
     * 筛选商店列表
     *
     * @return
     */
    @POST("/app/consult/findAuthorityShop")
    Observable<ShopBean> shopList();

    /**
     * 查询分店
     */
    @GET("/app/modelData/shop")
    Observable<ShopBean> shopDetialShopList();

    /**
     * 进行调拨
     */
    @GET("/app/modelData/transfer")
    Observable<ResponseBody> shopDoDetail(@Query("id") String id, @Query("shopId")String shopId);
    /**
     * 判断是否调拨
     */
    @GET("/app/modelData/transferList")
    Observable<ResponseBody> shopRequstDetail();
    /**
     * 同意调拨
     */
    @GET("/app/modelData/assent")
    Observable<ResponseBody> shopRequstAreggDetail(@Query("id") String id);
    /**
     * 不同意调拨
     */
    @GET("/app/modelData/noAssent")
    Observable<ResponseBody> shopRequstNotAreggDetail(@Query("id") String id);

    /**
     * 查询姓名
     */
    @GET("/app/modelData/user")
    Observable<UserBean> shopDetialUserList();

    /**
     * 进店看房列表 包含筛选
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/enter/list")
    Observable<SeeListBean> getSeeList(@Body RequestBody body);

    /**
     * 进店添加咨询备注
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("/app/enter/addremark")
    Observable<CommonBean> addSeeRemark(@Body RequestBody body);

    /**
     * 进店备注列表
     *
     * @return
     */
    @POST("/app/enter/findRemarkList")
    Observable<RemarkBean> remarSeekList(@Query("id") String id);

    /**
     * 是否登录校验
     */
    @GET("app/checklogin")
    Observable<CommonBean> checkLogin();

    /**
     * 登录接口
     *
     * @param account
     * @param pwd
     * @return
     */
    @POST("app/login")
    Observable<Response<LoginBean>> login(@Query("username") String account,
                                          @Query("password") String pwd);

    /**
     * 修改密码
     *
     * @param newpwd
     * @param oldpwd
     * @return
     */
    @GET("app/modifpwd")
    Observable<CommonBean> changPwd(@Query("newpwd") String newpwd,
                                    @Query("oldpwd") String oldpwd);


    /**
     * 内务数据列表 包含筛选
     *
     * @return
     */
    @POST("/app/modelData/list")
    Observable<JmModelDataBean> getJmModelList(@Body RequestBody body);
    /**
    入库
     */
    @POST("/app/modelData/storageList")
    Observable<JmModelDataBean> getStorageJmModelList(@Body RequestBody body);
 /**
     * 内务数据列表 包含筛选
     *
     * @return
     */
    @GET("/app/modelData/storage")
    Observable<RequestBody> getWarehou(@Query("id") String id,@Query("modelType")String shopId);

//    //旅游同业端APP注销登录
//    @POST("user/common/logout")
//    Observable<Object> logout();
//
//    //查询收藏列表接口
//    @POST("collection/getCollectionList")
//    Observable<CollectionListBean> getCollectionList(@Query("curPage") int curPage,
//                                                     @Query("pageSize") int pageSize);
//
//    //查询分享订单列表接口
//    @POST("/mbpm/order/userorder/info/queryOrderList")
//    Observable<ShareOrderListBean> getShareOrderList(@Body RequestBody body);
//
//    //查询退款列表接口
//    @POST("mbpm/order/userorder/info/batchquery")
//    Observable<RefundListBean> getRefundList(@Body RequestBody body);
//
//    //供应商退款订单详情
//    @GET("/order/trade/refund/Detail")
//    Observable<RefundTicketDetailBean> getRefundDetail(@Query("refundId") String refundId);
//
//    // B端APP用户钱包
//    @POST("/user/wallet/query/info")
//    Observable<WalletBean> getWallet();
//
//    //首页搜索查询结果列表
//    @GET("product/findAllProduct")
//    Observable guestInformation(int curPage, int pageSize);
//
//    //首页搜索查询结果列表
//    @GET("product/findAllProduct")
//    Observable guestDynamics(int curPage, int pageSize);
//
//    //目的地
//    @POST("appDestination/getDestination")
//    Observable<HomeDestinationInfoBean> getDestinationData();
//
//    //跟团游览产品详情页面 http://testmbpm.yktour.com.cn/mbpm/product/packagedholiday/info/query?productId=10791
//    @GET("mbpm/product/packagedholiday/info/query")
//    Observable<GroupProductDetailsBean> getGroupDetails(@Query("productId") Integer productId);
//
//    //快捷游产品详情页面
//    @GET("/product/express/packagedholiday/info/query")
//    Observable<KjyProductDetailsBean> getKjyGroupDetails(@Query("productId") Integer productId);
//
//    //周边游产品详情页面
//    @GET("/product/independentTravel/info/query")
//    Observable<GroupProductDetailsBean> getZzyGroupDetails(@Query("productId") Integer productId);
//
//    //首页搜索查询结果列表
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("product/productlist/queryAppointBigCategoryId")
//    Observable<SearchProductListBean> getSearchProductData(@Body RequestBody body);
//
//    //首页热销产品 ?startCityName=xx 需要登录
//    @POST("product/productlist/sellHigh")
//    Observable<HotProductBean> getHotProduct(@Query("startCityName") String startCityName);
//
//    //首页精选产品 ?startCityName=xx 需要登录
//    @GET("product/productlist/indexquery")
//    Observable<CommendProductBean> getCommendProduct(@Query("startCityName") String startCityName);
//
//    //首页最近订单信息 需要登录
//    @POST("order/userorder/latestorder/query")
//    Observable<LateOrder> getLateOrder();
//
//    //创建发票信息
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/order/invoice/info/create")
//    Observable<InvoiceResultBean> saveInvoiceData(@Body RequestBody body);
//
//    //更新发票信息
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/order/invoice/info/create")
//    Observable<InvoiceResultBean> updateInvoiceData(@Body RequestBody body);
//
//    //保存订单-保存旅客信息
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/order/common/visitor/create")
//    Observable<OrderUpdateTouristBean> saveTouristData(@Body RequestBody body);
//
//    //保存订单-修改旅客信息
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/order/common/visitor/update")
//    Observable<OrderUpdateTouristBean> updateTouristData(@Body RequestBody body);  //保存订单-修改旅客信息
//
//    //    @GET("appDestination/getDestination")
////    Observable<HomeDestinationInfoBean> getDestinationData();
////
////    @GET("/product/excursionsseat/info/query")
////    Observable<BusSiteDetailBean> getBusSiteDetail(@Query("groupId") int groupId);
////
////    //取消订单
////    @POST("/order/trade/info/cancel")
////    Observable<Object> oderCancel(@Query("orderNo") String orderNo, @Query("productType") int productType);
////    //用户申请退款
////    @POST("/order/trade/refund/apply")
////    Observable<Object> oderApply(@Body RequestBody body);
////    //用户申请退款
////    @GET("/order/findElectronicContract")
////    Observable<CommonBean> findElectronicContract(@Query("orderId") String orderId);
////
////    //添加历史记录
////    @Headers({"Content-Type: application/json", "Accept: application/json"})
////    @POST("/historyOperation/addHistory")
////    Observable<Object> addHistory(@Body RequestBody body);
//    //提交订单
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/order/packagedholiday/info/create")
//    Observable<OrderUpdateResultBean> creatOrderData(@Body RequestBody body);
//
//    //提交订单快捷游
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/order/packagedholiday/info/create")
//    Observable<OrderUpdateResultBean> creatQuickOrderData(@Body RequestBody body);
//
//    //产品列表页  郁闷 产品类型不同 接口不同 返回的数据类不同！！！！mmp！！
//    //跟团游 短途游 游轮 自助                1:跟团游，2:商务考察，3:高端境外体检，4:自助游，5:邮轮
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/product/productlist/query")
//    Observable<ProductListBean> getProductList(@Body RequestBody body);
//
//    //快捷旅游 快捷景区门票 mbpm/product/express/productlist/query
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/product/express/productlist/query")
//    Observable<ExpressProductListBean> getExpressProductList(@Body RequestBody body);
//    //签证 mbpm/product/new/agent/visa/list/query
//    //景区门票 mbpm/product/scenic/productlist/query
//    //快捷团队酒店 mbpm/product/express/productlist/query
//
//    //首页的直客动态消息
//    @GET("mbpm/browsingHistory/findStraightOrderListInfo?pageNum=1&pageSize=10")
//    Observable<CustomerInfo> getCustomeInfo();
//
//    //取消订单
//    @POST("/order/trade/info/cancel")
//    Observable<Object> oderCancel(@Query("orderNo") String orderNo, @Query("productType") int productType);
//
//    //用户申请退款
//    @POST("/order/trade/refund/apply")
//    Observable<Object> orderApplyRefund(@Body RequestBody body);
//
//    //查看合同
//    @POST("/mbpm/order/contract/info/query")
//    Observable<CommonBean> findElectronicContract(@Query("orderNo") String orderNo);
//
//    //订单详情
//    @GET("/order/userorder/info/query")
//    Observable<OrderDetailBean> queryOrderDetail(@Query("orderNo") String orderNo, @Query("productType") int productType);
//
//    //查询收藏
//    @POST("/collection/booleanCollection")
//    Observable<IsCollectionBean> queryIsCollection(@Query("productId") int productId);//查询收藏
//
//    //取消收藏
//    @POST("collection/deleteCollectionById")
//    Observable<IsCollectionBean> delCollection(@Query("id") int cId);
//
//    //添加收藏
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("collection/addProductCollection")
//    Observable<IsCollectionBean> addCollection(@Body RequestBody body);
//
//
//    //查询订单列表数量
//    @POST("/order/userorder/info/batchqueryCount")
//    Observable<BatchQueryCountBean> getBatchQueryCount();
//
//    //查询全部订单列表
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("mbpm/order/userorder/info/queryOrderList")
//    Observable<AllOrderBean> getAllOrder(@Body RequestBody body);
//
//    //B端App查询登录的用户信息
//    @GET("/user/common/query/info")
//    Observable<InfoBean> getInfo();
//
//    //查询直客浏览历史列表接口
//    @GET("/browsingHistory/findStraightBrowsingHistoryListInfo")
//    Observable<HistoryListBean> findStraightBrowsingHistoryListInfo(@Query("mtmUserId") String mtmUserId,
//                                                                    @Query("pageNum") int pageNum,
//                                                                    @Query("pageSize") int pageSize);
//
//    //查询直客动态列表
//    @GET("/browsingHistory/findStraightOrderListInfo")
//    Observable<GuestDynamicsBean> findStraightOrderListInfo(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
//
//    //查询直客信息列表
//    @GET("/user/findStraightListInfo")
//    Observable<GuestInformationBean> findStraightListInfo();//查询直客信息列表
//
//    //发送验证码支付
//    @GET("/sms/sendPaymentSms")
//    Observable<CommonBean> sendMSMCode();
//
//    //修改产品的对外价
//    @POST("/order/updateOrderOuterPrice")
//    Observable<CommonBean> changeProductOutPrice(@Query("orderNo") String orderNo, @Query("money") String money, @Query("remark") String remark, @Query("isInvoice") String isInvoice, @Query("invoiceId") String invoiceId, @Query("productType") int productType);
//
//    //支付
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("order/trade/payment/pay/safe")
//    Observable<CommonBean> payForOrder(@Body RequestBody body, @Query("payType") int payType);

}
