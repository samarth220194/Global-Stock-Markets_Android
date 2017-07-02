package com.global.market;

/**
 * Created by Samarth on 01-Jul-16.
 */
public class CountriesData {

        private String Country;
        private String Indicename;
        private String date;
        private String zone;
        private String CurrPrice;
        private String Chg;
        private String PerChg;
        private String prev_close;
        private String prev_close_m;
        private String prev_close_w;
    private String indices_data_onedy;
    private String indices_data_one_m;
    private String indices_data_3_m;
    private String indices_data_yr;
    private String indices_time_onedy;
    private String indices_time_one_m;
    private String indices_time_3_m;
    private String indices_time_yr;
    private String indices_zone_onedy;
    private String indices_zone_one_m;
    private String indices_zone_3_m;
    private String indices_zone_yr;

    private CountriesData(String indices_data_onedy,
                          String indices_data_one_m,String indices_data_3_m,
                          String Country,String Indicename,String indices_data_yr,
                          String indices_time_onedy,
                          String indices_time_one_m,String indices_time_3_m,String indices_time_yr,
                          String date,String zone,String Currprice,String Chg,
                          String prev_close,String prev_close_m,String indices_zone_onedy,String indices_zone_one_m,
                          String indices_zone_3_m,String indices_zone_yr,
                          String prev_close_w,String Perchg){
            this.Country=Country;
            this.Indicename=Indicename;
            this.CurrPrice=Currprice;
            this.Chg=Chg;
            this.PerChg=Perchg;
            this.date=date;
            this.zone=zone;
            this.prev_close=prev_close;
            this.prev_close_m=prev_close_m;
            this.prev_close_w=prev_close_w;
        this.indices_data_onedy=indices_data_onedy;
        this.indices_data_one_m=indices_data_one_m;
        this.indices_data_3_m=indices_data_3_m;
        this.indices_data_yr=indices_data_yr;
        this.indices_time_onedy=indices_time_onedy;
        this.indices_time_one_m=indices_time_one_m;
        this.indices_time_3_m=indices_time_3_m;
        this.indices_time_yr=indices_time_yr;
        this.indices_zone_onedy=indices_zone_onedy;
        this.indices_zone_one_m=indices_zone_one_m;
        this.indices_zone_3_m=indices_zone_3_m;
        this.indices_zone_yr=indices_zone_yr;
        }

        public void setCountry(String Country){this.Country=Country;}
        public String getCountry() {
            return Country;
        }
        public void setIndicename(String Indicename){
            this.Indicename=Indicename;
        }
        public String getIndicename() {
            return Indicename;
        }
        public void setCurrprice(String Currprice){
            this.CurrPrice=Currprice;
        }
        public String getCurrprice() {
            return CurrPrice;
        }
        public void setChg(String Chg){
            this.Chg=Chg;
        }
        public String getChg() {
            return Chg;
        }
        public void setPerchg(String Perchg){
            this.PerChg=Perchg;
        }
        public String getPerchg() {return PerChg;}
         public void setZone(String zone){this.zone=zone;}
        public String getZone() {return zone;}
    public void setDate(String date){this.date=date;}
    public String getDate() {return date;}

    public void setPrev_close(String prev_close){this.prev_close=prev_close;}
    public String getPrev_close() {
        return prev_close;
    }
    public void setPrev_close_m(String prev_close_m){this.prev_close_m=prev_close_m;}
    public String getPrev_close_m() {
        return prev_close_m;
    }

    public void setPrev_close_w(String prev_close_w){this.prev_close_w=prev_close_w;}
    public String getPrev_close_w() {
        return prev_close_w;
    }

    public void setIndices_data_onedy(String indices_data_onedy){ this.indices_data_onedy=indices_data_onedy;}
    public  void setIndices_data_one_m(String indices_data_one_m){ this.indices_data_one_m=indices_data_one_m;}
    public void setIndices_data_3_m(String indices_data_3_m){ this.indices_data_3_m=indices_data_3_m;}
    public void setIndices_data_yr(String indices_data_yr){ this.indices_data_yr=indices_data_yr;}
    public void setIndices_time_onedy(String indices_time_onedy){ this.indices_time_onedy=indices_time_onedy;}
    public void setIndices_time_one_m(String indices_time_one_m){ this.indices_time_one_m=indices_time_one_m;}
    public void setIndices_time_3_m(String indices_time_3_m){ this.indices_time_3_m=indices_time_3_m;}
    public void setIndices_time_yr(String indices_time_yr){ this.indices_time_yr=indices_time_yr;}
    public String getIndices_data_onedy(){ return indices_data_onedy;}
    public String getIndices_data_one_m(){return indices_data_one_m;}
    public String getIndices_data_3_m(){return indices_data_3_m;}
    public String getIndices_data_yr(){return indices_data_yr;}
    public String getIndices_time_onedy(){return indices_time_onedy;}
    public String getIndices_time_one_m(){return indices_time_one_m;}
    public String getIndices_time_3_m(){return indices_time_3_m;}
    public String getIndices_time_yr(){return indices_time_yr;}
    public String getIndices_zone_onedy(){return indices_zone_onedy; }
    public String getIndices_zone_one_m(){return indices_zone_one_m;}
    public String getIndices_zone_3_m(){return indices_zone_3_m;}
    public String getIndices_zone_yr(){return indices_zone_yr;}


}

