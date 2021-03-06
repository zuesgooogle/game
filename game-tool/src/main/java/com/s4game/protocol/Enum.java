// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/enum.proto

package com.s4game.protocol;

public final class Enum {
  private Enum() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code PPlayerProperty}
   *
   * <pre>
   *玩家属性
   * </pre>
   */
  public enum PPlayerProperty
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>LEVEL = 1;</code>
     */
    LEVEL(0, 1),
    /**
     * <code>EXP = 2;</code>
     */
    EXP(1, 2),
    ;

    /**
     * <code>LEVEL = 1;</code>
     */
    public static final int LEVEL_VALUE = 1;
    /**
     * <code>EXP = 2;</code>
     */
    public static final int EXP_VALUE = 2;


    public final int getNumber() { return value; }

    public static PPlayerProperty valueOf(int value) {
      switch (value) {
        case 1: return LEVEL;
        case 2: return EXP;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<PPlayerProperty>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<PPlayerProperty>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<PPlayerProperty>() {
            public PPlayerProperty findValueByNumber(int number) {
              return PPlayerProperty.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.s4game.protocol.Enum.getDescriptor().getEnumTypes().get(0);
    }

    private static final PPlayerProperty[] VALUES = values();

    public static PPlayerProperty valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private PPlayerProperty(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:PPlayerProperty)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020proto/enum.proto*%\n\017PPlayerProperty\022\t\n" +
      "\005LEVEL\020\001\022\007\n\003EXP\020\002B\033\n\023com.s4game.protocol" +
      "B\004Enum"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
