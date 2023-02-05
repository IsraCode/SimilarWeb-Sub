package api;

import cache.SiteUrlToMedianCache;
import cache.SiteUrlToNumSessionsCache;
import cache.VisitorIdToNumUniqueSitesCache;
import dao.VisitorUrlSessionsDAO;
import dataclass.SessionData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class APITest {
    private final String visitorId = "visitor_test";
    private final String visitorIdNotExist = "visitor_not_exist";
    private final String siteUrl = "www.test.com";
    private final String siteUrlNotExist = "www.not_exist.com";


    @Test
    void testSessionNumQuery() {
        int numSessions = 10;
        SiteUrlToNumSessionsCache.getInstance().put(siteUrl, numSessions);
        SessionNumQuery query = new SessionNumQuery(siteUrl);
        assertEquals(numSessions, query.apply());
    }

    @Test
    void testSessionNumQueryNotExist() {
        int expectedAns = 10;
        SiteUrlToNumSessionsCache.getInstance().put(siteUrlNotExist, expectedAns);
        SessionNumQuery query = new SessionNumQuery(siteUrlNotExist);
        assertEquals(expectedAns, query.apply());
    }

    @Test
    void testMedianSessionLengthQuery() {
        double expectedAns = 10.0;
        SiteUrlToMedianCache.getInstance().put(siteUrl, expectedAns);
        MedianSessionLengthQuery query = new MedianSessionLengthQuery(siteUrl);
        assertEquals(expectedAns, query.apply());
    }

    @Test
    void testMedianSessionLengthQueryNotExist() {
        MedianSessionLengthQuery query = new MedianSessionLengthQuery(siteUrlNotExist);
        assertEquals(0.0, query.apply());
    }

    @Test
    void testUniqueSessionCountQuery() {
        SessionData sessionData = new SessionData();
        List<SessionData> sessions = List.of(sessionData);
        int expectedAns = sessions.size();
        VisitorIdToNumUniqueSitesCache.getInstance().put(siteUrl, expectedAns);
        VisitorUrlSessionsDAO.getInstance().put(visitorId, siteUrl, sessions);
        UniqueSessionCountQuery query = new UniqueSessionCountQuery(visitorId);
        assertEquals(expectedAns, query.apply());
    }

    @Test
    void testUniqueSessionCountQueryNotExist() {
        UniqueSessionCountQuery query = new UniqueSessionCountQuery(visitorIdNotExist);
        assertEquals(0, query.apply());
    }

    @Test
    void testHandler() {
        QueryProcessor queryProcessor = QueryProcessor.getInstance();

        Query numSessionsQuery = queryProcessor.handler("Num_sessions", "www.s_1.com");
        assertTrue(numSessionsQuery instanceof SessionNumQuery);

        Query medianSessionLengthQuery = queryProcessor.handler("Median_session_length", "www.s_1.com");
        assertTrue(medianSessionLengthQuery instanceof MedianSessionLengthQuery);

        Query numUniqueVisitedSitesQuery = queryProcessor.handler("Num_unique_visited_sites", "visitor_1");
        assertTrue(numUniqueVisitedSitesQuery instanceof UniqueSessionCountQuery);

       assertThrows(IllegalArgumentException.class , () -> queryProcessor.handler("Unknown_query", "parameter"));
    }
}
