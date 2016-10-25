# Notes
Create a repository for myself.Mainly record some of my study notes.
###高仿IOS：从底部弹出的Dialog，拓展性比较高
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/ios_bottom_dialog.gif?raw=true" width="270" height="400"><img src="https://github.com/Elder-Wu/Notes/blob/master/gif/activity_anim1.gif?raw=true" width="270" height="400">

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
###"淘宝头条－京东头条"控件
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/taobao_headline.gif" width="270" height="400">

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
###广告倒计时View
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/countdown.gif" width="270" height="400">

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
###点赞头像列表
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/approve_list.gif" width="270" height="400">

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