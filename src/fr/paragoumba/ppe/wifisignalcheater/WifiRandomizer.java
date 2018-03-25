package fr.paragoumba.ppe.wifisignalcheater;

/**
 * Created by Paragoumba on 24/03/2018.
 */

public class WifiRandomizer implements Runnable {

    private WifiRandomizer(){}

    private static Thread thread = new Thread(new WifiRandomizer(), "Thread-WifiRandomizer");
    public static boolean running = true;
    private long gap = 1000;

    public static void init(){

        thread.start();

    }

    @Override
    public void run() {

        long lastRandomized = System.currentTimeMillis();

        randomize();

        while (running) {

            if (System.currentTimeMillis() - lastRandomized >= 1000) randomize();

        }
    }

    private void randomize(){

        for (Wifi wifi : WifiFactory.wifis){

            int noise = (int) Math.round(Math.random() * 4 - 2);
            wifi.setDbm(wifi.getDbm() + noise);

            //System.out.println(wifi);

        }
    }
}
