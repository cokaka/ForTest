package com.paic.hm.cas.front.biz.util;

import org.apache.log4j.Logger;

import com.paic.hm.cas.front.dto.ImageTextMaterialDTO;
import com.paic.hm.cas.front.dto.MaterialType;
import com.paic.hm.cas.front.dto.UploadTokenDTO;
import com.paic.hm.cas.front.integration.dao.PublicAccountDAO;
import com.paic.hm.cas.front.util.spring.ApplicationContextUtils;
import com.paic.pafa.app.biz.service.BusinessServiceException;
import com.paic.pafa.exceptions.BusinessException;


/**
 * 图文素材上传工具类
 * @author EX-ZHANGHAIGANG001
 *
 */
public class ImageTextUtil {
	private static final Logger Log = Logger.getLogger(ImageTextUtil.class);
	/**
	 * 将单个图文素材的标题、描述、封面、正文等信息（都不可为null），按相关HTML模板拼接成HTML格式文件，并上传到七牛服务器
	 * @param imageTextMaterial
	 * @return
	 * @throws BusinessServiceException
	 */
	public static String uploadImageText(ImageTextMaterialDTO imageTextMaterial) throws BusinessServiceException{
		Log.info("the parameter is: " + imageTextMaterial);
		Long pubAccID = imageTextMaterial.getPubAccID();
		String result = "";
		
		if(imageTextMaterial.getTitle() == null || imageTextMaterial.getDesc() == null 
				|| imageTextMaterial.getAlbum() == null || imageTextMaterial.getBody() == null){
			throw new IllegalArgumentException("ex: the title,desc,album or body maybe is null.");
		}
		
		String key = "";
		
		//1、获取上传文件用的UploadToken
		Log.info("begin to get the uploadToken.");
		UploadTokenDTO uploadTokenDTO = UploadTokenGenerator.getUploadToken(MaterialType.IMAGE_TEXT,pubAccID);
		Log.info("has gotten the uploadToken:" + uploadTokenDTO);
		
		//2、拼接成HTML文件（将HTML模板里面的变量值一一拼接）
		PublicAccountDAO publicAccount = ApplicationContextUtils.getApplicationContext()
				.getBean("publicAccountDAO", PublicAccountDAO.class);
		String pubAccName = publicAccount.getPubAccName(imageTextMaterial.getPubAccID());
		imageTextMaterial.setPubAccName(pubAccName);
		String htmlStr = HTMLTemplate.getHTMLStr(imageTextMaterial);
		
		try {
			//2、对HTML格式字符串进行上传，上传后将得到一个key值（该值为后续从七牛文件服务器读取该HTML文件时要用到的）
			Log.info("be ready to upload:" + htmlStr);
			key = UploadImageTextUtil.uploadImageText(pubAccID, uploadTokenDTO, htmlStr);
			Log.info("uploadImageText over!");
		} catch (BusinessException e) {
			Log.info("fail to uploadImageText:" + e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		//3、上传成功后，生成一个url地址（文件下载前缀+“/”+上传成功后系统返回的key），该地址为上传的文件资源（即HTML格式文件）访问路径地址
		
		if(uploadTokenDTO.getDownPrefix().endsWith("/")){
			result = uploadTokenDTO.getDownPrefix() + key;
		}else{
			result = uploadTokenDTO.getDownPrefix() + "/" + key;
		}
		
		return result;
	}
	
}
