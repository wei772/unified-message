package cn.garden.message.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liwei
 */
public class TestUtil {

    private static String testPrefix() {
        return "@Test";
    }

    public static String generateTestString() {
        return testPrefix() + RandomStringUtil.randomAlphanumeric(24);
    }

    public static boolean isTestData(String data) {
        if (StringUtils.isEmpty(data)) {
            return false;
        }
        return data.contains(testPrefix());
    }


}
