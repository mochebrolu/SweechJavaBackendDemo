package com.sweech.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sweech.app.dto.LoginRecordDto;
import com.sweech.app.model.LoginRecord;

@Mapper
public interface LoginRecordMapper {

	void insert(LoginRecord record);

    List<LoginRecordDto> findRecentByUserId(@Param("userId") Long userId, @Param("limit") int limit);
    List<Map<String, Object>> getWeeklyLoginRankings();
}
