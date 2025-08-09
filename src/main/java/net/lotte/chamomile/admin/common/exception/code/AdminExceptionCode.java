package net.lotte.chamomile.admin.common.exception.code;

import net.lotte.chamomile.core.exception.BusinessCode;

public enum AdminExceptionCode implements BusinessCode {

    ServerError(500, "server error"),

    BadRequest(400, "Bad Request."),

    AuthenticationFailed(1078, "Authentication failed."),
    DataNotFoundError(1063, "Requested data not found."),
    MissingParameter(1082, "Missing required parameter.");

    private final int code;

    private final String codeDescription;

    AdminExceptionCode(int code, String codeDescription) {
        this.code = code;
        this.codeDescription = codeDescription;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public final String toString() {
        return this.codeDescription;
    }
}
