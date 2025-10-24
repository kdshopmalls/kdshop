package com.kd.basic.cart;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter	
public class CartDTO {
	private	Integer item_num;
	private	String mb_id;
	private	int cart_amount;
	private Date cart_date;

}
