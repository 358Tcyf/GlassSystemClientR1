package project.ys.glasssystem_r1.util.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MediaUtil {
    public static File getFileFromMediaUri(Context context, Uri uri) {
        if(uri.getScheme().compareTo("content") == 0){
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        }else if(uri.getScheme().compareTo("file") == 0){
            return new File(uri.toString().replace("file://",""));
        }
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte[] getByteFromFile(File file){
        byte[] data;
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[(int)file.length()];
            inputStream.read(data);
            inputStream.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
