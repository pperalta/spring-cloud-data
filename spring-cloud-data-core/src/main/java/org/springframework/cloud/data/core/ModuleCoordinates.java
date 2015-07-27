/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.data.core;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.util.Assert;

/**
 * The {@code ModuleCoordinates} class contains <a href="https://maven.apache.org/pom.html#Maven_Coordinates">
 * Maven coordinates</a> for a jar file containing a module.
 *
 * @author David Turanski
 * @author Mark Fisher
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
public class ModuleCoordinates {

	private String name;

	private String artifactId;

	private String groupId;

	private String version;

	protected ModuleCoordinates() {
		// For (subclass) JSON deserialization only
	}

	protected ModuleCoordinates(String name, String artifactId, String groupId, String version) {
		Assert.hasLength(name, "'name' cannot be blank");
		Assert.hasLength(artifactId, "'artifactId' cannot be blank");
		Assert.hasLength(groupId, "'groupId' cannot be blank");
		Assert.hasLength(version, "'version' cannot be blank");
		this.name = name;
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ModuleCoordinates)) {
			return false;
		}
		ModuleCoordinates that = (ModuleCoordinates) o;
		if (!name.equals(that.name)) {
			return false;
		}
		if (!artifactId.equals(that.artifactId)) {
			return false;
		}
		if (!groupId.equals(that.groupId)) {
			return false;
		}
		if (!version.equals(that.version)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + artifactId.hashCode();
		result = 31 * result + groupId.hashCode();
		result = 31 * result + version.hashCode();
		return result;
	}
}
