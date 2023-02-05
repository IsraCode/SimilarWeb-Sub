package api;

import cache.VisitorIdToNumUniqueSitesCache;
import dao.VisitorUrlSessionsDAO;

import java.util.List;

public class UniqueSessionCountQuery implements Query<Integer> {

    private final String visitorId;

    public UniqueSessionCountQuery(String visitorId) {
        this.visitorId = visitorId;
    }

    public Integer apply() {
        return VisitorIdToNumUniqueSitesCache.getInstance().computeIfAbsent(visitorId, id -> {
            List<String> siteUrlList = VisitorUrlSessionsDAO.getInstance().getUrls(id);
            return siteUrlList.size();
        });
    }

    public void printAns() {
        System.out.println("Num of unique sites for " + visitorId + " = " + apply());
    }
}
