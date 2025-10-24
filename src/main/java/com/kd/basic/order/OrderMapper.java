package com.kd.basic.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.OrderDTO;

public interface OrderMapper {
	//주문테이블 저장
	void order_insert(OrderDTO dto);
	//주문상세테이블저장
	void order_detail_insert(@Param("or_code") Integer or_code,@Param("mb_id") String mb_id);
	//현재 주문내역 
	List<Map<String, Object>> getOrderByOr_code(Integer or_code);
	
	// 사용자별 주문 목록 조회
	List<OrderDTO> getOrderListByMemberId(String mb_id);

	// 주문 상세 정보 조회 (주문번호로)
	List<Map<String, Object>> getOrderDetailsByOrCode(Integer or_code);
	
	// 챗봇 주문코드 배송조회.
	OrderDTO findOrderById(@Param("or_code") int or_code);
}
