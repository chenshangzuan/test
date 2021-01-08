package java_base;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.Criteria.where;

/**
 * @author: Kled
 * @version: TestJsonPath.java, v0.1 2020-12-25 22:01 Kled
 */
public class TestJsonPath {

    public static void main(String[] args) {
        String jsonStr = "{ \"store\": {\n" +
                "    \"book\": [ \n" +
                "      { \"category\": \"reference\",\n" +
                "        \"author\": \"Nigel Rees\",\n" +
                "        \"title\": \"Sayings of the Century\",\n" +
                "        \"price\": 8.95\n" +
                "      },\n" +
                "      { \"category\": \"fiction\",\n" +
                "        \"author\": \"Evelyn Waugh\",\n" +
                "        \"title\": \"Sword of Honour\",\n" +
                "        \"price\": 12.99,\n" +
                "        \"isbn\": \"0-553-21311-3\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"bicycle\": {\n" +
                "      \"color\": \"red\",\n" +
                "      \"price\": 19.95\n" +
                "    }\n" +
                "  }\n" +
                "}";
        DocumentContext documentContext = JsonPath.parse(jsonStr);

        //输出book[1]的author值
        System.out.println(documentContext.read("$.store.book[1].author").toString());

        //输出book[*]中category == 'reference'的book
        System.out.println(documentContext.read("$.store.book[?(@.category == 'reference')]").toString());

        //@ 代表属性对象, ?() 代表过滤
        //输出book[*]中price>10的book
        System.out.println(documentContext.read("$.store.book[?(@.price>10)]").toString());

        //bicycle[*]中含有color元素的bicycle
        System.out.println(documentContext.read("$.store.bicycle[?(@.color)]").toString());

        //输出该json中所有price的值
        System.out.println("prices=" + documentContext.read("$..price").toString());

        Filter cheapFictionFilter = Filter.filter(
                where("category").is("fiction").and("price").gte(10D)
        );
        //注:[?]非贪心？
        List<Map<String, Object>> books1 =
                documentContext.read("$.store.book[?]", cheapFictionFilter);
        System.out.println(books1);

        Predicate booksWithISBN = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
                return ctx.item(Map.class).containsKey("isbn");
            }
        };
        List<Map<String, Object>> books2 =
                documentContext.read("$.store.book[*]", List.class, booksWithISBN);
        System.out.println(books2);

        //赋值
        String newJson = documentContext.set("$['store']['book'][0]['author']", "Kled").jsonString();
        System.out.println("new json=" + newJson);

        //对象解析
        System.out.println(documentContext.read("$.store.book[0]", Book.class));

        Configuration config = Configuration.defaultConfiguration()
                .jsonProvider(new GsonJsonProvider())
                .mappingProvider(new GsonMappingProvider());
        ReadContext context1 = JsonPath.using(config).parse(jsonStr);
        TypeRef<List<String>> typeRef1 = new TypeRef<List<String>>() {};
        List<String> titles = context1.read("$.store.book[*].title", typeRef1);
        System.out.println(titles);

        TypeRef<List<Book>> typeRef2 = new TypeRef<List<Book>>(){};
        System.out.println(context1.read("$.store.book[*]", typeRef2));

        //Enum Option
        //    DEFAULT_PATH_LEAF_TO_NULL, //不存在默认返回null
        //    ALWAYS_RETURN_LIST,        //List<?>返回
        //    AS_PATH_LIST,              //List<String> paths 路径返回
        //    SUPPRESS_EXCEPTIONS,       //异常捕获
        //    REQUIRE_PROPERTIES;        //访问配置属性必须存在
        Configuration config2 = Configuration.builder()
                .options(Option.AS_PATH_LIST).build();
        ReadContext context2 = JsonPath.using(config2).parse(jsonStr);
        List<String> pathList = context2.read("$..author");
        System.out.println(pathList);

        Configuration config3 = Configuration.builder()
                .options(Option.DEFAULT_PATH_LEAF_TO_NULL, Option.SUPPRESS_EXCEPTIONS).build();
        ReadContext context3 = JsonPath.using(config3).parse(jsonStr);
        List<String> aa = context3.read("$.store.book[*].aa");
        System.out.println(aa);
    }

    public static class Book{
        private String category;

        private String author;

        private String title;

        private Double price;

        private String isbn;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "category='" + category + '\'' +
                    ", author='" + author + '\'' +
                    ", title='" + title + '\'' +
                    ", price=" + price +
                    ", isbn='" + isbn + '\'' +
                    '}';
        }
    }
}
