# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

-keep class com.sdjnshq.circle.data.bean.** { *; }
-keep class com.kunminx.player.bean.** { *; }

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# webview
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}


# AndroidX

-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

# OkHttp

-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# RxJava

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-dontwarn sun.misc.Unsafe


# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


# 定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.loc.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

#Ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#Okio
-dontwarn org.codehaus.mojo.animal_sniffer.*

#eventbus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# video

-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }

-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }