package dao;

import dataclass.PageViewData;
import dataclass.SessionData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class VisitorUrlSessionsDAOTest {

    private VisitorUrlSessionsDAO dao;

    @BeforeEach
    void setUp() {
        dao = VisitorUrlSessionsDAO.getInstance();
        List<SessionData> sessions1 = new ArrayList<>(Arrays.asList(
                new SessionData(),
                new SessionData()
        ));


        PageViewData pageViewData1 = new PageViewData(
                "visitor1",
                "www.test1.com",
                "www.test1.com/page1",
                Instant.parse("2020-01-01T00:00:00Z").toEpochMilli() / 1000
        );

        PageViewData pageViewData2 = new PageViewData(
                "visitor1",
                "www.test1.com",
                "www.test1.com/page2",
                Instant.parse("2020-01-01T00:00:10Z").toEpochMilli() / 1000
        );

        sessions1.get(0).addPageView(pageViewData1);
        sessions1.get(0).addPageView(pageViewData2);

        List<SessionData> sessions2 = new ArrayList<>(Arrays.asList(
                new SessionData(),
                new SessionData()
        ));
        dao.put(pageViewData1.getVisitorId(), pageViewData1.getSiteUrl(), sessions1);
        dao.put(pageViewData1.getVisitorId(), pageViewData2.getSiteUrl(), sessions2);
    }

    @Test
    void testGetByUrl() {
        List<List<SessionData>> sessionsByUrl1 = dao.getByUrl("www.test1.com");
        Assertions.assertEquals(1, sessionsByUrl1.size());
        Assertions.assertEquals(2, sessionsByUrl1.get(0).size());

        List<List<SessionData>> sessionsByUrl2 = dao.getByUrl("www.test2.com");
        Assertions.assertEquals(0, sessionsByUrl2.size());

    }

    @Test
    void testGetUrls() {
        List<String> urls = dao.getUrls("visitor1");
        Assertions.assertEquals(1, urls.size());
        Assertions.assertTrue(urls.contains("www.test1.com"));
        Assertions.assertFalse(urls.contains("www.test2.com"));
    }

}