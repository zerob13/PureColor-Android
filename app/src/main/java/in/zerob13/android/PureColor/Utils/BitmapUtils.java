package in.zerob13.android.PureColor.Utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zerob13 on 14-3-1.
 */
public class BitmapUtils {


    public static Bitmap createBitmap(Context context, int height, int width, int color) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(width, height, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(color);
        return bmp;
    }

    public static void writeBitmapToFile(Bitmap src, String dirPath,String filename) {
        FileOutputStream m_fileOutPutStream = null;
        try {
            File target = new File(dirPath);
            if (!target.exists()) {
                target.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            m_fileOutPutStream = new FileOutputStream(dirPath + filename);//写入的文件路径
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        src.compress(Bitmap.CompressFormat.PNG, 100, m_fileOutPutStream);
        try {
            m_fileOutPutStream.flush();
            m_fileOutPutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setWallpaper(Context context,Bitmap bmp){
        Resources res=context.getResources();

        try{
            WallpaperManager.getInstance(context).setBitmap(bmp);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
