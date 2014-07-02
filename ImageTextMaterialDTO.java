package com.paic.hm.cas.front.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.paic.pafa.app.dto.PafaDTO;
import com.paic.pafa.validator.annotation.VEnum;

/**
 * 图文信息DTO，适用于拼接上传七牛的图文模板用
 * @author EX-ZHANGHAIGANG001
 *
 */
public class ImageTextMaterialDTO extends PafaDTO{
	private static final long serialVersionUID = -8884456788670456634L;
	/**
	 * 图文标题
	 */
	private String title;
	/**
	 * 图文描述
	 */
	private String desc;
	/**
	 * 图文封面url
	 */
	private String album;
	/**
	 * 图文正文HTML片段
	 */
	private String body;
	/**
	 * 图文原文链接
	 */
	private String originalUrl;
	/**
	 * 图文发布日期（字符串类型日期）
	 */
	private String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	/**
	 * 图文是否允许分享微信微博  0:不允许 1:允许
	 */
	@VEnum(value={"0","1"},message="shareable需为0或1.")
	private boolean shareable = true;
	/**
	 * 图文是否显示封面 0:不显示 1:显示
	 */
	@VEnum(value={"0","1"},message="isShowCover需为0或1.")
	private boolean isShowCover = true;
	/**
	 * 公共账号ID
	 */
	private Long pubAccID;
	/**
	 * 公共账号名称
	 */
	private String pubAccName;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public boolean isShareable() {
		return shareable;
	}

	public void setShareable(boolean shareable) {
		this.shareable = shareable;
	}

	public boolean isShowCover() {
		return isShowCover;
	}

	public void setShowCover(boolean isShowCover) {
		this.isShowCover = isShowCover;
	}
	
	public Long getPubAccID() {
		return pubAccID;
	}

	public void setPubAccID(Long pubAccID) {
		this.pubAccID = pubAccID;
	}
	
	public String getPubAccName() {
		return pubAccName;
	}

	public void setPubAccName(String pubAccName) {
		this.pubAccName = pubAccName;
	}

	public String toString(){
		return "ImageTextMaterialDTO[title=" + title + ",desc=" + desc + ",album=" + album + ",body=" + body
			+ ",originalUrl=" + originalUrl + ",currentDate=" + currentDate + ",shareable=" + shareable 
			+ ",isShowCover=" + isShowCover + ",pubAccID=" + pubAccID + ",pubAccName=" + pubAccName + "]";
	}
	
}
