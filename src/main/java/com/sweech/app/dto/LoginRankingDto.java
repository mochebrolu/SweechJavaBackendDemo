package com.sweech.app.dto;

public class LoginRankingDto {
	private String name;
	private Integer loginCount;
	private Integer rank;
	private Integer usersSharingRank;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getUsersSharingRank() {
		return usersSharingRank;
	}

	public void setUsersSharingRank(Integer usersSharingRank) {
		this.usersSharingRank = usersSharingRank;
	}
}
