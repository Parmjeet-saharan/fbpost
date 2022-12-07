package savita.example.shabadplay;
import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class FirebaseGetData {
    FirebaseGetData.OnItemClick onItemClick;

    public void setOnItemClickForFetchData(FirebaseGetData.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getRealList(SomeFunction.dataReturn list); //pass any things
    }
    public void fetchAllData(String rootRef){
        DatabaseReference mDatabase;
        Log.d("firebase", "function get call ok ########################");
        mDatabase = FirebaseDatabase.getInstance("https://shabad-play-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child(rootRef);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    String data = String.valueOf(task.getResult().getValue());
                    Log.d("firebase4", "Error getting data @@@@@@@@@@@"+task.getException());
                    SomeFunction someFunction = new SomeFunction();
                    SomeFunction.dataReturn twoList = someFunction.stringToList(data);
                    onItemClick.getRealList(twoList);
                }
     else {
                    //  Object obj = task.getResult().getValue();
                    String data = String.valueOf(task.getResult().getValue());
                    SomeFunction someFunction = new SomeFunction();
                    SomeFunction.dataReturn twoList = someFunction.stringToList(data);
                    onItemClick.getRealList(twoList);
                }
            }
        });
    }
}
