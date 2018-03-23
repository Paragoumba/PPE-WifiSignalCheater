package fr.paragoumba.wifisignalcheater;

public class Wifi {

    public Wifi(String ssid, double frequence){

        this.ssid = ssid;
        this.frequence = frequence;

    }

    private final String ssid;
    private final double frequence;


    public String getSsid() {

        return ssid;

    }

    public double getFrequence() {

        return frequence;

    }

    public double getDbm(){

        return 0.0;

    }

    public static double dbmToWatt(double dbm){

        return dbm;

    }
}
