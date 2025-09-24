package com.kd.basic.common.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class ReviewDTO {
	
	//상품후기
	private Integer rev_code;
	private String mb_id;
	private Integer item_num;
	private String rev_content;
	private int rev_rate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
	   private LocalDateTime rev_date;

}
