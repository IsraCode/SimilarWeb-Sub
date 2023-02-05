package dataclass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static common.Common.SESSION_MAX_SECONDS_GAP;

class SessionDataTest {

    private static final String VISITOR_ID = "visitor123";
    private static final String SITE_URL = "www.example.com";
    private static final String PAGE_VIEW_URL = "/index.html";
    private final long seconds = 123456789;

    @Test
    void testGetSessionLengthEmptyList() {
        SessionData sessionData = new SessionData();

        Long result = sessionData.getSessionLength();

        Assertions.assertEquals(0L, result.longValue());
    }

    @Test
    void testGetSessionLengthSinglePageView() {
        SessionData sessionData = new SessionData();
        Instant timestamp = Instant.ofEpochSecond(seconds);
        PageViewData pageView = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp.getEpochSecond());
        sessionData.addPageView(pageView);

        Long result = sessionData.getSessionLength();

        Assertions.assertEquals(0L, result.longValue());
    }

    @Test
    void testGetSessionLengthMultiplePageViews() {
        SessionData sessionData = new SessionData();
        Instant timestamp1 = Instant.ofEpochSecond(seconds);
        PageViewData pageView1 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp1.getEpochSecond());
        sessionData.addPageView(pageView1);

        Instant timestamp2 = Instant.ofEpochSecond(seconds + 1);
        PageViewData pageView2 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp2.getEpochSecond());
        sessionData.addPageView(pageView2);

        Long result = sessionData.getSessionLength();

        Assertions.assertEquals(1L, result.longValue());
    }

    @Test
    void testIsInSessionEmptyList() {
        SessionData sessionData = new SessionData();
        Instant timestamp = Instant.ofEpochSecond(seconds);
        PageViewData pageView = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp.getEpochSecond());

        Boolean result = sessionData.isInSession(pageView);

        Assertions.assertFalse(result);
    }

    @Test
    void testIsInSessionSinglePageView() {
        SessionData sessionData = new SessionData();
        Instant timestamp1 = Instant.ofEpochSecond(seconds);
        PageViewData pageView1 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp1.getEpochSecond());
        sessionData.addPageView(pageView1);

        Instant timestamp2 = Instant.ofEpochSecond(seconds + 1);
        PageViewData pageView2 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp2.getEpochSecond());

        Boolean result = sessionData.isInSession(pageView2);

        Assertions.assertTrue(result);

        Instant timestamp3 = Instant.ofEpochSecond(seconds);
        PageViewData pageView3 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp3.getEpochSecond());

        result = sessionData.isInSession(pageView3);

        Assertions.assertTrue(result);

        Instant timestamp4 = Instant.ofEpochSecond(seconds + SESSION_MAX_SECONDS_GAP);
        PageViewData pageView4 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp4.getEpochSecond());

        result = sessionData.isInSession(pageView4);

        Assertions.assertTrue(result);

        Instant timestamp5 = Instant.ofEpochSecond(seconds - SESSION_MAX_SECONDS_GAP);
        PageViewData pageView5 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp5.getEpochSecond());

        result = sessionData.isInSession(pageView5);

        Assertions.assertTrue(result);
    }

    @Test
    void testIsInSessionMultiplePageViews() {
        SessionData sessionData = new SessionData();

        Instant timestamp1 = Instant.ofEpochSecond(seconds);
        PageViewData pageView1 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp1.getEpochSecond());
        sessionData.addPageView(pageView1);

        Instant timestamp2 = Instant.ofEpochSecond(seconds + 1);
        PageViewData pageView2 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp2.getEpochSecond());
        sessionData.addPageView(pageView2);

        Instant timestamp3 = Instant.ofEpochSecond(seconds + 2);
        PageViewData pageView3 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp3.getEpochSecond());

        Boolean result = sessionData.isInSession(pageView3);

        Assertions.assertTrue(result);

        Instant timestamp4 = Instant.ofEpochSecond(seconds + SESSION_MAX_SECONDS_GAP);
        PageViewData pageView4 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp4.getEpochSecond());

        result = sessionData.isInSession(pageView4);

        Assertions.assertTrue(result);

        Instant timestamp5 = Instant.ofEpochSecond(seconds - SESSION_MAX_SECONDS_GAP);
        PageViewData pageView5 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp5.getEpochSecond());

        result = sessionData.isInSession(pageView5);

        Assertions.assertTrue(result);

        Instant timestamp6 = Instant.ofEpochSecond(seconds + (SESSION_MAX_SECONDS_GAP + 2));
        PageViewData pageView6 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp6.getEpochSecond());

        result = sessionData.isInSession(pageView6);

        Assertions.assertFalse(result);

        Instant timestamp7 = Instant.ofEpochSecond(seconds - (SESSION_MAX_SECONDS_GAP + 2));
        PageViewData pageView7 = new PageViewData(VISITOR_ID, SITE_URL, PAGE_VIEW_URL, timestamp7.getEpochSecond());

        result = sessionData.isInSession(pageView7);

        Assertions.assertFalse(result);
    }
}
