package com.global.market;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    JSONObject jsonobject;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    AdRequest adRequest;
    private InterstitialAd interstitialAds;
    private AdView adView;
    public String email;
    EasyTracker easyTracker;
    public String appversion,IntertialAdStatus;
    public int appcode;
    SharedPreferences sp;
    String url = "http://www.appuonline.com/appu_android_app/global_stock_app/register.php";
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;

    private void launchAds()
    {
        interstitialAds = new InterstitialAd(this);
        interstitialAds.setAdUnitId("ca-app-pub-8533861185056317/5625525386");
        interstitialAds.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                try {
                    if (IntertialAdStatus.equals("0")) {
                        Intent intent = new Intent(MainActivity.this, ShowAfterIntertialAd.class);
                        startActivity(intent);
                    }
                }catch(NullPointerException e){
                }

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

    public void showInterstitial()
    {
        if(interstitialAds.isLoaded()) {
            interstitialAds.show();
        }
    }

    public void loadInterstitial()
    {
        adRequest = new AdRequest.Builder().build();
        interstitialAds.loadAd(adRequest);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        easyTracker = EasyTracker.getInstance(this);
        adView.loadAd(adRequest);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        launchAds();
        loadInterstitial();
        something();
        email = FetchEmail.getEmail(getApplicationContext());
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            appversion =   packageInfo.versionName;
            appcode = packageInfo.versionCode;
              new insertsymbol().execute();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        expandableList = (ExpandableListView) findViewById(R.id.exp_list);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        //getting list of items
        prepareListData();

        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);



        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        //setting adapter
        expandableList.setAdapter(mMenuAdapter);
        boolean x;
        x=isNetworkAvailable();
        if(!x)Toast.makeText(this, "No Internet Connection!",
                Toast.LENGTH_LONG).show();

        final String[] indicesname=new String[]{"Shanghai Composite",
                "Nifty 50","S&P 500",
                "FTSE 100",
                "Euro Stoxx 50 "};

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                for(int j=0;j<indicesname.length;j++) {
                    if (listDataChild.get(listDataHeader.get(i)).get(i1).equals(indicesname[j]))
                    {
                        String str = indicesname[j];
                        str = str.toLowerCase();
                        str = str.replaceAll("&", "_");
                        str = str.replaceAll(" ", "_");
                        str = str.replaceAll("/", "_");
                        //  Log.e("Indicename",str);
                        Intent intent = new Intent(MainActivity.this, detailactivity.class);
                        intent.putExtra("detail", str);
                        startActivity(intent);
                        easyTracker.send(MapBuilder.createEvent("Detail Page", "Indices name", str, null).build());
                    }
                }
                if(listDataChild.get(listDataHeader.get(i)).get(i1).equals("Asia")) {
                    viewPager.setCurrentItem(0);
                    easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "Asia", null).build());
                }
                else  if(listDataChild.get(listDataHeader.get(i)).get(i1).equals("North America"))
                {
                    viewPager.setCurrentItem(1);
                    easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "North America", null).build());
                }
                else  if(listDataChild.get(listDataHeader.get(i)).get(i1).equals("Europe"))
                {
                    showInterstitial();
                    viewPager.setCurrentItem(2);
                    easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "Europe", null).build());
                }
                else  if(listDataChild.get(listDataHeader.get(i)).get(i1).equals("South America"))
                {
                    viewPager.setCurrentItem(3);
                    easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "South America", null).build());
                }
                else  if(listDataChild.get(listDataHeader.get(i)).get(i1).equals("Australia"))
                {
                    viewPager.setCurrentItem(4);
                    easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "Australia", null).build());
                }
                else if(listDataChild.get(listDataHeader.get(i)).get(i1).equals("Africa"))
                {
                    viewPager.setCurrentItem(5);
                    easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "Africa", null).build());
                }
                else {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);

                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "heading clicked");

                if(listDataHeader.get(i).getIconName().equals("Share")){
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi, I am using The Global Stock Markets app.Its Amazing.Why don't you check it?  http:bit.ly/wcc-app");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    easyTracker.send(MapBuilder.createEvent("Menu", "Share", null, null).build());
                }
                else if(listDataHeader.get(i).getIconName().equals("Feedback")){
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"helpdesk@appuonline.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Global Stock Markets: Feedback");
                    easyTracker.send(MapBuilder.createEvent("Menu", "Feedback", null, null).build());
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                }
                else if(listDataHeader.get(i).getIconName().equals("Rate Us")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.global.market"));
                    startActivity(intent);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    easyTracker.send(MapBuilder.createEvent("Menu", "Rate Us", null, null).build());
                }
                else if(listDataHeader.get(i).getIconName().equals("More Apps")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/search?q=pub%3Aappuonline&hl=en"));
                    startActivity(intent);
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    assert drawer != null;
                    drawer.closeDrawer(GravityCompat.START);
                    easyTracker.send(MapBuilder.createEvent("Menu", "More Apps", null, null).build());
                }

                return false;
            }
        });
    }


    private void something() {
        if(Utils.isNetworkAvailable(MainActivity.this)) {
            String url = "http://www.appuonline.com/appu_android_app/global_stock_app/interstitial_ad.php?appname=Global";
            // TODO Auto-generated method stub
            JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            jsonobject = response.getJSONObject(i);
                            IntertialAdStatus = jsonobject.optString("status");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //		VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(req, "tag_json_arry");
        }
    }



    private class insertsymbol extends AsyncTask<String, Integer, String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile()
        {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try
            {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener()
                {
                    @Override
                    public void transferred(long num)
                    {

                    }
                });
                if(Utils.isNetworkAvailable(MainActivity.this)) {
                    entity.addPart("email", new StringBody(email));
                    entity.addPart("AppCode", new StringBody(String.valueOf(appcode)));
                    entity.addPart("AppVersion", new StringBody(appversion));
                    httppost.setEntity(entity);
                }
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200)
                {
                    responseString = EntityUtils.toString(r_entity);
                }
                else
                {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }
            }
            catch (ClientProtocolException e)
            {
                responseString = e.toString();
            }
            catch (IOException e)
            {
                responseString = e.toString();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
        }
    }



    //    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();

        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Top Indices");
        item1.setIconImg(R.drawable.indices);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Continents");
        item2.setIconImg(R.drawable.globe);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Share");
        item3.setIconImg(R.drawable.shareit);
        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Feedback");
        item4.setIconImg(R.drawable.feedback);
        ExpandedMenuModel item5 = new ExpandedMenuModel();
        item5.setIconName("Rate Us");
        item5.setIconImg(R.drawable.rateus);
        ExpandedMenuModel item6 = new ExpandedMenuModel();
        item6.setIconName("More Apps");
        item6.setIconImg(R.drawable.more_app);
        listDataHeader.add(item3);
        listDataHeader.add(item4);
        listDataHeader.add(item5);
        listDataHeader.add(item6);

        // Adding child data
        List<String> heading1 = new ArrayList<String>();

        heading1.add("Shanghai Composite");
        heading1.add("Nifty 50");
        heading1.add("S&P 500");
        heading1.add("FTSE 100");
        heading1.add("Euro Stoxx 50 ");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Asia");
        heading2.add("North America");
        heading2.add("Europe");
        heading2.add("South America");
        heading2.add("Australia");
        heading2.add("Africa");


        listDataChild.put(listDataHeader.get(0), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(1), heading2);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Asia(), "ASIA");
        adapter.addFragment(new NorthAmerica(), "N.AMERICA");
        adapter.addFragment(new Europe(), "EUROPE");
        adapter.addFragment(new SouthAmerica(), "S.AMERICA");
        adapter.addFragment(new Australia(), "AUSTRALIA");
        adapter.addFragment(new Africa(), "AFRICA");

        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==3) {
                showInterstitial();
            }
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() { return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            easyTracker.send(MapBuilder.createEvent("Home Page", "Click", "Asia", null).build());
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            easyTracker.send(MapBuilder.createEvent("Exit", "Back", null, null).build());
            showInterstitial();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        easyTracker.send(MapBuilder.createEvent("Home Page", "Click", menuItem+"", null).build());
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }
}
