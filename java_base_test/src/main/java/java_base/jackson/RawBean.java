package java_base.jackson;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Kled
 * @date 2022/6/8 10:49 AM
 */
@JsonRootName(value = "raw")
public class RawBean {
    public String name;

    @JsonRawValue
    public String attrs;

    public RawBean(String name, String attrs) {
        this.name = name;
        this.attrs = attrs;
    }

    public static void main(String[] args) throws JsonProcessingException {
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");
        String result1 = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        System.out.println(result1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(bean);
        System.out.println(result2);
    }
}
