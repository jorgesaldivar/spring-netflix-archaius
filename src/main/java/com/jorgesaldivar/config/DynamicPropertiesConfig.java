package com.jorgesaldivar.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.*;

/**
 * Configuration using the documentation from:
 * https://github.com/Netflix/archaius/wiki/Getting-Started
 * 
 * @author saldivar
 *
 */
@Configuration
public class DynamicPropertiesConfig {

	@Value("${filepath}")
	private String filepath;

	@PostConstruct
	public void init() {

		System.setProperty("archaius.configurationSource.defaultFileName", "dynamic.properties");
		File file = Paths.get(filepath).toFile();

		if (file.exists() && file.isFile()) {
			System.setProperty("archaius.configurationSource.additionalUrls", "file:" + filepath);
			System.setProperty("archaius.fixedDelayPollingScheduler.initialDelayMills", "1000");
			System.setProperty("archaius.fixedDelayPollingScheduler.delayMills", "1000");
		}
	}

}
