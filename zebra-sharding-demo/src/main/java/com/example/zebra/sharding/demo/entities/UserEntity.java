package com.example.zebra.sharding.demo.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEntity {

	private Integer id;

	private String uid;

	private String name;

	@Override
	public String toString() {
		return "UserEntity{" +
				"id=" + id +
				", uid='" + uid + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}