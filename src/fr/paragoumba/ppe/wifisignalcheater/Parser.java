package fr.paragoumba.ppe.wifisignalcheater;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Paragoumba on 24/03/2018.
 */

public class Parser {

    public static ArrayList<Wifi> parse(File file){

        StringBuilder fileBuilder = new StringBuilder();

        try(FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)){

            String line;

            while ((line = br.readLine()) != null) {

                fileBuilder.append(line);
                fileBuilder.append('\n');

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        String scan = fileBuilder.toString();

        //Replace each gap of ten spaces by a void string
        scan = scan.replaceAll(" {10}", "");

        //Cutting all chars before "Cell"
        scan = scan.substring(scan.indexOf("Cell"));

        //Initializing variables to store data
        ArrayList<Wifi> wifis = new ArrayList<>();
        Wifi wifi = new Wifi();

        System.out.println(scan);

        //Split at each line
        for (String line : scan.split("\n")) {

            //Ignoring all unwanted lines
            if (!line.startsWith("IE: ") && !line.startsWith("Extra:") && !line.startsWith("    ") && !line.startsWith("Mode:") && !line.startsWith("Bit Rates:") && !line.startsWith("Encryption key:") && !line.startsWith("Channel:") && !Character.isDigit(line.toCharArray()[0])) {

                if (!line.startsWith("Cell ")) {

                    if (line.startsWith("Frequency:")) {

                        wifi.setFrequency(Double.parseDouble(line.substring(line.indexOf(":") + 1, line.indexOf(" (") - 4)));

                    } else if (line.startsWith("Quality=")) {

                        wifi.setQuality(Integer.parseInt(line.substring(8, line.indexOf("  ") - 3)));
                        wifi.setDbm(Double.parseDouble(line.substring(line.indexOf("Signal level=") + 13, line.lastIndexOf(" ") - 5)));

                    } else if (line.startsWith("ESSID:\"")) {

                        String ssid = line.substring(7, line.lastIndexOf("\""));
                        wifi.setSsid(scan.substring(scan.indexOf(ssid) + ssid.length()).contains(ssid) ? ssid + " (Cell " + /*wifi.get("id")*/0 + ")" : ssid);

                    }

                } else {

                    //If line starts with "Cell", we add the old wifi to wifis' list and we instantiate it with a new wifi
                    if (!wifi.equals(new Wifi())) {

                        wifis.add(wifi);
                        System.out.println("Wifi:" + wifi);

                        wifi = new Wifi();

                    }
                }
            }
        }

        wifis.add(wifi);

        return wifis;

    }
}
