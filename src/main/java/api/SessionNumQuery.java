package api;

import cache.SiteUrlToNumSessionsCache;
import dao.VisitorUrlSessionsDAO;
import dataclass.SessionData;

import java.util.List;

public class SessionNumQuery implements Query<Integer> {

    private final String siteUrl;

    public SessionNumQuery(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Integer apply() {
        return SiteUrlToNumSessionsCache.getInstance().computeIfAbsent(siteUrl, url -> {
            int count = 0;
            for (List<SessionData> session : VisitorUrlSessionsDAO.getInstance().getByUrl(url))
                count += session.size();
            return count;
        });

    }

    public void printAns() {
        System.out.println("Num sessions for site " + siteUrl + " = " + apply());
    }
}
