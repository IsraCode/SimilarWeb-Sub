package utils;

import common.Common;
import dao.DAO;
import dao.DAOImp;
import dataclass.PageViewData;
import dataclass.SessionData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    //O(n) time complexity, Median of medians https://en.wikipedia.org/wiki/Median_of_medians
    public static Double getMedian(List<Long> values) {
        int size = values.size();
        if (size < 1)
            return 0.0;
        else if (size % 2 == 0) {
            Long lowerHalf = values.get(select(values, 0, size, (size / 2) - 1));
            Long half = values.get(select(values, 0, size, size / 2));
            return (lowerHalf + half) / 2.0;
        } else {
            return values.get(select(values, 0, size, size / 2)).doubleValue();
        }
    }

    public static DAO<String, DAO<String, List<SessionData>>> toSessionMap(List<PageViewData> pageViewDataList) {
        final DAO<String, DAO<String, List<SessionData>>> visitorSiteSessionDAO = new DAOImp<>();
        for (PageViewData currentPageView : pageViewDataList) {
            List<SessionData> sessions =
                    visitorSiteSessionDAO.get(currentPageView.getVisitorId()) == null ?
                            null :
                            visitorSiteSessionDAO.get(currentPageView.getVisitorId()).get(currentPageView.getSiteUrl());
            if (sessions != null && sessions.get(sessions.size() - 1).isInSession(currentPageView))
                sessions.get(sessions.size() - 1).addPageView(currentPageView);
            else {
                List<SessionData> sessionList = visitorSiteSessionDAO
                        .computeIfAbsent(currentPageView.getVisitorId(), k -> new DAOImp<>())
                        .computeIfAbsent(currentPageView.getSiteUrl(), k -> new ArrayList<>());
                SessionData session = new SessionData();
                session.addPageView(currentPageView);
                sessionList.add(session);
            }
        }
        return visitorSiteSessionDAO;
    }

    private static int select(List<Long> values, int startIndex, int endIndex, int targetIndex) {
        if (startIndex >= endIndex || targetIndex < 0 || startIndex + targetIndex >= endIndex) {
            throw new IllegalArgumentException();
        }
        if (endIndex - startIndex < 10) {
            Collections.sort(values.subList(startIndex, endIndex));
            return startIndex + targetIndex;
        }
        int subListSize = endIndex - startIndex;
        int numOfPairs = subListSize / 5;
        for (int i = 0; i < numOfPairs; i++) {
            int subStart = startIndex + i * 5;
            int subEnd = (i + 1 == numOfPairs) ? endIndex : (subStart + 5);
            int pos = select(values, subStart, subEnd, 2);
            Collections.swap(values, pos, startIndex + i);
        }

        int pivotIndex = select(values, startIndex, startIndex + numOfPairs, numOfPairs / 2);

        int splitPoint = triage(values, startIndex, endIndex, pivotIndex);
        int difference = startIndex + targetIndex - splitPoint;
        if (difference > 0) {
            return select(values, splitPoint + 1, endIndex, targetIndex - (splitPoint - startIndex) - 1);
        } else if (difference < 0) {
            return select(values, startIndex, splitPoint, targetIndex);
        }
        return startIndex + targetIndex;
    }

    private static int triage(List<Long> values, int startIndex, int endIndex, int pivotIndex) {
        Long pivotValue = values.get(pivotIndex);
        int leftPointer = startIndex;
        int rightPointer = endIndex;
        while (leftPointer < rightPointer) {
            Long currentValue = values.get(leftPointer);
            int comparison = currentValue.compareTo(pivotValue);
            if (comparison < 0) {
                leftPointer++;
            } else if (comparison > 0) {
                Collections.swap(values, leftPointer, --rightPointer);
            } else {
                while (rightPointer > leftPointer + 1) {
                    assert (values.get(leftPointer).compareTo(pivotValue) == 0);
                    currentValue = values.get(--rightPointer);
                    comparison = currentValue.compareTo(pivotValue);
                    if (comparison <= 0) {
                        if (leftPointer + 1 == rightPointer) {
                            Collections.swap(values, leftPointer, leftPointer + 1);
                            leftPointer++;
                            break;
                        }
                        Collections.swap(values, leftPointer, leftPointer + 1);
                        assert (values.get(leftPointer + 1).compareTo(pivotValue) == 0);
                        Collections.swap(values, leftPointer, rightPointer);
                        leftPointer++;
                        rightPointer++;
                    }
                }
                break;
            }
        }
        assert (values.get(leftPointer).compareTo(pivotValue) == 0);
        return leftPointer;
    }

    public static class CSVReader {

        public static List<PageViewData> read(List<String> filePaths) {
            List<List<PageViewData>> readFiles = filePaths.stream().map(filePath -> {
                List<PageViewData> read;
                try {
                    read = read(filePath);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("File not found: " + filePath);
                } catch (NumberFormatException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return read;
            }).collect(Collectors.toList());
            List<PageViewData> combined;
            combined = combinePageViewDataLists(readFiles);
            return combined;
        }

        private static List<PageViewData> read(String filePath) throws Exception {
            List<PageViewData> pageViewDataList = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        String[] pageView = line.split(Common.CSV_SPLIT_BY);
                        pageViewDataList.add(new PageViewData(pageView));
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Invalid timestamp in line: " + line + ". " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new ArrayIndexOutOfBoundsException("Error parsing page view data on line: " + line + ". " + e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("File not found: " + filePath);
            }
            return pageViewDataList;
        }

        private static List<PageViewData> combinePageViewDataLists(List<List<PageViewData>> lst) {
            List<PageViewData> combinedList = new ArrayList<>();
            for (List<PageViewData> pageViewDataList : lst) {
                combinedList = combine(combinedList, pageViewDataList);
            }
            return combinedList;
        }

        private static List<PageViewData> combine(List<PageViewData> first, List<PageViewData> second) {
            int i = 0;
            int j = 0;
            int size1 = first.size();
            int size2 = second.size();
            int totalSize = size1 + size2;

            List<PageViewData> combinedPageViewData = new ArrayList<>(totalSize);

            while (i < size1 && j < size2) {
                PageViewData firstPageView = first.get(i);
                PageViewData secondPageView = second.get(j);

                if (firstPageView.getTimestamp().isBefore(secondPageView.getTimestamp())) {
                    combinedPageViewData.add(firstPageView);
                    i++;
                } else {
                    combinedPageViewData.add(secondPageView);
                    j++;
                }
            }

            while (i < size1) {
                combinedPageViewData.add(first.get(i));
                i++;
            }

            while (j < size2) {
                combinedPageViewData.add(second.get(j));
                j++;
            }

            return combinedPageViewData;
        }
    }
}
