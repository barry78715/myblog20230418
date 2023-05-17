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
@Table(name = "CommentLikes")
public class CommentLikes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer CLID;
	
	@ManyToOne
    @JoinColumn(name = "MID", referencedColumnName = "id")
    private Member m;
	
	@ManyToOne
    @JoinColumn(name = "CID", referencedColumnName = "CID")
    private Comment c;
	

	public Integer getCLID() {
		return CLID;
	}

	public void setCLID(Integer cLID) {
		CLID = cLID;
	}

	public Member getM() {
		return m;
	}

	public void setM(Member m) {
		this.m = m;
	}

	public Comment getC() {
		return c;
	}

	public void setC(Comment c) {
		this.c = c;
	}
}
