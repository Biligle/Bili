#请关注更新

Q群：468488556 JitPack

#一、视图
![image](https://github.com/Biligle/Bili/blob/master/picture/a.png)
![image](https://github.com/Biligle/Bili/blob/master/picture/b.png)
![image](https://github.com/Biligle/Bili/blob/master/picture/c.png)
![image](https://github.com/Biligle/Bili/blob/master/picture/d.png)
![image](https://github.com/Biligle/Bili/blob/master/picture/e.png)

#二、简介
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

#三、引用动态库

studio导入依赖

在根build.gradle里：

allprojects { repositories { ... maven { url "https://jitpack.io" } } }

在第二个build.gradle里：

dependencies {
        compile 'com.github.Biligle:Bili:1.0.3'
}

#使用方法
##1、圆形ImageView

直接说用法了：
圆形ImageView
直接用，
<com......CircleIamgeVIew>
width
height
src
设置外环civ_border_width="2dp"
设置外环颜色civ_border_color="@color/colorAccent"
</com......CircleIamgeVIew>

##2、设置头像（相册和照相）

实例化头像类（照相和相册）
HeaderPicture headerPicture = new HeaderPicture(PLoginActivity.this);
	
照相方法：camera()
	
相册方法：gallery()

回调设置头像，只需要继承HeaderActivity，实现setPhoto()




##3、侧滑菜单
	//初始化SliderHolder，首先隐藏策划菜单
	baseView.slideHolder.setEnabled(false);
	//baseView.slideHolder.setDirection(-1);//右边滑出
	//baseView.slideHolder.setSpeed(2);//设置滑出速度
        baseView.slideHolder.setOnSlideListener(new SlideHolder.OnSlideListener() {
            @Override
            public void onSlideCompleted(boolean opened) {
                baseView.slideHolder.setEnabled(false);
            }
        });

	//点击之后菜单滑出
	baseView.slideHolder.setEnabled(true);
        baseView.slideHolder.toggle();
        baseView.layoutRelativeLeft.setOnScrollListener
		//右边滑出
		doOnScrollRight{
			if(null != baseView.slideHolder){
                            if(baseView.slideHolder.isOpened()){
                                 baseView.slideHolder.close();
                                 return;
                            }
                        baseView.slideHolder.setEnabled(true);
                        baseView.slideHolder.toggle();
                    }
		}
		//左边滑出
		doOnScrollLeft{代码同上}
##4、解决项目崩溃问题
	在Application的onCreate方法里添加
	CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this,**.class);//处理未捕捉的异常，避免出现崩溃

##5、发送短信
   new SendMesssage(PLoginActivity.this).sendSMS("hello，world！");

##6、监听截取短信
   //实现接口IGetMessage
   PLoginActivity implements IGetMessage
   //实现方法
    @Override
    public void getMessage(String ms) {
        MyToastView.showToast(ms,this);
    }
   @Override
    protected void onResume() {
        super.onResume();
        //注册截取短信监听
        autoGetCodeUtil = new GetMessageUtil(this,this,
                new Handler(),null);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"),true,autoGetCodeUtil);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //解绑截取短信监听
        if(getContentResolver() != null && autoGetCodeUtil != null){
            getContentResolver().unregisterContentObserver(autoGetCodeUtil);
        }
    }

##7、打电话
    new Call(PLoginActivity.this).call("10086");
    
##8、录音
   new VideoUtil(PLoginActivity.this).soundRecorderMethod();
   
##9、视频
   new VideoUtil(PLoginActivity.this).videoMethod();
   
##10、压缩图片
   ActivityResult.crop = ture;//false:剪裁，true:不剪裁（如果不设置，默认剪裁）
   照相和相册，如果不调用剪裁功能，就调用压缩功能（HeaderPicture里的compress方法）
   
##11、筛选图片
   实现ISelect接口
   
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        new GetPicture(this,this);
    }
    
   /**
    * @params pictureList:所有图片路径
    * @params firstPictureList: 每个文件夹的封面图片路径
    */
   @Override
    public void getPicture(ArrayList<String> pictureList,ArrayList<String> firstPictureList) {
        if(pictureList.size() > 0){
            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("data",pictureList);
            bundle.putStringArrayList("data2",firstPictureList);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }
    
    /**
     * 获取文件夹中的所有图片
     * @param path 每个文件夹封面图片路径
     */
    public static ArrayList<String> getFilePic(String path)
    
    /**
     * 压缩图片，获得bitmap
     * @param path 图片路径
     * @param maxSize 压缩尺寸
     * @return
     */
    public static Bitmap compress(String path,double maxSize)
