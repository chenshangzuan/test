package kled.test.graphql.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author: Kled
 * @version: Book.java, v0.1 2021-03-18 10:50 Kled
 */
@Data
@Builder
public class Book {
    private Integer id;
    private String name;
    private Author author;
    private String publisher;
}
