package test.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.repository.CommentLikesRepository;

@Service
public class CommentLikesService {
	
	@Autowired
	CommentLikesRepository clr;
	
	public Boolean checkIfLiked(Integer MID, Integer CID) {
		if(clr.checkIfMemberLikedComment(MID, CID) > 0){
			return true;
		}
		return false;
	}
	
	public void likeComment(Integer MID, Integer CID) {
		clr.createByMIDAndCID(MID, CID);
	}
	
	public void unlikeComment(Integer MID, Integer CID) {
		clr.deleteByMIDandCID(MID, CID);
	}
}
