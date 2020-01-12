package com.example.connected_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

public class info_pg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pg);

        deleteCache(info_pg.this);
       ImageView imageView = findViewById(R.id.img);
        Bundle extras = getIntent().getExtras();
       String plant_name=extras.getString("plant_name");;

        String imageUrl = "http://192.168.43.77/html/employee/"+plant_name+".jpg";
        Picasso.get().load(imageUrl).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageView);

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }
    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        }
        else
            {
            return false;
        }
    }

    public void next(View view) {

        Intent intent = new Intent(this, iot_info_pg.class);
        startActivity(intent);
    }
}
