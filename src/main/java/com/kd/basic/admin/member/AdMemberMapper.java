package com.kd.basic.admin.member;

import java.util.List;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface AdMemberMapper {
	
	List<MemberDTO> mem_list(SearchCriteria cri);

}
