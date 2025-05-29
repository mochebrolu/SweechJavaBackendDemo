package com.sweech.app.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sweech.app.mapper.LoginRecordMapper;

@Service
public class LoginRankingService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    public List<Map<String, Object>> getWeeklyLoginRankings() {
        return loginRecordMapper.getWeeklyLoginRankings();
    }
}
