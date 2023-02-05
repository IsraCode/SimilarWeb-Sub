package utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedianTest {

    private void shuffle(List<Long> values) {
        for (int i = 0; i < values.size(); i++) {
            int randomIndex = (int) (Math.random() * values.size());
            Long temp = values.get(i);
            values.set(i, values.get(randomIndex));
            values.set(randomIndex, temp);
        }
    }

    @Test
    void testMedianEmpty() {
        List<Long> values = new ArrayList<>();
        Utils.getMedian(values);
        assertEquals(0.0, Utils.getMedian(values));
    }

    @Test
    void testMedian() {
        List<Long> values = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            values.add((long) i);
            shuffle(values);
            assertEquals(i / 2.0 + 0.5, Utils.getMedian(values));
        }
    }

}
