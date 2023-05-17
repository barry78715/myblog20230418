package test.myblog.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import test.myblog.model.Member;
import test.myblog.model.Tag;
import test.myblog.model.Article;
import test.myblog.service.MemberService;
import test.myblog.service.TagService;
import test.myblog.service.ArticleService;





@Controller
public class MainController {
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private TagService ts;
	
	@Autowired
	private ArticleService as;
	
	@GetMapping("/")
    public String viewHomePage(Model model, Authentication authentication) {
		if(authentication != null) {
			Integer userID = ms.getIDByUsername(authentication.getName());
			model.addAttribute("userID", userID);
		}
		List<Tag> tl= ts.getAllTag();		
		model.addAttribute("tl", tl);
		model.addAttribute("article", new Article());
		
		List<List<Article>> alByTag = tl.stream()
								  .map(t -> {return as.getArticleListByTID(t.getTID());})
								  .collect(Collectors.toList());
		
		model.addAttribute("alByTag", alByTag);
		for(int i = 0; i < alByTag.size(); i++) {
			System.out.println(alByTag.get(i));
			System.out.println(alByTag.get(i).size());
			for(int j = 0; j< alByTag.get(i).size(); j++) {
//				System.out.println(alByTag.get(i).get(j).getATitle());
			}
		}

        return "index";
    }
	
	@GetMapping("/login")
    public String loginPage() {
		
        return "login";
    }
	
	@GetMapping("/test")
    public String testPage(Principal principal, Authentication authentication) {
		System.out.println(principal.getName());
		System.out.println(authentication.getName());
		System.out.println(authentication.getPrincipal());
        return "test";
    }
	
	@GetMapping("/register")
    public String registerPage(Model model) {
		model.addAttribute("member", new Member());
        return "register";
    }
	
	@PostMapping("/saveMember")
	public String saveMember(@ModelAttribute("member") Member m, @RequestParam("imageData") String imageData) throws IOException {
		byte[] imageBytes = Base64.getDecoder().decode(imageData.split(",")[1]);
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);
		String fileName = m.getUsername() + "_" + formattedDateTime + ".jpg";
		String staticPath = servletContext.getRealPath("/upload");
	    String imagePath = staticPath + File.separator + fileName;
	    String imageURL = "http://localhost:8080/upload" + File.separator + fileName;
	    BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File(imagePath)));
		stream.write(imageBytes);
		stream.flush();
		stream.close();
		m.setPic(imageURL);
		ms.createMember(m);
		return "redirect:/login";
	}
	
	@GetMapping("/memberHP")
	public String getMemberHP(Authentication authentication) {
		Integer MID = ms.getIDByUsername(authentication.getName());
        return "redirect:/memberHP/" + MID;
	}
	
	
}
