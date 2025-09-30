package com.kd.basic.member;

import org.springframework.stereotype.Service;

import com.kd.basic.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberMapper memberMapper;
	
	public void join(MemberDTO dto) {
		memberMapper.join(dto);
	}
    
	public String idCheck(String mbsp_id) {
		return memberMapper.idCheck(mbsp_id);
	}
	
	public MemberDTO login(String mbsp_id) {
		return memberMapper.login(mbsp_id);
	}
	
	public MemberDTO modify(String mbsp_id) {
		return memberMapper.modify(mbsp_id);
	}
	
	public void modify_save(MemberDTO dto) {
		memberMapper.modify_save(dto);
	}
	
	public MemberDTO mypage(String mbsp_id) {
		return memberMapper.mypage(mbsp_id);
	}
	
	public void lastlogin(String mbsp_id) {
		memberMapper.lastlogin(mbsp_id);
	}
}
