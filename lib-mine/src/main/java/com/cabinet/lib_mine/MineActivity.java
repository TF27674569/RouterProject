package com.cabinet.lib_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cabinet.lib_base.BaseActivity;

import org.router.api.core.Router;
import org.router.api.result.ActionCallback;
import org.router.api.result.RouterResult;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/4/23
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class MineActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
    }

    public void homeClick(View view){
        Router.get()
                .action("libhome/homeActivity")
                .context(this)
                .invokeAction(new ActionCallback() {
                    @Override
                    public void onInterrupt() {
                        Toast.makeText(MineActivity.this, "拦截了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResult(RouterResult result) {
                        Log.e("TAG", "onResult: "+result.toString());
                    }
                });
    }
}

