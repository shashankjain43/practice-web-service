package com.snapdeal.bulkprocess.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FileDTO {
	
	private String prevMarker;
	
	private String nextMarker;
	
	private List<FileModel> files;
	
	
	
}
