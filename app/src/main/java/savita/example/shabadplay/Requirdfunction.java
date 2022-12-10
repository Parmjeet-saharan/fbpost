package savita.example.shabadplay;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Requirdfunction {
    public void addToProfomence(Context context,String name){
        Log.d("addtx", "add text: get call");
     SharedPreferences sharedPref = context.getSharedPreferences(
              "NmaeShared", Context.MODE_PRIVATE);
      SharedPreferences.Editor myEdit = sharedPref.edit();
        myEdit.putString("name", name);
        myEdit.apply();
    }
    public String getFromProfomence(Context context){
        Log.d("addtx", "get text: get call");
        SharedPreferences sharedPref = context.getSharedPreferences(
                "NmaeShared", Context.MODE_PRIVATE);
        String defaultValue ="you";
        String setvalue = sharedPref.getString("name", defaultValue);
        Log.d("addtx", "get text:text is "+setvalue);
        return setvalue;
    }
    public void saveImage(Bitmap bitmapImage,Context context){
     //   Log.d("saveim", "saveImage: get call");
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
        if(mypath.exists()){
           // Log.d("saveim", "saveImage: exist");
        }
    }
    public Bitmap getImage(Context context) throws FileNotFoundException {
     //   Log.d("saveim", "getimage: get call");
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("profile", Context.MODE_PRIVATE);
        File mypath = new File(directory, "profile.png");
        String imagePath = mypath.toString();
        if(mypath.exists()){
          //  Log.d("saveim", "saveImage: exist and path is "+imagePath);
        }

        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));
        return b;
    }
}
