package com.daggle.animory.common.error;

import com.daggle.animory.common.error.exception.BadRequest400Exception;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")  //  Post메소드에만 validationAdvice가 실행
    public void requestMapping() {
    }
    // 형식이 잘못된 요청이 들어오면 말 그대로 잘못된 요청이므로 400번대 에러를 넘긴다.
    @Before("requestMapping()")
    public void validationAdvice(final JoinPoint jp) {
        final List<Object> args = Arrays.stream(jp.getArgs()).toList();
        final List<Errors> errors = extractErrors(args);
        for(final Errors error : errors){
            if(error.hasErrors()){
                final List<FieldError> fieldErrors = extractFieldErrors(error);
                final String errorMessage = parsingErrorMessages(fieldErrors);
                throw new BadRequest400Exception(
                        errorMessage
                );
            }
        }
    }

    private List<Errors> extractErrors(final List<Object> args){
        return args.stream()
            .map(Errors.class::cast)
            .toList();
    }
    private List<FieldError> extractFieldErrors(final Errors errors){
        return errors.getFieldErrors();
    }

    private String parsingErrorMessages(final List<FieldError> fieldErrors){
        final List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getDefaultMessage()+":"+error.getField())
                .toList();
        return errorMessages.toString().replace("[","").replace("]","");
    }
}
