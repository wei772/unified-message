package cn.garden.message.config;

import cn.garden.message.util.ResponseBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liwei
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler()
    @ResponseBody
    ResponseBase handleException(Exception ex) {
        LOGGER.warn("未处理异常", ex);
        ResponseBase responseBase = new ResponseBase();
        responseBase.error(ex.getMessage());
        return responseBase;
    }
}
