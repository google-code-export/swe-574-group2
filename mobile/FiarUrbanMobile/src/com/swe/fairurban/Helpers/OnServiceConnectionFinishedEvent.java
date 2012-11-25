package com.swe.fairurban.Helpers;

public abstract class OnServiceConnectionFinishedEvent {
	public abstract void ConnectionFinished(String result);
	public abstract void ExceptionOccured();
}
