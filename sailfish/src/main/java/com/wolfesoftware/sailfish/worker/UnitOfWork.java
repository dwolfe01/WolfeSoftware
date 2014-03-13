package com.wolfesoftware.sailfish.worker;

public interface UnitOfWork<T> {

	T go();

}
