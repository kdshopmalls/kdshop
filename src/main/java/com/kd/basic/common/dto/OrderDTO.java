package com.kd.basic.common.dto;

import java.util.Date;

public class OrderDTO {
	// 소문자 CTRL + SHIFT + Y
	private Integer or_code; // auto_increment
	private String mb_id;
	private String or_name;
	private String or_addr_zipcode;
	private String or_addr_basic;
	private String or_addr_detail;
	private String or_tel;
	private String or_mail;
	private int or_price;
	private String or_status;
	private Date or_regdate;
	private String or_message;  // 관리자 메모용도
}
