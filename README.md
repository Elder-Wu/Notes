# Notes
Create a repository for myself.Mainly record some of my study notes.

[Demo apk下载地址](https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes.apk)

**扫描二维码下载**

<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/qr_code.png?raw=true">
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/app_ui.png?raw=true">

****
###全文目录
<p id="directory"><font color="#FF0000">版权声明：原创作品，每个控件都是自己辛辛苦苦写出来的，如需转载，请注明出处！</font></p>
<table>
    <tr align="center">
        <td>
            <a href="#ios_dialog">高仿ios底部弹窗</a>
        </td>
        <td>
            <a href="#taobao_headline">高仿京东头条</a>
        </td>
        <td>
            <a href="#countdown">广告倒计时控件</a>
        </td>
        <td>
            <a href="#approve_list">点赞列表</a>
        </td>
        <td>
            <a href="#float_view">可以悬浮在屏幕边缘的控件</a>
        </td>
    </tr>
    <tr align="center">
        <td>
            <a href="#guaguaka">刮刮卡</a>
        </td>
        <td>
            <a href="#banner">广告栏无限循环</a>
        </td>
        <td>
            <a href="#bezier_view">美拍点赞效果</a>
        </td>
    </tr>
</table>

****
<h3 id="ios_dialog">高仿IOS底部弹窗（扩展性较高）</h3>
<p>
    <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
    <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
    <a href="http://www.jianshu.com/p/2ebc5cc80835" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/ios_bottom_dialog.gif?raw=true">&nbsp;&nbsp;<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/activity_anim1.gif?raw=true">

使用方法
```
IosBottomDialog.Builder builder = new IosBottomDialog.Builder(context);
//1个标题＋2个操作
builder.setTitle("标题",Color.RED)
    .addOption("操作1",Color.DKGRAY,new IosBottomDialog.OnOptionClickListener(){
        @Override
        public void onOptionClick(){
            ToastUtils.show("操作1");
        }
    })
    .addOption("操作2",Color.DKGRAY,new IosBottomDialog.OnOptionClickListener(){
        @Override
        public void onOptionClick(){
            ToastUtils.show("操作2");
        }
    }).create().show();
```
****
<h3 id="taobao_headline">淘宝头条控件</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/3a7688cab9e1" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/taobao_headline.gif">

使用方法
```
List<HeadlineBean> data = new ArrayList<>();
data.add(new HeadlineBean("热门", "袜子裤子只要998～只要998～"));
data.add(new HeadlineBean("推荐", "秋冬上心，韩流服饰，一折起"));
data.add(new HeadlineBean("好货", "品牌二手车"));
data.add(new HeadlineBean("省钱", "MadCatz MMO7 游戏鼠标键盘套装"));

taobaoHeadline = (TaobaoHeadline) view.findViewById(R.id.fragment_taobao_headline_headline);
taobaoHeadline.setData(data);
taobaoHeadline.setHeadlineClickListener(new TaobaoHeadline.HeadlineClickListener() {
    @Override
    public void onHeadlineClick(HeadlineBean bean) {
        ToastUtils.show(bean.getTitle() + ":" + bean.getContent());
    }

    @Override
    public void onMoreClick() {
        ToastUtils.show("更多");
    }
});
```
****
<h3 id="countdown">广告倒计时View</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/3db73ba78882" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/countdown.gif">

使用方法
```
_布局文件中使用_
<com.wuzhanglao.niubi.widget.CountDownView
    android:id="@+id/fragment_count_down"
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:layout_alignParentRight="true"
    android:layout_margin="5dp"
    app:background_color="@color/gray_aaa"
    app:border_color="@color/blue_6eb"
    app:border_width="2dp"
    app:text="跳过广告"
    app:text_color="@color/white"
    app:text_size="14dp" />

_代码中使用_
countDownView.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {
    @Override
    public void onStartCount() {
        ToastUtils.show("开始了");
    }

    @Override
    public void onFinishCount() {
        ToastUtils.show("结束了");
    }
});
countDownView.start();
```
****
<h3 id="approve_list">点赞列表</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/993cea9d6631" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/approve_list.gif">

使用方法
```
_布局中使用_
<com.wuzhanglao.niubi.widget.ApproveListLayout
    android:id="@+id/approve_list_layout"
    android:layout_width="match_parent"
    android:layout_height="50dp" />

_代码中使用_
approveList = new ArrayList<>();
approveList.add(R.drawable.demo);
approveList.add(R.drawable.demo);
approveList.add(R.drawable.demo);
approveList.add(R.drawable.demo);
approveList.add(R.drawable.demo);

approveListLayout = (ApproveListLayout) view.findViewById(R.id.approve_list_layout);
approveListLayout.updateApproveList(approveList);
```
****
<h3 id="float_view">可以悬浮在屏幕边缘的控件</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/8b2247b3112d" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/drag_layout.gif">

使用方法：直接将DragLayout包裹在你想要悬浮的控件外面就好了，任何控件都阔以
```
<com.wuzhanglao.niubi.widget.FloatViewLayout
    android:id="@+id/fragment_drag_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:background="@color/black_444444"
        android:padding="20dp"
        android:text="我是一个可以拖动的View"
        android:textColor="@color/white" />
    <com.wuzhanglao.niubi.widget.CircleImageView
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_marginTop="80dp"
        android:src="#22000000" />
    <ImageView
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_marginTop="140dp"
        android:scaleType="centerCrop"
        android:src="@drawable/demo" />
    <com.wuzhanglao.niubi.widget.CustomClock
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:layout_marginTop="200dp" />
</com.wuzhanglao.niubi.widget.FloatViewLayout>
```
****
<h3 id="guaguaka">刮刮卡</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/a9abd8f7ef51" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/guaguaka.gif">

使用方法
```
<com.wuzhanglao.niubi.widget.GuaGuaKa
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
****
<h3 id="banner">广告栏无限循环</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/15dfbf64cf7a" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/banner1.gif">&nbsp;&nbsp;<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/banner2.gif">

使用方法
```
banner = (Banner) view.findViewById(R.id.fragment_viewpager_imagebanner);
banner.addImage(getImageView(R.drawable.mv1));
banner.addImage(getImageView(R.drawable.mv2));
banner.addImage(getImageView(R.drawable.mv3));
banner.addImage(getImageView(R.drawable.mv4));

private ImageView getImageView(int resId) {
    ImageView image = new ImageView(context);
    image.setImageResource(resId);
    return image;
}
```
<h3 id="bezier_view">美拍点赞效果</h3>
<p>
     <a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a>
     <a href="https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk"><font color="#3262b3" size="3dp">Demo apk下载</font></a>
     <a href="http://www.jianshu.com/p/6e5230503745" target="_blank"><font color="#3262b3" size="3dp">博客原文</font></a>
</p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/bezier_view.gif">

使用方法
```
<com.wuzhanglao.niubi.widget.BezierView
    android:layout_width="100dp"
    android:layout_height="match_parent"
    android:layout_alignParentRight="true"
    android:layout_marginTop="200dp" />
```