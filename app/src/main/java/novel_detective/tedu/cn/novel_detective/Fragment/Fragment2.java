package novel_detective.tedu.cn.novel_detective.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.adapter.Discover_adapter;
import novel_detective.tedu.cn.novel_detective.dataInfo.DemoInfo;

/**
 * Created by Administrator on 2016/3/17.
 */
public class Fragment2 extends Fragment {
    private View mview;
    private Discover_adapter discover_adapter;
    private ListView lv_discover;
    private List<DemoInfo> demoInfoList;
    private ImageView dividerImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mview.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
            Log.v("huahua", "fragment2-->移除已存在的View");
        }

        return mview;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        LayoutInflater inflater = getActivity().getLayoutInflater();
        mview = inflater.inflate(R.layout.fragment2, (ViewGroup)getActivity().findViewById(R.id.viewpager_listview), false);
        lv_discover= (ListView) mview.findViewById(R.id.lv_discover);
        demoInfoList=new ArrayList<DemoInfo>();
        DemoInfo demoInfo1=new DemoInfo();
        demoInfo1.setInfo("书库");
        demoInfo1.setResourceId(R.drawable.ic_book);
        demoInfoList.add(demoInfo1);
        DemoInfo demoInfo2=new DemoInfo();
        demoInfo2.setInfo("搜书");
        demoInfo2.setResourceId(R.drawable.ic_search);
        demoInfoList.add(demoInfo2);
        DemoInfo demoInfo3=new DemoInfo();
        demoInfo3.setInfo("排行");
        demoInfo3.setResourceId(R.drawable.ic_rank);
        demoInfoList.add(demoInfo3);
        DemoInfo demoInfo4=new DemoInfo();
        demoInfo4.setInfo("");
        demoInfoList.add(demoInfo4);
        discover_adapter=new Discover_adapter(getActivity(),demoInfoList);
        lv_discover.setAdapter(discover_adapter);
    }
}
