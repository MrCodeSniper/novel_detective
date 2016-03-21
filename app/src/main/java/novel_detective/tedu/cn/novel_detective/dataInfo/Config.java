package novel_detective.tedu.cn.novel_detective.dataInfo;

import android.os.Environment;

/**
 * Created by Administrator on 2016/3/15.
 */
public class Config {
    public static final String BOOKDIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" + "MyBook";
    public static final String BOOKPIC = "images";

}
