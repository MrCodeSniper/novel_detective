package novel_detective.tedu.cn.novel_detective.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import novel_detective.tedu.cn.novel_detective.Factory.PageFactory;
import novel_detective.tedu.cn.novel_detective.view.PageWidget;

/**
 * 工厂类解析文本返回的是bitmap图需要创建自定义view视图匹配活动
 */
public class ReadActivity extends Activity{
    private int screenWidth;
    private int screenHeight;
    private String bookPath;
    private PageWidget mPageWidget;
    Bitmap mCurPageBitmap, mNextPageBitmap;
    Canvas mCurPageCanvas, mNextPageCanvas;
    PageFactory pageFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bookPath = getIntent().getStringExtra("path");
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(outSize);
        screenWidth = outSize.x;
        screenHeight = outSize.y;
        mPageWidget = new PageWidget(this, screenWidth, screenHeight);
        setContentView(mPageWidget);
        mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
                Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
                Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        pageFactory = new PageFactory(screenWidth, screenHeight);// 设置分辨率
        try {
            pageFactory.setBook(bookPath);// 打开文件
            pageFactory.draw(mCurPageCanvas);// 将文字绘于屏幕

        } catch (IOException e) {
            e.printStackTrace();
        }
        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);
        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {

                boolean ret = false;
                if (v == mPageWidget) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        // 停止动画。与forceFinished(boolean)相反，Scroller滚动到最终x与y位置时中止动画。
                        mPageWidget.abortAnimation();
                        // 计算拖拽点对应的拖拽角
                        mPageWidget.calcCornerXY(e.getX(), e.getY());
                        // 将文字绘于当前页
                        pageFactory.draw(mCurPageCanvas);
                        if (mPageWidget.DragToRight()) {
                            // 是否从左边翻向右边
                            try {
                                // true，显示上一页
                                pageFactory.prePage();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            if (pageFactory.isfirstPage())
                                return false;
                            pageFactory.draw(mNextPageCanvas);
                        } else {
                            try {
                                // false，显示下一页
                                pageFactory.nextPage();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            if (pageFactory.islastPage())
                                return false;
                            pageFactory.draw(mNextPageCanvas);
                        }
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    }

                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }
                return false;
            }

        });









    }
}
