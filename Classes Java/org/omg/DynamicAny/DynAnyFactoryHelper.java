package org.omg.DynamicAny;


/**
* org/omg/DynamicAny/DynAnyFactoryHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../../../../src/share/classes/org/omg/DynamicAny/DynamicAny.idl
* Thursday, February 3, 2011 12:48:20 AM GMT
*/


/**
    * DynAny objects can be created by invoking operations on the DynAnyFactory object.
    * Generally there are only two ways to create a DynAny object:
    * <UL>
    * <LI>invoking an operation on an existing DynAny object
    * <LI>invoking an operation on a DynAnyFactory object
    * </UL>
    * A constructed DynAny object supports operations that enable the creation of new DynAny
    * objects encapsulating access to the value of some constituent.
    * DynAny objects also support the copy operation for creating new DynAny objects.
    * A reference to the DynAnyFactory object is obtained by calling ORB.resolve_initial_references()
    * with the identifier parameter set to the string constant "DynAnyFactory".
    * <P>Dynamic interpretation of an any usually involves creating a DynAny object using create_dyn_any()
    * as the first step. Depending on the type of the any, the resulting DynAny object reference can be narrowed
    * to a DynFixed, DynStruct, DynSequence, DynArray, DynUnion, DynEnum, or DynValue object reference.
    * <P>Dynamic creation of an any involves creating a DynAny object using create_dyn_any_from_type_code(),
    * passing the TypeCode associated with the value to be created. The returned reference is narrowed to one of
    * the complex types, such as DynStruct, if appropriate. Then, the value can be initialized by means of
    * invoking operations on the resulting object. Finally, the to_any operation can be invoked
    * to create an any value from the constructed DynAny.
    */
abstract public class DynAnyFactoryHelper
{
  private static String  _id = "IDL:omg.org/DynamicAny/DynAnyFactory:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.DynamicAny.DynAnyFactory that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.DynamicAny.DynAnyFactory extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.DynamicAny.DynAnyFactoryHelper.id (), "DynAnyFactory");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.DynamicAny.DynAnyFactory read (org.omg.CORBA.portable.InputStream istream)
  {
      throw new org.omg.CORBA.MARSHAL ();
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.DynamicAny.DynAnyFactory value)
  {
      throw new org.omg.CORBA.MARSHAL ();
  }

  public static org.omg.DynamicAny.DynAnyFactory narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.DynamicAny.DynAnyFactory)
      return (org.omg.DynamicAny.DynAnyFactory)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.DynamicAny._DynAnyFactoryStub stub = new org.omg.DynamicAny._DynAnyFactoryStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.omg.DynamicAny.DynAnyFactory unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.DynamicAny.DynAnyFactory)
      return (org.omg.DynamicAny.DynAnyFactory)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.omg.DynamicAny._DynAnyFactoryStub stub = new org.omg.DynamicAny._DynAnyFactoryStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}