package dataclass;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PageViewDataTest {

    @Test
    public void testPageViewDataArrayConstructor() {
        String[] pageView = {"visitor1", "www.example.com", "www.example.com/home", "1598694400"};

        PageViewData pageViewData = new PageViewData(pageView);

        assertEquals("visitor1", pageViewData.getVisitorId());
        assertEquals("www.example.com", pageViewData.getSiteUrl());
        assertEquals("www.example.com/home", pageViewData.getPageViewUrl());
        assertEquals(Instant.ofEpochSecond(1598694400L), pageViewData.getTimestamp());
    }

    @Test
    public void testPageViewDataConstructorWithParams() {
        PageViewData pageViewData = new PageViewData("visitor1", "site1", "pageView1", 1609459200);
        assertEquals("visitor1", pageViewData.getVisitorId());
        assertEquals("site1", pageViewData.getSiteUrl());
        assertEquals("pageView1", pageViewData.getPageViewUrl());
        assertEquals(Instant.ofEpochSecond(1609459200), pageViewData.getTimestamp());
    }

    @Test
    public void testPageViewDataEquals() {
        PageViewData pageViewData1 = new PageViewData("visitor1", "www.example.com", "www.example.com/home", 1598694400L);
        PageViewData pageViewData2 = new PageViewData("visitor1", "www.example.com", "www.example.com/home", 1598694400L);
        PageViewData pageViewData3 = new PageViewData("visitor2", "www.example.com", "www.example.com/home", 1598694400L);
        PageViewData pageViewData4 = new PageViewData("visitor1", "www.example.net", "www.example.com/home", 1598694400L);
        PageViewData pageViewData5 = new PageViewData("visitor1", "www.example.com", "www.example.net/home", 1598694400L);
        PageViewData pageViewData6 = new PageViewData("visitor1", "www.example.com", "www.example.com/home", 1598694401L);

        assertEquals(pageViewData1, pageViewData2);
        assertNotEquals(pageViewData1, pageViewData3);
        assertNotEquals(pageViewData1, pageViewData4);
        assertNotEquals(pageViewData1, pageViewData5);
        assertNotEquals(pageViewData1, pageViewData6);
    }

    @Test
    public void testNotEquals() {
        String visitorId = "visitor-1";
        String siteUrl = "www.example.com";
        String pageViewUrl = "www.example.com/home";
        long timestamp1 = 1600000000;
        long timestamp2 = 1600000001;

        PageViewData pageViewData1 = new PageViewData(visitorId, siteUrl, pageViewUrl, timestamp1);
        PageViewData pageViewData2 = new PageViewData(visitorId, siteUrl, pageViewUrl, timestamp2);

        Assertions.assertNotEquals(pageViewData1, pageViewData2);
        Assertions.assertNotEquals(pageViewData2, pageViewData1);
    }

}
