package com.sweech.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.LoginRecordDto;
import com.sweech.app.model.User;
import com.sweech.app.service.LoginRecordService;
import com.sweech.app.service.UserService;
import com.sweech.app.util.AuthUtil;

@RestController
@RequestMapping("/api/login")
public class LoginRecordController {

	@Autowired
	private LoginRecordService loginRecordService;

	@GetMapping("/history")
	public ResponseEntity<?> getLoginHistory(Authentication authentication) {
		long id = AuthUtil.getUserId(authentication);

		List<LoginRecordDto> records = loginRecordService.getRecentLogins(id, 30);
		return ResponseEntity.ok(records);
	}

}
