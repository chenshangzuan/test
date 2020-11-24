/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package java_base;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestStream {

    static class Employee {
        private String name;
        private String city;
        private Integer age;
        private BigDecimal salary;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public BigDecimal getSalary() {
            return salary;
        }

        public void setSalary(BigDecimal salary) {
            this.salary = salary;
        }

        public Employee(String name, String city, Integer age, BigDecimal salary) {
            this.name = name;
            this.city = city;
            this.age = age;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    '}';
        }
    }


    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("张三", "北京", 18, new BigDecimal("9999.99")),
                new Employee("张四", "上海", 20, new BigDecimal("19999.99")),
                new Employee("李四", "广州", 58, new BigDecimal("5555.55")),
                new Employee("王五", "上海", 26, new BigDecimal("3333.33")),
                new Employee("赵六", "杭州", 36, new BigDecimal("8888.88")));

        System.out.println(employees.stream().anyMatch(x -> x.getName().startsWith("王")));
        System.out.println(employees.stream().sorted(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getAge).reversed()).collect(Collectors.toList()));
        System.out.println(employees.stream().filter(x -> x.getAge() > 30).map(Employee::getSalary).reduce(new BigDecimal(0), BigDecimal::add));
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getCity)));
        System.out.println(employees.stream().map(Employee::getCity).distinct().count());
    }
}
