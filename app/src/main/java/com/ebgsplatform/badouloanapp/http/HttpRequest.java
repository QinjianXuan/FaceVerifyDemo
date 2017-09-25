package com.ebgsplatform.badouloanapp.http;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by xuan on 2016/11/4.
 */
public class HttpRequest extends IOException {

    private static JSONObject jsonObj;


    public HttpRequest() {
    }

    public static JSONObject mapToJSON(Map map) {
        JSONObject jsonParams = new JSONObject(map);

        return jsonParams;
    }






    /**
     * 上传key - file 类型文件 一个key对应一个文件
     *
     * @param url      请求url
     * @param context  指定Context
     * @param params   参数集合
     * @param callback 文件上传回调
     */
    public static void formUpload(String url, Context context, HttpParams params, AbsCallback callback) {


        OkGo.post(url)
                .tag(context)
                .params(params)
                .execute(callback);


    }




}
