package com.kd.basic.admin.payment;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminPaymentController {

	private final AdminPaymentService adminPaymentService;
}
