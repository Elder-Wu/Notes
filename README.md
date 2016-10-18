# Notes
Create a repository for myself.Mainly record some of my study notes.
###高仿IOS：从底部弹出的Dialog，拓展性比较高
<img src="https://github.com/Elder-Wu/Notes/blob/master/gif/ios_bottom_dialog.gif?raw=true" width="270" height="370">

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
