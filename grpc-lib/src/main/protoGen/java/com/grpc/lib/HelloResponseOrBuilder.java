// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello-service.proto

package com.grpc.lib;

public interface HelloResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:HelloResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string greeting = 1;</code>
   * @return The greeting.
   */
  java.lang.String getGreeting();
  /**
   * <code>string greeting = 1;</code>
   * @return The bytes for greeting.
   */
  com.google.protobuf.ByteString
      getGreetingBytes();

  /**
   * <code>bool allowed = 2;</code>
   * @return The allowed.
   */
  boolean getAllowed();
}
