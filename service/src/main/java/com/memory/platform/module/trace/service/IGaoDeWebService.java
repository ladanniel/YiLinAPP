package com.memory.platform.module.trace.service;

import java.util.Map;

import com.memory.platform.global.Coordinate;
import com.memory.platform.module.trace.dto.GaodeApiConfig;
import com.memory.platform.module.trace.dto.GeoReqDTO;
import com.memory.platform.module.trace.dto.GeoResponseDTO;
import com.memory.platform.module.trace.dto.ReGeoReqDTO;
import com.memory.platform.module.trace.dto.ReGeoResponseDTO;

public interface IGaoDeWebService {
	ReGeoResponseDTO getReGeo(ReGeoReqDTO addr);

	GeoResponseDTO getGeo(GeoReqDTO geoReq);

	GaodeApiConfig getConfig();

	Map<String, Object> getDrivingPath(Coordinate start, Coordinate end,
			int strage);
}
