package com.global.market;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Samarth on 04-Jul-16.
 */
public class  detailactivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //ProgressDialog pDialog;
    private AdView adView;
    private ArrayList<CountriesData> data;
    private LineChart chart;
    private String time_one_day, time_one_month, time_3_month, time_year;
    private String indices_one_day, indices_one_month, indices_3_month, indices_year;
    TextView textheader;
    TextView currprice;
    ImageView arrow1;
    TextView Chg1;
    TextView PerChg1;
    Button day;
    Button month;
    Button threemonth;
    Button year;
    TextView country1;
    TextView prev_day_close;
    TextView prev_week_close;
    TextView prev_month_close;
    TextView date1;
    TextView indicename1,timezone1 ;
    String indname;
    String[] time1, time2, time3, time4;
    String[] indices1, indices2, indices3, indices4;
    float[] indices11, indices22, indices33, indices44;
    EasyTracker easyTracker;

    private SwipeRefreshLayout swipe;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        setContentView(R.layout.detailcountry);

        Thread adThread = new Thread()
        {
            @Override
            public void run()
            {
                loadAd();
            }
        };
        adThread.start();

        indicename1 = (TextView) findViewById(R.id.indicename1);
        timezone1 = (TextView) findViewById(R.id.Timezone);
        textheader = (TextView) findViewById(R.id.textheader);
        currprice = (TextView) findViewById(R.id.CurrPrice1);
        Chg1 = (TextView) findViewById(R.id.Chg1);
        PerChg1 = (TextView) findViewById(R.id.PerChg1);
        arrow1 = (ImageView) findViewById(R.id.arrow1);
        day = (Button) findViewById(R.id.day);
        month = (Button) findViewById(R.id.month);
        year = (Button) findViewById(R.id.year);
        threemonth = (Button) findViewById(R.id.threemonth);
        country1 = (TextView) findViewById(R.id.country1);
        prev_day_close = (TextView) findViewById(R.id.prev_day_close);
        prev_month_close = (TextView) findViewById(R.id.prev_month_close);
        prev_week_close = (TextView) findViewById(R.id.prev_week_close);
        date1 = (TextView) findViewById(R.id.date1);
        chart = (LineChart) findViewById(R.id.linechart);

        assert chart != null;
        chart.setNoDataText("");
        chart.setDescription("");
        chart.setNoDataTextDescription("");
        chart.setBackgroundColor(Color.rgb(63, 81, 181));
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDrawMarkerViews(true);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);
        chart.getLegend().setEnabled(false);


        XAxis x = chart.getXAxis();

        x.setEnabled(true);
        x.setDrawGridLines(false);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setTextColor(Color.argb(255, 128, 128, 255));
        x.isDrawLabelsEnabled();
        x.setDrawAxisLine(true);
        x.setAxisLineColor(Color.BLACK);
        x.setSpaceBetweenLabels(9);
        x.setAvoidFirstLastClipping(true);
        x.setXOffset(4f);


        YAxis y = chart.getAxisLeft();

        //  y.setDrawZeroLine(false);
        //  y.setDrawLimitLinesBehindData(false);
        //  y.isDrawGridLinesEnabled();
        //  y.setDrawGridLines(true);
        y.setEnabled(true);
        y.setValueFormatter(new MyYAxisFormatter());
        y.setDrawAxisLine(true);
        y.setAxisLineColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setLabelCount(5, true);
        y.setGridColor(Color.argb(255, 128, 128, 255));
        y.setTextColor(Color.argb(255, 128, 128, 255));
        y.setYOffset(6f);

