package com.sweech.app.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweech.app.dto.LoginRecordDto;
import com.sweech.app.mapper.LoginRecordMapper;
import com.sweech.app.mapper.UserMapper;
import com.sweech.app.model.LoginRecord;

@Service
public class LoginRecordService {

	@Autowired
	private LoginRecordMapper loginRecordMapper;

	@Autowired
	private UserMapper userMapper;

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
			.withZone(ZoneId.systemDefault());

	public void recordLogin(Long userId, String ipAddress) {
		LoginRecord record = new LoginRecord();
		record.setUserId(userId);
		record.setIpAddress(ipAddress);
		record.setLoginTime(FORMATTER.format(Instant.now()));
		loginRecordMapper.insert(record);
	}

	public List<LoginRecordDto> getRecentLogins(Long userId, int maxRecords) {
		return loginRecordMapper.findRecentByUserId(userId, maxRecords);
	}
}
