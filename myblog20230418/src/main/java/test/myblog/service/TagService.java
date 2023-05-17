package test.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.model.Tag;
import test.myblog.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tr;
	
	public List<Tag> getAllTag(){
		return tr.findAll();
	}

}
