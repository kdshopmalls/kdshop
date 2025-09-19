package com.kd.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	private final ReviewMapper reviewMapper;
	
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
}
