package sdg.annotation.domain;

import org.springframework.data.gemfire.mapping.annotation.Region;

@Region
public class Customer {
  String name;

  public Customer(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Customer{" + "name='" + name + '\'' + '}';
  }
}
