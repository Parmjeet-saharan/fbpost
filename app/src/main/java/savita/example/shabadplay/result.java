package savita.example.shabadplay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class result extends AppCompatActivity implements OnUserEarnedRewardListener {
    Button button ,share,tryagain;
    TextView textView,nametext;
    int isadd =1;
    int ranNum = 5;
    RecyclerView recyclerView;
    ImageView imageView,imageView2;
    CreateImage createImage = new CreateImage();
    Bitmap image5;
    int position=1;
    String data;
    AdView mAdView;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    RewardedInterstitialAd rewardedInterstitialAd;
    RelativeLayout relativeLayout;
    final String TAG = "result";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_result);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        nametext = (TextView) findViewById(R.id.textView) ;
        textView = (TextView) findViewById(R.id.textView2) ;
        button = (Button) findViewById(R.id.upload);
        tryagain = (Button) findViewById(R.id.tryagain);
        mAdView = findViewById(R.id.adView);
        share = (Button) findViewById(R.id.fb_share_button);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        relativeLayout.setDrawingCacheEnabled(true);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Bundle extras = getIntent().getExtras();
        Requirdfunction requirdfunction = new Requirdfunction();
        try {
            Bitmap profile = requirdfunction.getImage(result.this);
            imageView2.setImageBitmap(profile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (extras != null) {
            position = extras.getInt("number");
            data = extras.getString("data");
            nametext.setText(data);
            // and get whatever type user account id is
        }
        FirebaseGetData firebaseGetData = new FirebaseGetData();
        firebaseGetData.fetchAllData("all data");
        firebaseGetData.setOnItemClickForFetchData(new FirebaseGetData.OnItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void getRealList(SomeFunction.dataReturn list) {
                list.totalList.sort(new Comparator<HashMap<String,String>>(){
                    public int compare(HashMap<String,String> mapping1,HashMap<String,String> mapping2){
                        return mapping1.keySet().iterator().next().compareTo( mapping2.keySet().iterator().next());
                    }});
                //  Log.d("allclass", "getRealList: "+list.totalList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                AllDataAdapter allDataAdapter = new AllDataAdapter(result.this, list);
                recyclerView.setAdapter(allDataAdapter); // set the Adapter to RecyclerView
            }

        });
        FirebaseGetData firebaseGetData2 = new FirebaseGetData();
        firebaseGetData2.fetchAllData("result/image"+String.valueOf(position));
        firebaseGetData2.setOnItemClickForFetchData(new FirebaseGetData.OnItemClick() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void getRealList(SomeFunction.dataReturn list) {
                list.totalList.sort(new Comparator<HashMap<String,String>>(){
                    public int compare(HashMap<String,String> mapping1,HashMap<String,String> mapping2){
                        return mapping1.keySet().iterator().next().compareTo( mapping2.keySet().iterator().next());
                    }});
                Picasso.get().load(list.totalList.get(0).get("image")).into(imageView);
                int l = list.totalKey.size();
                Random random = new Random();
                int ra = random.nextInt(l);
                if(ra==0){
                    ra=1;
                }
                String val = list.totalList.get(ra).get("string"+String.valueOf(ra));
                Requirdfunction requirdfunction = new Requirdfunction();
                String name = requirdfunction.getFromProfomence(result.this);
                val=name+" "+val;
                textView.setText(val);
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // code goes here.
                        try {
                            TimeUnit.SECONDS.sleep(2);
                            image5 = createImage.loadBitmapFromView(relativeLayout);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                t1.start();

                //     image5 = createImage.loadBitmapFromView(relativeLayout);
                //       BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                //     Bitmap image2 = drawable.getBitmap();
            }

        });
        //    Bitmap bitmap = relativeLayout.getDrawingCache();
        //    Bitmap bmp = createImage.textAsBitmap("hi all", 2, 3700);
        //    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
        //   Bitmap image4 = createImage.StringToBitMap("hi hanuman");
        //     Bitmap image = combineImages(image2,bmp);

        //  showAd();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage shareImage = new ShareImage();
                shareImage.shrareAll(result.this,image5);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   showAd();
                ShareImage shareImage = new ShareImage();
                try {
                    shareImage.sharefb(callbackManager,shareDialog,result.this,image5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseGetData firebaseGetData = new FirebaseGetData();
                firebaseGetData.fetchAllData("result/image"+String.valueOf(position));
                firebaseGetData.setOnItemClickForFetchData(new FirebaseGetData.OnItemClick() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void getRealList(SomeFunction.dataReturn list) {
                        list.totalList.sort(new Comparator<HashMap<String,String>>(){
                            public int compare(HashMap<String,String> mapping1,HashMap<String,String> mapping2){
                                return mapping1.keySet().iterator().next().compareTo( mapping2.keySet().iterator().next());
                            }});
                        int l = list.totalKey.size();
                        Random random = new Random();
                        int ra = random.nextInt(l);
                        if(ra==0){
                            ra=1;
                        }
                        String val = list.totalList.get(ra).get("string"+String.valueOf(ra));
                        textView.setText(val);
                        image5 = createImage.loadBitmapFromView(relativeLayout);
                    }

                });
            }
        });
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        isadd = firebaseGetData.getRemote("isadd",result.this);
        ranNum = firebaseGetData.getRemote("ranNum",result.this);
        if(isadd ==1){
            Random random = new Random();
            int rand = random.nextInt(ranNum);
            //    Toast.makeText(result.this, " random num is  "+String.valueOf(rand)+" "+isadd,
            //               Toast.LENGTH_LONG).show();
            if(rand == 0){
                showAd();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareImage shareImage = new ShareImage();
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //    image5 = createImage.loadBitmapFromView(relativeLayout);
    }
    public void shareFB (View view) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
    }
    public void showAd(){
        RewardedInterstitialAd.load(this, "ca-app-pub-3940256099942544/5354046379",
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        rewardedInterstitialAd = ad;
                        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                rewardedInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                rewardedInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });
                        rewardedInterstitialAd.show( result.this, result.this);
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        rewardedInterstitialAd = null;
                    }
                });

    }
    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(result.this,AllClass.class);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        relativeLayout.post(new Runnable() {
            @Override
            public void run() {

                //  image5 = createImage.loadBitmapFromView(relativeLayout);
            }
        });

    }
}