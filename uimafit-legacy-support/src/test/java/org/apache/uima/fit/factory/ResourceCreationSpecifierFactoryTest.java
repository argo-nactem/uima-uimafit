/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.uima.fit.factory;

import java.awt.Point;

import org.apache.uima.fit.factory.testAes.ParameterizedAE;
import org.apache.uima.resource.ResourceCreationSpecifier;
import org.junit.Test;

/**
 */

public class ResourceCreationSpecifierFactoryTest {

  @Test(expected = IllegalArgumentException.class)
  public void test4() throws Exception {
    ResourceCreationSpecifierFactory.createResourceCreationSpecifier(
            "src/test/resources/org/apache/uima/fit/component/NoOpAnnotator.xml",
            new String[] { "test" });
  }

  @Test(expected = IllegalArgumentException.class)
  public void test3() throws Exception {
    UimaContextFactory.createUimaContext("test");
  }

  @Test(expected = IllegalArgumentException.class)
  public void test2() throws Exception {
    ResourceCreationSpecifierFactory.createResourceCreationSpecifier(
            "src/test/resources/org/apache/uima/fit/component/NoOpAnnotator.xml", new Object[] {
                "test", new Point(0, 5) });
  }

  @Test(expected = IllegalArgumentException.class)
  public void test1() {
    ResourceCreationSpecifierFactory.setConfigurationParameters((ResourceCreationSpecifier) null,
            ParameterizedAE.PARAM_BOOLEAN_1);
  }
}
