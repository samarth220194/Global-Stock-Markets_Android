package com.global.market;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Samarth on 30-Jun-16.
 */
public class Europe extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
 //   ProgressDialog pDialog;
  //  private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CountryDataAdapter adapter;
    private ArrayList<CountriesData> data;
    private SwipeRefreshLayout swipe;
    private InterstitialAd interstitialAds;
    AdRequest adRequest;

    public Europe(){

    }
    private void launchAds()
    {
        interstitialAds = new InterstitialAd(getActivity());
        interstitialAds.setAdUnitId("ca-app-pub-8533861185056317/5625525386");
        interstitialAds.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
    }



    public void loadInterstitial()
    {
        adRequest = new AdRequest.Builder().build();
        interstitialAds.loadAd(adRequest);
    }

    public void showInterstitial()
    {
        if(interstitialAds.isLoaded()) {
            interstitialAds.show();
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.countrydata, container, false);
     //   pDialog = new ProgressDialog(this.getContext(), ProgressDialog.THEME_HOLO_LIGHT);
        mRecyclerView=(RecyclerView) v.findViewById(R.id.my_recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        launchAds();
        loadInterstitial();

        swipe=(SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(R.color.b, R.color.p, R.color.g, R.color.o);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                         //  swipe.setRefreshing(true);

                           loadRetro();
                       }
                   }
        );



       // loadJSON();
        return v;

    }
    @Override
    public void setMenuVisibility(final boolean visible) {

        super.setMenuVisibility(visible);
        if (visible) {
            if(interstitialAds!=null)
            showInterstitial();
           else getActivity();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            boolean x=isNetworkAvailable();
            if(x)
          // swipe.setRefreshing(true);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
            {loadRetro();
            return true;}
            else  Toast.makeText(getActivity(), "No Internet Connection!",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRefresh(){
      //  swipe.setRefreshing(true);
        loadRetro();
    }
    public void loadRetro(){
       swipe.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.appuonline.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonrequest request = retrofit.create(jsonrequest.class);
        Call<JsonResponse> call = request.getJSON2();
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                try{
                JsonResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                adapter = new CountryDataAdapter(data);
                mRecyclerView.setAdapter(adapter);

                    swipe.setRefreshing(false);
                }
                catch (Exception e){}
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                try{Log.d("Error3","no connection");
                swipe.setRefreshing(false);}
                catch(Exception e){}
            }
        });
    }

}
