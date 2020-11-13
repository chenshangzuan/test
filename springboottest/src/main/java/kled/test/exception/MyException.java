package kled.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: Kled
 * @version: MyException.java, v0.1 2020-11-13 14:16 Kled
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="请求的网页不存在")
public class MyException extends Exception{
}
