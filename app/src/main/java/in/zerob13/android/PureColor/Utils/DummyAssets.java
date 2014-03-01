package in.zerob13.android.PureColor.Utils;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.InputStream;

import in.zerob13.android.PureColor.R;

/**
 * Created by zerob13 on 14-2-28.
 */
public class DummyAssets {

    public static String getFromRaw(Context context){
        String result = "";
        try {
            InputStream in = context.getResources().openRawResource(R.raw.data);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
