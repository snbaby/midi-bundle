package com.seadun.midi.aas.exception;

@SuppressWarnings("serial")
public class MidiException extends RuntimeException {

	private final int statusCode;
	private final String message;

	public MidiException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
