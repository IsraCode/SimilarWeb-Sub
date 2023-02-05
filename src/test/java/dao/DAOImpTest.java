package dao;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DAOImpTest {

    private final String key1 = "key1";
    private final String key2 = "key2";

    @Test
    void testPutAndGet() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        daoImp.put(key1, 1);
        daoImp.put(key2, 2);

        assertEquals(1, daoImp.get(key1).intValue());
        assertEquals(2, daoImp.get(key2).intValue());
    }

    @Test
    void testComputeIfAbsent() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();

        Integer value = daoImp.computeIfAbsent(key1, k -> 1);
        assertEquals(1, value.intValue());

        value = daoImp.computeIfAbsent(key1, k -> 2);
        assertEquals(1, value.intValue());
    }

    @Test
    void testGetKeys() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        daoImp.put(key1, 1);
        daoImp.put(key2, 2);

        Set<String> keys = daoImp.getKeys();
        assertEquals(2, keys.size());
        assertTrue(keys.contains(key1));
        assertTrue(keys.contains(key2));
    }

    @Test
    void testGetValues() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        daoImp.put(key1, 1);
        daoImp.put(key2, 2);

        assertTrue(daoImp.getValues().contains(1));
        assertTrue(daoImp.getValues().contains(2));
    }

    @Test
    void testGetEntries() {

        DAOImp<String, Integer> daoImp = new DAOImp<>();
        daoImp.put(key1, 1);
        daoImp.put(key2, 2);

        Set<Map.Entry<String, Integer>> entries = daoImp.getEntries();
        assertEquals(2, entries.size());
        Map<String, Integer> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        assertTrue(map.containsKey(key1));
        assertTrue(map.containsKey(key2));
        assertEquals(1, map.get(key1).intValue());
        assertEquals(2, map.get(key2).intValue());
    }

    @Test
    void testGetNull() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        assertNull(daoImp.get(key1));
    }

    @Test
    void testPutNull() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        daoImp.put(key1, null);
        assertNull(daoImp.get(key1));
    }

    @Test
    void testGetKeysEmpty() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        Set<String> keys = daoImp.getKeys();
        assertNotNull(keys);
        assertTrue(keys.isEmpty());
    }

    @Test
    void testGetValuesEmpty() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        assertTrue(daoImp.getValues().isEmpty());
    }

    @Test
    void testGetEntriesEmpty() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        assertTrue(daoImp.getEntries().isEmpty());
    }

    @Test
    void testPutDuplicate() {
        DAOImp<String, Integer> daoImp = new DAOImp<>();
        daoImp.put(key1, 1);
        daoImp.put(key1, 2);
        assertEquals(2, daoImp.get(key1).intValue());
    }
}


