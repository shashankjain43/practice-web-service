package com.snapdeal.bulkprocess.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snapdeal.bulkprocess.entity.FileTrackEntity;

@Service("fileTrackerDao")
public interface FileTrackerDao {

	public List<FileTrackEntity> getIncompleteFileIds();
	
	public void insertFileInfo(FileTrackEntity fileTrackEntity);
}
