package savita.example.shabadplay;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SomeFunction {
    public static class dataReturn {
        public ArrayList<HashMap<String,String>> totalList;
        public ArrayList totalKey;
        public dataReturn(ArrayList<HashMap<String, String>> totalList, ArrayList totalKey){
            this.totalList = totalList;
            this.totalKey = totalKey;
        }
    }

    @SuppressLint("LongLogTag")
    public dataReturn stringToList(String str){
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.deleteCharAt((stringBuilder.length()-1));
        stringBuilder.deleteCharAt(0);
        str = stringBuilder.toString();
        String[] strings = str.split(",");
        for(int i=0;i<strings.length;i++){
            String[] strs = strings[i].split("=",2);
            Log.d("firebase somefunction @@@@@@@@   "+strings[i],str);
            HashMap<String,String> hashMap = new HashMap<String,String>();
            hashMap.put((strs[0].replaceAll(" ","")),strs[1]);
            arrayList.add(strs[0].replaceAll(" ",""));
            list.add(hashMap);

            Log.d("firebase somefunction @@@@@@@@   ",arrayList.get(i)+" "+ list.get(i).get(arrayList.get(i)));
        }
        return new dataReturn(list,arrayList);
    }
    @SuppressLint("LongLogTag")
    public ArrayList effectiveList(List totalList, ArrayList existist){
        ArrayList realList = new ArrayList();
        for(int i=0;i<totalList.size();i++){

            boolean istrue = existist.contains(totalList.get(i));
            if(!istrue){
                realList.add(totalList.get(i));
                // Log.d("check somefunction@@@@@@@@@@@@@@@@@@@@@", totalList.get(i).toString()+" "+
                //     realList.get(i).toString().length()+" "+existist.get(i).toString().length()+" "+i);
            }
        }
        return realList;
    }
}