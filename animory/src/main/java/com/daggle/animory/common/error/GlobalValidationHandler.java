package com.daggle.animory.common.error;

import com.daggle.animory.common.error.exception.BadRequest400;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")  //  Post메소드에만 validationAdvice가 실행
    public void requestMapping() {
    }
    // 형식이 잘못된 요청이 들어오면 말 그대로 잘못된 요청이므로 400번대 에러를 넘긴다.
    @Before("requestMapping()")
    public void validationAdvice(JoinPoint jp) {
        List<Object> args = Arrays.stream(jp.getArgs()).toList();
        List<Errors> errors = extractErrors(args);
        for(Errors error : errors){
            if(error.hasErrors()){
                List<FieldError> fieldErrors = extractFieldErrors(error);
                String errorMessage = parsingErrorMessages(fieldErrors);
                throw new BadRequest400(
                        errorMessage
                );
            }
        }
    }

    private List<Errors> extractErrors(List<Object> args){
        List<Errors> errors = args.stream()
                .filter(arg -> arg instanceof Errors).map(arg -> (Errors)arg).collect(Collectors.toList());
        return errors;
    }
    private List<FieldError> extractFieldErrors(Errors errors){
        return errors.getFieldErrors();
    }

    private String parsingErrorMessages(List<FieldError> fieldErrors){
        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getDefaultMessage()+":"+error.getField())
                .collect(Collectors.toList());
        String parsingErrorMessages = errorMessages.toString().replace("[","").replace("]","");
        return parsingErrorMessages;
    }
}
