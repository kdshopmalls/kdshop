package com.kd.basic.common.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 클래스명은 테이블명과 상관이 없지만, 필드는 컬럼명과 동일하게 작성한다.
@Getter
@Setter
@ToString
public class MemberDTO {

	private String mb_id;
	private String mb_name;
	private String mb_email;
	private String mb_pw;
	private String mb_zipcode;
	private String mb_addr;
	private String mb_deaddr;
	private String mb_phone;
	private String mb_nick;
	private String mb_receive;
	private int mb_point;
	private Date mb_lastlogin;
	private Date mb_datesub;
	private Date mb_updatedate;
}
