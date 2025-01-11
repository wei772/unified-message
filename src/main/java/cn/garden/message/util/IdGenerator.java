package cn.garden.message.util;

import java.util.UUID;

public class IdGenerator {

    /**
     * 生成随机字符串Id
     */
    public static String generateRandom() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
