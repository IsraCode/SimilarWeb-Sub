package cache;

public class SiteUrlToNumSessionsCache extends CacheImp<String, Integer> {

    private static final SiteUrlToNumSessionsCache instance = new SiteUrlToNumSessionsCache();

    private SiteUrlToNumSessionsCache() {
    }

    public static SiteUrlToNumSessionsCache getInstance() {
        return instance;
    }

}
