package kled.test.graphql;

import graphql.kickstart.tools.GraphQLQueryResolver;
import kled.test.graphql.bean.Author;
import kled.test.graphql.bean.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Kled
 * @version: BookQueryResolver.java, v0.1 2021-03-18 10:48 Kled
 */
@Component
public class BookQueryResolver implements GraphQLQueryResolver {

    public List<Book> findBooks() {
        Author author = Author.builder()
                .id(1)
                .name("Bill Venners")
                .age(40)
                .build();
        Book b = Book.builder()
                .id(1)
                .name("scala编程第三版")
                .author(author)
                .publisher("电子工业出版社")
                .build();
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(b);
        return bookList;
    }
}
