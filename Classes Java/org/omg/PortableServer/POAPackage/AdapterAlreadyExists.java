package org.omg.PortableServer.POAPackage;


/**
* org/omg/PortableServer/POAPackage/AdapterAlreadyExists.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ../../../../src/share/classes/org/omg/PortableServer/poa.idl
* Thursday, February 3, 2011 12:48:15 AM GMT
*/

public final class AdapterAlreadyExists extends org.omg.CORBA.UserException
{

  public AdapterAlreadyExists ()
  {
    super(AdapterAlreadyExistsHelper.id());
  } // ctor


  public AdapterAlreadyExists (String $reason)
  {
    super(AdapterAlreadyExistsHelper.id() + "  " + $reason);
  } // ctor

} // class AdapterAlreadyExists
