package com.example.rana.azanreminder;

import java.net.InetAddress;

/**
 * Created by siraj on 30-Jan-17.
 */

public class UtilitiesClass {
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }
}
