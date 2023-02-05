package api;

import cache.SiteUrlToMedianCache;
import dao.VisitorUrlSessionsDAO;
import dataclass.SessionData;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.getMedian;

public class MedianSessionLengthQuery implements Query<Double> {

    private final String siteUrl;

    public MedianSessionLengthQuery(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @Override
    public Double apply() {
        return SiteUrlToMedianCache.getInstance().computeIfAbsent(siteUrl, url -> {
            List<List<SessionData>> sessionsList = VisitorUrlSessionsDAO.getInstance().getByUrl(url);
            ArrayList<Long> sessionLengths = new ArrayList<>();
            sessionsList.forEach(sessions ->
                    sessions.forEach(session ->
                            sessionLengths.add(session.getSessionLength())));
            return getMedian(sessionLengths);
        });
    }

    @Override
    public void printAns() {
        System.out.println("Median session length for site " + siteUrl + " = " + apply());
    }
}
