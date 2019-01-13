package client;

import org.springframework.data.gemfire.mapping.annotation.Region;

@Region
public class Customer {
  String name;
  int age;

  public Customer(){}

  public Customer(String name) {
    this.name = name;
  }

  public Customer(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override public String toString() {
    return "Customer{" + "name='" + name + '\'' + ", age=" + age + '}';
  }
}
