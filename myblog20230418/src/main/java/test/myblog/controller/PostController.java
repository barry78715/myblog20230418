package test.myblog.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import test.myblog.model.Article;
import test.myblog.model.Comment;
import test.myblog.model.Member;
import test.myblog.model.Tag;
import test.myblog.service.ArticleService;
import test.myblog.service.CommentLikesService;
import test.myblog.service.CommentService;
import test.myblog.service.MemberService;
import test.myblog.service.ArticleLikesService;
import test.myblog.service.TagService;

@Controller
public class PostController {
	
	@Autowired
	private TagService ts;
	
	@Autowired
	private CommentLikesService cls;
	
	@Autowired
	private CommentService cs;
	
	@Autowired
	private ArticleLikesService als;
	
	@Autowired
	private ArticleService as;
	
	@Autowired
	private MemberService ms;
	
	@PostMapping("/saveArticle")
	public String saveMember(Model model, @ModelAttribute("article") Article a, Authentication authentication) {
		
		Date now = new Date();
		a.setADate(now);
		
		Member author = ms.getMemberByUsername(authentication.getName());
		
		a.setM(author);
		a.setALike(0);
		a.setAView(0);
		as.createArticle(a);
		Article lastestPost = as.getLatestArticleId();
		return "redirect:/post/" + lastestPost.getAID();
	}
	
	@GetMapping("/post/{AID}")
	public String postPage(Model model, @PathVariable("AID") Integer AID, Authentication authentication) {
		Article a = as.getArticle(AID);
		model.addAttribute("article", a);
		//新發文
		Article newPost = new Article();
		model.addAttribute("newPost", newPost);
		//有按讚嗎
		Boolean ifLiked = null;
		List<Comment> cl = cs.getCommentsList(AID);
		model.addAttribute("cl", cl);
		List<Member> cml = cs.getCommentedMemberList(cl);
		model.addAttribute("cml", cml);
		
		List<Boolean> ifCommentLikedList = null;
		//瀏覽次數
		if(authentication != null) {
			Integer userID = ms.getIDByUsername(authentication.getName());
			model.addAttribute("userID", userID);
			ifLiked = als.checkIfLiked(userID, AID);
			ifCommentLikedList = cl.stream()
								   .map(c -> {
										return cls.checkIfLiked(userID, c.getCID());
								   })
								   .collect(Collectors.toList());
			if(ms.getMemberByUsername(authentication.getName()).getId()!= a.getM().getId()){
				as.addOneArticleView(AID);
			}
		} else {
			ifLiked = false;
			as.addOneArticleView(AID);
			ifCommentLikedList = cl.stream().map(c -> false).collect(Collectors.toList());
		}
		model.addAttribute("ifLiked", ifLiked);
		model.addAttribute("ifCommentLikedList", ifCommentLikedList);
		List<Tag> tl= ts.getAllTag();		
		model.addAttribute("tl", tl);
		
		
		return "article";
	}
	
	@PostMapping("/post/like")
	public ResponseEntity<String> like(@RequestBody Map<String, String> data) {
		String likedMemberUsername = data.get("loggedInUsername");
		Integer MID = ms.getIDByUsername(likedMemberUsername);
		if(data.get("AID") == null) {
			return ResponseEntity.unprocessableEntity().build();
		}
		Integer AID = Integer.parseInt(data.get("AID"));
		als.like(MID, AID);
		as.addOneArticleLike(AID);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/post/unlike")
	public ResponseEntity<String> unlike(@RequestBody Map<String, String> data) {
		String likedMemberUsername = data.get("loggedInUsername");
		Integer MID = ms.getIDByUsername(likedMemberUsername);
		Integer AID = Integer.parseInt(data.get("AID"));
		als.unlike(MID, AID);
		as.substractOneArticleLike(AID);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/post/saveComment")
	public ResponseEntity<Map<String, String>> saveComment(@RequestBody Map<String, String> data) {
		
		Integer cMID = Integer.parseInt(data.get("cMID"));
		Member commentedM = ms.getMemberByID(cMID).get();
		
		String dateTimeStr = data.get("cSubmitTime");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
		
		Integer cAID = Integer.parseInt(data.get("cAID"));
		Article commentedA = as.getArticle(cAID);
        
		String cContent = data.get("cContent");
		
		Comment c = new Comment();
		
		c.setCContent(cContent);
		c.setCLikes(0);
		c.setCTime(dateTime);
		c.setM(commentedM);
		c.setA(commentedA);
		cs.createComment(c);
		String CID = cs.getIDOfLatestComment() + "";
		
		Map<String, String> proccessedC = new HashMap<>(); 
		proccessedC.put("userName",commentedM.getName());
		proccessedC.put("userPic", commentedM.getPic());
		proccessedC.put("0", "0");
		proccessedC.put("userComment", cContent);
		proccessedC.put("userCTime", dateTimeStr);
		proccessedC.put("CID", CID);
	
		
		return new ResponseEntity<>(proccessedC, HttpStatus.OK);
	}
	
	@PostMapping("/post/likeComment")
	public ResponseEntity<String> likeComment(@RequestBody Map<String, String> data) {
		Integer MID = Integer.parseInt(data.get("MID"));
		System.out.println(MID);
		Integer CID = Integer.parseInt(data.get("CID"));
		System.out.println(CID);
		cls.likeComment(MID, CID);
		cs.addOneCommentLike(CID);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/post/unlikeComment")
	public ResponseEntity<String> unlikeComment(@RequestBody Map<String, String> data) {
		Integer MID = Integer.parseInt(data.get("MID"));
		System.out.println(MID);
		Integer CID = Integer.parseInt(data.get("CID"));
		System.out.println(CID);
		cls.unlikeComment(MID, CID);
		cs.substractOneCommentLike(CID);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
