package com.xinding.travel.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @Description: 未知驻户错误Exception
 */
public class UnknownCustomerException extends AuthenticationException {
	 private static final long serialVersionUID = 1L;

     public UnknownCustomerException() {
          super();
     }

     public UnknownCustomerException(String message, Throwable cause) {
          super(message, cause);
     }

     public UnknownCustomerException(String message) {
          super(message);
     }

     public UnknownCustomerException(Throwable cause) {
          super(cause);
     }
}
