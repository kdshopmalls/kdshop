package com.kd.basic.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DeliveryDTO {
	private Long delivery_id;
	private Integer or_code;
	private String ship_zipcode;
	private String ship_addr;
	private String ship_deaddr;
	private LocalDateTime delivery_date;
	private String delivery_status;
}
