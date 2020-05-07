/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.zanzibar.ingi.testinglinks;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ingimar
 */
public class ReadSearchSitesProperties {

    /**
     * 
     * @param key ("site.mediafiles.google")
     * @return 
     */
    public static String getProperty(String key) {
        String value = "";
        try (InputStream input = new FileInputStream("search.sites.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            value = prop.getProperty(key);
            System.out.println(value); // debug only, remove later

        } catch (IOException ex) {
        }

        return value ;
    }
}