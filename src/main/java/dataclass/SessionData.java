package dataclass;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static common.Common.SESSION_MAX_SECONDS_GAP;

public class SessionData {
    private final List<PageViewData> pageViews = new ArrayList<>();

    public void addPageView(PageViewData pageView) {
        pageViews.add(pageView);
    }

    public Long getSessionLength() {
        // If there is only one-page view, the session length is 0, same with no page views
        if (pageViews.size() < 2)
            return 0L;

        return Duration.between(pageViews.get(0).getTimestamp(),
                        pageViews.get(pageViews.size() - 1)
                                .getTimestamp())
                .getSeconds();
    }

    public Boolean isInSession(PageViewData pageView) {
        if (pageViews.isEmpty())
            return false;
        Instant startGap = pageViews.get(0).getTimestamp().minusSeconds(SESSION_MAX_SECONDS_GAP);
        Instant endGap = pageViews.get(pageViews.size() - 1).getTimestamp().plusSeconds(SESSION_MAX_SECONDS_GAP);
        Instant pageViewTimestamp = pageView.getTimestamp();

        return (pageViewTimestamp.isAfter(startGap) && pageViewTimestamp.isBefore(endGap)) || pageViewTimestamp.equals(startGap) || pageViewTimestamp.equals(endGap);
    }

}
