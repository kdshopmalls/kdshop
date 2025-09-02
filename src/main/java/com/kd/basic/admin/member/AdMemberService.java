package com.kd.basic.admin.member;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kd.basic.common.domain.MemberDTO;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdMemberService {
	private final AdMemberMapper adMemberMapper;
	
	public List<MemberDTO> mem_list(SearchCriteria cri){
		
		return adMemberMapper.mem_list(cri);
		
	}

}
