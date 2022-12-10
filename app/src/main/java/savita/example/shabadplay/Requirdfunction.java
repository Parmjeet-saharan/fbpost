package savita.example.shabadplay;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class Requirdfunction {
    public void addToProfomence(Context context,String name){
     SharedPreferences sharedPref = context.getSharedPreferences(
              "NmaeShared", Context.MODE_PRIVATE);
      SharedPreferences.Editor myEdit = sharedPref.edit();
        myEdit.putString("name", name);
        myEdit.apply();
    }
    public String getFromProfomence(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                "NmaeShared", Context.MODE_PRIVATE);
        String defaultValue ="you";
        String setvalue = sharedPref.getString("name", defaultValue);
        return setvalue;
    }
    public void saveImage(Bitmap bitmapImage,Context context){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("profile", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, "profile.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }
    }
    public void getImage(Bitmap bitmapImage,Context context){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("profile", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile.png");
        String imagePath = mypath.toString();
    }
}
