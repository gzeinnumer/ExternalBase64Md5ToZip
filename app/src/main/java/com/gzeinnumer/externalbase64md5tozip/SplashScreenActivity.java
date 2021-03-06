package com.gzeinnumer.externalbase64md5tozip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.gzeinnumer.externalbase64md5tozip.helper.FunctionGlobalDir;
import com.gzeinnumer.externalbase64md5tozip.helper.FunctionGlobalZip;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity_";

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    TextView tv;
    String msg="externalbase64md5tozip\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTitle(TAG);

        tv = findViewById(R.id.tv);

        if (checkPermissions()) {
            msg+="Izin diberikan\n";
            tv.setText(msg);
            onSuccessCheckPermitions();
        } else {
            msg+="Beri izin dulu\n";
            tv.setText(msg);
        }
    }

    private void onSuccessCheckPermitions() {
        if (FunctionGlobalDir.initFolder()){
            if (FunctionGlobalDir.isFileExists(FunctionGlobalDir.appFolder)){
                msg+="Sudah bisa lanjut\n";
                tv.setText(msg);

                //dari file zip diubah jadi base64
                //https://base64.guru/converter/encode/file
                String base64EncodeFromFile = "UEsDBBQAAAAIAJK6+FDfGqHQdAEAAABAAAAZAAAARXh0ZXJuYWxCYXNlNjRNZDVUb1ppcC5kYu3aQU7CQBQG4BlKgJJgWZh042JsQgIBTNQLiKYhRCgIJYqbZqRj0tgWgXIAbuQJvIk3MC516xRMhLowLiX/l2nmvXnpm25f0sFV24sEu5/MAh6xU1IklJIzxgghqnzS5Fs6kVPyO5UcHTwVZKDsPRPN03S5AQAAAAAAAPzRtZLR63U6jvidL3joziae6wQi4i6PeDJPX/TNhm0yu3HeNlmyysr+ZMx9wWzzxq70Uhm9WqWjVeP51JczsjMX04UIx8lU2WqbKJZDHoiazCrLfZrVSyW6fFj35MGjL5wfcWqrm7FZMlg5rxqea6gtyzabZp9ZXZtZw3a7Js/jiww1/vjN416/1Wn0R+zSHJXjV1ljaHdblrykY1p2JV9ZzebaC9HetVe5AQAAAAAAAMB/U1AUcti8FV5oLQIxy6UUosfZSY5+Rcfx/E+1NyIXAAAAAAAAAOyEIlVKdPOfAmU9/38QuQAAAAAAAABgt2RpShehMxx8AlBLAQI/ABQAAAAIAJK6+FDfGqHQdAEAAABAAAAZACQAAAAAAAAAgAAAAAAAAABFeHRlcm5hbEJhc2U2NE1kNVRvWmlwLmRiCgAgAAAAAAABABgAgEsYXNZh1gH+eSEy1mHWASKHEDLWYdYBUEsFBgAAAAABAAEAawAAAKsBAAAAAA==";

                //dari file zip diubah jadi md5
                //https://emn178.github.io/online-tools/md5_checksum.html
                String md5EncodeFromFile = "966af03a49f85b0df0afd3d9a42d0264";

                if (FunctionGlobalZip.initFileFromStringToZipToFile(base64EncodeFromFile,md5EncodeFromFile, true)){
                    msg+="Sudah Success load Data\n";
                    tv.setText(msg);
                } else {
                    msg+="Gagal load Data\n";
                    tv.setText(msg);
                }
            } else {
                msg+="Direktory tidak ditemukan\n";
                tv.setText(msg);
            }
        } else {
            msg+="Gagal membuat folder\n";
            tv.setText(msg);
        }
    }

    int MULTIPLE_PERMISSIONS = 1;
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onSuccessCheckPermitions();
            } else {
                StringBuilder perStr = new StringBuilder();
                for (String per : permissions) {
                    perStr.append("\n").append(per);
                }
            }
        }
    }
}