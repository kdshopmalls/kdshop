package com.kd.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kd.basic.admin.review.AdminReviewMapper;
import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.dto.ReviewReplyDTO;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;
import com.kd.basic.product.ProductMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	private final ReviewMapper reviewMapper;
	private final ProductMapper productMapper;
	private final AdminReviewMapper adminReviewMapper;
	
	public Map<String, Object> getReiviewList(Integer item_num, int page){
	 Map<String, Object> map =new HashMap<>();
	 SearchCriteria cri = new SearchCriteria();
	 cri.setPage(page);
	 
	 List<ReviewDTO> rev_list = reviewMapper.rev_list(item_num, cri);
	 
		
		PageMaker pageMaker= new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(reviewMapper.getReviewCount(item_num));
		
		map.put("rev_list", rev_list);
		map.put("pageMaker", pageMaker);
		
		
		return map;
	}
	//상품후기 저장, 상품테이블 후기 카운트 변경
	@Transactional
	public void review_save(ReviewDTO dto) {
	 reviewMapper.review_save(dto);	
	 productMapper.review_count_update(dto.getItem_num());
	}
	
	public void review_delete(Integer rev_code) {
	  reviewMapper.review_delete(rev_code);	
	}
	public  ReviewDTO  review_info(Integer rev_code) {
		return	reviewMapper.review_info(rev_code);
	}
	
	public void review_modify(ReviewDTO dto) {
		reviewMapper.review_modify(dto);
	}
}
