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

package org.springframework.cloud.data.rest.controller;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.data.core.ModuleCoordinates;
import org.springframework.cloud.data.core.ModuleDefinition;
import org.springframework.cloud.data.core.ModuleDeploymentRequest;
import org.springframework.cloud.data.core.StreamDefinition;
import org.springframework.cloud.data.module.deployer.ModuleDeployer;
import org.springframework.cloud.data.module.registry.ModuleRegistry;
import org.springframework.cloud.data.module.registry.StubModuleRegistry;
import org.springframework.cloud.data.repository.StreamDefinitionRepository;
import org.springframework.cloud.data.repository.StubStreamDefinitionRepository;
import org.springframework.cloud.data.rest.resource.StreamDefinitionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Fisher
 */
@RestController
@RequestMapping("/streams")
@ExposesResourceFor(StreamDefinitionResource.class)
public class StreamController {
	private static final Logger logger = LoggerFactory.getLogger(StreamController.class);

	private final StreamDefinitionRepository repository = new StubStreamDefinitionRepository();

	private final ModuleRegistry registry = new StubModuleRegistry();

	private final ModuleDeployer deployer;

	/**
	 * Create a StreamController that delegates to the provided {@link ModuleDeployer}.
	 *
	 * @param moduleDeployer the deployer this controller will use to deploy stream modules.
	 */
	public StreamController(ModuleDeployer moduleDeployer) {
		Assert.notNull(moduleDeployer, "moduleDeployer must not be null");
		this.deployer = moduleDeployer;
	}

	private final Assembler streamAssembler = new Assembler();

	@ResponseBody
	@RequestMapping(value = "/definitions", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PagedResources<StreamDefinitionResource> list(Pageable pageable,
			PagedResourcesAssembler<StreamDefinition> assembler) {

		return assembler.toResource(repository.findAll(pageable), streamAssembler);
	}

	@RequestMapping(value = "/definitions", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestParam("name") String name,
			@RequestParam("definition") String dsl,
			@RequestParam(value = "deploy", defaultValue = "true")
			boolean deploy) throws Exception {

		StreamDefinition definition = new StreamDefinition(name, dsl);
		this.repository.save(definition);

		// todo: deploy
	}


//	@RequestMapping
//	public String list() {
//		StringBuilder builder = new StringBuilder();
//		for (StreamDefinition definition : this.repository.findAll()) {
//			builder.append(String.format("%s\n", definition));
//		}
//		return builder.toString();
//	}
//
//	@RequestMapping(value = "/{name}", method = RequestMethod.POST)
//	public void create(@PathVariable("name") String name, @RequestBody String dsl) {
//		this.repository.save(new StreamDefinition(name, dsl));
//	}
//
//	@RequestMapping(value = "/{name}", method = RequestMethod.PUT)
//	public void deploy(@PathVariable("name") String name) {
//		StreamDefinition streamDefinition = this.repository.findByName(name);
//		Iterator<ModuleDefinition> iterator = streamDefinition.getDeploymentOrderIterator();
//		for (int i = 0; iterator.hasNext(); i++) {
//			String type = (i == 0) ? "sink" : "source"; // TODO: support processors
//			ModuleDefinition definition = iterator.next();
//			ModuleCoordinates coordinates = this.registry.findByNameAndType(definition.getName(), type);
//			Assert.notNull(coordinates, String.format("unable to find coordinates for %s", definition));
//			this.deployer.deploy(new ModuleDeploymentRequest(definition, coordinates));
//		}
//	}

	class Assembler extends StreamDefinitionResource.Assembler {
		@Override
		public StreamDefinitionResource toResource(StreamDefinition entity) {
			// todo: set stream status based on SPI status
			return super.toResource(entity);
		}
	}
}
