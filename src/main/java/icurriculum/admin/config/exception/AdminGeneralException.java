package icurriculum.admin.config.exception;

import icurriculum.global.response.status.ErrorStatus;
import lombok.Getter;

@Getter
public class AdminGeneralException extends RuntimeException {

    private final ErrorStatus errorStatus;
    private final Object data;

    public AdminGeneralException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.data = null;
    }

    public AdminGeneralException(ErrorStatus errorStatus, Object data) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.data = data;
    }

    public AdminGeneralException(ErrorStatus errorStatus, Throwable cause) {
        super(errorStatus.getMessage(), cause);
        this.errorStatus = errorStatus;
        this.data = null;
    }

    public AdminGeneralException(ErrorStatus errorStatus, Object data, Throwable cause) {
        super(errorStatus.getMessage(), cause);
        this.errorStatus = errorStatus;
        this.data = data;
    }
}
