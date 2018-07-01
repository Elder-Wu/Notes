package com.wuzhanglao.niubi.utils;

import android.util.Log;

/**
 * Created by wuming on 2018/3/22.
 */

public class ITest {

    private void outerMethod() {
        Log.d("TAG", "outer");
    }

    private class InnerClass {
        private void innerMethod() {
            Log.d("TAG", "inner");
        }
    }
}
