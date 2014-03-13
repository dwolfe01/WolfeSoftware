package com.wolfesoftware.sailfish.worker;

/*
 * The worker class represents one or more units of work that are executed sequentially
 * Typically many workers will be executed in parallel
 * In this simplistic case we only have one unit of work but we could have many
 */

public class Worker {

	UnitOfWork<?>[] unitOfWork;

	public Worker(UnitOfWork<?>... unitOfWork) {
		this.unitOfWork = unitOfWork;
	}

	public void go() {
		for (int x=0;x<unitOfWork.length;x++){
			unitOfWork[x].go();
		}
	}

}