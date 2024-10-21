package com.boss.To_Do_List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Rewards extends AppCompatActivity {
    TextView rewardcoin;
    Button btnAddReward;
    int storedValue;
    int Rewards=0;
    private static String AD_UNIT_ID="ca-app-pub-8523770818071031/8286141639";
    private static String TEST_AD_UNIT_ID="ca-app-pub-3940256099942544/5224354917";
    ImageView imgCoinInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rewards);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        rewardcoin = findViewById(R.id.rewardcoin);
        btnAddReward = findViewById(R.id.btnaddreward);
        imgCoinInfo= findViewById(R.id.imgcoininfo);

        loadRewardedAds();

        DatabaseHelper dbHelper = new DatabaseHelper(Rewards.this);
         // To insert or update the integer value
//        dbHelper.insertOrUpdateValue(123);
        storedValue = dbHelper.getValue();
        rewardcoin.setText(storedValue+"\uD83E\uDE99");
        btnAddReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardPopUp();
            }
        });
        imgCoinInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(com.boss.To_Do_List.Rewards.this);
                alert.setIcon(R.drawable.info_button_svgrepo_com);
                alert.setTitle("About coin");
                alert.setMessage("The coin\uD83E\uDE99 currently serves no actual function. \n You can collect it just for fun.");
                alert.setCancelable(false);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alert.show();
            }
        });



    }
    private void showRewardPopUp(){
        AlertDialog.Builder alert = new AlertDialog.Builder(com.boss.To_Do_List.Rewards.this);
        alert.setIcon(R.drawable.reshot_icon_coin_lnrdtuc85b);
        alert.setTitle("Free Coin");
        alert.setMessage("Watch fill ad to\n get the reward");
        alert.setCancelable(false);
        alert.setPositiveButton("Watch now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showReward();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alert.show();
    }
    private RewardedAd rewardedAd;
    private void loadRewardedAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                rewardedAd=null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rd) {
                rewardedAd=rd;
            }
        });

    }
    private  void  showReward(){
        if(rewardedAd==null){
            Toast.makeText(this, "No ads available at the moment", Toast.LENGTH_SHORT).show();
            return;
        }
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                rewardedAd=null;
                DatabaseHelper dbHelper = new DatabaseHelper(Rewards.this);
                storedValue=storedValue+1;
//                 To insert or update the integer value

                dbHelper.insertOrUpdateValue(storedValue);
                rewardcoin.setText(storedValue+"\uD83E\uDE99");
                loadRewardedAds();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                rewardedAd=null;
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
            }
        });
        rewardedAd.show(com.boss.To_Do_List.Rewards.this, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                //reward
                Toast.makeText(Rewards.this, "+1\uD83E\uDE99 ", Toast.LENGTH_SHORT).show();
                //
            }
        });

    }



}