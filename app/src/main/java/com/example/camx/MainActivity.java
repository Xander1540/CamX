package com.example.camx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> galleryLauncher1;
    private ActivityResultLauncher<Intent> galleryLauncher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgCamera = findViewById(R.id.imgCamera);
        FloatingActionButton btnCamera = findViewById(R.id.floatingActionButton);

        CircleImageView profile = findViewById(R.id.profile_image);
        FloatingActionButton probtn = findViewById(R.id.floatingActionButton2);

        galleryLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                Uri uri = result.getData().getData();
                Glide.with(this).load(uri).into(imgCamera);
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                ImagePicker.Companion.getError(result.getData());
            }
        });

        galleryLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
            if (result.getResultCode() == RESULT_OK) {
                Uri uri = result.getData().getData();
                Glide.with(this).load(uri).into(profile);
            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                ImagePicker.Companion.getError(result.getData());
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(MainActivity.this)
                        .cropSquare()
                        .maxResultSize(1080, 1080, true)
                        .createIntentFromDialog((Function1) (new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                galleryLauncher1.launch(it);
                            }
                        }));
            }
        });

        probtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(MainActivity.this)
                        .cropSquare()
                        .maxResultSize(1080, 1080, true)
                        .createIntentFromDialog((Function1) (new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                galleryLauncher2.launch(it);
                            }
                        }));
            }
        });
    }
}
