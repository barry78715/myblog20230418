package test.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.model.Member;
import test.myblog.repository.AritcleLikesRepository;
import test.myblog.repository.MemberRepository;

@Service
public class ArticleLikesService {
	
	@Autowired
	AritcleLikesRepository lr;
	
	
	public Integer getLikeCount(Integer AID) {
		return lr.getLikeCountByAID(AID);
	}
	
	public void like(Integer MID, Integer AID) {
		lr.createByMIDAndAID(MID, AID);
	}
	
	public void unlike(Integer MID, Integer AID) {
		lr.deleteByMIDAndAID(MID, AID);
	}
	public Boolean checkIfLiked(Integer MID, Integer AID) {
		if(lr.checkIfMemberLikedArticle(MID, AID) > 0){
			return true;
		}
		return false;
	}
}
