/**
 *
 * Copyright (C) 2009 Global Cloud Specialists, Inc. <info@globalcloudspecialists.com>
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
package org.jclouds.http.functions;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.HttpUtils;
import org.jclouds.logging.Logger;
import org.jclouds.rest.RestContext;

import com.google.common.base.Function;

/**
 * @author Adrian Cole
 */
public class ParseContentMD5FromHeaders implements Function<HttpResponse, byte[]>, RestContext {

   public static class NoContentMD5Exception extends RuntimeException {

      private static final long serialVersionUID = 1L;
      private final HttpRequest request;
      private final HttpResponse response;

      public NoContentMD5Exception(HttpRequest request, HttpResponse response) {
         super(String.format("no MD5 returned from request: %s; response %s", request, response));
         this.request = request;
         this.response = response;
      }

      public HttpRequest getRequest() {
         return request;
      }

      public HttpResponse getResponse() {
         return response;
      }

   }

   @Resource
   protected Logger logger = Logger.NULL;
   private Object[] args;
   private HttpRequest request;

   public byte[] apply(HttpResponse from) {
      IOUtils.closeQuietly(from.getContent());
      String contentMD5 = from.getFirstHeaderOrNull("Content-MD5");
      if (contentMD5 != null) {
         return HttpUtils.fromBase64String(contentMD5);
      }
      throw new NoContentMD5Exception(request, from);
   }

   public Object[] getArgs() {
      return args;
   }

   public HttpRequest getRequest() {
      return request;
   }

   public void setContext(HttpRequest request, Object[] args) {
      this.request = request;
      this.args = args;
   }

}