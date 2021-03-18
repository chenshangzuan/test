package kled.test.graphql.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author: Kled
 * @version: Author.java, v0.1 2021-03-18 10:51 Kled
 */
@Data
@Builder
public class Author {
    private Integer id;
    private String name;
    private Integer age;
}
