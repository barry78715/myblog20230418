package test.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.model.Comment;
import test.myblog.model.Member;
import test.myblog.repository.CommentRepository;
import test.myblog.repository.MemberRepository;

@Service
public class CommentService {
	
	
	@Autowired
	private CommentRepository cr;
	
	@Autowired
	private MemberRepository mr;
	
	
	public List<Comment> getCommentsList(Integer AID){
		return cr.getCommentListByAID(AID);
	}
	public List<Member> getCommentedMemberList(List<Comment> cl){
		List<Member> cml = cl.stream()
							  .map(c -> c.getM().getId())
							  .map(MID -> mr.findById(MID).get())
							  .collect(Collectors.toList());
		return cml;
		
	}
	
	public Comment createComment(Comment c){
		return cr.save(c);
	}
	
	public Integer getIDOfLatestComment() {
		return cr.getCountOfCommentTable().getCID();
	}
	
	public void addOneCommentLike(Integer CID) {
		Integer currentCommentLikes = cr.getCurrentCommentLikes(CID);
		cr.setNewCommentLikes(currentCommentLikes + 1, CID);
	}
	
	public void substractOneCommentLike(Integer CID) {
		Integer currentCommentLikes = cr.getCurrentCommentLikes(CID);
		cr.setNewCommentLikes(currentCommentLikes - 1 , CID);
	}
}
