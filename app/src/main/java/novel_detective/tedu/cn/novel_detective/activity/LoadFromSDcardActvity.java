package novel_detective.tedu.cn.novel_detective.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.Fragment.Fragment1;
import novel_detective.tedu.cn.novel_detective.Helper.BookDBHelper;
import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.adapter.ListViewAdapter;
import novel_detective.tedu.cn.novel_detective.adapter.ListViewAdapter2;
import novel_detective.tedu.cn.novel_detective.dataInfo.DemoInfo;
import novel_detective.tedu.cn.novel_detective.dataInfo.book;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LoadFromSDcardActvity extends ActionBarActivity implements View.OnClickListener{
    private TextView tv_number;
    private Button bt_import;
    private ListView lv_txt;
    private List<book> bookList;
    private  int txt_number=0 ;
    private ListViewAdapter2 listViewAdapte2;
    private ProgressDialog progressDialog;
    private int press_count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_book);
        android.support.v7.app.ActionBar actionBar=this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tv_number= (TextView) findViewById(R.id.tv_number);
        bt_import= (Button) findViewById(R.id.bt_import);
        lv_txt= (ListView) findViewById(R.id.lv_book_search);
        bt_import.setOnClickListener(this);
       lv_txt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               book book=bookList.get(position);
               book.setImage("default");
               book.setBook_description("");
               Intent intent=new Intent(LoadFromSDcardActvity.this,MainActivity.class);
               Bundle bundle=new Bundle();
               bundle.putSerializable("book",book);
               intent.putExtras(bundle);
               startActivity(intent);
               LoadFromSDcardActvity.this.finish();
           }
       });



    }
    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        txt_number++;
                        String path=file.getPath().toString();
                      book book_obj=new book();
                        String s = fileName.substring(0,
                                fileName.lastIndexOf(".")).toString();
                      book_obj.setBook_name(s);
                     book_obj.setPath(path);
                        book_obj.setBook_description("暂无");
                        book_obj.setImage(null);
                        bookList.add(book_obj);
                }
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        txt_number=0;
        new AsyncTask<Void, Void, List<book>>() {
            @Override
            protected List<book> doInBackground(Void... params) {
                bookList=new ArrayList<book>();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
                    // File path = new File("/mnt/sdcard/");
                    File[] files = path.listFiles();// 读取
                    getFileName(files);
                }

                return bookList;
            }

            @Override
            protected void onPostExecute(List<book> bookList) {
                super.onPostExecute(bookList);
                listViewAdapte2=new ListViewAdapter2(getApplicationContext(),bookList);
                lv_txt.setAdapter(listViewAdapte2);
                tv_number.setText("扫描到" + txt_number + "个txt文件");
                progressDialog.dismiss();

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(LoadFromSDcardActvity.this);
                progressDialog.setTitle("读取本地书籍");
                progressDialog.setCancelable(false);
                progressDialog.setMessage("加载中，请稍后......");
                progressDialog.show();
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        press_count++;
        Toast.makeText(LoadFromSDcardActvity.this, "再次返回退出应用", Toast.LENGTH_SHORT).show();
        if(press_count>1){
            super.onBackPressed();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("选了");
            Intent intent=new Intent(LoadFromSDcardActvity.this,LoadChooseActivity.class);
            startActivity(intent);
            finish();
            return true;
    }
    }

