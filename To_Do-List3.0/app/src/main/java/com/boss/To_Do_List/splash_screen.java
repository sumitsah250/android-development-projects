package com.boss.To_Do_List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class splash_screen extends AppCompatActivity {
    ImageView donesymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        Animation drop = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.drop);
        donesymbol=findViewById(R.id.donesymbol);
        donesymbol.startAnimation(rotate);
//        donesymbol.startAnimation(drop);
        Intent home = new Intent(splash_screen.this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(home);
                finish();
            }
        },2200);



    }
}