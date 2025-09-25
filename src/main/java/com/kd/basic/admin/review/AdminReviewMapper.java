package com.kd.basic.admin.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.AdReviewReplyDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface AdminReviewMapper {

	List<AdReviewReplyDTO> review_list(@Param("cri") SearchCriteria cri, @Param("rev_rate") String rev_rate, @Param("rev_content") String rev_content);
	
	int review_count(@Param("cri") SearchCriteria cri, @Param("rev_rate") String rev_rate, @Param("rev_content") String rev_content);
	
}