//        adView = (AdView) findViewById(R.id.adView1);
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        easyTracker = EasyTracker.getInstance(this);
        boolean net;
        net=isNetworkAvailable();
        if(!net) Toast.makeText(this, "No Internet Connection!",
                Toast.LENGTH_LONG).show();


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        assert swipe != null;
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(R.color.b, R.color.p, R.color.g, R.color.o);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);

                           loadRetro();
                       }
                   }
        );

        // loadRetro();
    }

    private void loadAd()
    {
        // Banner Ad
        final AdView adview = (AdView) this.findViewById(R.id.adView1);

        // Request for ads
        final AdRequest adRequest = new AdRequest.Builder().build();

        // Load Ads on UI Thread
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                assert adview != null;
                adview.loadAd(adRequest);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void Onbackpressed(View v) {
        finish();
        String str = getIntent().getStringExtra("detail");
        easyTracker.send(MapBuilder.createEvent("Detail Page", "Back", str, null).build());
    }


    public void onRefresh(){
//            swipe.setRefreshing(true);
        easyTracker.send(MapBuilder.createEvent("Detail Page", "Pull to Refresh", null, null).build());
        loadRetro();
    }

    /** Called when leaving the activity */
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

    private void loadRetro() {

        swipe.setRefreshing(true);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.appuonline.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        detailrequest request = retrofit.create(detailrequest.class);
        String str = getIntent().getStringExtra("detail");
        //  Log.e("String",str);
        Call<JsonResponse> call = request.DetailURL("appu_android_app/global_stock_app/graph.php?indices=".concat(str));
        //   Log.i("URL", "appu_android_app/global_stock_app/graph.php?indices=".concat(str));
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                try {
                    JsonResponse jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    DecimalFormat df = new DecimalFormat("#0.00");
                    //Log.i("Data",data.get(0).getIndices_data_onedy().toString());
                    for (int i = 0; i < data.size(); i++) {
                        textheader.setText(data.get(i).getIndicename());
                        indicename1.setText(data.get(i).getIndicename());
                        timezone1.setText(data.get(i).getZone());
                        indname=data.get(i).getIndicename();

                        Double curprice=Double.parseDouble(data.get(i).getCurrprice());
                        currprice.setText(df.format(curprice)+"");

                        Double perchg=Double.parseDouble(data.get(i).getPerchg().replace("-", ""));
                        PerChg1.setText("(" + df.format(perchg) + "%)");

                        Double chg=Double.parseDouble(data.get(i).getChg().replace("-", ""));

                        Chg1.setText(df.format(chg) + "");
                        //    date1.setText(data.get(i).getDate());

                        /** Calculating duration between two dates **/

                        Date current_date=new Date();
                        DateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Format.format(current_date);
                        //Log.i("current",current_date+"");


                        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sourceFormat.setTimeZone(TimeZone.getTimeZone(data.get(i).getZone()));
                        Date parsed = sourceFormat.parse(data.get(i).getDate());

                        Log.i("final1",parsed+"");
                        TimeZone tz = TimeZone.getTimeZone("Asia/Kolkata");
                        //Log.i("timezone",tz+"");
                        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        destFormat.setTimeZone(tz);

                        String result = destFormat.format(parsed);
                        Date newdate=destFormat.parse(result);
                        Log.i("final",newdate+"");

                        long secs=Math.abs((current_date.getTime() -newdate.getTime())/1000);
                        long hours = secs / 3600;
                        long mins = secs/60;

                        if(secs==0) date1.setText(String.valueOf("now"));
                        if(secs>0 && secs<60) date1.setText(String.valueOf(secs)+" secs ago");
                        if(secs>=60 && secs<120) date1.setText(String.valueOf(mins)+" min ago");
                        if(secs>=120 && secs<3600) date1.setText(String.valueOf(mins)+" mins ago");
                        if(secs>=3600 && secs<7200 )date1.setText(String.valueOf(hours)+" hour ago");
                        if(secs>=7200 &&  hours<24)date1.setText(String.valueOf(hours)+" hours ago");
                        if(hours>=24 && hours<48)date1.setText(String.valueOf(hours/24)+" day ago");
                        if(hours>=48)date1.setText(String.valueOf(hours/24)+" days ago");

//                        zone1.setText(data.get(i).getZone());


                        country1.setText(data.get(i).getCountry() + " -");

                        Double prev_day=Double.parseDouble(data.get(i).getPrev_close());
                        prev_day_close.setText(df.format(prev_day));

                        if(data.get(i).getPrev_close_w().equals("0"))
                        {
                            prev_week_close.setText("NA");
                        }
                        else {
                            Double prev_week = Double.parseDouble(data.get(i).getPrev_close_w());
                            prev_week_close.setText(df.format(prev_week));
                        }

                        if(data.get(i).getPrev_close_m().equals("0"))
                        {
                            prev_month_close.setText("NA");
                        }
                        else
                        {
                            Double prev_month=Double.parseDouble(data.get(i).getPrev_close_m());
                            prev_month_close.setText(df.format(prev_month));
                        }



                        time_one_day = data.get(i).getIndices_time_onedy();
                        //   Log.i("Data", time_one_day + "");
                        time_one_month = data.get(i).getIndices_time_one_m();
                        //   Log.i("Data1", time_one_month + "");
                        time_3_month = data.get(i).getIndices_time_3_m();
                        //  Log.i("Data2", time_3_month + "");
                        time_year = data.get(i).getIndices_time_yr();
                        //  Log.i("Data3", time_year + "");
                        indices_one_day = data.get(i).getIndices_data_onedy();
                        //   Log.i("Data4", indices_one_day + "");
                        indices_one_month = data.get(i).getIndices_data_one_m();
                        //   Log.i("Data5", indices_one_month + "");
                        indices_3_month = data.get(i).getIndices_data_3_m();
                        //   Log.i("Data6", indices_one_month + "");
                        indices_year = data.get(i).getIndices_data_yr();
                        //    Log.i("Data7", indices_year + "");

                        if (Float.parseFloat(data.get(i).getPerchg()) >= 0) {
                            arrow1.setBackgroundResource(R.drawable.arrowup);
//                        Chg1.setTextColor(Color.parseColor("#15B670"));
//                        PerChg1.setTextColor(Color.parseColor("#15B670"));
                        } else {
                            arrow1.setBackgroundResource(R.drawable.arrowdown);
//                        Chg1.setTextColor(Color.parseColor("#FF0000"));
//                        PerChg1.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }
                    time1 = time_one_day.split(",");
                    //      Log.i("time1", time1[0]);
                    time2 = time_one_month.split(",");
                    //    Log.i("date",time2[0]);
                    time3 = time_3_month.split(",");
                    //        Log.i("time3", time3[1]);
                    time4 = time_year.split(",");
                    //      Log.i("time4", time4[1]);
                    indices1 = indices_one_day.split(",");
                    //    Log.i("ind1", indices_one_day + "");
                    indices2 = indices_one_month.split(",");
                    //   Log.i("ind2", indices2[1]);
                    indices3 = indices_3_month.split(",");
                    //    Log.i("ind3", indices3[1]);
                    indices4 = indices_year.split(",");
                    //      Log.i("ind3", indices4[1]);


                    indices11 = new float[indices1.length];
                    indices22 = new float[indices2.length];
                    indices33 = new float[indices3.length];
                    indices44 = new float[indices4.length];

                    for(int i=0;i<time1.length;i++)
                    {
                        time1[i]=time1[i].replace(".",":");
                    }
                    for(int j=0;j<time2.length;j++){
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM");
                        String inputDateStr=time2[j];
                        Date date = null;
                        try {
                            date = inputFormat.parse(inputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Log.i("dateformat",outputFormat.format(date));
                        String outputDateStr = outputFormat.format(date);
                        time2[j]=outputDateStr;
                    }
                    for(int j=0;j<time3.length;j++){
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM");
                        String inputDateStr=time3[j];
                        Date date = null;
                        try {
                            date = inputFormat.parse(inputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //  Log.i("dateformat",outputFormat.format(date));
                        String outputDateStr = outputFormat.format(date);
                        time3[j]=outputDateStr;
                    }
                    for(int j=0;j<time4.length;j++) {
                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd MMM yy");
                        String inputDateStr=time4[j];
                        Date date = null;
                        try {
                            date = inputFormat.parse(inputDateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // Log.i("dateformat",outputFormat.format(date));
                        String outputDateStr = outputFormat.format(date);
                        time4[j]=outputDateStr;
                    }
                    for (int i = 0; i < indices1.length; i++) {
                        indices11[i] = Float.parseFloat(indices1[i]);
                        //      Log.i("Indices11", indices11[i] + "");
                    }
                    for (int i = 0; i < indices2.length; i++) {
                        indices22[i] = Float.parseFloat(indices2[i]);
                        //     Log.i("Indices22", indices22[i] + "");
                    }
                    for (int i = 0; i < indices3.length; i++) {
                        indices33[i] = Float.parseFloat(indices3[i]);
                        //    Log.i("Indices33", indices33[i] + "");
                    }
                    for (int i = 0; i < indices4.length; i++) {
                        indices44[i] = Float.parseFloat(indices4[i]);
                        //   Log.i("Indices44", indices44[i] + "");
                    }

                    ColorDrawable color1 = (ColorDrawable) day.getBackground();
                    ColorDrawable color2 = (ColorDrawable) month.getBackground();
                    ColorDrawable color3 = (ColorDrawable) threemonth.getBackground();
                    //   ColorDrawable color4 = (ColorDrawable) year.getBackground();

                    if(color1.getColor()==Color.rgb(30, 46, 141))
                        plotchart(time1, indices11,indname);
                    else if(color2.getColor()==Color.rgb(30,46,141))
                        plotchart(time2,indices22,indname);
                    else if(color3.getColor()==Color.rgb(30,46,141))
                        plotchart(time3,indices33,indname);
                    else plotchart(time4,indices44,indname);
//                    day.setBackgroundColor(Color.rgb(30, 46, 141));


                    day.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plotchart(time1, indices11,indname);
                            day.setBackgroundColor(Color.rgb(30, 46, 141));
                            month.setBackgroundColor(Color.rgb(63, 81, 181));
                            threemonth.setBackgroundColor(Color.rgb(63, 81, 181));
                            year.setBackgroundColor(Color.rgb(63, 81, 181));
                            easyTracker.send(MapBuilder.createEvent("Detail Page", "Day", indname  , null).build());
                        }
                    });
                    month.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plotchart(time2,indices22,indname);
                            month.setBackgroundColor(Color.rgb(30, 46, 141));
                            day.setBackgroundColor(Color.rgb(63, 81, 181));
                            threemonth.setBackgroundColor(Color.rgb(63, 81, 181));
                            year.setBackgroundColor(Color.rgb(63, 81, 181));
                            easyTracker.send(MapBuilder.createEvent("Detail Page", "Month", indname, null).build());
                        }
                    });
                    threemonth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plotchart(time3,indices33,indname);

                            threemonth.setBackgroundColor(Color.rgb(30, 46, 141));
                            month.setBackgroundColor(Color.rgb(63,81,181));
                            day.setBackgroundColor(Color.rgb(63, 81, 181));
                            year.setBackgroundColor(Color.rgb(63, 81, 181));
                            easyTracker.send(MapBuilder.createEvent("Detail Page", "3 Months", indname, null).build());
                        }
                    });
                    year.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plotchart(time4, indices44,indname);
                            year.setBackgroundColor(Color.rgb(30, 46, 141));
                            month.setBackgroundColor(Color.rgb(63, 81, 181));
                            threemonth.setBackgroundColor(Color.rgb(63, 81, 181));
                            day.setBackgroundColor(Color.rgb(63, 81, 181));
                            easyTracker.send(MapBuilder.createEvent("Detail Page", "1 Year", indname, null).build());
                        }
                    });

                    swipe.setRefreshing(false);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Log.d("Error0", "no connection");
                swipe.setRefreshing(false);
            }
        });

    }

    private void plotchart(String s[],float[] f1,String str){


        ArrayList<String> xVals=new ArrayList<String>();

        for(int i=0;i<s.length;i++){
            xVals.add(s[i]);
        }


        ArrayList<com.github.mikephil.charting.data.Entry> entries= new ArrayList<>();
        for (int i = 0; i < f1.length; i++) {
            entries.add(new Entry(f1[i], i));
        }
        LineDataSet dataset;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            dataset= (LineDataSet)chart.getData().getDataSetByIndex(0);
            dataset.setYVals(entries);

            chart.getData().setXVals(xVals);
            MymarkerView mv = new MymarkerView(this, R.layout.custom_marker_view, xVals);
            chart.setMarkerView(mv);

            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        } else {
            dataset = new LineDataSet(entries, "");
            // dataset.isDrawCirclesEnabled();
            dataset.setColor(Color.BLACK);
            dataset.setCircleRadius(0f);
            dataset.setLineWidth(0.2f);
            dataset.setValueTextSize(0f);

            dataset.setDrawFilled(true);
            dataset.setFillColor(Color.BLACK);

            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            {
                chart.setHardwareAccelerationEnabled(false);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(dataset); // add the datasets


            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            MymarkerView mv = new MymarkerView(this, R.layout.custom_marker_view, xVals);

            chart.setMarkerView(mv);
            easyTracker.send(MapBuilder.createEvent("Detail Page", "MarkerView", str, null).build());
            chart.setData(data);

            chart.notifyDataSetChanged();
            chart.invalidate();

        }
    }

}
