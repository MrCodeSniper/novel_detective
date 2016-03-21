package novel_detective.tedu.cn.novel_detective.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;

import novel_detective.tedu.cn.novel_detective.R;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_activity);
        //取消顶栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new AsyncTask<Void, Void, Integer>() {
            //在子线程进行的操作
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
          //在主线程更新UI，在doInBackground执行完执行
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //UI动画
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        }.execute();
    }
}