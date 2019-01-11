package io.pivotal.support.domain;

public class Customer {

	public Customer() {}

	public Customer(String name) {
		this.name = name;
	}

	String name;
	int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override public String toString() {
		return "Customer{" + "name='" + name + '\'' + ", age=" + age + '}';
	}
}
