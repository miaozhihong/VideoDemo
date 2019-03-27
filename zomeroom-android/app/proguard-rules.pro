 # Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#百度混淆
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#友盟混淆配置
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
 *;
}
-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep public class com.umeng.com.umeng.soexample.R$*{
public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
public static final int *;
    }
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keepattributes Signature

-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

-keepattributes InnerClasses,Signature
-keepattributes *Annotation*

-keep class cn.qqtheme.framework.entity.** { *;}
#---------------------------------3.与js互相调用的类------------------------
-keepclasseswithmembers class cn.com.yktour.www.ylty.mvp.ui.activity.HomeActivity$JsInteration {
          <methods>;
    }

#-------------------------------------------------------------------------
#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
#-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

-dontoptimize
-dontwarn dalvik.**
#-overloadaggressively

#@proguard_debug_start
# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-renamesourcefileattribute TbsSdkJava
-keepattributes SourceFile,LineNumberTable
#@proguard_debug_end


#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Application{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class *{
 public <init>(org.json.JSONObject);
}
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-printmapping build/outputs/mapping/release/mapping.txt
#pushsdk
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**



-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

#（可选）避免Log打印输出
-assumenosideeffects class android.util.Log {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
 }

 # Add project specific ProGuard rules here.
 # By default, the flags in this file are appended to flags specified
 # in C:\Users\73559\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
 # You can edit the include path and order by changing the proguardFiles
 # directive in build.gradle.
 #
 # For more details, see
 #   http://developer.android.com/guide/developing/tools/proguard.html

 # Add any project specific keep options here:

 # If your project uses WebView with JS, uncomment the following
 # and specify the fully qualified class name to the JavaScript interface
 # class:
 #-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 #   public *;
 #}

 # Uncomment this to preserve the line number information for
 # debugging stack traces.
 #-keepattributes SourceFile,LineNumberTable

 # If you keep the line number information, uncomment this to
 # hide the original source file name.
 #-renamesourcefileattribute SourceFile
 #环信#
