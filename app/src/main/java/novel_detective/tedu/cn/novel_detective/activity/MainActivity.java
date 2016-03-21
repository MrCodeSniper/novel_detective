package novel_detective.tedu.cn.novel_detective.activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.Fragment.Fragment1;
import novel_detective.tedu.cn.novel_detective.Fragment.Fragment2;
import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.dataInfo.book;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MyViewPagerAdapter mypageAdapter;
    private TabLayout mtabLayout;
    private ViewPager mviewpager;
    //页面列表
    private ArrayList<Fragment> fragmentList;
    private Fragment mfragment1;
    private Fragment mfragment2;
    private String name;

    //页卡标题集合
    private List<String> mtitlelist=new ArrayList<>();
    private String bookPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mviewpager= (ViewPager) findViewById(R.id.viewpager_listview);
        mtabLayout= (TabLayout) findViewById(R.id.tabs);

        mfragment1=new Fragment1();



        mfragment2=new Fragment2();
        fragmentList=new ArrayList<Fragment>();

        fragmentList.add(mfragment1);
        fragmentList.add(mfragment2);
        System.out.println(fragmentList.size());

        mtitlelist.add("追书");
        mtitlelist.add("发现");

        mtabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        //添加tab选项卡
        mtabLayout.addTab(mtabLayout.newTab().setText(mtitlelist.get(0)));
        mtabLayout.addTab(mtabLayout.newTab().setText(mtitlelist.get(1)));

        mypageAdapter=new MyViewPagerAdapter(getSupportFragmentManager());

          mviewpager.setAdapter(mypageAdapter);
        mtabLayout.setupWithViewPager(mviewpager);//将TabLayout和ViewPager关联起来。
         mtabLayout.setTabsFromPagerAdapter(mypageAdapter);

        process();











    }



    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }



        @Override
        public android.support.v4.app.Fragment getItem(int arg0) {
            return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return mtitlelist.get(position);
        }


    }




    private void process() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
       public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     switch (item.getItemId()){
         case R.id.menu_local_books:
             System.out.println("你点击了本地");
             Intent intent=new Intent(this,LoadChooseActivity.class);
             startActivity(intent);
             MainActivity.this.finish();
             break;
         default:
             break;
     }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_message) {

        } else if (id == R.id.nav_bookshelf) {

        } else if (id == R.id.nav_neighbor) {

        } else if (id == R.id.nav_contact) {
            AlertDialog.Builder dialog_mail=new AlertDialog.Builder(this);
            dialog_mail.setTitle("联系我们");
            dialog_mail.setCancelable(true);
            dialog_mail.setMessage("若您有宝贵的意见请致信568261070@qq.com");
            dialog_mail.show();
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"请多多支持我的app");
            intent.putExtra(Intent.EXTRA_TEXT,"EasyBook");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, getTitle()));

        } else if (id == R.id.nav_homepage) {
            Uri uri = Uri.parse("http://baidu.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(id==R.id.nav_good){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    }

