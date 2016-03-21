package novel_detective.tedu.cn.novel_detective.dataInfo;

/**
 * Created by Administrator on 2016/3/17.
 */
public class DemoInfo {
    public DemoInfo(String info, int resourceId) {
        this.info = info;
        this.resourceId = resourceId;
    }

    public DemoInfo(){

    }


    private String info;
    private int   resourceId;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }



    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
