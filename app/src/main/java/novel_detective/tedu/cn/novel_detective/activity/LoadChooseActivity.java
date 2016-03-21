package novel_detective.tedu.cn.novel_detective.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.adapter.Search_adapter;
import novel_detective.tedu.cn.novel_detective.dataInfo.DemoInfo;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LoadChooseActivity extends ActionBarActivity {
     private ListView lv_search;
    private List<DemoInfo> demoInfoList=new ArrayList<DemoInfo>();
   private Search_adapter search_adapter;
    private int press_count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_from_sdcard);
        ActionBar actionBar=this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lv_search= (ListView) findViewById(R.id.lv_search);
        DemoInfo demoInfo1=new DemoInfo("搜索本地",R.drawable.ic_searchsd);
        demoInfoList.add(demoInfo1);
        DemoInfo demoInfo2=new DemoInfo("上传云盘",R.drawable.ic_cloud);
        demoInfoList.add(demoInfo2);
        DemoInfo demoInfo3=new DemoInfo("自动扫描",R.drawable.ic_scan);
        demoInfoList.add(demoInfo3);
        search_adapter=new Search_adapter(this,demoInfoList);
        lv_search.setAdapter(search_adapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(LoadChooseActivity.this,LoadFromSDcardActvity.class);
                        startActivity(intent);
                        LoadChooseActivity.this.finish();
                        break;
                    default:
                        break;
                }

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("选了");
          Intent intent=new Intent(LoadChooseActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return true;

    }

    @Override
    public void onBackPressed() {
        press_count++;
        Toast.makeText(LoadChooseActivity.this,"再次返回退出应用",Toast.LENGTH_LONG).show();
      if(press_count>1){
          super.onBackPressed();
      }
    }
}
