package com.community.protectcommunity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import razerdp.basepopup.BasePopupWindow;

public class FrontGateKnowledgePointPopup extends BasePopupWindow{
    private View popupWindow;

    public FrontGateKnowledgePointPopup(Context context) {
        super(context);
    }

    @Override
    public View onCreateContentView() {
        popupWindow = createPopupById(R.layout.pop_up_window_front_gate_knowledge_point);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://crossroad-1038b.appspot.com/");
        StorageReference islandRef = storageRef.child("knowledge_point_canteen_comfort.png");
        try {
            final File localFile = File.createTempFile("images", "png");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Drawable drawable = Drawable.createFromPath(localFile.getPath());
                    popupWindow.setBackground(drawable);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return popupWindow;
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    //close the pop up window
    public void dismissPopupLayout() {
        popupWindow = null;
        this.dismiss();
        System.gc();
    }
}
