package com.mall.hlcloundposproject.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * 用户登录  信息  实体类
 * @author hl zhaoq_hero@163.com
 */
@SuppressWarnings("serial")
public class User implements Serializable{
	
	@SuppressWarnings("unused")
	private static final long serialzUID = 1L;
	
	public User() {
		super();
	}
	/**
	 * data":[
	 * {"Pass":"000",
	 * "Name":"打价签",
	 * "User":"000",
	 * "Right":null,
	 * "Ki":null,
	 * "quanxian":"YNNNNNN",
	 * "userType":"价签"},
	 */
	
	/**
	 * 解析  服务器端  返回数据  到实体类
	 * @param obj
	 * @return
	 */
	public static User userLoginParser(JSONObject obj){
		User user = null;
		if(obj!=null){
			user = new User();
			try {
				user.setKi(obj.getString("Ki"));
				user.setName(obj.getString("Name"));
				user.setPass(obj.getString("Pass"));
				user.setQuanxian(obj.getString("quanxian"));
				user.setRight(obj.getString("Right"));
				user.setUser(obj.getString("User"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	private String Pass;
	
	private String Name;
	
	private String User;
	
	private String Right;
	
	private String Ki;
	
	private String quanxian;
	
	private String userType;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	public String getKi() {
		return Ki;
	}

	public void setKi(String ki) {
		Ki = ki;
	}

	public String getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(String quanxian) {
		this.quanxian = quanxian;
	}

	public static long getSerialzuid() {
		return serialzUID;
	}

	public String getPass() {
		return Pass;
	}

	public void setPass(String pass) {
		Pass = pass;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getRight() {
		return Right;
	}

	public void setRight(String right) {
		Right = right;
	}

	@Override
	public String toString() {
		return "User [Pass=" + Pass + ", Name=" + Name + ", User=" + User
				+ ", Right=" + Right + ", Ki=" + Ki + ", quanxian=" + quanxian
				+ ", userType=" + userType + "]";
	}
	
}
