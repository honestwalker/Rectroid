# Rectroid
Android 组件化开发框架

![Fastroid](http://www.kancart.com/images/kancart_logo.png)

Rectroid是有上海信行软件Android团队开发的Android组件化开发框架。类似于Vue.js。但这是一套基于Android Native的组件化开发方式。
Rectroid的目标是通过尽可能简单的将页面、视图、控件甚至功能逻辑已特定方式封装达到高可复用性。甚至一般情况下，创建页面，无需建立一个
Activity类和AndroidManifest中配置Activity，只需要建立一个组件，由它开始，拼装组件直至完成页面。因为在Rectroid中，
从Activity布局本身可视为一个组件直至它的内容每一块和一个控件都可以是一个组件。



## 导入

- 项目根目录build.gradle 配置
````Gradle
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
````




- 主项目build.gradle中配置Rectroid的依赖。
````Gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
      compile 'com.github.honestwalker:Rectroid:0.1.0'
    }
}
````

## 使用


- AndroidManifest 配置公共Activity。

ComponentActivity是一个通用组件化的Activity。但他不是必须的。因为在任何Activity和Fragment中，都可以自己加载组件。
ComponentActivity的一个目的是为了减少Activity类的编写。你只需要和创建普通类一样去创建一个组件类，然后跳转到ComponentActivity时告诉他用
什么组件去渲染。所以一般来说不需要在建立一个Activity类。如果需要多进程和AndroidManifest中配置特殊的Activity，也可和传统开发一样建立Activity
Rectroid和传统开发完全兼容，另外也可以继承至ComponentActivity。

````
<activity android:name="com.honestwalker.android.xview.ComponentActivity"
          android:theme="@android:style/Theme.NoTitleBar"  
          android:screenOrientation="portrait"                                                                                             android:configChanges="orientation|keyboardHidden|screenSize" 
          android:windowSoftInputMode="adjustResize">
     <!-- 第一个加载的组件 -->
     <meta-data android:value="com.honestwalker.android.modules.commons.components.WelcomeComponent" android:name="main-component" />

</activity>
````

- 建立一个组件

一个组件其实就是一个View，他看起来就像是个自定义控件，但又并非仅此。首先思想上来说，在Rectroid中，从开发一个页面开始，你关心的是页面分块，
就是给一个页面划分区域，这是只需要关心页面上有哪几块内容，哪部分是我们可能复用的，或想引用已经写好的组件。

组件布局写法是这样的： (它本身就是R.layout.component_base，所以一般建立组件页面不用建立此布局，可以反复复用)
````
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="#ffffff">

    <com.honestwalker.android.xview.XView
        android:id="@+id/content"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />

</LinearLayout>
````
XView 是一个组件的容器。传统开发来说，从这个布局文件开始，我们开始要关心这个页面上面有什么元素。然后意义罗列和布局。而在一个


组建对象的写法
````
public class HelloComponent extends Component {

    /**
     * 头像选择组件
     */
    private SelectAvatarComponent avatarComponent;

    /**
     * 测试按钮组件
     */
    private ButtonComponent buttonComponent;

    /**
     * 搜索页面组件
     */
    private SearchPageComponent searchPage;

    public HelloComponent(Context context) {
        super(context);
    }

    public HelloComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HelloComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRegisterRectView() {
        registerRectView(R.id.component1);   // 注册容器1
        registerRectView(R.id.component2);   // 注册容器2
        registerRectView(R.id.component3);   // 注册容器2
    }

    @Override
    protected void initComponent() {
        avatarComponent = bind(R.id.component1, SelectAvatarComponent.class);// 获取组件1  (同时会渲染组件)
        searchPage      = bind(R.id.component3, SearchPageComponent.class);  // 获取组件2  (同时会渲染组件)
        buttonComponent = bind(R.id.component2, ButtonComponent.class);      // 获取组件2  (同时会渲染组件)
    }

    /**
     * 组件布局
     * @return
     */
    @Override
    protected int contentViewLayout() {
        return R.layout.component_hello;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        // 组件的一些内容设置
        buttonComponent.setText("通用下拉刷新listview demo");
        searchPage.setSearchHint("请输入搜索内容");
        searchPage.setSearchBtnText("搜索");
        
        // 组件控件事件绑定
        bindEvent(buttonComponent.getButton(), R.id. component2);
    }

    @OnClick(R.id.component2)
    public boolean buttonComponentOnClick() {
        ComponentActivity.startComponent(getActivity(), WebViewDemoComponent.class);
        return true;
    }

}
````

一个组件继承Component类。
onRegisterRectView 方法注册组件，需要把布局中的组件向当前组件注册。
initComponent 方法是对子组件的一些初始化，初始化主要是告诉子组件用什么组件去渲染。
contentViewLayout 方法是此组件加载的布局。

组件的生命周期，同Activity生命周期依次被调用。

bindEvent方法 把一个控件或组件和一个事件绑定，给一个方法添加注解如@OnClick(组件id) ， 绑定事件时bindEvent(buttonComponent.getButton(), 组件id); ，执行相应事件时，就会回调该方法。

编辑中.....
