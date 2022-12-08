package savita.example.shabadplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AllClass extends AppCompatActivity {
    RecyclerView recyclerView;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdView = findViewById(R.id.adView);
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
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}