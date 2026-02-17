package com.example.headline.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SystemMapper {
    @Select("SELECT GET_LOCK(#{key}, #{timeoutSeconds})")
    Integer tryLock(@Param("key") String key, @Param("timeoutSeconds") int timeoutSeconds);

    @Select("SELECT RELEASE_LOCK(#{key})")
    Integer releaseLock(@Param("key") String key);
}
