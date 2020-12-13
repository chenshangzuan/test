/*
 * Copyright 2012-2013 the original author or authors.
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

package kled.test.service;

import api.HelloRequest;
import api.HelloResponse;
import api.HelloWorldGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@GrpcService
public class HelloWorldService extends HelloWorldGrpc.HelloWorldImplBase {

	@Override
	public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		System.out.println("grpc HelloWorldService -> request=" + request);
		HelloResponse helloResponse = HelloResponse.newBuilder().setSuccess(true).setMsg(request.getMsg() + " handled by grpc server").build();
		responseObserver.onNext(helloResponse);
		responseObserver.onCompleted();
	}
}
