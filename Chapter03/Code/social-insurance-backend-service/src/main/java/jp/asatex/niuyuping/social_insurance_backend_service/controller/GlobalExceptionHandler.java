package jp.asatex.niuyuping.social_insurance_backend_service.controller;

import jp.asatex.niuyuping.social_insurance_backend_service.controller.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 全局异常处理器
 * 采用 WebFlux 响应式编程风格，统一处理所有异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理通用异常（作为兜底处理器）
     *
     * @param ex      异常对象
     * @param exchange ServerWebExchange 对象，用于获取请求信息
     * @return Mono<ResponseEntity<ErrorResponseDTO>> 错误响应
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleException(Exception ex, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            // 输出异常信息到控制台
            LocalDateTime now = LocalDateTime.now();
            String path = exchange.getRequest().getPath().value();
            
            System.err.println("[" + now + "] 发生异常: " + ex.getClass().getSimpleName());
            System.err.println("异常消息: " + ex.getMessage());
            System.err.println("请求路径: " + path);
            ex.printStackTrace();

            // 构建错误响应（默认500错误）
            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .timestamp(now)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message(ex.getMessage() != null ? ex.getMessage() : "发生未知错误")
                    .path(path)
                    .build();

            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
        });
    }

    /**
     * 处理参数绑定异常（WebExchangeBindException）
     *
     * @param ex      参数绑定异常
     * @param exchange ServerWebExchange 对象
     * @return Mono<ResponseEntity<ErrorResponseDTO>> 错误响应
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleWebExchangeBindException(
            WebExchangeBindException ex, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            // 输出异常信息到控制台
            LocalDateTime now = LocalDateTime.now();
            String path = exchange.getRequest().getPath().value();
            
            System.err.println("[" + now + "] 发生参数绑定异常: " + ex.getClass().getSimpleName());
            System.err.println("异常消息: " + ex.getMessage());
            System.err.println("请求路径: " + path);
            ex.printStackTrace();

            // 构建详细的错误消息
            StringBuilder errorMessage = new StringBuilder("参数验证失败: ");
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            });

            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .timestamp(now)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(errorMessage.toString())
                    .path(path)
                    .build();

            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));
        });
    }

    /**
     * 处理 ResponseStatusException（Spring WebFlux 的响应状态异常）
     *
     * @param ex      ResponseStatusException
     * @param exchange ServerWebExchange 对象
     * @return Mono<ResponseEntity<ErrorResponseDTO>> 错误响应
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleResponseStatusException(
            ResponseStatusException ex, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            // 输出异常信息到控制台
            LocalDateTime now = LocalDateTime.now();
            String path = exchange.getRequest().getPath().value();
            
            System.err.println("[" + now + "] 发生响应状态异常: " + ex.getClass().getSimpleName());
            System.err.println("异常消息: " + ex.getMessage());
            System.err.println("HTTP状态码: " + ex.getStatusCode());
            System.err.println("请求路径: " + path);
            ex.printStackTrace();

            HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .timestamp(now)
                    .status(status.value())
                    .error(status.getReasonPhrase())
                    .message(ex.getReason() != null ? ex.getReason() : ex.getMessage())
                    .path(path)
                    .build();

            return Mono.just(ResponseEntity.status(status).body(errorResponse));
        });
    }

    /**
     * 处理 IllegalArgumentException（非法参数异常）
     *
     * @param ex      IllegalArgumentException
     * @param exchange ServerWebExchange 对象
     * @return Mono<ResponseEntity<ErrorResponseDTO>> 错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleIllegalArgumentException(
            IllegalArgumentException ex, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            // 输出异常信息到控制台
            LocalDateTime now = LocalDateTime.now();
            String path = exchange.getRequest().getPath().value();
            
            System.err.println("[" + now + "] 发生非法参数异常: " + ex.getClass().getSimpleName());
            System.err.println("异常消息: " + ex.getMessage());
            System.err.println("请求路径: " + path);
            ex.printStackTrace();

            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .timestamp(now)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(ex.getMessage())
                    .path(path)
                    .build();

            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));
        });
    }

    /**
     * 处理 NullPointerException（空指针异常）
     *
     * @param ex      NullPointerException
     * @param exchange ServerWebExchange 对象
     * @return Mono<ResponseEntity<ErrorResponseDTO>> 错误响应
     */
    @ExceptionHandler(NullPointerException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleNullPointerException(
            NullPointerException ex, ServerWebExchange exchange) {
        return Mono.defer(() -> {
            // 输出异常信息到控制台
            LocalDateTime now = LocalDateTime.now();
            String path = exchange.getRequest().getPath().value();
            
            System.err.println("[" + now + "] 发生空指针异常: " + ex.getClass().getSimpleName());
            System.err.println("异常消息: " + ex.getMessage());
            System.err.println("请求路径: " + path);
            ex.printStackTrace();

            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .timestamp(now)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message("发生空指针异常: " + (ex.getMessage() != null ? ex.getMessage() : "未知错误"))
                    .path(path)
                    .build();

            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
        });
    }
}

