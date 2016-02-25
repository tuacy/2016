package com.tuacy.common.base.handle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class BaseHandleReference<T> extends Handler {

	private WeakReference<T> mReference = null;

	public BaseHandleReference(T reference) {
		this.mReference = new WeakReference<T>(reference);
	}

	public BaseHandleReference(Callback callback, T reference) {
		super(callback);
		this.mReference = new WeakReference<T>(reference);
	}

	public BaseHandleReference(Looper looper, T reference) {
		super(looper);
		this.mReference = new WeakReference<T>(reference);
	}

	public BaseHandleReference(Looper looper, Callback callback, T reference) {
		super(looper, callback);
		this.mReference = new WeakReference<T>(reference);
	}

	@Override
	public void handleMessage(Message msg) {
		final T reference = mReference.get();
		if (reference != null) {
			referenceHandleMessage(reference, msg);
		}
	}

	public abstract void referenceHandleMessage(T reference, Message msg);
}
