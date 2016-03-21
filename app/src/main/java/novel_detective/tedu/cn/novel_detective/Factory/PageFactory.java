package novel_detective.tedu.cn.novel_detective.Factory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * 将小说内容转换画出视图
 */
public class PageFactory {
    private File book;
    private MappedByteBuffer mBookBuf;
    private int mBookBufLen = 0;
    private int mCurBufBegin = 0;
    private int mCurBufEnd = 0;
    private String mCharset = "GBK";
    private Bitmap mBookBitmap = null;
    private int mWidth;
    private int mHeight;

    private Vector<String> mLines = new Vector<String>();

    private int mFontSize;
    private int mFontColor = Color.BLACK;
    private int mBackColor = Color.rgb(204, 235, 204); // 豆沙绿

    private int mLineCount = 25;// 默认设置为30行
    private int mLineBetweenLen = 15;
    private boolean isFirstPage, isLastPage;

    private Paint mPaint;
    private Paint percentPaint;

    public PageFactory(int w, int h) {
        mWidth = w;
        mHeight = h;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);// 设置绘制文字的对齐方向
        mFontSize = (mHeight - (mLineCount + 1) * mLineBetweenLen)
                / (mLineCount + 1);// 根据屏幕大小确定字体的大小
        mPaint.setTextSize(mFontSize);
        mPaint.setColor(mFontColor);
        percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        percentPaint.setTextSize(mFontSize / 3 * 2);
        percentPaint.setColor(mFontColor);
    }

    public void setBook(String bookPath) throws IOException {
        book = new File(bookPath);
        mBookBufLen = (int) book.length();
		/*
		 * 内存映射文件能让你创建和修改那些因为太大而无法放入内存的文件。有了内存映射文件，你就可以认为文件已经全部读进了内存，
		 * 然后把它当成一个非常大的数组来访问。这种解决办法能大大简化修改文件的代码。
		 */
        FileChannel fc = new RandomAccessFile(book, "r").getChannel();
        mBookBuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, mBookBufLen);
    }

    protected byte[] readPreParagraph(int nFromPos) {
        int nEnd = nFromPos;
        int i;
        byte b0, b1;
        if (mCharset.equals("UTF-16LE")) {
            i = nEnd - 2;
            while (i > 0) {
                b0 = mBookBuf.get(i);
                b1 = mBookBuf.get(i + 1);
                if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
                    i += 2;
                    break;
                }
                i--;
            }

        } else if (mCharset.equals("UTF-16BE")) {
            i = nEnd - 2;
            while (i > 0) {
                b0 = mBookBuf.get(i);
                b1 = mBookBuf.get(i + 1);
                if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
                    i += 2;
                    break;
                }
                i--;
            }
        } else {
            i = nEnd - 1;
            while (i > 0) {
                b0 = mBookBuf.get(i);
                if (b0 == 0x0a && i != nEnd - 1) {
                    i++;
                    break;
                }
                i--;
            }
        }
        if (i < 0)
            i = 0;
        int nParaSize = nEnd - i;
        int j;
        byte[] buf = new byte[nParaSize];
        for (j = 0; j < nParaSize; j++) {
            buf[j] = mBookBuf.get(i + j);
        }
        return buf;
    }

    protected byte[] readNextParagraph(int nFromPos) {
        int nStart = nFromPos;
        int i = nStart;
        byte b0, b1;
        // 根据编码格式判断换行
        if (mCharset.equals("UTF-16LE")) {
            while (i < mBookBufLen - 1) {
                b0 = mBookBuf.get(i++);
                b1 = mBookBuf.get(i++);
                if (b0 == 0x0a && b1 == 0x00) {
                    break;
                }
            }
        } else if (mCharset.equals("UTF-16BE")) {
            while (i < mBookBufLen - 1) {
                b0 = mBookBuf.get(i++);
                b1 = mBookBuf.get(i++);
                if (b0 == 0x00 && b1 == 0x0a) {
                    break;
                }
            }
        } else {
            while (i < mBookBufLen) {
                b0 = mBookBuf.get(i++);
                if (b0 == 0x0a) {
                    break;
                }
            }
        }
        // 共读取了多少字符
        int nParaSize = i - nStart;
        byte[] buf = new byte[nParaSize];
        for (i = 0; i < nParaSize; i++) {
            // 将已读取的字符放入数组
            buf[i] = mBookBuf.get(nFromPos + i);
        }
        return buf;
    }

    protected Vector<String> readNextPage() {
        String strParagraph = "";
        Vector<String> lines = new Vector<String>();
        while (lines.size() < mLineCount && mCurBufEnd < mBookBufLen) {
            byte[] paraBuf = readNextParagraph(mCurBufEnd); // 读取一个段落
            mCurBufEnd += paraBuf.length;// 结束位置后移paraBuf.length
            try {
                strParagraph = new String(paraBuf, mCharset);// 通过decode指定的编码格式将byte[]转换为字符串
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String strReturn = "";

            // 去除将字符串中的特殊字符
            if (strParagraph.indexOf("\r\n") != -1) {
                strReturn = "\r\n";
                strParagraph = strParagraph.replaceAll("\r\n", "");
            } else if (strParagraph.indexOf("\n") != -1) {
                strReturn = "\n";
                strParagraph = strParagraph.replaceAll("\n", "");
            }

            if (strParagraph.length() == 0) {
                lines.add(strParagraph);
            }
            while (strParagraph.length() > 0) {
                // 计算每行可以显示多少个字符
                // 获益匪浅
                int nSize = mPaint.breakText(strParagraph, true, mWidth, null);
                lines.add(strParagraph.substring(0, nSize));
                strParagraph = strParagraph.substring(nSize);// 截取从nSize开始的字符串
                if (lines.size() >= mLineCount) {
                    break;
                }
            }
            // 当前页没显示完
            if (strParagraph.length() != 0) {
                try {
                    mCurBufEnd -= (strParagraph + strReturn).getBytes(mCharset).length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return lines;
    }

    protected void goBackToPrePage() {
        if (mCurBufBegin < 0)
            mCurBufBegin = 0;
        Vector<String> lines = new Vector<String>();
        String strParagraph = "";
        while (lines.size() < mLineCount && mCurBufBegin > 0) {
            Vector<String> paraLines = new Vector<String>();
            byte[] paraBuf = readPreParagraph(mCurBufBegin);
            mCurBufBegin -= paraBuf.length;
            try {
                strParagraph = new String(paraBuf, mCharset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            strParagraph = strParagraph.replaceAll("\r\n", "");
            strParagraph = strParagraph.replaceAll("\n", "");

            if (strParagraph.length() == 0) {
                paraLines.add(strParagraph);
            }
            while (strParagraph.length() > 0) {
                int nSize = mPaint.breakText(strParagraph, true, mWidth, null);
                paraLines.add(strParagraph.substring(0, nSize));
                strParagraph = strParagraph.substring(nSize);
            }
            lines.addAll(0, paraLines);
        }
        while (lines.size() > mLineCount) {
            try {
                mCurBufBegin += lines.get(0).getBytes(mCharset).length;
                lines.remove(0);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        mCurBufEnd = mCurBufBegin;
    }

    public void prePage() throws IOException {
        if (mCurBufBegin <= 0) {
            // 第一页
            mCurBufBegin = 0;
            isFirstPage = true;
            return;
        } else
            isFirstPage = false;
        mLines.clear();// Removes all elements from this vector, leaving it
        // empty.
        goBackToPrePage();
        mLines = readNextPage();
    }

    public void nextPage() throws IOException {
        if (mCurBufEnd >= mBookBufLen) {
            isLastPage = true;
            return;
        } else
            isLastPage = false;
        mLines.clear();
        mCurBufBegin = mCurBufEnd;
        mLines = readNextPage();
    }

    public void draw(Canvas c) {
        if (mLines.size() == 0)
            mLines = readNextPage();
        if (mLines.size() > 0) {
            if (mBookBitmap == null)
                c.drawColor(mBackColor);
            else
                c.drawBitmap(mBookBitmap, 0, 0, null);
            int y = 30;
            for (String strLine : mLines) {
                y += (mFontSize + mLineBetweenLen);
                // 从（x,y）坐标将文字绘于手机屏幕
                c.drawText(strLine, 0, y, mPaint);
            }
        }
        // 计算百分比（不包括当前页）并格式化
        float fPercent = (float) (mCurBufBegin * 1.0 / mBookBufLen);
        DecimalFormat df = new DecimalFormat("#0.0");
        String strPercent = df.format(fPercent * 100) + "%";

        // 计算999.9%所占的像素宽度
        int nPercentWidth = (int) percentPaint.measureText("999.9%") + 1;
        c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 5,
                percentPaint);
    }

    public void setBgBitmap(Bitmap BG) {
        mBookBitmap = BG;
    }

    public boolean isfirstPage() {
        return isFirstPage;
    }

    public boolean islastPage() {
        return isLastPage;
    }

}
