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
package org.cfg4j.source.git;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.cfg4j.source.context.environment.Environment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AllButFirstTokenPathResolverTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  private Environment environment;

  private AllButFirstTokenPathResolver pathResolver;

  @Before
  public void setUp() throws Exception {
    pathResolver = new AllButFirstTokenPathResolver();
  }

  @Test
  public void resolvesEmptyStringToEmptyPath() throws Exception {
    when(environment.getName()).thenReturn("us-west-1/");

    assertThat(pathResolver.getPathFor(environment).toString()).isEqualTo("");
  }

  @Test
  public void discardsFirstToken() throws Exception {
    when(environment.getName()).thenReturn("us-west-1/local/path");

    assertThat(pathResolver.getPathFor(environment).toString()).isEqualTo("local/path");
  }

  @Test
  public void ignoresMissingFirstToken() throws Exception {
    when(environment.getName()).thenReturn("/local/path");

    assertThat(pathResolver.getPathFor(environment).toString()).isEqualTo("local/path");
  }

  @Test
  public void treatsMissingPathAsEmptyPath() throws Exception {
    when(environment.getName()).thenReturn("us-west-1/");

    assertThat(pathResolver.getPathFor(environment).toString()).isEqualTo("");
  }
}