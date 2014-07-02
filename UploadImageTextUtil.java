package com.paic.hm.cas.front.biz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.paic.hm.cas.front.dto.MaterialType;
import com.paic.hm.cas.front.dto.UploadTokenDTO;
import com.paic.pafa.exceptions.BusinessException;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.io.*;

public class UploadImageTextUtil {
	private static final Logger LOGGER = Logger.getLogger(UploadImageTextUtil.class);
	
	/**
	 * 上传图文
	 * 
	 * @param publicAccountID 公众账号ID
	 * @param uploadToken 上传token
	 * @param htmlString 图文的HTML
	 * @return
	 * @throws BusinessException
	 */
	public static String uploadImageText(Long publicAccountID, UploadTokenDTO uploadToken, String htmlString) throws BusinessException {
		LOGGER.info("uploadtoken when upload imagetext: " + uploadToken);
		if (publicAccountID == null || uploadToken == null || htmlString == null) {
			throw new IllegalArgumentException("参数为null");
		}
		if (StringUtils.isEmpty(uploadToken.getDownPrefix())) {
			throw new IllegalArgumentException("下载地址为空");
		}
		if (StringUtils.isEmpty(uploadToken.getUpToken())) {
			throw new IllegalArgumentException("上传token为空");
		}
		if (StringUtils.isEmpty(htmlString)) {
			throw new IllegalArgumentException("html为空");
		}
		// 获得素材ID
		String key = MaterialIDGenerator.getID(publicAccountID, MaterialType.IMAGE_TEXT);
		LOGGER.info("materialkey when upload imagetext: " + key);
		// 创建临时文件，保存html格式的图文
		File localFile = new File(key);
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(localFile), "utf8");
			osw.write(htmlString);
			osw.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				LOGGER.error("fileserver response:"+e.getMessage().toString());
			}
		}
		// 上传图文
		PutExtra pubExtra = new PutExtra();
		InputStream fileInputStream = null;
		PutRet putRet = null;
		try {
			fileInputStream = new FileInputStream(localFile);
			putRet = IoApi.Put(uploadToken.getUpToken(), key, fileInputStream, pubExtra);
		} catch (FileNotFoundException e) {
			LOGGER.debug(e.getMessage(), e);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					LOGGER.debug(e.getMessage(), e);
				}
			}
		}
		
		LOGGER.info("fileserver response:" + putRet);
		try {
			if (putRet != null) {
				JSONObject resultJSON = new JSONObject(putRet.toString());
				if (resultJSON.has("error")) {
					LOGGER.error("errorcode:" + resultJSON.getString("error"));
					throw new BusinessException("上传图文失败");
				} else {
					LOGGER.info("succeed in upload imagetext:" + resultJSON.getString("key"));
					return resultJSON.getString("key");
				}
			} else {
				LOGGER.error("no response of fileserver");
				throw new BusinessException("文件服务器没有响应");
			}
		} catch (JSONException e) {
			LOGGER.debug(e.getMessage(), e);
			throw new BusinessException("无法解析文件服务器返回JSON");
		} finally {
			localFile = null;
		}
	}
}
