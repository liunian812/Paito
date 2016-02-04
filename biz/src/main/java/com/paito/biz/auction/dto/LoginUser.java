package com.paito.biz.auction.dto;

import java.io.Serializable;

public class LoginUser implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 用户id. */
	private Long userId;

	/** 旺旺昵称. */
	private String nickname;

	/** 用户类型. */
	private int userType;

	/** 用户状态 1、正常 2、处罚中. */
	private int userStatus;

	private String ip;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
