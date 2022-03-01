package org.future.grpc.proto;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: ServerProto.proto")
public final class ServerServiceGrpc {

  private ServerServiceGrpc() {}

  public static final String SERVICE_NAME = "org.future.grpc.proto.ServerService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<org.future.grpc.proto.ServerProto.ServerProtoRequest,
      org.future.grpc.proto.ServerProto.ServerProtoResponse> METHOD_RESPONSE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "org.future.grpc.proto.ServerService", "response"),
          io.grpc.protobuf.ProtoUtils.marshaller(org.future.grpc.proto.ServerProto.ServerProtoRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(org.future.grpc.proto.ServerProto.ServerProtoResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerServiceStub newStub(io.grpc.Channel channel) {
    return new ServerServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ServerServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ServerServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ServerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void response(org.future.grpc.proto.ServerProto.ServerProtoRequest request,
        io.grpc.stub.StreamObserver<org.future.grpc.proto.ServerProto.ServerProtoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RESPONSE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_RESPONSE,
            asyncUnaryCall(
              new MethodHandlers<
                org.future.grpc.proto.ServerProto.ServerProtoRequest,
                org.future.grpc.proto.ServerProto.ServerProtoResponse>(
                  this, METHODID_RESPONSE)))
          .build();
    }
  }

  /**
   */
  public static final class ServerServiceStub extends io.grpc.stub.AbstractStub<ServerServiceStub> {
    private ServerServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void response(org.future.grpc.proto.ServerProto.ServerProtoRequest request,
        io.grpc.stub.StreamObserver<org.future.grpc.proto.ServerProto.ServerProtoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RESPONSE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServerServiceBlockingStub extends io.grpc.stub.AbstractStub<ServerServiceBlockingStub> {
    private ServerServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.future.grpc.proto.ServerProto.ServerProtoResponse response(org.future.grpc.proto.ServerProto.ServerProtoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RESPONSE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServerServiceFutureStub extends io.grpc.stub.AbstractStub<ServerServiceFutureStub> {
    private ServerServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.future.grpc.proto.ServerProto.ServerProtoResponse> response(
        org.future.grpc.proto.ServerProto.ServerProtoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RESPONSE, getCallOptions()), request);
    }
  }

  private static final int METHODID_RESPONSE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RESPONSE:
          serviceImpl.response((org.future.grpc.proto.ServerProto.ServerProtoRequest) request,
              (io.grpc.stub.StreamObserver<org.future.grpc.proto.ServerProto.ServerProtoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class ServerServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.future.grpc.proto.ServerProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerServiceDescriptorSupplier())
              .addMethod(METHOD_RESPONSE)
              .build();
        }
      }
    }
    return result;
  }
}
