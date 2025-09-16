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
}
