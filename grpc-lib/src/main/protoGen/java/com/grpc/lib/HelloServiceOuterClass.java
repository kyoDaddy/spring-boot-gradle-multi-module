// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello-service.proto

package com.grpc.lib;

public final class HelloServiceOuterClass {
  private HelloServiceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HelloResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HelloResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023hello-service.proto\"@\n\014HelloRequest\022\021\n" +
      "\tfirstName\030\001 \001(\t\022\020\n\010lastName\030\002 \001(\t\022\013\n\003ag" +
      "e\030\003 \001(\005\"2\n\rHelloResponse\022\020\n\010greeting\030\001 \001" +
      "(\t\022\017\n\007allowed\030\002 \001(\01026\n\014HelloService\022&\n\005h" +
      "ello\022\r.HelloRequest\032\016.HelloResponseB\020\n\014c" +
      "om.grpc.libP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HelloRequest_descriptor,
        new java.lang.String[] { "FirstName", "LastName", "Age", });
    internal_static_HelloResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_HelloResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HelloResponse_descriptor,
        new java.lang.String[] { "Greeting", "Allowed", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
