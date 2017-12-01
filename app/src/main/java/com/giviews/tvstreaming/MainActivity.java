package com.giviews.tvstreaming;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;

//    @SuppressLint("SdCardPath")
    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Channel");
        mDatabase.keepSynced(true);

        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

        //addmob
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Interstials Ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8903703979382343/5285073432");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });
//        showInterstitial();

        // VideoReward Ads
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(MainActivity.this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }

        //declarasi Variable
//        list = (GridView) findViewById(R.id.list_tv);

        //membuat Array
//        final String channel[] = {
//                "https://kmklive-lh.akamaihd.net/i/trans7_live@137568/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/transtv_live@137568/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/sctv_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/indosiar_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "http://ott.tvri.co.id/Content/HLS/Live/Channel(TVRINasional)/Stream(04)/index.m3u8",
//                "https://kmklive-lh.akamaihd.net/i/jaktv_live@137568/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/rcti_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/antv_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/inewstv_live@137568/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/globaltv_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/mnctv_live@137568/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/ochannel_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/metrotv_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/tvone_live@577566/index_270_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/rtv_live@137568/index_144_av-p.m3u8?sd=10&rebase=on",
//                "https://kmklive-lh.akamaihd.net/i/dwtv_live@137568/index_270_av-p.m3u8?sd=10&rebase=on",
//                "http://ott.tvri.co.id/Content/HLS/Live/Channel(TVRIdki)/Stream(01)/index.m3u8",
//                "http://ott.tvri.co.id/Content/HLS/Live/Channel(TVRI3)/Stream(02)/index.m3u8",
//                "http://ott.tvri.co.id/Content/HLS/Live/Channel(TVRI4)/Stream(01)/index.m3u8",
//                "http://ott.tvri.co.id/Content/HLS/Live/Channel(TVRIjabarbandung)/Stream(01)/index.m3u8"
//        };

        //membuat Array
//        final String nama[] = {
//                "TRANS7",
//                "TRANSTV",
//                "SCTV",
//                "INDOSIAR",
//                "TVRI Nasional",
//                "JAK TV",
//                "RCTI",
//                "ANTV",
//                "INEWS TV",
//                "GTV",
//                "MNC TV",
//                "O CHANNEL",
//                "METRO TV",
//                "TV ONE",
//                "RTV",
//                "DW TV",
//                "TVRI DKI Jakarta",
//                "TVRI Budaya",
//                "TVRI Olahraga",
//                "TVRI Jawa Barat"
//        };

//        final int gambar[] = {
//                R.drawable.trans7,
//                R.drawable.transtv,
//                R.drawable.sctv,
//                R.drawable.indosiar,
//                R.drawable.tvri,
//                R.drawable.jaktv,
//                R.drawable.rcti,
//                R.drawable.antv,
//                R.drawable.inews,
//                R.drawable.gtv,
//                R.drawable.mnc,
//                R.drawable.ochannel,
//                R.drawable.metrotv,
//                R.drawable.tvone,
//                R.drawable.rtv,
//                R.drawable.dwtv,
//                R.drawable.tvri,
//                R.drawable.tvri,
//                R.drawable.tvri,
//                R.drawable.tvri
//        };

//        CustomListAdapter adapter = new CustomListAdapter(this,channel,gambar,nama);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nama);
//        list.setAdapter(arrayAdapter);

        if(CheckNetwork.isInternetAvailable(MainActivity.this)) {
            //do something.
//            mBlogList.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent kirimdata = new Intent(MainActivity.this,PlayerActivity.class);
//                    kirimdata.putExtra("url",mBlogList.getUrl());
//                    kirimdata.putExtra("nama",model.getTitle());
//                    kirimdata.putExtra("gambar",model.getImage);
//
//                    startActivity(kirimdata);
//                }
//            });
        } else {
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

    }

    public void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showInterstitial();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
//        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
//        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdOpened() {
//        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
//        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem reward) {
//        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
//                reward.getAmount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
//        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        showInterstitial();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-8903703979382343/7799556134",
                new AdRequest.Builder().build());
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        if(CheckNetwork.isInternetAvailable(MainActivity.this)) {
            //lanjut
            FirebaseRecyclerAdapter<Model, ModelViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ModelViewHolder>(
                    Model.class,
                    R.layout.blog_row,
                    ModelViewHolder.class,
                    mDatabase
            ) {
                @Override
                protected void populateViewHolder(ModelViewHolder viewHolder, final Model model, int position) {

                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setImage(getApplicationContext(), model.getImage());

                    final String url = model.getUrl();
                    final String title = model.getTitle();
                    final String gambar = model.getImage();

                    mBlogList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent kirimdata = new Intent(MainActivity.this,PlayerActivity.class);
                            kirimdata.putExtra("url", url);
                            kirimdata.putExtra("nama", title);
                            kirimdata.putExtra("gambar", gambar);

                            startActivity(kirimdata);
                        }
                    });
                }
            };

            mBlogList.setAdapter(firebaseRecyclerAdapter);
        }else {
            Toast.makeText(MainActivity.this,"No Internet Connection", Toast.LENGTH_LONG).show();
        }
        super.onStart();
    }

    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ModelViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setImage(final Context ctx, final String image) {
            final ImageView image_post = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(image_post, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(image_post);
                }
            });
        }
    }
}
