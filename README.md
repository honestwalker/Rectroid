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
