package com.daggle.animory.common.errors;

import com.daggle.animory.common.errors.exception.BadRequest400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class GlobalValidationHandler {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")  //  Post메소드에만 validationAdvice가 실행
    public void postMapping() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMapping(){
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void patchMapping(){
    }
    // 형식이 잘못된 요청이 들어오면 말 그대로 잘못된 요청이므로 400번대 에러를 넘긴다.
    @Before("postMapping()||getMapping()||patchMapping()")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                if(errors.hasErrors()){
                    List<FieldError> fieldErrors = errors.getFieldErrors();
                    List<String> errorMessages = new ArrayList<>();
                    for(FieldError error : fieldErrors) {
                        errorMessages.add(error.getDefaultMessage()+":"+error.getField());
                    }
                    String parsingErrorMessages = errorMessages.toString().replace("[","").replace("]","");
                    throw new BadRequest400(
                            parsingErrorMessages
                    );
                }
            }
        }
    }
}
