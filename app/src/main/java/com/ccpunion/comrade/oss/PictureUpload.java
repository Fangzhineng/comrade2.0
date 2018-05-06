package com.ccpunion.comrade.oss;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.ccpunion.comrade.constant.AppConstant;
import com.ccpunion.comrade.constant.HttpConstant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/28.
 */

public class PictureUpload {
    private static PictureUpload pictureUpload;

    private static String TAG = "PictureUpload";
    /**
     * 上传client
     */
    OSS oss;
    /**
     * 上传次数
     */
    int number;
    /**
     * 成功上传(本地文件名作为key,阿里云地址为value)
     */
    Map<String, Object> success = new HashMap<>();

    /**
     * 成功失败(本地文件名作为key,阿里云地址为value)
     */
    Map<String, Object> failure = new HashMap<>();

//    /**
//     * 失败上传(返回失败文件的本地地址)
//     */
//    List<String> failure = new ArrayList<>();
    /**
     * 上传回调
     */
    UploadListener uploadListener;
    /**
     * 上传任务列表
     */
    List<OSSAsyncTask> ossAsyncTasks = new ArrayList<>();
//    /**
//     * 自动更新Token
//     */
//    OSSCredentialProvider credetialProvider = new OSSFederationCredentialProvider() {
//        @Override
//        public OSSFederationToken getFederationToken() {
//            try {
//                //阿里鉴权请求
//                return new OSSFederationToken(HttpConstant.OSS_ACCESS_KEY_ID, HttpConstant.OSS_ACCESS_KEY_SECRET,
//                        "", HttpUtils.getTimeStamp());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    };

    OSSCredentialProvider credetialProvider = new OSSPlainTextAKSKCredentialProvider(HttpConstant.OSS_ACCESS_KEY_ID, HttpConstant.OSS_ACCESS_KEY_SECRET);

    /**
     * 单列模式
     *
     * @return
     */
    public static PictureUpload getInstance(Context context,String testBucket, List<String> uploadFilePaths, Handler handler) {
        if (pictureUpload == null) {
            pictureUpload = new PictureUpload(context,testBucket,uploadFilePaths,handler);
        }
        return pictureUpload;
    }


    private String testBucket;
    private List<String> uploadFilePaths;
    private WeakReference<Handler> handler;

    /**
     * 构造函数
     */
    public PictureUpload(Context context,String testBucket, List<String> uploadFilePaths, Handler handler) {
        this.testBucket = testBucket;
        this.uploadFilePaths = uploadFilePaths;
        this.handler = new WeakReference<Handler>(handler);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(20 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(20 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(3); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context, HttpConstant.OSS_END_POINT, credetialProvider, conf);
    }

//    /**
//     * 添加上传任务
//     *
//     * @param paths
//     * @param listener
//     */
//    public void setDatas(final List<String> paths, UploadListener listener) {
//        this.uploadListener = listener;
//        ossAsyncTasks.clear();
//        number = 1;
//        success.clear();
//        failure.clear();
//        for (String path : paths) {
//            final File file = new File(path);
//            /**
//             * 阿里云上文件名称
//             */
////            String objectKey = HttpUtils.getMyUUID() + "_" + SharePerefrence.getTokenKey() + ".jpg";
//
//            String objectKey = HttpUtils.getMyUUID() + "_" + ".jpg";
//            /**
//             * 用户自定义参数
//             */
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.addUserMetadata("filePath", file.getPath());
//            objectMetadata.addUserMetadata("fileName", file.getName());
//            objectMetadata.addUserMetadata("objectKey", objectKey);
//
//
//            PutObjectRequest put = new PutObjectRequest(HttpConstant.OSS_BUCKET_NAME, objectKey, file.getPath());
//            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//                @Override
//                public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
//
//                }
//            });
//            /**
//             * 上传任务
//             */
//            OSSAsyncTask task;
//            task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//                @Override
//                public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
//                    number++;
//                    String aliPath = formAliPath(putObjectRequest);
//                    success.put(putObjectRequest.getMetadata().getUserMetadata().get("fileName"), aliPath);
//                    if (number == paths.size()) {
//                        uploadListener.onUploadComplete(success, failure);
//                    }
//                }
//
//                @Override
//                public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
//                    number++;
//                    failure.add(putObjectRequest.getMetadata().getUserMetadata().get("filePath"));
//                    if (number == paths.size()) {
//                        uploadListener.onUploadComplete(success, failure);
//                    }
//                }
//            });
//            /**
//             * 添加到上传记录
//             */
//            ossAsyncTasks.add(task);
//        }
//    }



    // Resumable upload without checkpoint directory.
    public void upload(UploadListener listener) {
        this.uploadListener = listener;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ossAsyncTasks.clear();
                number = 0;
                success.clear();
                failure.clear();
                for (int i = 0; i < uploadFilePaths.size(); i++) {
                    String filePath = uploadFilePaths.get(i);
                    syncPutObject(testBucket, getFileName(filePath), filePath);
                }
                if (handler.get() != null) {
                    handler.get().sendEmptyMessage(AppConstant.UPLOAD_SUC);
                }
            }
        });
        thread.start();
    }


    public static String getFileName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('/');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    // 从本地文件上传，采用阻塞的同步接口
    public void syncPutObject(String bucket, String object, final String filePath) {
        Log.d(TAG, "filePath : " + filePath);
        Log.d(TAG, "object : " + object);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, filePath);

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                int item = (int) ((100 * currentSize) / totalSize);

            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                String aliPath = formAliPath(request);
                success.put(uploadFilePaths.get(number), aliPath);
                if (number == uploadFilePaths.size()-1) {
                    uploadListener.onUploadSuccess(success);
                }
                number++;
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                    info = clientException.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }

                failure.put(uploadFilePaths.get(number),info);
                if (number == uploadFilePaths.size()-1) {
                    uploadListener.onUploadFailure(failure);
                }

                number++;
            }
        });
        task.waitUntilFinished();
    }


    public void cancleTasks() {
        for (OSSAsyncTask task : ossAsyncTasks) {
            if (task.isCompleted()) {

            } else {
                task.cancel();
            }
        }
    }


    /**
     * 拼接远程访问地址
     *
     * @param putObjectRequest
     * @return
     */
    private String formAliPath(PutObjectRequest putObjectRequest) {
        return  HttpConstant.OSS_GET +  putObjectRequest.getObjectKey();
    }

}