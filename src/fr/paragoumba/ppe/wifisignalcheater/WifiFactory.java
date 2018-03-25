package fr.paragoumba.ppe.wifisignalcheater;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Paragoumba on 24/03/2018.
 */

public class WifiFactory {

    public static ArrayList<Wifi> wifis;

    public static void generateWifis() throws URISyntaxException {

        wifis = Parser.parse(new File(WifiFactory.class.getResource("data/data.txt").toURI()));

        WifiRandomizer.init();

    }
}
