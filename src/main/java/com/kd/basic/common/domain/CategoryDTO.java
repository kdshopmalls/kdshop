package com.kd.basic.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CategoryDTO {
	private Integer cate_code;   
	private int cate_prtcode;
	private String cate_name; 
}
