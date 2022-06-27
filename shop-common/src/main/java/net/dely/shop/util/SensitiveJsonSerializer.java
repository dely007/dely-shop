package net.dely.shop.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import net.dely.shop.annotation.Sensitive;
import net.dely.shop.enums.SensitiveStrategy;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: dengjiaxin
 * Date: 2022/6/27
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 * @author admin
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {
    private SensitiveStrategy strategy;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        //strategy.desensitizer() 返一个Function
        // Function.apply(value) 执行枚举里面定义的脱敏方法
        gen.writeString(strategy.desensitizer().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitive annotation = property.getAnnotation(Sensitive.class);
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.strategy = annotation.strategy();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
