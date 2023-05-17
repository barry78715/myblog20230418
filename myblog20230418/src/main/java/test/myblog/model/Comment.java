package test.myblog.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Comment")
public class Comment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CID;
	private LocalDateTime CTime;
	
	@Column(columnDefinition="TEXT")
	private String CContent;
	private Integer CLike;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AID")
	private Article a;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member m;
	
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getCID() {
		return CID;
	}


	public void setCID(Integer cID) {
		CID = cID;
	}


	public LocalDateTime getCTime() {
		return CTime;
	}


	public void setCTime(LocalDateTime cTime) {
		CTime = cTime;
	}


	public String getCContent() {
		return CContent;
	}


	public void setCContent(String cContent) {
		CContent = cContent;
	}


	public Integer getCLikes() {
		return CLike;
	}


	public void setCLikes(Integer cLike) {
		CLike = cLike;
	}


	public Article getA() {
		return a;
	}


	public void setA(Article a) {
		this.a = a;
	}


	public Member getM() {
		return m;
	}


	public void setM(Member m) {
		this.m = m;
	}

}


