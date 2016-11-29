# Rectroid
Android 组件化开发框架

![Rectroid](http://www.kancart.com/images/kancart_logo.png)

Rectroid是有上海信行软件Android团队开发的Android组件化开发框架。是一套基于Android Native的组件化开发方式。
Rectroid的目标是通过尽可能简单的将页面、视图、控件甚至功能逻辑已特定方式封装达到高可复用性。一般情况下，创建页面，无需再建立一个
Activity类和AndroidManifest中配置Activity，只需要建立一个组件，由此组件开始，拼装其他组件直至完成页面。
在传统开发中，在布局里我们关心是什么控件在什么位置什么样式，在Rectroid中，我们关心的是此页面或此布局有哪些区域，然后去指定这些区域用什么组件去渲染。
这时，这样Rectroid会自动去组装页面，并且包含了组件各自的功能。组件可以多层次的套用其他组件，达到更高的复用性和灵活性。


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
