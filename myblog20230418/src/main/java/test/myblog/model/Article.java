package test.myblog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "Article")
public class Article implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer AID;
	private String ATitle;
	
	@Column(columnDefinition="MEDIUMTEXT")
	private String AContent;
	private Integer ALike;
	private Integer AView;
	
	@Temporal(TemporalType.DATE)
	private Date ADate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TID")
	private Tag t;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MID")
    private Member m;
	
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member getM() {
		return m;
	}
	public void setM(Member m) {
		this.m = m;
	}
	public Integer getAID() {
		return AID;
	}
	public void setAID(Integer aID) {
		AID = aID;
	}
	public String getATitle() {
		return ATitle;
	}
	public void setATitle(String aTitle) {
		ATitle = aTitle;
	}
	public String getAContent() {
		return AContent;
	}
	public void setAContent(String aContent) {
		AContent = aContent;
	}
	public int getALike() {
		return ALike;
	}
	public void setALike(Integer aLike) {
		ALike = aLike;
	}
	public Integer getAView() {
		return AView;
	}
	public void setAView(Integer aView) {
		AView = aView;
	}
	
	public Date getADate() {
		return ADate;
	}
	public void setADate(Date aDate) {
		ADate = aDate;
	}
	public Tag getT() {
		return t;
	}
	public void setT(Tag t) {
		this.t = t;
	}
	
	

}

