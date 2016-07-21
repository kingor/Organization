package by.mainsoft.organization.shared.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String data;
	private String address;
	private String phone;
	private Integer employee;
	private String info;
	private Type type;
	private User manager;
	private Date date;

}
