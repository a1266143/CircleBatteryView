# CircleBatteryView

***
圆环电池电量View
***

*效果*

![image](https://github.com/a1266143/CircleBatteryView/blob/master/image/example4.png)

***
**依赖**
***

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency to your module's build.gradle
```
dependencies {
	    implementation 'com.github.a1266143:CircleBatteryView:Tag'
}
```

***
**使用**
***

在您的布局文件中添加如下代码即可:
```
<com.lee.circlebatteryview.CircleBatteryView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:_batteryLevel="85"
        />
```

设置电池电量值:
CircleBatteryViewInstance.setBattery(50,false);

设置电池充电状态:
CircleBatteryViewInstance.setBattery(50,true);

That's it

***
**注意事项**
***

因为CircleBatteryView宽度和高度默认是一样的,如果你在布局文件中给CircleBatteryView使用不同的宽度和高度,CircleBatteryView会使用宽度和高度之中的更大值作为默认值

在你的布局文件中输入 "_" 即可显示所有自定义属性

|属性名              |属性格式          | 属性描述     | 对应方法 |
| -----------------  | -------------- | ---------------- |----------------|
|_backColor        |color             | 底层圈颜色    |setBackColor|
|_foreColor         |color             | 上层圈颜色    |setForeColor|
|_backStrokeWidth|dimension | 底层圈线宽     |setBackStrokeWidth|
|_foreStrokeWidth|dimension  |上层圈线宽      |setForeStrokeWidth|
|_showBatteryText|boolean     |是否显示电池电量文本|showBatteryText|
|_batteryTextSize |dimension   | 电量文本字体大小 |setBatteryTextSize|
|_batteryTextColor|color          |电池电量文本颜色|setBatteryTextColor|
|_batteryLevel      |integer        |电池电量         |setBattery|


