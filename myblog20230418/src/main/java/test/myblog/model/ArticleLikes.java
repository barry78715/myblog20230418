package test.myblog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ArticleLikes")
public class ArticleLikes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ALID;

	@ManyToOne
    @JoinColumn(name = "MID", referencedColumnName = "id")
    private Member m;
	
	@ManyToOne
    @JoinColumn(name = "AID", referencedColumnName = "AID")
    private Article a;

	
	public ArticleLikes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getALID() {
		return ALID;
	}

	public void setALID(Integer aLID) {
		ALID = aLID;
	}

	public Member getM() {
		return m;
	}


	public void setM(Member m) {
		this.m = m;
	}


	public Article getA() {
		return a;
	}


	public void setA(Article a) {
		this.a = a;
	}
	
	
}