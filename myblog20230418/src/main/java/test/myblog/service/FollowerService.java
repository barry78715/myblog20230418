package test.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import test.myblog.repository.FollowerRepository;
import test.myblog.repository.MemberRepository;


@Service
public class FollowerService {
	
	@Autowired
	private FollowerRepository fr;
	
	@Autowired
	private MemberRepository mr;
	
	
	public void stopFollowing(Integer FollowerID, Integer MID) {
		fr.deleteByMIDAndFollowerID(MID, FollowerID);
	}
	public void startFollowing(Integer FollowerID, Integer MID) {
		fr.createByMIDAndFollowerID(MID, FollowerID);
	}
	
	//粉絲數
	public Integer getFollwedCountByID(Integer id) {
		return fr.countFollowed(id);
	}
	//追蹤中
	public Integer getFollwingCountByID(Integer id) {
		return fr.countFollowing(id);
	}
	
	//追蹤名單
	public List<String> getFollowingListByID(Integer id){
		List<Integer> FollowingIDList = fr.findFollowingListByID(id);
		List<String> FollowingList = FollowingIDList.stream()
												  .map(mid -> mr.findById(mid).get())
												  .map(member -> member.getUsername())
												  .collect(Collectors.toList());
		return FollowingList;
	}
	
	//粉絲名單
	public List<String> getFollowedListByID(Integer MID){
		List<Integer> FollowedIDList = fr.findFollowedListByID(MID);
		List<String> FollowedList = FollowedIDList.stream()
												  .map(mid -> mr.findById(mid).get())
												  .map(member -> member.getUsername())
												  .collect(Collectors.toList());
		return FollowedList;
	}
	
	public Boolean checkIfMemberIsFollower(Integer id, Integer MID) {
		if(fr.checkIfMemberIsFollower(id, MID) > 0 || id == MID) {
			return true;
		}
		return false;
	}
}
