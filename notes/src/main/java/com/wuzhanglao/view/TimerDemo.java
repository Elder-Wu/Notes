package com.wuzhanglao.niubi.view;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.misc.DemoActivity;
import com.wuzhanglao.niubi.utils.ToastUtil;
import com.wuzhanglao.niubi.widget.RoundDrawable;
import com.wuzhanglao.niubi.widget.TimerView;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ming.wu@shanbay.com on 2017/6/25.
 */

public class TimerDemo implements DemoActivity.DemoView {

    private Activity mActivity;
    private View mRoot;

    public TimerDemo(@NonNull Activity activity) {
        mActivity = activity;

        mRoot = activity.getLayoutInflater().inflate(R.layout.layout_demo_timer, null);
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.avatar);
        ViewCompat.setBackground(mRoot, new RoundDrawable(bitmap));

        final TimerView timerView = (TimerView) mRoot.findViewById(R.id.timer_view);
        timerView.setTimerSize(18, false);
        timerView.setCountDownTime(0, 0, 10, 10);
        timerView.start();

        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerView.isRunning()) {
                    timerView.stop();
                } else {
                    timerView.start();
                }
            }
        });
        timerView.setOnFinishedListener(new TimerView.OnFinishedListener() {
            @Override
            public void onFinished() {
                ToastUtil.showInfo("finished");
            }
        });

        mRoot.findViewById(R.id.start_add_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("123","执行到这儿了");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testAddContact2();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).run();
            }
        });

        mRoot.findViewById(R.id.start_temp_qq_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qqNum="470782682";
                String url="mqqwpa://im/chat?chat_type=wpa&uin="+qqNum;//qqNum代表会话的客服QQ号
                mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    /**
     * 批量向通讯录中添加联系人信息
     *
     * @throws RemoteException
     * @throws OperationApplicationException
     */
    private void startAddContact() throws RemoteException, OperationApplicationException {
        Uri dataUri = Uri.parse("content://com.android.contacts/data");         // 联系人信息表uri
        Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");  // 联系人id表uri
        ContentResolver resolver = mActivity.getContentResolver();    // 获取ContentResolver
        ArrayList<ContentProviderOperation> operationList = new ArrayList<ContentProviderOperation>(); // 定义处理list
        // 定义批量的操作
        ContentProviderOperation operation1 = ContentProviderOperation.newInsert(rawUri)
                .withValue("_id", null)
                .build();
        ContentProviderOperation operation2 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)    // 将第一次操作的返回值赋给raw_contact_id
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data1", "lisisi")
                .build();
        ContentProviderOperation operation3 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data1", "18977658899")
                .build();
        ContentProviderOperation operation4 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/email_v2")
                .withValue("data1", "lisissi@qq.com")
                .build();
        // 将操作添加到list
        operationList.add(operation1);
        operationList.add(operation2);
        operationList.add(operation3);
        operationList.add(operation4);
        // 批量执行
        resolver.applyBatch("com.android.contacts", operationList);
    }

    //批量添加
    public void testAddContact2() throws Exception {
        long index = 17600000000L;

        for (int i = 0; i < 3; i++) {
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = mActivity.getContentResolver();
            ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
                    .withValue("account_name", null)
                    .build();
            operations.add(op1);

            uri = Uri.parse("content://com.android.contacts/data");
            //添加姓名
            ContentProviderOperation op2 = ContentProviderOperation.newInsert(uri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/name")
                    .withValue("data2", String.valueOf(index + i))
                    .build();
            operations.add(op2);
            //添加电话号码
            ContentProviderOperation op3 = ContentProviderOperation.newInsert(uri)
                    .withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data1", String.valueOf(index + i))
                    .withValue("data2", "2")
                    .build();
            operations.add(op3);

            resolver.applyBatch("com.android.contacts", operations);

            Log.e("当前手机号:", String.valueOf(index + i));
        }
    }

    @Override
    public View getView() {
        return mRoot;
    }

    @Override
    public String getTitle() {
        return getClass().getSimpleName();
    }
}
