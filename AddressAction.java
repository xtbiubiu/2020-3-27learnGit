package com.baizhi.xt.action;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.baizhi.xt.entity.Address;
import com.baizhi.xt.service.AddressServiceImpl;


public class AddressAction {
	
	 private String addid;
	 private String name;//收货人姓名
	 private String detail;//收货地址
	 private int zipcode;//邮编
	 private String mobile;//收件人电话
	 private String addname;//收件地址
	 private Address address;
	 public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getAddid() {
		return addid;
	}
	public void setAddid(String addid) {
		this.addid = addid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}

	/**
	 * 查询地址
	 */
	public String queryAddress(){
		AddressServiceImpl as = new AddressServiceImpl();
		//查询地址
		List<Address> queryAlladd = as.queryAlladd();
		System.out.println("走了查询地址方法，地址是==="+queryAlladd);
		
		//如果地址存在，跳转到回显地址信息的action
		if(queryAlladd!=null){
			return "address-ok";//回显action
		}else {
			//如果地址不存在，跳到jsp页面，从jsp页面添加地址
			return "address";
		}
	}
	
	/**
	 * 地址信息回显。
	 * 订单确定下一步跳查询地址Action，如果存在跳到该action，下拉框显示信息
	 */
	public String addMessage(){
		//查询地址
		AddressServiceImpl as = new AddressServiceImpl();
		Address queryByaddid = as.queryByaddid(addid);
		System.out.println("queryByaddid=="+queryByaddid);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("queryByaddid", queryByaddid);
		return "address";//跳到地址jsp页面
	}
	
	/**
	 * 添加地址
	 */
	public String addAddress(){
		System.out.println("进入添加地址方法");
		
		HttpServletRequest request = ServletActionContext.getRequest();
		AddressServiceImpl as = new AddressServiceImpl();
		//创建用户，并封装属性
		Address address1 = new Address();
		String uuid = UUID.randomUUID().toString();
		address1.setAddid(uuid);
		System.out.println("地址id是===="+uuid);
		address1.setAddname(addname);
		address1.setDetail(detail);
		address1.setMobile(mobile);
		address1.setZipvode(zipcode);
		address1.setName(name);
		//根据详细地址查询
		Address queryBydetail = as.queryBydetail(detail);
		//如果这个地址不为空，表单提交跨包跳转到订单入库action
		if(queryBydetail!=null){
			return "address-ok";//跨包跳转到订单入库action
		}else{
			//调添加方法
			Address addAddress = as.addAddress(address1);
			System.out.println("新添加的地址是=="+addAddress);
			//把添加的地址对象放进session作用域
			request.getSession().setAttribute("addAddress", addAddress);
			
		}
		return "addAddress";//添加地址，点击下一步按钮跳转订单成功页面 
		
	}
}
