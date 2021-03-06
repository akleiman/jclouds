/**
 *
 * Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
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
 * ====================================================================
 */
package org.jclouds.simpledb.options;

import static org.jclouds.simpledb.options.ListDomainsOptions.Builder.maxNumberOfDomains;
import static org.jclouds.simpledb.options.ListDomainsOptions.Builder.nextToken;
import static org.testng.Assert.assertEquals;

import java.util.Collections;

import org.jclouds.http.options.HttpRequestOptions;
import org.testng.annotations.Test;

/**
 * Tests possible uses of ListDomainsOptions and ListDomainsOptions.Builder.*
 * 
 * @author Adrian Cole
 */
public class ListDomainsOptionsTest {

   @Test
   public void testAssignability() {
      assert HttpRequestOptions.class.isAssignableFrom(ListDomainsOptions.class);
      assert !String.class.isAssignableFrom(ListDomainsOptions.class);
   }

   @Test
   public void testNextToken() {
      ListDomainsOptions options = new ListDomainsOptions();
      options.nextToken("test");
      assertEquals(options.buildFormParameters().get("NextToken"), Collections.singletonList("test"));
   }

   @Test
   public void testNullNextToken() {
      ListDomainsOptions options = new ListDomainsOptions();
      assertEquals(options.buildFormParameters().get("NextToken"), Collections.EMPTY_LIST);
   }

   @Test
   public void testNextTokenStatic() {
      ListDomainsOptions options = nextToken("test");
      assertEquals(options.buildFormParameters().get("NextToken"), Collections.singletonList("test"));
   }

   public void testInvalidMaxNumberOfDomainsZero() {
      maxNumberOfDomains(0);
   }

   public void testInvalidMaxNumberOfDomainsOver100() {
      maxNumberOfDomains(101);
   }

   @Test
   public void testMaxNumberOfDomains() {
      ListDomainsOptions options = new ListDomainsOptions();
      options.maxNumberOfDomains(1);
      assertEquals(options.buildFormParameters().get("MaxNumberOfDomains"), Collections.singletonList("1"));
   }

   @Test
   public void testNullMaxNumberOfDomains() {
      ListDomainsOptions options = new ListDomainsOptions();
      assertEquals(options.buildFormParameters().get("MaxNumberOfDomains"), Collections.EMPTY_LIST);
   }

   @Test
   public void testMaxNumberOfDomainsStatic() {
      ListDomainsOptions options = maxNumberOfDomains(1);
      assertEquals(options.buildFormParameters().get("MaxNumberOfDomains"), Collections.singletonList("1"));
   }

}
