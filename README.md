# Notes
Create a repository for myself.Mainly record some of my study notes.

[Demo apk下载地址](https://raw.githubusercontent.com/Elder-Wu/Notes/master/apk/notes-debug.apk)

**扫描二维码下载**

![](http://upload-images.jianshu.io/upload_images/2377552-0add330e65789ab4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
****
<table>
    <caption id="directory">目录</caption>
    <tr align="center">
        <td>
            <a href="#ios_dialog">高仿IOS底部弹窗（扩展性较高）</a>
        </td>
        <td>
            <a href="#taobao_headline">淘宝头条控件</a>
        </td>
        <td>
            <a href="#countdown">广告倒计时View</a>
        </td>
        <td>
            <a href="#approve_list">点赞列表</a>
        </td>
        <td>
            <a href="#float_view">可以悬浮在屏幕边缘的控件</a>
        </td>
        <td>
            <a href="#guaguaka">刮刮卡</a>
        </td>
        <td>
            <a href="#banner">广告栏无限循环</a>
        </td>
    </tr>
</table>

****
<h3 id="ios_dialog">高仿IOS底部弹窗（扩展性较高）</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
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
<h3 id="taobao_headline">淘宝头条控件</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
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
<h3 id="countdown">广告倒计时View</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/countdown.gif">

使用方法
```
_布局文件中使用_
<com.example.customview.widget.CountDownView
    android:id="@+id/fragment_count_down"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:layout_centerInParent="true"
    app:background_color="@color/gray_aaa"
    app:border_color="@color/blue_6eb"
    app:border_width="3dp"
    app:text="跳过广告"
    app:text_color="@color/white"
    app:text_size="55dp" />

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
<h3 id="approve_list">点赞列表</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/approve_list.gif">

使用方法
```
_布局中使用_
<com.example.customview.widget.ApproveListLayout
    android:id="@+id/approve_list_layout"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    app:pic_count="9"
    app:pic_offset="0.3f"
    app:pic_size="20dp"/>

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
<h3 id="float_view">可以悬浮在屏幕边缘的控件</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/drag_layout.gif">

使用方法：直接将DragLayout包裹在你想要悬浮的控件外面就好了，任何控件都阔以
```
<com.example.notes.widget.DragLayout
    android:id="@+id/fragment_drag_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@color/black_444444"
        android:padding="20dp"
        android:text="我是一个可以拖动的View"
        android:textColor="@color/white" />
    <de.hdodenhof.circleimageview.CircleImageView
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
    <com.example.notes.widget.CustomClock
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:layout_marginTop="200dp" />
</com.example.notes.widget.DragLayout>
```
<h3 id="guaguaka">刮刮卡</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/guaguaka.gif">

使用方法
```
<com.wuzhanglao.niubi.widget.GuaGuaKa
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
<h3 id="banner">广告栏无限循环</h3>
<p><a href="#directory"><font color="#3262b3" size="3dp">返回目录</font></a></p>
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/banner1.gif">&nbsp;&nbsp;<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/banner2.gif">

使用方法
```
@Override
public void initView(View view, @Nullable Bundle savedInstanceState) {
    banner = (ImageBanner) view.findViewById(R.id.fragment_viewpager_imagebanner);
    banner.addImage(getImageView(R.drawable.mv1));
    banner.addImage(getImageView(R.drawable.mv2));
    banner.addImage(getImageView(R.drawable.mv3));
    banner.addImage(getImageView(R.drawable.mv4));
}
private ImageView getImageView(int resId) {
    ImageView image = new ImageView(context);
    image.setImageResource(resId);
    return image;
}
```
