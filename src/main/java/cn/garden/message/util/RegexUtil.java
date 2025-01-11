package cn.garden.message.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author liwei
 */
public class RegexUtil {

    /**
     * 是否是数字
     */
    public static boolean isNumber(String mobile) {
        if (StringUtils.isNotEmpty(mobile)) {
            return Pattern.matches("^[0-9]*$", mobile);
        }
        return false;
    }

}
