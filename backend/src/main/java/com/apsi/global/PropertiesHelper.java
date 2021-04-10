package com.apsi.global;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class PropertiesHelper {
	@Value("${frontend.address}")
	private String frontendAddress;
}
