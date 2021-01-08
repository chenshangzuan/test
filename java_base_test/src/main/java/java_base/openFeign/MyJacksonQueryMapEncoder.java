package java_base.openFeign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.QueryMapEncoder;

import java.util.Map;

/**
 * @author wolfgang
 * @date 2020-07-03 18:13:05
 * @version $ Id: JacksonQueryMapEncoder.java, v 0.1  wolfgang Exp $
 */
public class MyJacksonQueryMapEncoder implements QueryMapEncoder {

    ObjectMapper objectMapper;

    public MyJacksonQueryMapEncoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public MyJacksonQueryMapEncoder() {
        this(new ObjectMapper());
    }

    @Override
    public Map<String, Object> encode(Object object) {
        return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
    }
}
