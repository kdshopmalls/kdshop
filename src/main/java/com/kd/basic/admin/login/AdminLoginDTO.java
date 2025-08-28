package com.kd.basic.admin.login;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class AdminLoginDTO {
	
	private String ad_id;
	private String ad_pw;
	private Date login_date;
}
