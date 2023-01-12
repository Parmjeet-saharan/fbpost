package savita.example.shabadplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class ShareImage {
    public void sharefb(CallbackManager callbackManager, ShareDialog shareDialog,Context context, Bitmap image5) throws IOException {
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
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            CreateImage createImage = new CreateImage();
            Uri uri = createImage.getmageToShare(image5,context);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            ShareHashtag shareHashTag = new ShareHashtag.Builder().setHashtag("#ShabadPlay").build();
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .setCaption("#ShabadPlay")
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .setShareHashtag(shareHashTag)
                    .setRef("hi")
                    .build();
            shareDialog.show(content);
        }
    }
    public void shrareAll(Context context, Bitmap image5){
        CreateImage createImage = new CreateImage();
        Uri uri = createImage.getmageToShare(image5,context);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "#ShabadPlay");

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "#ShabadPlay");

        // setting type to image
        intent.setType("image/*\" + \"text/*");

        // calling startactivity() to share
        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }
}
