package com.memory.platform.module.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.member.Staff;
import com.memory.platform.global.DateUtil;
import com.memory.platform.module.system.dao.StaffDao;
import com.memory.platform.module.system.service.IStaffService;

@Service
public class StaffService implements IStaffService {

	@Autowired
	private StaffDao staffDao;

	@Override
	public Map<String, Object> getPageStaff(Staff staff, int start, int limit) {
		return staffDao.getPageStaff(staff, start, limit);
	}

	@Override
	public Object getEduList() {
		return staffDao.getEduList();
	}

	@Override
	public Map<String, Object> save(Staff staff) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			staffDao.save(staff);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}

	
	public List<Staff> getStaffList() {
		return staffDao.getListByHql(" from Staff ");
	}

	@Override
	public Staff getById(String id) {
		return staffDao.getObjectById(Staff.class, id);
	}

	@Override
	public Map<String, Object> updateStaff(Staff staff) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			Staff persistent = getById(staff.getId());
			persistent.setBirthday(staff.getBirthday());
			persistent.setSex(staff.getSex());
			persistent.setNation(staff.getNation());
			persistent.setOrigin(staff.getOrigin());
			persistent.setMajor(staff.getMajor());
			persistent.setGraduate_school(staff.getGraduate_school());
			persistent.setEmail(staff.getEmail());
			persistent.setEducation(staff.getEducation());
			persistent.setAge(DateUtil.getAge(staff.getBirthday()));
			persistent.setUpdate_time(new Date());
			staffDao.update(persistent);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}
		return map;
	}
}
