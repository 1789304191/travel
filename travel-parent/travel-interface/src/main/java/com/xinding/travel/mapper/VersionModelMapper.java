package com.xinding.travel.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.xinding.travel.pojo.WHYVersion;

@Repository
public interface VersionModelMapper {
	List<WHYVersion> versionList();
}
