package com.itcast.actviti7demo.common.vo;

public class HolidayQueryVo {
	
	//请假单信息
	public HolidayCustom orderCustom;
	
	//请假单审核信息
	private HolidayAuditCustom orderAuditCustom;

	public HolidayCustom getOrderCustom() {
		return orderCustom;
	}

	public void setOrderCustom(HolidayCustom orderCustom) {
		this.orderCustom = orderCustom;
	}

	public HolidayAuditCustom getOrderAuditCustom() {
		return orderAuditCustom;
	}

	public void setOrderAuditCustom(HolidayAuditCustom orderAuditCustom) {
		this.orderAuditCustom = orderAuditCustom;
	}
	
	
	
}
