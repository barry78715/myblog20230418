package test.myblog.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Follower")
public class Follower implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer FID;
	
	@ManyToOne
    @JoinColumn(name = "MID", referencedColumnName = "id")
    private Member m;
	
	@ManyToOne
    @JoinColumn(name = "FollowerID", referencedColumnName = "id")
    private Member f;

	
	public Follower() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getFID() {
		return FID;
	}

	public void setFID(Integer fID) {
		FID = fID;
	}

	public Member getM() {
		return m;
	}

	public void setM(Member m) {
		this.m = m;
	}

	public Member getF() {
		return f;
	}

	public void setF(Member f) {
		this.f = f;
	}
	
	
	
}
