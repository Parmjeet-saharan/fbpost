package savita.example.shabadplay;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class UpdateApp {
    private static final int MY_REQUEST_CODE= 100;
    public void flexiableUpdate(AppUpdateManager appUpdateManager, View view2,Context context,LinearLayout linearLayout){
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.d("update", "flexiableUpdate@@@@@@@@@@@@@@@: "+String.valueOf(appUpdateInfo.updateAvailability()));
            Log.d("update", "flexiableUpdate@@@@@@@@@@@@@@@: "+String.valueOf(appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)));
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            (Activity) context,
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackBarForCompleteUpdate(appUpdateManager,view2,context,linearLayout);
            }
        });
    }
    public void popupSnackBarForCompleteUpdate(AppUpdateManager appUpdateManager, View view2, Context context, LinearLayout linearLayout) {
        Snackbar.make(view2.findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
                .setAnchorView(linearLayout)
                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(context.getResources().getColor(R.color.purple_500))
                .show();
    }
    public void removeInstallStateUpdateListener(AppUpdateManager appUpdateManager, InstallStateUpdatedListener installStateUpdatedListener) {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }
}

