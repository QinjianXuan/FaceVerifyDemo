package com.ebgsplatform.badouloanapp.http.response;

import java.io.Serializable;

/**
 * Created by xuan on 2016/11/9.
 */
public class BaseResponse<T> implements Serializable {

    public T data;
    public String sign;



}
