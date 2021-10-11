package fridgy.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fridgy.testutil.TestUtil;

public class UiUtilTest {

    @Test
    public void isTruncated() {
        assertEquals(TestUtil.createString('a', 100) + "...",
                UiUtil.truncateText(TestUtil.createString('a', 200), 100));
        assertEquals(TestUtil.createString('b', 50) + "...",
                UiUtil.truncateText(TestUtil.createString('b', 200), 50));
        assertEquals(TestUtil.createString('c', 10) + "...",
                UiUtil.truncateText(TestUtil.createString('c', 200), 10));
        assertEquals(TestUtil.createString('d', 20) + "...",
                UiUtil.truncateText(TestUtil.createString('d', 200), 20));
    }

    @Test
    public void isNotTruncated() {
        assertEquals(TestUtil.createString('a', 100),
                UiUtil.truncateText(TestUtil.createString('a', 100), 100));
        assertEquals(TestUtil.createString('b', 50) ,
                UiUtil.truncateText(TestUtil.createString('b', 50), 200));
        assertEquals(TestUtil.createString('c', 10) ,
                UiUtil.truncateText(TestUtil.createString('c', 10), 200));
        assertEquals(TestUtil.createString('d', 20) ,
                UiUtil.truncateText(TestUtil.createString('d', 20), 100));
    }
}
