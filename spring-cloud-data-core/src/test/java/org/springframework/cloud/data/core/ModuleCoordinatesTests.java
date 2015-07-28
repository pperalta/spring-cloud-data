/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.data.core;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Patrick Peralta
 */
public class ModuleCoordinatesTests {
	public static final String GROUP_ID = "org.springframework";

	public static final String ARTIFACT_ID = "spring-core";

	public static final String VERSION = "5.0.0";

	@Test
	public void testParse() {
		validateModuleCoordinates(ModuleCoordinates.parse(
				String.format("%s:%s:%s", GROUP_ID, ARTIFACT_ID, VERSION)));
	}

	@Test
	public void testBuilder() {
		validateModuleCoordinates(new ModuleCoordinates.Builder()
				.setGroupId(GROUP_ID)
				.setArtifactId(ARTIFACT_ID)
				.setVersion(VERSION)
				.build());
	}

	private void validateModuleCoordinates(ModuleCoordinates coordinates) {
		assertEquals(GROUP_ID, coordinates.getGroupId());
		assertEquals(ARTIFACT_ID, coordinates.getArtifactId());
		assertEquals(VERSION, coordinates.getVersion());
	}

}