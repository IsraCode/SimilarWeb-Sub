package cache;

public class VisitorIdToNumUniqueSitesCache extends CacheImp<String, Integer> {

    private static final VisitorIdToNumUniqueSitesCache instance = new VisitorIdToNumUniqueSitesCache();

    private VisitorIdToNumUniqueSitesCache() {
    }

    public static VisitorIdToNumUniqueSitesCache getInstance() {
        return instance;
    }

}
