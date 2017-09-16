# AreaSelect
项目：四级地址选择器

![Aaron Swartz](https://github.com/TheWindMeanFar/AreaSelect/blob/master/Screenshot/Screenshot_1505531470.png)

一、使用方式

1、在 project 的 build.gradle 中添加
``` 
allprojects {      
  repositories {          
    jcenter()            
    maven { url 'https://jitpack.io' }       
    maven { url 'https://maven.google.com' }
}
``` 
2、在 module 的 build.gradle 中添加
```
compile 'com.github.TheWindMeanFar:AreaSelect:1.0'
```
3、调用方法
```
new AreaSelector(context, 4, new FinishListener() {   
  @Override    
  public void finish(AreaInfo areaInfo) {       
    //地址信息在 AreaInfo 中
  }
}).setTitleBgColor(R.color.red).show();
```
参数1：Context

参数2：数据库中最多可选择4级，也可以是1、2、3

参数3：地址选择完成的监听器，AreaInfo中包含了地址的信息

二、注意事项

项目中的数据库用了谷歌最新的 ROOM 框架，使用的 compileSdkVersion、buildToolsVersion、support 版本都是 26 的，如果引入本项目之后出现版本不一致，可尝试改为 26。
