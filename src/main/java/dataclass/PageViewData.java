package dataclass;

import java.time.Instant;

public class PageViewData {
    private final String visitorId;
    private final String siteUrl;
    private final String pageViewUrl;
    private final Instant timestamp;

    public PageViewData(String visitorId, String siteUrl, String pageViewUrl, long timestamp) {
        this.visitorId = visitorId;
        this.siteUrl = siteUrl;
        this.pageViewUrl = pageViewUrl;
        this.timestamp = Instant.ofEpochSecond(timestamp);
    }

    public PageViewData(String[] pageView) {
        visitorId = pageView[0];
        siteUrl = pageView[1];
        pageViewUrl = pageView[2];
        try {
            timestamp = Instant.ofEpochSecond(Long.parseLong(pageView[3]));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error parsing timestamp: " + pageView[3] + ". " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageViewData that = (PageViewData) o;

        if (!visitorId.equals(that.visitorId)) return false;
        if (!siteUrl.equals(that.siteUrl)) return false;
        if (!pageViewUrl.equals(that.pageViewUrl)) return false;
        return timestamp.equals(that.timestamp);
    }

    public String getPageViewUrl() {
        return pageViewUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getVisitorId() {
        return visitorId;
    }

}