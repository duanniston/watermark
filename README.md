# Watermark Library to Android 

<a href='https://bintray.com/duanniston/repository/watermarklib/_latestVersion'><img src='https://api.bintray.com/packages/duanniston/repository/watermarklib/images/download.svg'></a>


```gradle
 repositories {
    jcenter()
    //This repository is not necessary, but if  jcenter() not work, add the line below 
     maven {
         url  "http://dl.bintray.com/duanniston/repository" 
     }
 }
 ```

```gradle
compile 'br.com.duanniston:watermarklib:0.0.4'
```


> minSdkVersion 16

## Usage

#### Generating watermark from XML (view)

```java
private Bitmap generateWaterMark(Bitmap src) {

 //src is your original image
 WaterMark waterMark = new WaterMark(mContext);
 //return the your original image with watermark added
 return waterMark.getImageWaterMarkFromView(src, R.layout.watermark_all);
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:gravity="center"
    android:orientation="vertical">

<TextView
        android:layout_width="match_parent"
        android:layout_gravity="center"
        android:gravity="center"
        android:layout_height="match_parent"
        android:text="watermark" />
</LinearLayout>
```



