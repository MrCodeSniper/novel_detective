package novel_detective.tedu.cn.novel_detective.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import novel_detective.tedu.cn.novel_detective.dataInfo.book;

/**
 * Created by Administrator on 2016/3/20.
 */
public class DBManager {
    private List<book> book_list;
    private BookDBHelper myDatabaseHelper;
    private SQLiteDatabase database;
    public DBManager(Context context) {
        myDatabaseHelper = new BookDBHelper(context);
        database = myDatabaseHelper.getWritableDatabase();
        book_list=new ArrayList<book>();
    }

    public void delete(book book) {
        database.delete("bookinfo", "path = ?", new String[]{String.valueOf(book.getPath())});
    }

    public void add(book book) {
        ContentValues values = new ContentValues();
        values.put("title", book.getBook_name());
        values.put("author", book.getBook_description());
        values.put("path", book.getPath());
        values.put("image", book.getImage());
        database.insert("bookinfo", null, values);
    }


    public List<book> query() {
        Cursor cursor = database.query("bookinfo", null, null, null, null, null,
                "id asc");
        while (cursor.moveToNext()){
            book book_obj3=new book();
            book_obj3.setBook_name(cursor.getString(cursor.getColumnIndex("title")));
            book_obj3.setBook_description(cursor.getString(cursor.getColumnIndex("author")));
            book_obj3.setPath(cursor.getString(cursor.getColumnIndex("path")));
            book_obj3.setImage(cursor.getString(cursor.getColumnIndex("image")));
            book_list.add(book_obj3);
        }
        cursor.close();
        return  book_list;
}

    private Cursor queryTheCursor() {
        Cursor cursor = database.rawQuery("SELECT * FROM bookinfo",null);
        return cursor;
    }
    public void closeDB() {
        database.close();
    }
}
