package net.dely.shop.config;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import net.dely.shop.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * 处理日志脱敏配置.
 * User: dengjiaxin
 * Date: 2022/6/26
 * Time: 16:27
 * @author admin
 */
public class SensitiveDataLogConfig extends MessageConverter {

    private static Logger logger = LoggerFactory.getLogger(SensitiveDataLogConfig.class);
    /**
     * 脱敏开关
     */
    private static boolean SENSITIVE_FLAG = true;

    @Override
    public String convert(ILoggingEvent event){
        String logMsg = event.getFormattedMessage();
        return invokeMsg(logMsg);
    }
    /**
     * 处理日志字符串，返回脱敏后的字符串
     * @param oriMsg
     * @return
     */
    public String invokeMsg(final String oriMsg) {
        if(SENSITIVE_FLAG) {
            return getMessageByPattern(oriMsg);
        }
        return oriMsg;
    }
    /**
     * 通过正则来处理日志脱敏
     * @param inputMessage
     * @return
     */
    private String getMessageByPattern(String inputMessage){
        try{
            /******************************身份证号正则开始*****************************/

            /******************************手机号正则开始*****************************/
            String phone = "^$|^[1][3,4,5,6,7,8,9][0-9]{9}$";
            inputMessage = matchAndReplace(inputMessage, phone);

            /******************************邮箱正则开始*****************************/
            String email = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
            inputMessage = matchAndReplace(inputMessage, email);

            /******************************银行卡号正则开始*****************************/

        }catch(Exception e){
            logger.error("日志脱敏错误:{}",e.getMessage());
            return "";
        }
        return inputMessage;
    }

    /**
     * 脱敏处理
     * @param inputMessage 内容
     * @param p 正则
     * @return
     */
    private static String matchAndReplace(String inputMessage,String p){
        if (StringUtils.isNotEmpty(inputMessage)&&Pattern.matches(p, inputMessage)) {
            if(inputMessage.length() <= Constant.ELEVEN){
                inputMessage = inputMessage.substring(Constant.ZERO,Constant.TWO)+"****"+inputMessage.substring(7, inputMessage.length());
            }else{
                inputMessage = inputMessage.substring(Constant.ZERO,Constant.TWO)+"****"+ StringUtils.substring(inputMessage, inputMessage.length()-Constant.FOUR);
            }
        }
        return inputMessage;
    }

}
