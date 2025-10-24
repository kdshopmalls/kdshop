package com.kd.basic.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class ProductDTO {
	private Integer  item_num;
	private Integer cate_code; //카테고리 코드
	private String cate_name;
    private String  item_name;
    private int item_price;
    private int item_discount; //할인율
    private String  item_publisher;
    private String item_content;
    private String item_up_folder;
    private String item_img; //상품이미지
    private int item_amount;
    private String item_buy;
    private int item_review;
    private Date item_date;
    private Date item_updatedate;

}
