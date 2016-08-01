 适用于懒人群体，以及新手，请关注更新
 
 Q群：468488556  JitPack

1、模式：MVP

2、圆形ImageView

3、设置头像（相册和照相）

4、侧滑菜单

5、解决项目崩溃问题

6、studio导入依赖

  在根build.gradle里：
  
  allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
	
	在第二个build.gradle里：
	
	dependencies {
	        compile 'com.github.Biligle:Bili:1.0.0'
	}
