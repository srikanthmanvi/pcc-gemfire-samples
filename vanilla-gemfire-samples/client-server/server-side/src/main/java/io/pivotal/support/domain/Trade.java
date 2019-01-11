package io.pivotal.support.domain;

public class Trade {

	public Trade(){}

	String name;

	public Trade(String name) {
		this.name = name;
	}

	@Override public String toString() {
		return "Trade{" + "name='" + name + '\'' + '}';
	}




}
