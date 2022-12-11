package savita.example.shabadplay;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 100 ;
    public static final int PICK_IMAGE = 1;
    Button button,editname,uploadimage;
    LinearLayout namelayout,namelayout2;
    EditText editText;
    TextView nametext;
    ImageView imageView;
    AdView mAdView,mAdView2;
    AppUpdateManager appUpdateManager;
    InstallStateUpdatedListener installStateUpdatedListener;
     final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.next);
        editname = (Button) findViewById(R.id.edittext);
        uploadimage = (Button) findViewById(R.id.upload);
        editText = (EditText) findViewById(R.id.edit);
        namelayout = (LinearLayout) findViewById(R.id.nametext);
        namelayout2 = (LinearLayout) findViewById(R.id.nametext2);
        nametext = (TextView) findViewById(R.id.textView);
        mAdView = findViewById(R.id.adView);
        mAdView2= findViewById(R.id.adView2);
        appUpdateManager = AppUpdateManagerFactory.create(this);
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                removeInstallStateUpdateListener();
            } else {
                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
            }
        };
        appUpdateManager.registerListener(installStateUpdatedListener);
        flexiableUpdate();
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(editText.getText()) &&  namelayout.getVisibility()==View.VISIBLE){
                    editText.setError( "First name is required!" );
                }else {
                    Requirdfunction requirdfunction = new Requirdfunction();
                    requirdfunction.addToProfomence(Main2Activity.this,String.valueOf(editText.getText()));
                    requirdfunction.getFromProfomence(Main2Activity.this);
                    Intent myIntent = new Intent(Main2Activity.this, AllClass.class);
                    myIntent.putExtra("name", editText.getText().toString());
                    editText.setError(null);
                    startActivity(myIntent);
                }

            }
        });
        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namelayout.setVisibility(View.VISIBLE);
                namelayout2.setVisibility(View.GONE);
            }
        });
        Requirdfunction requirdfunction = new Requirdfunction();
        String name = requirdfunction.getFromProfomence(Main2Activity.this);
        if(name!="you"){
            namelayout2.setVisibility(View.VISIBLE);
            namelayout.setVisibility(View.GONE);
            nametext.setText("hi "+name);
        }
        if(requirdfunction.isImageexist(Main2Activity.this)){
            Bitmap b = null;
            try {
                b = requirdfunction.getImage(Main2Activity.this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(b);
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
               //     imageView.setImageURI(selectedImageUri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        Requirdfunction requirdfunction = new Requirdfunction();
                        requirdfunction.saveImage(bitmap,Main2Activity.this);
                        Bitmap b = requirdfunction.getImage(Main2Activity.this);
                        imageView.setImageBitmap(b);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        if (requestCode == MY_REQUEST_CODE) {

            if (resultCode != RESULT_OK) {
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }

    }

    public void flexiableUpdate(){
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.d(TAG, "flexiableUpdate@@@@@@@@@@@@@@@: "+String.valueOf(appUpdateInfo.updateAvailability()));
            Log.d(TAG, "flexiableUpdate@@@@@@@@@@@@@@@: "+String.valueOf(appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)));
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate();
            }
        });
    }
    public void imedareUpdate(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.purple_500))
                .show();
    }
    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

}