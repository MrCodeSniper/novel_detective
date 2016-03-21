package novel_detective.tedu.cn.novel_detective.Helper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.Context;
import android.os.Environment;
public class FileHelper {
    private Context context;
    /** SD卡是否存在**/
    private boolean hasSD = false;
    /** SD卡的路径**/
    private String SDPATH;
    /** 当前程序包的路径**/
    private String FILESPATH;
    public FileHelper(Context context) {
        this.context = context;
        hasSD = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        SDPATH = Environment.getExternalStorageDirectory().getPath();
        FILESPATH = this.context.getFilesDir().getPath();
    }

    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public String readSDFile(String fileName) {
        StringBuffer sb = new StringBuffer();
        File file = new File(SDPATH + "//" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public String getFILESPATH() {
        return FILESPATH;
    }
    public String getSDPATH() {
        return SDPATH;
    }
    public boolean hasSD() {
        return hasSD;
    }
}