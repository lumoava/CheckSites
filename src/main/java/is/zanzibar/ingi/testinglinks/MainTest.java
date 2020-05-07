package is.zanzibar.ingi.testinglinks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

/**
 *
 * @author ingimar
 */
public class MainTest {

    private static final Logger logger = Logger.getLogger(MainTest.class.getName());

    private final int CONNECT_TIMEOUT = 4000;

    private final String ENCODING = "UTF-8";

    final String ERROR_MESSAGE = "Failed to access URL ";

    public static void main(String[] args) throws MalformedURLException {

        MainTest testing = new MainTest();
        // pingar alla 'siter' på naturforskaren 
        List<String> fetchAllPingSites = testing.fetchAllPingSites();

        for (String site : fetchAllPingSites) {
            ExternalLink link = testing.pinging(site);
            System.out.println(link);
        }

        // Först måste 'Google' ha passerat ping ovan ... sen checkar vi av de 4 siter:
        String site = testing.fetchSingleSearchSite("site.mediafiles.google");
        
        final String scientificName = "Gonepteryx rhamni"; // citronfjäril
        ExternalLink linkGoogle = testing.checkGoogle(site, scientificName);
        System.out.println("\n\n Google search for '"+scientificName +"' images was "+linkGoogle.isLinkOk());

    }

    private List fetchAllPingSites() {
        List<String> list = ReadPingSitesProperties.getAllValues();
        return list;
    }
    
    private String fetchSingleSearchSite(String site) {
        String value = ReadSearchSitesProperties.getProperty(site);
        return value;
    }

    private ExternalLink pinging(String site) throws MalformedURLException {
        ExternalLink link = new ExternalLink(new URL(site));
        boolean isPingable = this.genericPing(site);

        if (isPingable) {
            link.setLinkOk(Boolean.TRUE);
        } else {
            link.setLinkOk(Boolean.FALSE);
        }

        return link;
    }

    protected boolean genericPing(String website) {
        boolean isOK = false;

        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(website).openConnection();
            con.setRequestMethod("HEAD");

            con.setConnectTimeout(CONNECT_TIMEOUT);
            con.setReadTimeout(CONNECT_TIMEOUT);
            final int responseCode = con.getResponseCode();

            isOK = (responseCode == HttpURLConnection.HTTP_OK)
                    || (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM)
                    || (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP);
            return isOK;
        } catch (SocketTimeoutException e) {
            return isOK;
        } catch (IOException e) {
            return isOK;
        }
    }

    protected ExternalLink checkGoogle(String site, String scientificName) throws MalformedURLException {
        ExternalLink external = new ExternalLink(new URL(site));

        String extendedWebSite = "";
        try {
            extendedWebSite = site + URLEncoder.encode(scientificName, ENCODING);
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, "Problem to close stream", ex);

        }

        try {
            URL url = new URL(extendedWebSite);
            external.setLinkAddress(url);
            Elements elements = getLinkNodeJsoup(extendedWebSite, "img");
            if (!elements.isEmpty()) {
                external.setLinkOk(Boolean.TRUE);
            } else {
                external.setLinkOk(Boolean.FALSE);
            }
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.INFO, "Google", ex.getMessage());
        } catch (MalformedURLException ex) {
            logger.log(Level.INFO, "Google", ex.getMessage());
        } catch (Exception ex) {
            logger.log(Level.INFO, "Google", ex.getMessage());
        }

        return external;
    }

    // used by checking: Google and xx and yyy and zz 
    private Elements getLinkNodeJsoup(String url, String selector) throws Exception {
        int timeOut = 10 * 1000;
        Document document = Jsoup.connect(url).userAgent("Mozilla").timeout(timeOut).get();
        Elements elements = document.select(selector);

        return elements;
    }

}
