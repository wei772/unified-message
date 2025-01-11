package cn.garden.message.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author liwei
 */
public class IdGeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdGeneratorTest.class);

    @Test
    public void generateRandom() {
        String id = IdGenerator.generateRandom();

        LOGGER.info("生成的Id:{}", id);
        assertNotNull(id);
    }
}
