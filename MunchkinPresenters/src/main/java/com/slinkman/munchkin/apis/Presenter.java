package com.slinkman.munchkin.apis;

public interface Presenter {
	/**
	 * Bind the pieces passed in and initialize the object
	 */
	public void bind();
	/**
	 * Presenter is about to be shutdown. Run persistence/cleanup actions
	 */
	public void onPause();
	/**
	 * Exception has been thrown
	 * @param ex
	 */
	public void onException(Exception ex);
	
	/**
	 * Implement the application handle.
	 * @return Application Handle
	 */
	public<U> U getAppHandle();
}
