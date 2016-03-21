package novel_detective.tedu.cn.novel_detective.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库辅助类来创建或打开数据库
 */
public class BookDBHelper extends SQLiteOpenHelper {
    public BookDBHelper(Context context) {
        super(context, "book.db", null, 1);//3个固定格式的参数无需传入(数据库名，工厂，版本名）
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists bookinfo("
                + "id integer primary key," + "title text not null,"
                + "author text not null," + "path text not null,"
                + "image text not null" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
