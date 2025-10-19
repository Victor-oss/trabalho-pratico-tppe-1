package com.trabalhopratico1.exception;

public class BusinessError {
	private BusinessError() {}
	public static final String LIST_SIZE_INVALID = "A lista deve conter exatamente 20 elementos.";
	public static final String NULL_LIST = "A lista de times não pode ser nula.";
	public static final String DUPLICATE_ELEMENTS = "A lista contém elementos duplicados: ";
}
