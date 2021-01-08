package kled.test.bean;

/**
 * @author: Kled
 * @version: TbUser.java, v0.1 2020-12-31 15:33 Kled
 */
public class TbUser {

    private Integer uuid;

    private String name;

    private String gender;

    private Integer age;

    public TbUser(Integer uuid, String name, String gender, Integer age) {
        this.uuid = uuid;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TbUser{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
