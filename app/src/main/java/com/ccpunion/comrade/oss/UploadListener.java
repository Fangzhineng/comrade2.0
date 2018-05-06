package com.ccpunion.comrade.oss;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/28.
 */

public interface UploadListener {
    /**
     * 上传完成
     *
     * @param success
     */
//    void onUploadComplete(Map<String, Object> success, List<String> failure);

    void onUploadSuccess(Map<String, Object> success);

    void onUploadFailure(Map<String, Object> failure);

}