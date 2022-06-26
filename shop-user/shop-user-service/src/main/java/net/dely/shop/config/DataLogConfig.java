package net.dely.shop.config;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 不处理脱敏
 * User: dengjiaxin
 * Date: 2022/6/26
 * Time: 19:00
 * @author admin
 */
public class DataLogConfig extends MessageConverter {

    private static Logger logger = LoggerFactory.getLogger(DataLogConfig.class);

    @Override
    public String convert(ILoggingEvent event){
        String logMsg = event.getFormattedMessage();
        return logMsg;
    }
}
