package test.myblog.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Tag")
public class Tag {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer TID;
	private String TName;
	private String TPic;
	@OneToMany(mappedBy = "t")
	private List<Article> articles;
	
	
	public Integer getTID() {
		return TID;
	}
	public void setTID(Integer tID) {
		TID = tID;
	}
	public String getTName() {
		return TName;
	}
	public void setTName(String tName) {
		TName = tName;
	}
	public String getTPic() {
		return TPic;
	}
	public void setTPic(String tPic) {
		TPic = tPic;
	}
	

}
