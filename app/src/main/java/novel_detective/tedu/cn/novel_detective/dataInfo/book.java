package novel_detective.tedu.cn.novel_detective.dataInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/15.
 */
public class book implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String image;
    private String book_name;
    private  String book_description;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public book(){

    }
    public book(String book_name,String book_description){
     this.book_name=book_name;
        this.book_description=book_description;
    }

    public String getImage() {
        return image;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public void setImage(String image) {
        this.image = image;

    }

    @Override
    public String toString() {
        return "book{" +
                "image=" + image +
                ", book_name='" + book_name + '\'' +
                ", book_description='" + book_description + '\'' +
                '}';
    }
}
