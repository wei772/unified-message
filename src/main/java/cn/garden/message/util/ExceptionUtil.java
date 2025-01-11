package cn.garden.message.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常工具
 */
public class ExceptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionUtil.class);

    public static BusinessException createDefaultException(String message) {
        return createDefaultException(message, null);
    }

    public static BusinessException createDefaultException(String message, Exception exception) {
        System.out.println();
        LOGGER.warn(message, exception);
        throw new RuntimeException(message, exception);
    }
}
