#请关注更新

Q群：468488556 JitPack

1、模式：MVP

2、圆形ImageView

3、设置头像（相册和照相）

4、侧滑菜单

5、解决项目崩溃问题

6、发送短信

7、监听截取短信

8、打电话

9、录音

10、录视频

11、压缩图片

12、筛选图片

13、studio导入依赖

在根build.gradle里：

allprojects { repositories { ... maven { url "https://jitpack.io" } } }

在第二个build.gradle里：

dependencies {
        compile 'com.github.Biligle:Bili:1.0.0'
}
