package com.kd.basic.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PaymentDTO {
	private Integer pay_id;
	private Integer or_code;
	private String mb_id;
	private String pay_method;
	private int  pay_price;
	private String pay_status;
	private Date pay_date;
}
