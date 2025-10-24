package com.kd.basic.admin.payment;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.PaymentDTO;

public interface AdminPaymentMapper {
	//결제상태변경 
	void payment_status_change(@Param("or_code") Integer or_code, @Param("pay_status") String pay_status);
	
	//결제내역 

	PaymentDTO payment_info(Integer or_code);
	
	//결제금액 차감
	void payment_price_change(@Param("or_code")  Integer or_code, @Param("unitprice")  int unitprice)  ;
}
