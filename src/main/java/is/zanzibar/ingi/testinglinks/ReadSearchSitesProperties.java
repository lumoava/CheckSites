/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.zanzibar.ingi.testinglinks;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
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
    
     private static final String SEARCH_SITES_PROPERTIES = "search.sites.properties"; //linda 
    
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
    
    public static List getAllProperties() {
        List<String> list = new ArrayList<>();
        try (InputStream input = new FileInputStream(SEARCH_SITES_PROPERTIES)) {
            Properties prop = new Properties();
            prop.load(input);
            Enumeration<Object> em = prop.keys();
            while (em.hasMoreElements()) {
                String str = (String) em.nextElement();
                String value = (String)prop.get(str);
                list.add(value);
            }

        } catch (IOException ex) {
        }
        
        return list;
    }
}