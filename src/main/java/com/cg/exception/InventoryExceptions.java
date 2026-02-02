package com.cg.exception;

/**
 * Base domain exception for Inventory operations with simple error codes.
 * This single file contains nested specific exceptions so you only keep ONE file.
 */
public class InventoryExceptions extends RuntimeException {

    private final String code;

    public InventoryExceptions(String code, String message) {
        super(message);
        this.code = code;
    }

    public InventoryExceptions(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /** 404-like: resource not found */
    public static class NotFound extends InventoryExceptions {
        public NotFound(Long id) {
            super("INV_NOT_FOUND", "Inventory with id " + id + " not found");
        }
        public NotFound(String message) {
            super("INV_NOT_FOUND", message);
        }
    }

    /** 400-like: invalid arguments, missing fields, etc. */
    public static class BadRequest extends InventoryExceptions {
        public BadRequest(String message) {
            super("INV_BAD_REQUEST", message);
        }
    }

    /** 409-like: state conflicts (optional, use if needed) */
    public static class Conflict extends InventoryExceptions {
        public Conflict(String message) {
            super("INV_CONFLICT", message);
        }
    }
}