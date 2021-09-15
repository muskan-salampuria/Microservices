package com.epam.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
	
	@Id
	@Column(unique=true,name = "username")
	@NonNull
	private String username;
	
	@Column(name = "email")
	@NonNull
	private String email;
	
	@Column(name = "name")
	@NonNull
	private String name;
	String port;
}