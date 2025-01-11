package cn.garden.message.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author liwei
 */
public class RandomStringUtil {

    public static String randomAlphanumeric(final int count) {
        return RandomStringUtils.secure().nextAlphanumeric(count);
    }
}
