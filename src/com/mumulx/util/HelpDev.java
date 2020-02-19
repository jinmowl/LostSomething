package com.mumulx.util;

import java.io.File;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component("helpDev")
public class HelpDev {
	
	//window本地
	//定义图片保存的本地文件夹
	private static final String UPLADMIMGSRC = "C:\\workplace\\STS\\MyProject";
	//部署在linux
	//private static final String UPLADMIMGSRC = "/app/tools";
	public String getUPLADMIMGSRC() {
		return UPLADMIMGSRC;
	}
	private String errMessage = null;

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	/* 每页显示记录数目 */
	private int rows = 8;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	/* 保存查询后总的页数 */
	private int totalPage;

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	/* 保存查询到的总记录数 */
	private int recordNumber;

	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}

	public int getRecordNumber() {
		return recordNumber;
	}
	//MD5加密
	public  String md5Encode(byte[] input) {
        return DigestUtils.md5Hex(input);//byte[]--->String
    }
	//计算总的页数
	public void computeTotalPag(int rm) {
		this.recordNumber=rm;
		int mod = this.recordNumber % this.rows;
		this.totalPage = this.recordNumber / this.rows;
        if(mod != 0) this.totalPage++;
	}
	//删除本地图片
	public void deleteImgPhoto(String imgSrc) {
		if(!imgSrc.equals("upload/NoImage.jpg")) {
    		File file = new File(UPLADMIMGSRC+"\\"+imgSrc);
    		if(file.isFile()&& file.exists()){
    			file.delete();
    		}
		}
	}
}
