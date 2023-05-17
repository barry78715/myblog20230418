package test.myblog.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import test.myblog.model.*;
import test.myblog.service.*;



@Controller
public class MemberHPController {

	 @Autowired
	 private TagService ts;
	 @Autowired
	 private FollowerService fs;
	 @Autowired
	 private MemberService ms;
	 @Autowired
	 private ArticleService as;
 
	 @PostMapping("/memberHP/unfollow")
	 public ResponseEntity<String> unfollow(@RequestBody Map<String, String> data) {
		 String loggedInUsername = data.get("loggedInUsername");
		 String HPOwnerName = data.get("HPOwnerName");
		 Integer loggedInUserID = ms.getIDByUsername(loggedInUsername);
		 Integer HPOwnerID = ms.getIDByUsername(HPOwnerName);
		 fs.stopFollowing(loggedInUserID, HPOwnerID);
		 return ResponseEntity.status(HttpStatus.OK).build();
	 }
	 
	 @PostMapping("/memberHP/follow")
	 public ResponseEntity<String> follow(@RequestBody Map<String, String> data) {
		 String loggedInUsername = data.get("loggedInUsername");
		 String HPOwnerName = data.get("HPOwnerName");
		 Integer loggedInUserID = ms.getIDByUsername(loggedInUsername);
		 Integer HPOwnerID = ms.getIDByUsername(HPOwnerName);
		 fs.startFollowing(loggedInUserID, HPOwnerID);
		 return ResponseEntity.status(HttpStatus.OK).build();
	 }
	
	 @GetMapping("/memberHP/{id}")
	 public String getById(Model model, @PathVariable("id") Integer id, Authentication authentication) {
		 model.addAttribute("article", new Article());
		 //有追蹤嗎
		 Boolean ifIsFollower = null;
		 if(authentication != null) {
			 Integer userID = ms.getIDByUsername(authentication.getName());
			 model.addAttribute("userID", userID);
			 ifIsFollower = fs.checkIfMemberIsFollower(userID, id);
		 } else {
			 ifIsFollower = false;
		 }
		 model.addAttribute("ifIsFollower", ifIsFollower);
		 Optional<Member> member = ms.getMemberByID(id);
		 Member m = member.get();
		 model.addAttribute("member", m);
		 Integer Followed = fs.getFollwedCountByID(id);
		 model.addAttribute("Followed", Followed);
		 Integer Following = fs.getFollwingCountByID(id);//取得追蹤數
		 model.addAttribute("Following", Following);
		 List<String> FollowingList = fs.getFollowingListByID(id); //取得追蹤者名字
		 model.addAttribute("FollowingList", FollowingList);
		 List<String> FollowedList = fs.getFollowedListByID(id); //取得粉絲名字
		 model.addAttribute("FollowedList", FollowedList);
		 
		 List<Article> articles = as.getArticlesByMID(id); //取得文章所有物件
	     model.addAttribute("articles", articles);
	     
	     Integer postCount = as.getPostCountByMID(id);
	     model.addAttribute("postCount", postCount);
	     
	     List<String> aILByMID = as.getArticleImgListByMID(id);
	     model.addAttribute("urlList", aILByMID);
	     
	     List<Tag> tl= ts.getAllTag();		
		 model.addAttribute("tl", tl);
		 return "memberHP";
		 
	}

}
