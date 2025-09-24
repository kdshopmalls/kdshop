package com.kd.basic.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface ReviewMapper {
	//상품후기 목록
	List<ReviewDTO> rev_list(@Param("item_num") Integer item_num,@Param("cri") SearchCriteria cri );
	
	//상품후기 갯수
	int getReviewCount(Integer item_num);
	
	//상품후기 저장 
	void review_save(ReviewDTO dto);
	
	//상품후기 삭제
	void review_delete(Integer rev_code);
	
	//상품후기 수정폼
	ReviewDTO  review_info(Integer rev_code);
	
	//상품후기 수정
	void review_modify(ReviewDTO dto);

}
