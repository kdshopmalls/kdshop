package com.kd.basic.admin.delivery;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminDeliveryController {
	private final AdminDeliveryService adminDeliveryService;
}
