package com.wuzhanglao.niubi.fragment;

import android.app.ActivityManager;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseFragment;
import com.wuzhanglao.niubi.utils.AppUtils;
import com.wuzhanglao.niubi.widget.RippleView;
import com.wuzhanglao.niubi.widget.RoundDrawable;
import com.wuzhanglao.niubi.widget.TimerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ming.wu@shanbay.com on 2017/4/27.
 */

public class TimerViewFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_demo_timer, container, false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        ViewCompat.setBackground(rootView, new RoundDrawable(bitmap));

        final TimerView timerView = (TimerView) rootView.findViewById(R.id.timer_view);
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
                AppUtils.showToast("finish");
            }
        });

        RippleView rippleView = (RippleView) rootView.findViewById(R.id.ripple_view);
        rippleView.startAnimator();

        rootView.findViewById(R.id.start_add_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("123", "执行到这儿了");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testAddContact2("Thread---1");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).run();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            testAddContact4("Thread---4");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).run();
            }
        });

        rootView.findViewById(R.id.start_temp_qq_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> qqList = new ArrayList<String>();
                        qqList.add("470782682");
                        qqList.add("1105422834");
                        String url = "mqqwpa://im/chat?chat_type=wpa&uin=470782682";
                         getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //获取ActivityManager
                        ActivityManager mAm = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
                        //获得当前运行的task
                        List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
                        for (ActivityManager.RunningTaskInfo rti : taskList) {
                            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
                            if (rti.topActivity.getPackageName().equals(getActivity().getPackageName())) {
                                mAm.moveTaskToFront(rti.id, 0);
                            }
                        }

                        String url2 = "mqqwpa://im/chat?chat_type=wpa&uin=1105422834";
                        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url2)));
                        /*for(final String qq:qqList){
                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qq)));
//                            Observable.empty()
//                                    .delay(5,TimeUnit.SECONDS)
//                                    .subscribeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new Action1<Object>() {
//                                        @Override
//                                        public void call(Object o) {
//                                            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
//                                            Log.d("tagtag", url);
//                                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                                        }
//                                    });
                        }*/
                    }
                }).start();
            }
        });

        return rootView;
    }

    private long[] weifang156 = {1560536, 1560636, 1560646, 1561526, 1561536, 1561556, 1561566, 1561576, 1561586, 1561596, 1565341, 1565342, 1565343, 1565344, 1565345, 1565346, 1565347, 1565360, 1565361, 1565362, 1565363, 1565364, 1565365, 1565366, 1565367, 1565368, 1565369, 1566606, 1566623, 1566646, 1566656, 1566674, 1566688, 1569236, 1569825, 1569826};
    private long[] wuHan130 = {1300071, 1300610, 1300611, 1300612, 1300613, 1300614, 1300615, 1300616, 1300617, 1300618, 1300619, 1300630, 1300631, 1300632, 1300633, 1300634, 1300635, 1300636, 1300637, 1300638, 1300639, 1300710, 1300711, 1300712, 1300713, 1300714, 1300715, 1300716, 1300717, 1300718, 1300719, 1301071, 1301077, 1301640, 1301641, 1301642, 1301643, 1301644, 1301645, 1301646, 1301647, 1301648, 1301649, 1301800, 1301801, 1301802, 1301803, 1301804, 1301805, 1301806, 1301807, 1301808, 1301809, 1302610, 1302611, 1302612, 1302613, 1302614, 1302615, 1302616, 1302617, 1302618, 1302619, 1302630, 1302631, 1302632, 1302633, 1302634, 1302635, 1302636, 1302637, 1302638, 1302639, 1302710, 1302711, 1302712, 1302713, 1302714, 1302715, 1302716, 1302717, 1302718, 1302719, 1303510, 1303511, 1303512, 1303513, 1303514, 1303610, 1303611, 1303612, 1303613, 1303614, 1303615, 1303616, 1303710, 1303711, 1303712, 1303713, 1303714, 1303715, 1303716, 1303717, 1303718, 1303719, 1307120, 1307121, 1307122, 1307123, 1307124, 1307125, 1307126, 1307127, 1307128, 1307129, 1307270, 1307271, 1307272, 1307273, 1307274, 1307275, 1307276, 1307277, 1307278, 1307279, 1308060, 1308061, 1308062, 1308063, 1308064, 1308065, 1308066, 1308067, 1308068, 1308069, 1309880, 1309881, 1309882, 1309883, 1309884, 1309885, 1309886, 1309887, 1309888, 1309889};
    private long[] baoTou186 = {1860471, 1860488, 1864710, 1864711, 1864712, 1864713, 1864714, 1864715, 1864720, 1864721, 1864722, 1864723, 1864724, 1864728, 1868600, 1868601, 1868602, 1868603, 1868604, 1868605, 1868606, 1868607, 1868608, 1868609, 1868610, 1868611, 1868612, 1868613, 1868614, 1868615, 1868616, 1868617, 1868618, 1868619};
    private long[] baotou156 = {1560472, 1564720, 1564721, 1564722, 1564723, 1564724, 1564725, 1564726, 1564727, 1564728, 1564729, 1564730, 1564731, 1564732, 1564733, 1564734, 1564735, 1564736, 1564737, 1564738, 1564739, 1564740, 1564741, 1564742, 1564743, 1564744, 1564745, 1564746, 1564747, 1564748, 1564749, 1564750, 1564751, 1564752, 1564753, 1564754, 1564755, 1564756, 1564757, 1564758, 1564759, 1564760, 1564761, 1564762, 1564763, 1564764, 1564765, 1564766, 1564767, 1564768, 1564769, 1569090, 1569098, 1569472,
            1560471, 1564710, 1564711, 1564712, 1564713, 1564714, 1564715, 1564716, 1564717, 1564718, 1564719, 1564810, 1564811, 1564812, 1564813, 1564814, 1564815, 1564816, 1564817, 1564818, 1564819, 1566100, 1566101, 1566102, 1566103, 1566104, 1566105, 1566106, 1566107, 1566108, 1566109, 1566110, 1566111, 1566112, 1566113, 1566114, 1566115, 1566116, 1566117, 1566118, 1566119, 1566120, 1566121, 1566122, 1566123, 1566124, 1566125, 1566126, 1566127, 1566128, 1566129, 1569091, 1569099, 1569471};

    //批量添加
    public void testAddContact2(String tag) throws Exception {

        long index;
        long count;
        long weishu = 98;
        long[] numList = weifang156;
        int weishuLength = String.valueOf(weishu).length();
        int qiuyu = Double.valueOf(Math.pow(10, weishuLength)).intValue();

        Uri uriRawContacts = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");

        ContentResolver resolver = getActivity().getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();


        for (int k = 0; k < numList.length; k++) {
            index = numList[k] * 10000L;
            count = 10000L;

            for (int i = 0; i < count; i++) {
//                Log.d("当前手机号", String.valueOf(index));
                if (index % qiuyu != weishu) {
                    index++;
                    continue;
                }

                operations.clear();
                ContentProviderOperation op1 = ContentProviderOperation.newInsert(uriRawContacts)
                        .withValue("account_name", null)
                        .build();
                operations.add(op1);

                //添加姓名
                ContentProviderOperation op2 = ContentProviderOperation.newInsert(uriData)
                        .withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/name")
                        .withValue("data2", String.valueOf(index))
                        .build();
                operations.add(op2);

                //添加电话号码
                ContentProviderOperation op3 = ContentProviderOperation.newInsert(uriData)
                        .withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                        .withValue("data1", String.valueOf(index))
                        .withValue("data2", "2")
                        .build();
                operations.add(op3);

                resolver.applyBatch("com.android.contacts", operations);
                Log.e(tag + "当前手机号", String.valueOf(index));
                index++;
            }
        }
    }

    //批量添加
    public void testAddContact4(String tag) throws Exception {

        long index;
        long count;
        long weishu = 6881;
        long[] numList = baotou156;
        int weishuLength = String.valueOf(weishu).length();
        int qiuyu = Double.valueOf(Math.pow(10, weishuLength)).intValue();

        Uri uriRawContacts = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri uriData = Uri.parse("content://com.android.contacts/data");

        ContentResolver resolver = getActivity().getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();


        for (int k = 0; k < numList.length; k++) {
            index = numList[k] * 10000L;
            count = 10000L;

            for (int i = 0; i < count; i++) {
//                Log.d("当前手机号", String.valueOf(index));
                if (index % qiuyu != weishu) {
                    index++;
                    continue;
                }

                operations.clear();
                ContentProviderOperation op1 = ContentProviderOperation.newInsert(uriRawContacts)
                        .withValue("account_name", null)
                        .build();
                operations.add(op1);

                //添加姓名
                ContentProviderOperation op2 = ContentProviderOperation.newInsert(uriData)
                        .withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/name")
                        .withValue("data2", String.valueOf(index))
                        .build();
                operations.add(op2);

                //添加电话号码
                ContentProviderOperation op3 = ContentProviderOperation.newInsert(uriData)
                        .withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                        .withValue("data1", String.valueOf(index))
                        .withValue("data2", "2")
                        .build();
                operations.add(op3);

                resolver.applyBatch("com.android.contacts", operations);
                Log.e(tag + "当前手机号", String.valueOf(index));
                index++;
            }
        }
    }

    //批量添加 掐头去尾
    public void testAddContact3() throws Exception {

        long index;
        long count;

        for (int k = 0; k < baoTou186.length; k++) {
            index = baoTou186[k] * 10000L;
            count = 10000;

            Uri uriRawContacts = Uri.parse("content://com.android.contacts/raw_contacts");
            Uri uriData = Uri.parse("content://com.android.contacts/data");
            ContentResolver resolver = getActivity().getContentResolver();
            ArrayList<ContentProviderOperation> operations = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                operations.clear();
                ContentProviderOperation op1 = ContentProviderOperation.newInsert(uriRawContacts)
                        .withValue("account_name", null)
                        .build();
                operations.add(op1);

                //添加姓名
                ContentProviderOperation op2 = ContentProviderOperation.newInsert(uriData)
                        .withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/name")
                        .withValue("data2", String.valueOf(index))
                        .build();
                operations.add(op2);
                //添加电话号码
                ContentProviderOperation op3 = ContentProviderOperation.newInsert(uriData)
                        .withValueBackReference("raw_contact_id", 0)
                        .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                        .withValue("data1", String.valueOf(index))
                        .withValue("data2", "2")
                        .build();
                operations.add(op3);
                Log.e("当前手机号:", String.valueOf(index));
                index++;
                resolver.applyBatch("com.android.contacts", operations);
            }
        }
    }
}
