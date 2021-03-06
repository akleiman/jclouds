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
package org.jclouds.blobstore;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.blobstore.domain.Blob;
import org.jclouds.blobstore.functions.BlobToHttpGetOptions;
import org.jclouds.blobstore.options.GetOptions;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpUtils;
import org.jclouds.http.filters.BasicAuthentication;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class TransientBlobRequestSigner implements BlobRequestSigner {

   private final BasicAuthentication basicAuth;
   private final BlobToHttpGetOptions blob2HttpGetOptions;

   @Inject
   public TransientBlobRequestSigner(BasicAuthentication basicAuth, BlobToHttpGetOptions blob2HttpGetOptions) {
      this.basicAuth = checkNotNull(basicAuth, "basicAuth");
      this.blob2HttpGetOptions = checkNotNull(blob2HttpGetOptions, "blob2HttpGetOptions");
   }

   @Override
   public HttpRequest signGetBlob(String container, String name) {
      HttpRequest request = new HttpRequest("GET", URI.create(String.format("http://localhost/%s/%s", container, name)));
      return basicAuth.filter(request);
   }

   @Override
   public HttpRequest signPutBlob(String container, Blob blob) {
      HttpRequest request = HttpRequest.builder().method("PUT").endpoint(
               URI.create(String.format("http://localhost/%s/%s", container, blob.getMetadata().getName()))).payload(
               blob.getPayload()).headers(
               HttpUtils.getContentHeadersFromMetadata(blob.getMetadata().getContentMetadata())).build();
      return basicAuth.filter(request);
   }

   @Override
   public HttpRequest signRemoveBlob(String container, String name) {
      HttpRequest request = new HttpRequest("DELETE", URI.create(String.format("http://localhost/%s/%s", container,
               name)));
      return basicAuth.filter(request);
   }

   @Override
   public HttpRequest signGetBlob(String container, String name, GetOptions options) {
      HttpRequest request = HttpRequest.builder().method("GET").endpoint(
               URI.create(String.format("http://localhost/%s/%s", container, name))).headers(
               blob2HttpGetOptions.apply(options).buildRequestHeaders()).build();
      return basicAuth.filter(request);
   }

}