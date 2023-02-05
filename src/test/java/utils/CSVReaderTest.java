package utils;

import dataclass.PageViewData;
import org.junit.jupiter.api.Test;
import utils.Utils.CSVReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVReaderTest {

    private static final PageViewData VISITOR_1 = new PageViewData("visitor1", "site1.com", "page1.html", 1234567890L);
    private static final PageViewData VISITOR_2 = new PageViewData("visitor2", "site2.com", "page2.html", 2345678901L);
    private static final PageViewData VISITOR_3 = new PageViewData("visitor3", "site3.com", "page3.html", 3456789012L);

    @Test
    void testReadInvalidFile() {
        List<String> filePath = new ArrayList<>();
        filePath.add("nonexistentfile.csv");

        assertThrows(RuntimeException.class, () -> CSVReader.read(filePath));
    }

    @Test
    void testReadInvalidLineDataExpectNumberFormatException() throws IOException {
        String filePath = "testData.csv";
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        String invalidLineData = "visitor1,site1.com,page1.html,invalidTimestamp";

        // create the testData.csv file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(invalidLineData);
            bw.newLine();
        }

        assertThrows(NumberFormatException.class, () -> CSVReader.read(filePaths));

        // delete the testData.csv file
        new File(filePath).delete();
    }

    @Test
    void testReadValidFileExpectValidData() throws IOException {
        String filePath = "testData.csv";
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        List<PageViewData> expectedPageViewDataList = new ArrayList<>();
        expectedPageViewDataList.add(VISITOR_1);
        expectedPageViewDataList.add(VISITOR_2);
        expectedPageViewDataList.add(VISITOR_3);


        // create the csv file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(VISITOR_1.getVisitorId() + "," + VISITOR_1.getSiteUrl() + "," + VISITOR_1.getPageViewUrl() + "," + VISITOR_1.getTimestamp().toEpochMilli() / 1000);
            bw.newLine();
            bw.write(VISITOR_2.getVisitorId() + "," + VISITOR_2.getSiteUrl() + "," + VISITOR_2.getPageViewUrl() + "," + VISITOR_2.getTimestamp().toEpochMilli() / 1000);
            bw.newLine();
            bw.write(VISITOR_3.getVisitorId() + "," + VISITOR_3.getSiteUrl() + "," + VISITOR_3.getPageViewUrl() + "," + VISITOR_3.getTimestamp().toEpochMilli() / 1000);
            bw.newLine();
        }

        List<PageViewData> actualPageViewDataList = CSVReader.read(filePaths);
        assertEquals(expectedPageViewDataList, actualPageViewDataList);

        // delete the csv file
        new File(filePath).delete();
    }

}