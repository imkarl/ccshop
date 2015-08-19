package cn.jeesoft.mvc.services;

import org.springframework.stereotype.Service;

import cn.jeesoft.mvc.bean.Admin;

@Service("adminService")
public class AdminService extends BaseService<Admin> {

	@Override
	public String getTablename() {
		return "admin";
	}

}
