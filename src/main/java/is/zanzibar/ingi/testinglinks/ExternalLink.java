package is.zanzibar.ingi.testinglinks;

import java.net.URL;

public class ExternalLink {

    private boolean linkOk;

    private URL linkAddress;

    public ExternalLink(URL linkAddress) {
        this.linkOk = Boolean.FALSE;
        this.linkAddress = linkAddress;
    }
    
    public ExternalLink(boolean linkOk) {
        this.linkOk = linkOk;
    }

    public boolean isLinkOk() {
        return linkOk;
    }

    public void setLinkOk(boolean linkOk) {
        this.linkOk = linkOk;
    }

    public URL getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(URL linkAddress) {
        this.linkAddress = linkAddress;
    }

    @Override
    public String toString() {
        return this.getLinkAddress()+" :: "+this.isLinkOk();
    }
    
    

}
