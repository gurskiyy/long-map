package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LongMapTest {

    private LongMap<String> emptyLongMap;
    private LongMap<UserMock> mapWithCapacity;
    private UserMock userMock1 = new UserMock(1L, "Ted");
    private UserMock userMock2 = new UserMock(2L, "Jack");
    private UserMock userMock3 = new UserMock(3L, "Robert");

    @Before
    public void setUp() {
        emptyLongMap = new LongMapImpl<>();
        //todo set capacity 0
        mapWithCapacity = new LongMapImpl<>(4);
        mapWithCapacity.put(-1, userMock1);
        mapWithCapacity.put(0, userMock2);
        mapWithCapacity.put(1, userMock3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifCapacityLessThanZeroThrowIllegalArgumentException() {
        LongMap<String> map = new LongMapImpl<>(-2);
    }

    @Test
    public void ifPutElementThenCheckSizeMapAndResultPutOfNewElements() {
        String value1 = emptyLongMap.put(-1, "value1");
        String value2 = emptyLongMap.put(0, "value2");
        String value3 = emptyLongMap.put(1, "value3");

        Assert.assertFalse(emptyLongMap.isEmpty());
        Assert.assertEquals(3, emptyLongMap.size());
        Assert.assertNull(value1);
        Assert.assertNull(value2);
        Assert.assertNull(value3);


        Assert.assertFalse(mapWithCapacity.isEmpty());
        Assert.assertEquals(3, mapWithCapacity.size());
        UserMock userMockJava = mapWithCapacity.put(12, new UserMock(1L, "Java"));
        Assert.assertEquals(4, mapWithCapacity.size());
        Assert.assertNull(userMockJava);
    }

    @Test
    public void ifKeyPresentThenValueOverwriteAndReturnOldValue() {
        emptyLongMap.put(-1, "value1");
        emptyLongMap.put(0, "value2");

        String oldValue1 = emptyLongMap.put(-1, "newValue");
        String oldValue2 = emptyLongMap.put(0, "newValue");

        Assert.assertFalse(emptyLongMap.isEmpty());
        Assert.assertEquals(2, emptyLongMap.size());
        Assert.assertEquals("value1", oldValue1);
        Assert.assertEquals("value2", oldValue2);
        Assert.assertEquals("newValue", emptyLongMap.get(-1));
        Assert.assertEquals("newValue", emptyLongMap.get(0));

        UserMock newUserMock = new UserMock(22L, "new");
        UserMock oldValueUserMock = mapWithCapacity.put(-1, newUserMock);

        Assert.assertFalse(mapWithCapacity.isEmpty());
        Assert.assertEquals(3, mapWithCapacity.size());
        Assert.assertEquals(userMock1, oldValueUserMock);
        Assert.assertEquals(newUserMock, mapWithCapacity.get(-1));
    }

    @Test
    public void ifGetByKeyThenReturnValue() {
        emptyLongMap.put(-1, "value1");
        emptyLongMap.put(0, "value2");
        emptyLongMap.put(1, "value3");

        Assert.assertNull(emptyLongMap.get(176386327469372487L));
        Assert.assertEquals(3, emptyLongMap.size());
        Assert.assertEquals("value1", emptyLongMap.get(-1));
        Assert.assertEquals("value2", emptyLongMap.get(0));
        Assert.assertEquals("value3", emptyLongMap.get(1));

        Assert.assertEquals(userMock1, mapWithCapacity.get(-1));
        Assert.assertEquals(userMock2, mapWithCapacity.get(0));
        Assert.assertEquals(userMock3, mapWithCapacity.get(1));
    }

    @Test
    public void ifRemoveByKeyThenReturnValueAndCheckSize() {
        emptyLongMap.put(-1, "value1");
        emptyLongMap.put(0, "value2");
        emptyLongMap.put(1, "value3");

        Assert.assertNull(emptyLongMap.remove(11233124321231L));
        Assert.assertEquals(3, emptyLongMap.size());

        Assert.assertEquals("value1", emptyLongMap.remove(-1));
        Assert.assertEquals("value2", emptyLongMap.remove(0));
        Assert.assertEquals("value3", emptyLongMap.remove(1));
        Assert.assertEquals(0, emptyLongMap.size());

        Assert.assertNull(mapWithCapacity.remove(11233124321231L));
        Assert.assertEquals(3, mapWithCapacity.size());

        Assert.assertEquals(userMock1, mapWithCapacity.remove(-1));
        Assert.assertEquals(userMock2, mapWithCapacity.remove(0));
        Assert.assertEquals(userMock3, mapWithCapacity.remove(1));
        Assert.assertEquals(0, mapWithCapacity.size());
    }

    @Test
    public void ifSizeZeroThenIsEmptyTrueElseFalse() {
        Assert.assertTrue(emptyLongMap.isEmpty());
        Assert.assertFalse(mapWithCapacity.isEmpty());
    }

    @Test
    public void ifContainsKeyThenReturnTrueOrIfNotFalse() {
        emptyLongMap.put(-1, "value1");
        emptyLongMap.put(0, "value2");
        emptyLongMap.put(1, "value3");

        Assert.assertTrue(emptyLongMap.containsKey(-1));
        Assert.assertTrue(emptyLongMap.containsKey(0));
        Assert.assertTrue(emptyLongMap.containsKey(1));
        Assert.assertFalse(emptyLongMap.containsKey(286487247));

        Assert.assertTrue(mapWithCapacity.containsKey(-1));
        Assert.assertTrue(mapWithCapacity.containsKey(0));
        Assert.assertTrue(mapWithCapacity.containsKey(1));
        Assert.assertFalse(mapWithCapacity.containsKey(1382794862734L));
    }

    @Test
    public void ifContainsValueThenReturnTrueOrIfNotFalse() {
        emptyLongMap.put(-1, "value1");
        emptyLongMap.put(0, "value2");
        emptyLongMap.put(1, "value3");

        Assert.assertTrue(emptyLongMap.containsValue("value1"));
        Assert.assertTrue(emptyLongMap.containsValue("value2"));
        Assert.assertTrue(emptyLongMap.containsValue("value3"));
        Assert.assertFalse(emptyLongMap.containsValue("286487247"));

        Assert.assertTrue(mapWithCapacity.containsValue(userMock1));
        Assert.assertTrue(mapWithCapacity.containsValue(userMock2));
        Assert.assertTrue(mapWithCapacity.containsValue(userMock3));
        Assert.assertFalse(mapWithCapacity.containsValue(new UserMock(123L, "sjfhskd")));
    }

    @Test
    public void shouldReturnArrayOfKeysIfTheyPresent() {
        long[] expectedEmptyMapKeys = new long[]{};

        Assert.assertNotNull(emptyLongMap.keys());
        Assert.assertEquals(0, emptyLongMap.keys().length);
        Assert.assertArrayEquals(expectedEmptyMapKeys, emptyLongMap.keys());

        long[] expectedCapacityMapKeys = new long[]{-1, 0, 1};

        Assert.assertNotNull(mapWithCapacity.keys());
        Assert.assertEquals(3, mapWithCapacity.keys().length);
        Assert.assertArrayEquals(expectedCapacityMapKeys, mapWithCapacity.keys());
    }

    @Test
    public void shouldReturnArrayOfValuesIfTheyPresent() {
        String[] expectedEmptyMapValues = {};

        Object[] values = emptyLongMap.values();

        Assert.assertNotNull(values);
        Assert.assertEquals(0, values.length);
        Assert.assertArrayEquals(expectedEmptyMapValues, values);

        UserMock[] expectedUserMocks = new UserMock[]{userMock1, userMock2, userMock3};

        Object[] peopleValues = mapWithCapacity.values();

        Assert.assertNotNull(peopleValues);
        Assert.assertEquals(3, peopleValues.length);
        Assert.assertArrayEquals(expectedUserMocks, peopleValues);
    }

    @Test
    public void shouldReturnSizeMap() {
        Assert.assertEquals(0, emptyLongMap.size());
        Assert.assertEquals(3, mapWithCapacity.size());

        mapWithCapacity.put(12, new UserMock(123L, "Karl"));
        mapWithCapacity.put(13, new UserMock(123L, "Karl"));

        Assert.assertEquals(5, mapWithCapacity.size());
    }

    @Test
    public void ifClearThenSizeShouldBeZeroAllElementsDeleted() {
        emptyLongMap.put(-1, "value1");
        emptyLongMap.put(0, "value2");
        emptyLongMap.put(1, "value3");

        emptyLongMap.clear();
        mapWithCapacity.clear();

        Assert.assertEquals(0, emptyLongMap.size());
        Assert.assertNull(emptyLongMap.get(-1));
        Assert.assertNull(emptyLongMap.get(0));
        Assert.assertNull(emptyLongMap.get(1));

        Assert.assertEquals(0, mapWithCapacity.size());
        Assert.assertNull(mapWithCapacity.get(-1));
        Assert.assertNull(mapWithCapacity.get(0));
        Assert.assertNull(mapWithCapacity.get(1));
    }
}