-keep class com.hyphenate.** {*;}
-dontwarn com.hyphenate.**
-keep class com.superrtc.** {*;}

     #okhttp
     -dontwarn okhttp3.**
     -keep class okhttp3.**{*;}

     #okio
     -dontwarn okio.**
     -keep class okio.**{*;}

     #retrofit
     -dontwarn retrofit.**
     -keep class retrofit.** { *; }
     -keepattributes Signature
     -keepattributes Exceptions
     -dontwarn okio.**
     -dontwarn retrofit2.**
     -keep class retrofit2.** { *; }
     -dontwarn javax.annotation.**
     -dontwarn javax.inject.**
     # OkHttp3
     -dontwarn okhttp3.logging.**
     -keep class okhttp3.internal.**{*;}

     # RxJava RxAndroid
     -dontwarn sun.misc.**
     -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
         long producerIndex;
         long consumerIndex;
     }
     -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
         rx.internal.util.atomic.LinkedQueueNode producerNode;
     }
     -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
         rx.internal.util.atomic.LinkedQueueNode consumerNode;
     }

     # Gson
     -keep class com.google.gson.stream.** { *; }
     -keepattributes EnclosingMethod
     -keep class com.jm.bean.**{*;}
     -keep class cn.com.yktour.network.bean.**{*;}
     #Butterknife
     -keep class butterknife.** { *; }
     -dontwarn butterknife.internal.**
     -keep class **$$ViewBinder { *; }

     -keepclasseswithmembernames class * {
         @butterknife.* <fields>;
     }
     -keepclasseswithmembernames class * {
         @butterknife.* <methods>;
     }
     #友盟
     -keepclassmembers class * {
        public <init> (org.json.JSONObject);
     }
     -keep public class com.jm.R$*{
     public static final int *;
     }
     #分享
     -keep class com.umeng.scrshot.**
     -keep public class com.tencent.** {*;}
     -keep class com.umeng.socialize.sensor.**
     -keep class com.umeng.socialize.handler.**
     -keep class com.umeng.socialize.handler.*
     -keep class com.umeng.weixin.handler.**
     -keep class com.umeng.weixin.handler.*
     -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
     -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
     -keep class com.tencent.mm.sdk.** {
        *;
     }
     -keep class com.tencent.mm.opensdk.** {
        *;
     }
     -keep class com.tencent.wxop.** {
        *;
     }
     -keep class com.tencent.mm.sdk.** {
        *;
     }
     -keepclassmembers enum * {
         public static **[] values();
         public static ** valueOf(java.lang.String);
     }

     -keep class com.tencent.open.TDialog$*
     -keep class com.tencent.open.TDialog$* {*;}
     -keep class com.tencent.open.PKDialog
     -keep class com.tencent.open.PKDialog {*;}
     -keep class com.tencent.open.PKDialog$*
     -keep class com.tencent.open.PKDialog$* {*;}
     -keep class com.umeng.socialize.impl.ImageImpl {*;}
     #百度地图
     -keep class com.baidu.** {*;}
     -keep class vi.com.** {*;}
     -dontwarn com.baidu.**
     #Bugly
     -dontwarn com.tencent.bugly.**
     -keep public class com.tencent.bugly.**{*;}
     -keep class android.support.**{*;}
     #支付宝
     -keep class com.alipay.android.app.IAlixPay{*;}
     -keep class com.alipay.android.app.IAlixPay$Stub{*;}
     -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
     -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
     -keep class com.alipay.sdk.app.PayTask{ public *;}
     -keep class com.alipay.sdk.app.AuthTask{ public *;}
     -keep class com.alipay.sdk.app.H5PayCallback {
         <fields>;
         <methods>;
     }
     -keep class com.alipay.android.phone.mrpc.core.** { *; }
     -keep class com.alipay.apmobilesecuritysdk.** { *; }
     -keep class com.alipay.mobile.framework.service.annotation.** { *; }
     -keep class com.alipay.mobilesecuritysdk.face.** { *; }
     -keep class com.alipay.tscenter.biz.rpc.** { *; }
     -keep class org.json.alipay.** { *; }
     -keep class com.alipay.tscenter.** { *; }
     -keep class com.ta.utdid2.** { *;}
     -keep class com.ut.device.** { *;}
     #支付宝特别注意需要此混淆项
     -dontwarn android.net.SSLCertificateSocketFactory
     #Glide
     -keep public class * implements com.bumptech.glide.module.GlideModule
     -keep public class * extends com.bumptech.glide.module.AppGlideModule
     -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
       **[] $VALUES;
       public *;
     }
 #---------------------------------3.与js互相调用的类------------------------
 -keepclasseswithmembers class cn.dreamtowns.jints.fragment.Page_Frum$JSInterface {
           <methods>;
     }
     -keepclasseswithmembers class cn.dreamtowns.jints.activity.CommontWebViewActivity$JSInterface {
               <methods>;
     }
     -keepclasseswithmembers class cn.dreamtowns.jints.activity.AlipayWevActivity$JSInterface {
                   <methods>;
     }


 #-------------------------------------------------------------------------

 #---------------------------------4.反射相关的类和方法-----------------------



 #----------------------------------------------------------------------------
 #---------------------------------------------------------------------------------------------------

 #-------------------------------------------基本不用动区域--------------------------------------------
 #---------------------------------基本指令区----------------------------------
 -optimizationpasses 5
 -dontusemixedcaseclassnames
 -dontskipnonpubliclibraryclasses
 -dontskipnonpubliclibraryclassmembers
 -dontpreverify
 -verbose
 -ignorewarnings
 -printmapping proguardMapping.txt
 -optimizations !code/simplification/cast,!field/*,!class/merging/*
 -keepattributes *Annotation*,InnerClasses
 -keepattributes Signature
 -keepattributes SourceFile,LineNumberTable
 #----------------------------------------------------------------------------

 #---------------------------------默认保留区---------------------------------
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Appliction
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class * extends android.view.View
 -keep public class com.android.vending.licensing.ILicensingService
 -keep class android.support.** {*;}

 -keepclasseswithmembernames class * {
     native <methods>;
 }
 -keepclassmembers class * extends android.app.Activity{
     public void *(android.view.View);
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
 -keep public class * extends android.view.View{
     *** get*();
     void set*(***);
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }
 -keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }
 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }
 -keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }
 -keep class **.R$* {
  *;
 }
 -keepclassmembers class * {
     void *(**On*Event);
 }
 #----------------------------------------------------------------------------

 #---------------------------------webview------------------------------------
 -keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
 }
 -keepclassmembers class * extends android.webkit.webViewClient {
     public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
     public boolean *(android.webkit.WebView, java.lang.String);
 }
 -keepclassmembers class * extends android.webkit.webViewClient {
     public void *(android.webkit.webView, jav.lang.String);
 }
 #----------------------------------------------------------------------------
 #---------------------------------------------------------------------------------------------------

 -dontoptimize
 -dontwarn dalvik.**
 #-overloadaggressively

 #@proguard_debug_start
 # ------------------ Keep LineNumbers and properties ---------------- #
 -keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
 -renamesourcefileattribute TbsSdkJava
 -keepattributes SourceFile,LineNumberTable
 #@proguard_debug_end


 #------------------  下方是android平台自带的排除项，这里不要动         ----------------

 -keep public class * extends android.app.Activity{
 	public <fields>;
 	public <methods>;
 }
 -keep public class * extends android.app.Application{
 	public <fields>;
 	public <methods>;
 }
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference

 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 -keepclasseswithmembers class * {
 	public <init>(android.content.Context, android.util.AttributeSet);
 }

 -keepclasseswithmembers class * {
 	public <init>(android.content.Context, android.util.AttributeSet, int);
 }

 -keepattributes *Annotation*

 -keepclasseswithmembernames class *{
 	native <methods>;
 }

 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }

 #------------------  下方是共性的排除项目         ----------------
 # 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
 # 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

 -keepclasseswithmembers class * {
     ... *JNI*(...);
 }

 -keepclasseswithmembernames class * {
 	... *JRI*(...);
 }

 -keep class **JNI* {*;}

 #友盟分享
 -dontshrink
 -dontoptimize
 -dontwarn com.google.android.maps.**
 -dontwarn android.webkit.WebView
 -dontwarn com.umeng.**
 -dontwarn com.tencent.weibo.sdk.**
 -dontwarn com.facebook.**
 -keep public class javax.**
 -keep public class android.webkit.**
 -dontwarn android.support.v4.**
 -keep enum com.facebook.**
 -keepattributes Exceptions,InnerClasses,Signature
 -keepattributes *Annotation*
 -keepattributes SourceFile,LineNumberTable

 -keep public interface com.facebook.**
 -keep public interface com.tencent.**
 -keep public interface com.umeng.socialize.**
 -keep public interface com.umeng.socialize.sensor.**
 -keep public interface com.umeng.scrshot.**

 -keep public class com.umeng.socialize.* {*;}


 -keep class com.facebook.**
 -keep class com.facebook.** { *; }
 -keep class com.umeng.scrshot.**
 -keep public class com.tencent.** {*;}
 -keep class com.umeng.socialize.sensor.**
 -keep class com.umeng.socialize.handler.**
 -keep class com.umeng.socialize.handler.*
 -keep class com.umeng.weixin.handler.**
 -keep class com.umeng.weixin.handler.*
 -keep class com.umeng.qq.handler.**
 -keep class com.umeng.qq.handler.*
 -keep class UMMoreHandler{*;}
 -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
 -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
 -keep class im.yixin.sdk.api.YXMessage {*;}
 -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
 -keep class com.tencent.mm.sdk.** {
    *;
 }
 -keep class com.tencent.mm.opensdk.** {
    *;
 }
 -keep class com.tencent.wxop.** {
    *;
 }
 -keep class com.tencent.mm.sdk.** {
    *;
 }
 -dontwarn twitter4j.**
 -keep class twitter4j.** { *; }

 -keep class com.tencent.** {*;}
 -dontwarn com.tencent.**
 -keep class com.kakao.** {*;}
 -dontwarn com.kakao.**
 -keep public class com.umeng.com.umeng.soexample.R$*{
     public static final int *;
 }
 -keep public class com.linkedin.android.mobilesdk.R$*{
     public static final int *;
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 -keep class com.tencent.open.TDialog$*
 -keep class com.tencent.open.TDialog$* {*;}
 -keep class com.tencent.open.PKDialog
 -keep class com.tencent.open.PKDialog {*;}
 -keep class com.tencent.open.PKDialog$*
 -keep class com.tencent.open.PKDialog$* {*;}
 -keep class com.umeng.socialize.impl.ImageImpl {*;}
 -keep class com.sina.** {*;}
 -dontwarn com.sina.**
 -keep class  com.alipay.share.sdk.** {
    *;
 }

 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }

 -keep class com.linkedin.** { *; }
 -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
 -keepattributes Signature
# 权限三方包混淆
 -dontwarn com.yanzhenjie.permission.**
# 选择器混淆
-keepattributes InnerClasses,Signature
-keepattributes *Annotation*

-keep class cn.qqtheme.framework.entity.** { *;}
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.amap.mapcore.*{*;}
    -keep   class com.amap.api.trace.**{*;}

    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.**{*;}
    -keep   class com.amap.api.trace.**{*;}

    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}

    -keep   class com.amap.api.services.**{*;}

    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}