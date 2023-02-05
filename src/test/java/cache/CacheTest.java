package cache;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    private final double delta = 0.0001;

    @Test
    public void testCacheImp() {
        CacheImp<String, String> cache = new CacheImp<>();

        cache.put("key", "value");
        Assert.assertEquals("value", cache.get("key"));
    }

    @Test
    public void testSiteUrlToMedianCache() {
        SiteUrlToMedianCache.getInstance().put("www.s_1.com", 1.0);
        Assert.assertEquals(1.0,
                SiteUrlToMedianCache.getInstance().get("www.s_1.com"),
                delta);
    }

    @Test
    public void testSiteUrlToNumSessionsCache() {
        SiteUrlToNumSessionsCache.getInstance().put("www.s_1.com", 1);
        Assert.assertEquals(1,
                SiteUrlToNumSessionsCache.getInstance().get("www.s_1.com"),
                delta);
    }

    @Test
    public void testVisitorIdToNumUniqueSitesCache() {
        VisitorIdToNumUniqueSitesCache.getInstance().put("visitor_1", 1);
        Assert.assertEquals(1,
                VisitorIdToNumUniqueSitesCache.getInstance().get("visitor_1"),
                delta);
    }
}
