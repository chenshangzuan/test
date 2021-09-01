package serializer.avro;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/2 6:09 下午
 */
public class TestAvro {
    public static void main(String[] args) throws IOException {
//        serializeUser();
//        deserializeUser();
    }

//    private static void deserializeUser() throws IOException {
//        // Deserialize Users from disk
//        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
//        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File("/Users/kled/git/test/bigdata_test/src/main/resources/avro/file/users.avro"), userDatumReader);
//        User user = null;
//        while (dataFileReader.hasNext()) {
//            user = dataFileReader.next(user);
//            System.out.println(user);
//        }
//    }
//
//    @SneakyThrows
//    private static void serializeUser() throws IOException {
//        User user1 = new User();
//        user1.setName("Alyssa");
//        user1.setFavoriteNumber(256);
//
//        User user2 = new User("Ben", 7, "red");
//
//        User user3 = User.newBuilder()
//                .setName("Charlie")
//                .setFavoriteColor("blue")
//                .setFavoriteNumber(null)
//                .build();
//
//        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
//        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
//        dataFileWriter.create(user1.getSchema(), new File("/Users/kled/git/test/bigdata_test/src/main/resources/avro/file/users.avro"));
//        dataFileWriter.append(user1);
//        dataFileWriter.append(user2);
//        dataFileWriter.append(user3);
//        dataFileWriter.close();
//    }
}
