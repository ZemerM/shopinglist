package com.sap.icf.samples.shoppinglist.controller;

import com.sap.icd.odatav2.spring.exceptions.UiRelevantRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sap.icd.odatav2.spring.messages.Message;
import com.sap.icd.odatav2.spring.messages.Message.Severity;
import com.sap.icd.odatav2.spring.messages.MessageBuffer;
import com.sap.icf.samples.shoppinglist.service.ProductService.ProductAlreadyExistsException;
import com.sap.icf.samples.shoppinglist.service.ProductService.ProductIdNotNullException;
import com.sap.icf.samples.shoppinglist.service.ProductService.ProductNotFoundException;
import com.sap.icf.samples.shoppinglist.service.ProductService.ProductTextNotFoundException;
import com.sap.icf.samples.shoppinglist.service.ShoppingListService.ShoppingListAlreadyExistsException;
import com.sap.icf.samples.shoppinglist.service.ShoppingListService.ShoppingListIdNotNullException;
import com.sap.icf.samples.shoppinglist.service.ShoppingListService.ShoppingListNotFoundException;

@ControllerAdvice(assignableTypes = {ProductRestControllerImpl.class, ShoppingListRestControllerImpl.class})
public class ControllerExceptionHandler {

    private MessageBuffer msgBuffer;

    @Autowired
    public ControllerExceptionHandler(MessageBuffer msgBuffer) {
        super();
        this.msgBuffer = msgBuffer;
    }

    // -------------------------------------------------------------------------------------------------------
    // Validation Exceptions
    // -------------------------------------------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageBuffer handleException(MethodArgumentNotValidException ex) {
        msgBuffer.addMessage(Message.builder().code("rest.argumentNotValid").severity(Severity.ERROR).build());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Object[] args = {fieldError.getField()};
            msgBuffer.addMessage(
                    Message.builder().severity(Severity.ERROR).code("rest.fieldDoesNotExist").args(args).build());
        }
        return msgBuffer;
    }

    // -------------------------------------------------------------------------------------------------------
    // Evaluation
    // -------------------------------------------------------------------------------------------------------
    @ExceptionHandler(EvaluationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageBuffer handleException(EvaluationException ex) {
        Object[] args = {ex.getMessage()};
        msgBuffer.addMessage(
                Message.builder().code("spring.expressionEvaluationError").severity(Severity.ERROR).args(args).build());
        return msgBuffer;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageBuffer handleException(HttpMessageNotReadableException ex) {
        Object[] args = {ex.getMessage()};
        msgBuffer.addMessage(
                Message.builder().code("spring.httpMessageNotReadable").severity(Severity.ERROR).args(args).build());
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        if (mostSpecificCause != null) {
            String tmp = mostSpecificCause.getMessage();
            if (tmp != null) {
                Object[] args2 = {tmp};
                msgBuffer.addMessage(Message.builder().code("spring.httpMessageNotReadableCause")
                        .severity(Severity.ERROR).args(args2).build());
            }
        }
        return msgBuffer;
    }

    // -------------------------------------------------------------------------------------------------------
    // Product + Shopping List
    // -------------------------------------------------------------------------------------------------------
    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public MessageBuffer handleConfictException(ProductAlreadyExistsException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(ShoppingListAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public MessageBuffer handleConfictException(ShoppingListAlreadyExistsException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageBuffer handleNotFoundException(ProductNotFoundException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(ProductTextNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageBuffer handleTextNotFoundException(ProductTextNotFoundException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(ShoppingListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageBuffer handleNotFoundException(ShoppingListNotFoundException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(ShoppingListIdNotNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageBuffer handleIdNotNullException(ShoppingListIdNotNullException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(ProductIdNotNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageBuffer handleIdNotNullException(ProductIdNotNullException ex) {
        msgBuffer.addMessage(Message.builder().code(ex.getI18nKey()).severity(Severity.ERROR).args(ex.getArgs()).build());
        return msgBuffer;
    }

    @ExceptionHandler(UiRelevantRuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageBuffer handleUiRelevantException(UiRelevantRuntimeException ex) {
        msgBuffer.addMessage(ex.getUiRelevantMessage());
        return msgBuffer;
    }

    // -------------------------------------------------------------------------------------------------------
    // Everything else
    // -------------------------------------------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MessageBuffer handleException(Exception ex) {
        Object[] args = {ex.getMessage()};
        msgBuffer.addMessage(
                Message.builder().code("spring.notSpecificError").severity(Severity.ERROR).args(args).build());
        return msgBuffer;
    }
}
