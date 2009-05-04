/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/almond/workspace/MonBattery/src/com/almondmendoza/monBattery/IBattery.aidl
 */
package com.almondmendoza.monBattery;
import java.lang.String;
import android.os.RemoteException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
/* <!-- 
* Copyright (C) 2009 AlmondMendoza
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
-->
*/
public interface IBattery extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.almondmendoza.monBattery.IBattery
{
private static final java.lang.String DESCRIPTOR = "com.almondmendoza.monBattery.IBattery";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an IBattery interface,
 * generating a proxy if needed.
 */
public static com.almondmendoza.monBattery.IBattery asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.almondmendoza.monBattery.IBattery))) {
return ((com.almondmendoza.monBattery.IBattery)iin);
}
return new com.almondmendoza.monBattery.IBattery.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getPercent:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPercent();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_stopNotification:
{
data.enforceInterface(DESCRIPTOR);
this.stopNotification();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.almondmendoza.monBattery.IBattery
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public int getPercent() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPercent, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void stopNotification() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopNotification, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getPercent = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopNotification = (IBinder.FIRST_CALL_TRANSACTION + 1);
}
public int getPercent() throws android.os.RemoteException;
public void stopNotification() throws android.os.RemoteException;
}
