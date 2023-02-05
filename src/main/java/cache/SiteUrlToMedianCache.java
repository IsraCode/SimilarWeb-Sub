package cache;

public class SiteUrlToMedianCache extends CacheImp<String, Double> {

    private static final SiteUrlToMedianCache instance = new SiteUrlToMedianCache();

    private SiteUrlToMedianCache() {
    }

    public static SiteUrlToMedianCache getInstance() {
        return instance;
    }

}
