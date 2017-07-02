package com.global.market;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CountryDataAdapter extends RecyclerView.Adapter<com.global.market.CountryDataAdapter.ViewHolder> {
        public ArrayList<CountriesData> data;
        EasyTracker easyTracker;
    String countryName;

        public CountryDataAdapter(ArrayList<CountriesData> data) {
            this.data = data;
        }

        @Override
        public CountryDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(com.global.market.CountryDataAdapter.ViewHolder viewHolder, int i) {

            viewHolder.country.setText(data.get(i).getCountry());
            viewHolder.indicename.setText(data.get(i).getIndicename());
            countryName = data.get(i).getCountry();
           // viewHolder.date.setText(data.get(i).getDate());
          //  viewHolder.zone.setText(data.get(i).getzone());
            DecimalFormat df = new DecimalFormat("#0.00");
            Double currentprice=Double.parseDouble(data.get(i).getCurrprice());


            viewHolder.currprice.setText(df.format(currentprice)+"");
            Double change=Double.parseDouble(data.get(i).getChg().replace("-", ""));
            viewHolder.chg.setText(df.format(change)+"");
            Double perchange=Double.parseDouble(data.get(i).getPerchg().replace("-",""));
            viewHolder.perchg.setText("("+df.format(perchange)+"%)");
            if(Double.parseDouble(data.get(i).getChg()) < 0)
            {
                viewHolder.arrow.setBackgroundResource(R.drawable.arrowdown);
                viewHolder.chg.setTextColor(Color.parseColor("#FF0000"));
                viewHolder.perchg.setTextColor(Color.parseColor("#FF0000"));
            }
            else
            {
                viewHolder.arrow.setBackgroundResource(R.drawable.arrowup);
                viewHolder.chg.setTextColor(Color.parseColor("#15B670"));
                viewHolder.perchg.setTextColor(Color.parseColor("#15B670"));
            }
            if(data.get(i).getCountry().equals("Argentina")){
                viewHolder.map.setBackgroundResource(R.drawable.argentina);
            }
            else if(data.get(i).getCountry().equals("Brazil")){
                viewHolder.map.setBackgroundResource(R.drawable.brazil);
            }
            else if(data.get(i).getCountry().equals("New York")){
                viewHolder.map.setBackgroundResource(R.drawable.newyork);
            }
            else if(data.get(i).getCountry().equals("Peru")){
                viewHolder.map.setBackgroundResource(R.drawable.peru);
            }
            else if(data.get(i).getCountry().equals("Austria")){
                viewHolder.map.setBackgroundResource(R.drawable.austria);
            }
            else if(data.get(i).getCountry().equals("Belgium")){
                viewHolder.map.setBackgroundResource(R.drawable.belgium);
            }
            else if(data.get(i).getCountry().equals("Denmark")){
                viewHolder.map.setBackgroundResource(R.drawable.denmark);
            }
            else if(data.get(i).getCountry().equals("Europe")){
                viewHolder.map.setBackgroundResource(R.drawable.europeanunion);
            }
            else if(data.get(i).getCountry().equals("France")){
                viewHolder.map.setBackgroundResource(R.drawable.france);
            }
            else if(data.get(i).getCountry().equals("Germany")){
                viewHolder.map.setBackgroundResource(R.drawable.germany);
            }
            else if(data.get(i).getCountry().equals("Italy")){
                viewHolder.map.setBackgroundResource(R.drawable.italy);
            }
            else if(data.get(i).getCountry().equals("Netherlands")){
                viewHolder.map.setBackgroundResource(R.drawable.netherlands);
            }
            else if(data.get(i).getCountry().equals("Poland")){
                viewHolder.map.setBackgroundResource(R.drawable.poland);
            }
            else if(data.get(i).getCountry().equals("Portugal")){
                viewHolder.map.setBackgroundResource(R.drawable.portugal);
            }
            else if(data.get(i).getCountry().equals("Spain")){
                viewHolder.map.setBackgroundResource(R.drawable.spain);
            }
            else if(data.get(i).getCountry().equals("Sweden")){
                viewHolder.map.setBackgroundResource(R.drawable.sweden);
            }
            else if(data.get(i).getCountry().equals("Switzerland")){
                viewHolder.map.setBackgroundResource(R.drawable.switzerland);
            }
            else if(data.get(i).getCountry().equals("United Kingdom")){
                viewHolder.map.setBackgroundResource(R.drawable.unitedkingdom);
            }
            else if(data.get(i).getCountry().equals("Australia")){
                viewHolder.map.setBackgroundResource(R.drawable.australia);
            }
            else if(data.get(i).getCountry().equals("New Zealand")){
                viewHolder.map.setBackgroundResource(R.drawable.newzealand);
            }
            else if(data.get(i).getCountry().equals("Canada")){
                viewHolder.map.setBackgroundResource(R.drawable.canada);
            }
            else if(data.get(i).getCountry().equals("MEXICO")){
                viewHolder.map.setBackgroundResource(R.drawable.mexico);
            }
            else if(data.get(i).getCountry().equals("Namibia")){
                viewHolder.map.setBackgroundResource(R.drawable.namibia);
            }
            else if(data.get(i).getCountry().equals("South Africa")){
                viewHolder.map.setBackgroundResource(R.drawable.south_africa);
            }
            else if(data.get(i).getCountry().equals("US")){
                viewHolder.map.setBackgroundResource(R.drawable.unitedstates);
            }
            else if(data.get(i).getCountry().equals("China")){
                viewHolder.map.setBackgroundResource(R.drawable.china);
            }
            else if(data.get(i).getCountry().equals("East Israel")){
                viewHolder.map.setBackgroundResource(R.drawable.israel);
            }
            else if(data.get(i).getCountry().equals("Hong Kong (China)")){
                viewHolder.map.setBackgroundResource(R.drawable.hongkong);
            }
            else if(data.get(i).getCountry().equals("India")){
                viewHolder.map.setBackgroundResource(R.drawable.india);
            }
            else if(data.get(i).getCountry().equals("Indonesia")){
                viewHolder.map.setBackgroundResource(R.drawable.indonesia);
            }
            else if(data.get(i).getCountry().equals("Japan")){
                viewHolder.map.setBackgroundResource(R.drawable.japan);
            }
            else if(data.get(i).getCountry().equals("Malaysia ")){
                viewHolder.map.setBackgroundResource(R.drawable.maaysia);
            }
            else if(data.get(i).getCountry().equals("Saudi Arabia")){
                viewHolder.map.setBackgroundResource(R.drawable.saudiarabia);
            }
            else if(data.get(i).getCountry().equals("South Korea")){
                viewHolder.map.setBackgroundResource(R.drawable.southkorea);
            }
            else if(data.get(i).getCountry().equals("Taiwan")){
                viewHolder.map.setBackgroundResource(R.drawable.taiwan);
            }
            else if(data.get(i).getCountry().equals("Thailand")){
                viewHolder.map.setBackgroundResource(R.drawable.thailand);
            }
            else if(data.get(i).getCountry().equals("Turkey")){
                viewHolder.map.setBackgroundResource(R.drawable.turkey);
            }
            else if(data.get(i).getCountry().equals("Kenya")){
                viewHolder.map.setBackgroundResource(R.drawable.kenya);
            }
            else if(data.get(i).getCountry().equals("Egypt")){
                viewHolder.map.setBackgroundResource(R.drawable.egypt);
            }
            else{
                viewHolder.map.setBackgroundResource(R.drawable.unknown);
            }
        }


        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView country,indicename,currprice,chg,perchg,date;
            public ImageView arrow,map;

            public String [] time1,time2,time3,time4;

            public ViewHolder(View view) {
                super(view);

                country = (TextView)view.findViewById(R.id.country);
                indicename = (TextView)view.findViewById(R.id.indicename);
               currprice=(TextView)view.findViewById((R.id.currprice));
                chg=(TextView)view.findViewById((R.id.chg));
                perchg=(TextView)view.findViewById((R.id.perchg));
                arrow=(ImageView)view.findViewById((R.id.arrow));
                map=(ImageView)view.findViewById(R.id.map);
               view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        easyTracker = EasyTracker.getInstance(v.getContext());
                        String str = indicename.getText().toString();
                        str=str.toLowerCase();
                        str=str.replaceAll("&", "_");
                        str=str.replaceAll(" ","_");
                        str=str.replaceAll("/","_");
                      //  Log.e("Indicename",str);
                        Intent intent = new Intent(v.getContext(),detailactivity.class);
                        intent.putExtra("detail",str);
                        v.getContext().startActivity(intent);
                        easyTracker.send(MapBuilder.createEvent("Home Page","Indice name", str, null).build());

                    }
                }) ;

            }
        }

    }

