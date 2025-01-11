package cn.garden.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 异常1
 * Caused by: java.lang.NoClassDefFoundError: org/aspectj/lang/annotation/Pointcut
 * 删除 @EnableAspectJAutoProxy
 *
 * @author liwei
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.garden.message"})
public class Bootstrap {

    /**
     *
     */
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
