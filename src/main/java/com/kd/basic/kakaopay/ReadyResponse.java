package com.kd.basic.kakaopay;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReadyResponse {
	private String tid; //결제 고유 번호, 20자
	private String next_redirect_pc_url; //요청한 클라이언트가 PC 웹일 경우
	private Date created_at; //결제 준비 요청 시간
}
