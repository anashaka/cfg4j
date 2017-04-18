/*
 * Copyright 2015-2016 Norbert Potocki (norbert.potocki@nort.pl)
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

package org.cfg4j.provider;

import static org.assertj.core.api.Assertions.assertThat;

import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;


@RunWith(MockitoJUnitRunner.class)
public class SimpleConfigurationProviderWithClasspathIntegrationTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void readsYamlBooleans() throws Exception {
    String path = "org/cfg4j/provider/SimpleConfigurationProviderIntegrationTest_readsYamlBooleans.yaml";

    ConfigurationProvider provider = getConfigurationProvider(path);

    assertThat(provider.getProperty("someSetting", Boolean.class)).isTrue();
  }

  @Test
  public void readsYamlIntegers() throws Exception {
    String path = "org/cfg4j/provider/SimpleConfigurationProviderIntegrationTest_readsYamlIntegers.yaml";

    ConfigurationProvider provider = getConfigurationProvider(path);

    assertThat(provider.getProperty("someSetting", Integer.class)).isEqualTo(42);
  }

  private ConfigurationProvider getConfigurationProvider(final String path) {
    ConfigurationSource source = new ClasspathConfigurationSource(new ConfigFilesProvider() {
      @Override
      public Iterable<Path> getConfigFiles() {
        return Collections.singleton(Paths.get(path));
      }
    });

    return new ConfigurationProviderBuilder()
        .withConfigurationSource(source)
        .build();
  }
}