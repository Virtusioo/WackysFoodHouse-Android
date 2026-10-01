package com.example.wackysfoodhouse.core;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wackysfoodhouse.R;
import com.example.wackysfoodhouse.databinding.DialogBasicBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class App {
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public static void showBasicDialog(
            Context context,
            int icon,
            String title,
            String message
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogBasicBinding dialog = DialogBasicBinding.inflate(LayoutInflater.from(context));
        builder.setView(dialog.getRoot());

        AlertDialog alert = builder.create();
        alert.show();

        dialog.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

        dialog.icon.setImageResource(icon);
        dialog.title.setText(title);
        dialog.message.setText(message);
    }

    public static void showErrorDialog(
            Context context,
            String title,
            String message
    ) {
        showBasicDialog(
                context,
                R.drawable.ic_error,
                title,
                message
        );
    }
}
