package savita.example.shabadplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ShareImage {
    public void sharefb(CallbackManager callbackManager, ShareDialog shareDialog){
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();
            shareDialog.show(linkContent);
        }
    }
    public void shrareAll(Context context, Bitmap image5){
        CreateImage createImage = new CreateImage();
        Uri uri = createImage.getmageToShare(image5,context);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "https://firebase.google.com/docs/dynamic-links/use-cases/user-to-user#java");

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "https://firebase.google.com/docs/dynamic-links/use-cases/user-to-user#java");

        // setting type to image
        intent.setType("image/*\" + \"text/*");

        // calling startactivity() to share
        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }
}
