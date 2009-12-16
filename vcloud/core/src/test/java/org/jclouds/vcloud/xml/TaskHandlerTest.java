/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
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
 * ====================================================================
 */
package org.jclouds.vcloud.xml;

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.net.URI;

import org.jclouds.date.DateService;
import org.jclouds.http.functions.BaseHandlerTest;
import org.jclouds.rest.internal.NamedResourceImpl;
import org.jclouds.vcloud.VCloudMediaType;
import org.jclouds.vcloud.domain.Task;
import org.jclouds.vcloud.domain.TaskStatus;
import org.jclouds.vcloud.domain.internal.TaskImpl;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests behavior of {@code TaskHandler}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "vcloud.TaskHandlerTest")
public class TaskHandlerTest extends BaseHandlerTest {

   private DateService dateService;

   @BeforeTest
   @Override
   protected void setUpInjector() {
      super.setUpInjector();
      dateService = injector.getInstance(DateService.class);
   }

   public void testApplyInputStream() {
      InputStream is = getClass().getResourceAsStream("/task.xml");

      Task result = factory.create(injector.getInstance(TaskHandler.class)).parse(is);

      Task expects = new TaskImpl("3299", URI
               .create("https://services.vcloudexpress.terremark.com/api/v0.8/task/3299"),
               TaskStatus.SUCCESS, dateService.iso8601DateParse("2009-08-24T21:29:32.983Z"),
               dateService.iso8601DateParse("2009-08-24T21:29:44.65Z"), new NamedResourceImpl("1",
                        "VDC Name", VCloudMediaType.VDC_XML,
                        URI.create("https://services.vcloudexpress.terremark.com/api/v0.8/vdc/1")),
               new NamedResourceImpl("4012", "Server1", VCloudMediaType.VAPP_XML, URI
                        .create("https://services.vcloudexpress.terremark.com/api/v0.8/vapp/4012")

               )

      );
      assertEquals(result, expects);

   }

   public void testSelf() {
      InputStream is = getClass().getResourceAsStream("/task-self.xml");

      Task result = factory.create(injector.getInstance(TaskHandler.class)).parse(is);

      Task expects = new TaskImpl("d188849-78", URI
               .create("https://vcloud.safesecureweb.com/api/v0.8/task/d188849-78"),
               TaskStatus.QUEUED, null, null, null, null
      );
      assertEquals(result, expects);

   }

   public void testApplyInputStream2() {
      InputStream is = getClass().getResourceAsStream("/task-hosting.xml");

      Task result = factory.create(injector.getInstance(TaskHandler.class)).parse(is);

      Task expects = new TaskImpl("d188849-72", URI
               .create("https://vcloud.safesecureweb.com/api/v0.8/task/d188849-72"),
               TaskStatus.RUNNING, dateService.iso8601SecondsDateParse("2001-01-01T05:00:00Z"),
               null, new NamedResourceImpl("188849", "188849", VCloudMediaType.VDC_XML, URI
                        .create("https://vcloud.safesecureweb.com/api/v0.8/vdc/188849")), null);
      assertEquals(result, expects);

   }
}
