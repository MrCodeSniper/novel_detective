package novel_detective.tedu.cn.novel_detective.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.dataInfo.DemoInfo;
import novel_detective.tedu.cn.novel_detective.dataInfo.book;

/**
 * Created by Administrator on 2016/3/17.
 */
public class Discover_adapter extends BaseAdapter{
    private Context context;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/
    private List<DemoInfo> discover_list;
    public Discover_adapter(Context context, List<DemoInfo> list){
        this.context=context;
        this.discover_list=list;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return discover_list.size();
    }

    @Override
    public DemoInfo getItem(int position) {
        return discover_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        /**
         * 如果重复利用视图为空就从模板上创建
         */
        if(convertView==null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_discover,parent,false);
            /*得到各个控件的对象*/
            holder.textView2 = (TextView) convertView.findViewById(R.id.tv_details);
            holder.imageView2 = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else {
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        DemoInfo demoInfo=getItem(position);
            holder.imageView2.setImageResource(demoInfo.getResourceId());
        holder.textView2.setText(demoInfo.getInfo());
        return convertView;
    }
    /**
     * 先创建ViewHolder类来放模板的控件
     */
    public final class ViewHolder{
        public ImageView imageView2;
        public TextView textView2;
    }

}
