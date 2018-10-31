package com.zdb.demoSecurity.util;
import java.util.*;
import java.util.stream.Collectors;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		list.add("aa");list.add("bb");list.add("cc");
		list = list.stream().map(String::toUpperCase).collect(Collectors.toCollection(ArrayList::new));
		System.out.println(list);
	}

}
