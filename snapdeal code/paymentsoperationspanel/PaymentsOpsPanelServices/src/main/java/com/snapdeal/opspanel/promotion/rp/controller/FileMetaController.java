package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.service.FileMetaService;

@Controller
@RequestMapping("/filemeta")
public class FileMetaController {

	@Autowired
	FileMetaService fms;

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public @ResponseBody void insertToFileMetaTable(FileMetaEntity e) {

		fms.insertFileMetaEntity(e);

	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public @ResponseBody List<FileMetaEntity> getAllFilesMeta() {

		return fms.getAllFilesMeta();

	}

	@RequestMapping(value = "/getallbyuser", method = RequestMethod.GET)
	public @ResponseBody List<FileMetaEntity> getAllFilesMetaByUser(@RequestParam("userId") String userId) {

		return fms.getAllFilesMetaByUser(userId);

	}

	@RequestMapping(value = "/getfilestatus", method = RequestMethod.GET)
	public @ResponseBody String getFileMetaStatus(@RequestParam("userId") String userId,
			@RequestParam("fileName") String fileName) {

		List<FileMetaEntity> list = fms.getFileMetaStatus(userId, fileName);
		if (!list.isEmpty()) {
			return list.get(0).getStatus();
		}
		return null;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody void updateFileMetaStatus(FileMetaEntity e) {

		fms.updateFileMetaStatus(e);

	}

}
