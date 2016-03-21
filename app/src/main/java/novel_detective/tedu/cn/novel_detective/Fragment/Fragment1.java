package novel_detective.tedu.cn.novel_detective.Fragment;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.Helper.BookDBHelper;
import novel_detective.tedu.cn.novel_detective.Helper.DBManager;
import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.activity.ReadActivity;
import novel_detective.tedu.cn.novel_detective.adapter.ListViewAdapter;
import novel_detective.tedu.cn.novel_detective.dataInfo.Config;
import novel_detective.tedu.cn.novel_detective.dataInfo.book;

/**
 * Created by Administrator on 2016/3/17.
 */
public class Fragment1 extends Fragment implements AdapterView.OnItemLongClickListener{


    private SharedPreferences sp;
    private BookDBHelper bookDBHelper;

    private ListViewAdapter listViewAdapter;
    private View mview;
  private  DBManager dbManager;
    private List<book> book_list;
    private ListView lv_book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mview = inflater.inflate(R.layout.fragment1, (ViewGroup)getActivity().findViewById(R.id.viewpager_listview), false);
        lv_book= (ListView) mview.findViewById(R.id.lv_book);
        book_list=new ArrayList<book>();
        dbManager=new DBManager(getActivity());
        sp=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);//得到名为配置的偏好文件
        bookDBHelper=new BookDBHelper(getActivity());
        init();//设置listView并放入例书






    }


    private void init() {
        if(sp.getBoolean("isFirst",true)){
            loadExampleBook();
            book_list=dbManager.query();
        }else{
            Bundle bundle=getActivity().getIntent().getExtras();
            if(null!=bundle) {
                book book = (book) bundle.getSerializable("book");
                dbManager.add(book);
            }
            book_list=dbManager.query();
        }
        listViewAdapter=new ListViewAdapter(getActivity(),book_list);
        lv_book.setAdapter(listViewAdapter);


        lv_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                book book = book_list.get(position);//
                String path = book.getPath();
                Intent intent = new Intent(getActivity(), ReadActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
        lv_book.setOnItemLongClickListener(this);



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("huahua", "fragment1-->onCreateView()");

        ViewGroup p = (ViewGroup) mview.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
            Log.v("huahua", "fragment1-->移除已存在的View");
        }
        return mview;
    }



    /**
     * 将例书放入sd卡中显示
     */
    private void loadExampleBook() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {//判断sd卡是否挂载
            File bookdir=new File(Config.BOOKDIR); //打开文件夹路径为bookdir
            File bookPicDir = new File(Config.BOOKDIR + "/" + Config.BOOKPIC);//存放照片的路径
            if(!bookdir.exists()){
                bookdir.mkdir();
            }
            if(!bookPicDir.exists()){
                bookPicDir.mkdir();
            }
            String bookTitle = "完美世界";
            String bookAuthor = "辰东";
            String bookPath = Config.BOOKDIR + "/" + bookTitle + ".txt";
            File file = new File(bookPath);
            FileOutputStream fos = null;
            if (!file.exists()) {
                InputStream is =getResources().openRawResource(R.raw.book);
                try {
                    fos = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            String bookPic = Config.BOOKDIR + "/" + "images" + "/" + bookTitle
                    + ".jpg";
            File pic = new File(bookPic);
            if (!pic.exists()) {
                InputStream is = getResources().openRawResource(R.raw.pic);
                try {
                    fos = new FileOutputStream(pic);
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            book book_obj2=new book();
            book_obj2.setBook_name(bookTitle);
            book_obj2.setBook_description(bookAuthor);
            book_obj2.setImage(bookPic);
            book_obj2.setPath(bookPath);
            book_list.add(book_obj2);
            dbManager.add(book_obj2);
            sp.edit().putBoolean("isFirst",false).commit();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
         final book book_1=book_list.get(position);
        new AlertDialog.Builder(getActivity())
                .setTitle("警告")
                .setMessage("你确定要删除这个文本吗")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        dbManager.delete(book_1);
                        //刷新界面
                        dbManager=new DBManager(getActivity());
                        book_list=new ArrayList<book>();
                        listViewAdapter=new ListViewAdapter(getActivity(),book_list);
                        lv_book.setAdapter(listViewAdapter);
                        book_list.clear();
                        List<book> books = dbManager.query();
                        for (book book : books) {
                            book_list.add(book);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
       listViewAdapter.notifyDataSetChanged();
        return true;
    }
}
