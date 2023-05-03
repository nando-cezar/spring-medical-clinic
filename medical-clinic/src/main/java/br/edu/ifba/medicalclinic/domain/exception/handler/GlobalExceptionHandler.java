package br.edu.ifba.medicalclinic.domain.exception.handler;

import br.edu.ifba.medicalclinic.domain.exception.dto.ApiError;
import br.edu.ifba.medicalclinic.domain.exception.model.ContentNotAllowedException;
import br.edu.ifba.medicalclinic.domain.exception.model.MedicNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Provides handling for exceptions throughout this service.
     *
     * @param ex The target exception
     * @param request The current request
     */
    @ExceptionHandler({
            MedicNotFoundException.class,
            ContentNotAllowedException.class
    })
    @Nullable
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        LOGGER.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());

        if (ex instanceof MedicNotFoundException unfe) {
            var status = HttpStatus.NOT_FOUND;
            return handleMedicNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof ContentNotAllowedException cnae) {
            var status = HttpStatus.BAD_REQUEST;
            return handleContentNotAllowedException(cnae, headers, status, request);
        } else {
            if (LOGGER.isWarnEnabled())
                LOGGER.warn("Unknown exception type: " + ex.getClass().getName());

            var status = HttpStatus.INTERNAL_SERVER_ERROR;
            var error = Collections.singletonList(
                    "Unknown exception: " +
                    LocalDateTime.now() + ": " +
                    ex.getCause().getLocalizedMessage()
            );
            return handleExceptionInternal(ex, new ApiError(error), headers, status, request);
        }
    }

    /**
     * Customize the response for MedicNotFoundException.
     *
     * @param ex The exception
     * @param headers The headers to be written to the response
     * @param status The selected response status
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<ApiError> handleMedicNotFoundException(MedicNotFoundException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {
        var errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    /**
     * Customize the response for ContentNotAllowedException.
     *
     * @param ex The exception
     * @param headers The headers to be written to the response
     * @param status The selected response status
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<ApiError> handleContentNotAllowedException(ContentNotAllowedException ex,
                                                                        HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest request) {
        var errors = ex.getErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     *
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     *
     * @param ex The exception
     * @param body The body for the response
     * @param headers The headers for the response
     * @param status The response status
     * @param request The current request
     */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }

}
