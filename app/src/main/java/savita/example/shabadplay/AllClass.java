package savita.example.shabadplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class AllClass extends AppCompatActivity {
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        FirebaseGetData firebaseGetData = new FirebaseGetData();
        firebaseGetData.fetchAllData("all data");
        firebaseGetData.setOnItemClickForFetchData(new FirebaseGetData.OnItemClick() {
            @Override
            public void getRealList(SomeFunction.dataReturn list) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                AllDataAdapter allDataAdapter = new AllDataAdapter(AllClass.this, list);
                recyclerView.setAdapter(allDataAdapter); // set the Adapter to RecyclerView
            }

        });
    }
}