package com.xinding.travel.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;

public class JacksonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static ObjectMapper getInstance() {   
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;    
    } 
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {

	}
}
