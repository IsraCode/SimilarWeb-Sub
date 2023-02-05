package dao;

import dataclass.PageViewData;
import dataclass.SessionData;
import utils.Utils;
import utils.Utils.CSVReader;

import java.util.ArrayList;
import java.util.List;

import static common.Common.INPUT_PATHS;

public class VisitorUrlSessionsDAO {

    private static VisitorUrlSessionsDAO single_instance = null;

    private VisitorUrlSessionsDAO() {
    }

    public static VisitorUrlSessionsDAO getInstance() {
        if (single_instance == null) {
            single_instance = new VisitorUrlSessionsDAO();
            List<PageViewData> pageViewDataListAll = CSVReader.read(INPUT_PATHS);
            DAO<String, DAO<String, List<SessionData>>> sessionMap = Utils.toSessionMap(pageViewDataListAll);
            sessionMap.getEntries().forEach(visitorToDAO ->
                    visitorToDAO.getValue().getEntries()
                            .forEach(urlToSessionList ->
                                    single_instance.put(visitorToDAO.getKey(),
                                            urlToSessionList.getKey(),
                                            urlToSessionList.getValue())));
        }
        return single_instance;
    }

    private final DAO<String, DAO<String, List<SessionData>>> visitorToUrlValMap = new DAOImp<>();
    private final DAO<String, DAO<String, List<SessionData>>> urlToVisitorValMap = new DAOImp<>();

    public List<List<SessionData>> getByUrl(String url) {
        try {
            return new ArrayList<>(urlToVisitorValMap.get(url).getValues());
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }

    }

    public List<String> getUrls(String visitor) {
        try {
            return new ArrayList<>(visitorToUrlValMap.get(visitor).getKeys());
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    public void put(String visitor, String url, List<SessionData> value) {
        visitorToUrlValMap.computeIfAbsent(visitor, k -> new DAOImp<>())
                .put(url, value);
        urlToVisitorValMap.computeIfAbsent(url, k -> new DAOImp<>())
                .put(visitor, value);
    }

}
