package java_base.jackson;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Kled
 * @date 2022/6/8 11:21 AM
 */
@JsonIgnoreProperties({ "id" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeanWithCreator {
    public Integer id;
    //@JsonIgnoreType //只作用于类
    @JsonIgnore
    public Integer age;
    public String name;

    //反序列化注解
    @JsonCreator
    public BeanWithCreator(
            @JsonProperty("id") int id,
            @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BeanWithCreator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"id\":1,\"theName\":\"My bean\"}";
        BeanWithCreator bean = new ObjectMapper().readerFor(BeanWithCreator.class).readValue(json);
        System.out.println(bean);
    }
}
