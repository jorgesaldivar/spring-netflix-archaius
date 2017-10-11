package com.jorgesaldivar.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

/**
 * Rest controller to test the dynamic configuration updated real-time
 * 
 * @author saldivar
 *
 */
@RestController
public class MainRestController {

	private DynamicLongProperty dynamicNumericValue = DynamicPropertyFactory.getInstance()
			.getLongProperty("dynamic.numericVariable", 100);

	private DynamicBooleanProperty dynamicBooleanValue = DynamicPropertyFactory.getInstance()
			.getBooleanProperty("dynamic.booleanVariable", false);

	private DynamicStringProperty dynamicStringValue = DynamicPropertyFactory.getInstance()
			.getStringProperty("dynamic.stringVariable", "default");

	/**
	 * End-points:
	 * 
	 * http://localhost:8080?variable=string
	 * http://localhost:8080?variable=boolean
	 * http://localhost:8080?variable=numeric
	 * 
	 * @param variable
	 * @return
	 */
	@GetMapping()
	public String getVariable(@RequestParam(value = "variable", required = false) Optional<String> variable) {

		String constant = "";

		if (variable.isPresent()) {

			String variableValue = variable.get();

			if ("string".equalsIgnoreCase(variableValue))
				constant = dynamicStringValue.get() + " <- string : changed on: "
						+ new Date(dynamicStringValue.getChangedTimestamp());
			else if ("boolean".equalsIgnoreCase(variableValue))
				constant = dynamicBooleanValue.get() + " <- boolean : changed on: "
						+ new Date(dynamicBooleanValue.getChangedTimestamp());
			else if ("numeric".equalsIgnoreCase(variableValue))
				constant = dynamicNumericValue.get() + " <- numeric : changed on: "
						+ new Date(dynamicNumericValue.getChangedTimestamp());
			else
				constant = "Undefined variable";

			return constant;
		}

		return "${variable} not present as a request parameter";

	}

}
