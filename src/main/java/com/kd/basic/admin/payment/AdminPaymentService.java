package com.kd.basic.admin.payment;

import org.springframework.stereotype.Service;

import com.kd.basic.common.dto.PaymentDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminPaymentService {

	private final AdminPaymentMapper adminPaymentMapper;
	
	public PaymentDTO payment_info(Integer or_code) {
		return adminPaymentMapper.payment_info(or_code);
	}
}
