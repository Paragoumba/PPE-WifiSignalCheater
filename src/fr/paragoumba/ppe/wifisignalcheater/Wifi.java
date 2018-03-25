package fr.paragoumba.ppe.wifisignalcheater;

public class Wifi {

    public Wifi(){}

    public Wifi(String ssid, double frequency, double dbm, int quality){

        this.ssid = ssid;
        this.frequency = frequency;
        this.dbm = dbm;
        this.quality = quality;

    }

    private String ssid;
    private double frequency;
    private double dbm;
    private int quality;


    public String getSsid() {

        return ssid;

    }

    public double getFrequency() {

        return frequency;

    }

    public double getDbm(){

        return dbm;

    }

    public void setSsid(String ssid) {

        this.ssid = ssid;

    }

    public void setFrequency(double frequency) {

        this.frequency = frequency;

    }

    public void setDbm(double dbm) {

        this.dbm = dbm;

    }

    public void setQuality(int quality) {

        this.quality = quality;

    }

    public static double dbmToWatt(double dbm){

        return Math.pow(10, dbm / 10 - 3);

    }

    @Override
    public String toString() {

        return ssid + " " + frequency + " " + dbm + " " + quality;

    }
}
