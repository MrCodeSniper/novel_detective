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

import java.io.IOException;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.R;
import novel_detective.tedu.cn.novel_detective.dataInfo.book;

/**
 * Created by Administrator on 2016/3/14.
 */
public class ListViewAdapter extends BaseAdapter{
private  Context context;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/
  private List<book> bookList;
    public ListViewAdapter(Context context,List<book> bookList) {
        this.context=context;
        this.bookList=bookList;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
     return  bookList.size();
    }

    @Override
    public book getItem(int position) {
       return  bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //使用ViewHolder进行优化
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        /**
         * 如果重复利用视图为空就从模板上创建
         */
       if(convertView==null) {
           holder = new ViewHolder();
           convertView = mInflater.inflate(R.layout.item_book,parent,false);
            /*得到各个控件的对象*/
           holder.title = (TextView) convertView.findViewById(R.id.tv_book_title);
           holder.part = (TextView) convertView.findViewById(R.id.tv_author);
           holder.imageView = (ImageView) convertView.findViewById(R.id.ima_book_pic);
           convertView.setTag(holder);//绑定ViewHolder对象
       }
        else {
           holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
       }
       book book_obj=getItem(position);
        if(book_obj.getImage().equals("default")){
            holder.imageView.setImageResource(R.drawable.ic_txt);
        }
        else {
            String iv_path = book_obj.getImage();
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(iv_path));
        }
        holder.title.setText(book_obj.getBook_name());
        holder.part.setText(book_obj.getBook_description());
        return convertView;
    }

    /**
     * 先创建ViewHolder类来放模板的控件
     */
public final class ViewHolder{
   public ImageView imageView;
        public TextView title;
        public TextView part;
}
}
