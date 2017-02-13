package com.myolq.frame.loader;

import android.app.Application;
import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.myolq.frame.ErrorBean;
import com.myolq.frame.NetConfig;
import com.myolq.frame.Utils.GsonUtils;
import com.myolq.frame.callback.BitmapCallBack;
import com.myolq.frame.callback.DisposeCallBack;
import com.myolq.frame.callback.FileCallBack;
import com.myolq.frame.callback.GsonCallBack;
import com.myolq.frame.callback.HttpCallBack;
import com.myolq.frame.callback.StringCallBack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/3.
 */

public class OkgoLoader {

    private static OkgoLoader mOkgoUtils;
    private final DisposeCallBack disposeCallBack;
    private final HttpHeaders httpHeaders;

    private OkgoLoader() {
        disposeCallBack = new DisposeCallBack();
        httpHeaders = new HttpHeaders();
    }

    public static OkgoLoader getInstance(){
        if (mOkgoUtils==null){
            synchronized (OkgoLoader.class){
                if (mOkgoUtils==null){
                    mOkgoUtils=new OkgoLoader();
                }
            }
        }
        return mOkgoUtils;
    }


    public void init(Application app){
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//

        //必须调用初始化
        OkGo.init(app);
        getConfiguration();

    }

    /**
     * 全局设置
     */
    private void getConfiguration(){
        setHttpHeaders();
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

            //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

            //这两行同上，不需要就不要加入

                    .addCommonHeaders(httpHeaders)  //设置全局公共头
//                    .addCommonParams(params)   //设置全局公共参数
            ;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置全局公共头
     */
    private void setHttpHeaders(){
        httpHeaders.put(NetConfig.APPID_KEY,NetConfig.APPID_VALUE);
        httpHeaders.put(NetConfig.APIKEY_KEY,NetConfig.APIKEY_VALUE);
        httpHeaders.put(NetConfig.TYPE_KEY,NetConfig.TYPE_VALUE);
    }


    /**
     * 基本网络请求
     * @param url
     */
    public void sendByGet(String url, final GsonCallBack<?> callBack){
        OkGo.get(url)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        disposeCallBack.onSuccess(callBack,s,call,response);
                    }

                });
    }
    public void sendByGet(String url, final StringCallBack callBack){
        OkGo.get(url)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        disposeCallBack.onSuccess(callBack,s,call,response);
                    }

                });
    }

    /**
     * 请求 Bitmap 对象
     * @param url
     */
    public void sendByBitmap(String url, final BitmapCallBack callBack){
        OkGo.get(url)//
                .tag(this)//
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        // bitmap 即为返回的图片数据
                        disposeCallBack.onSuccess(callBack,bitmap,call,response);
                    }
                });
    }


    /**
     *请求 文件下载
     * @param url
     */
    public void sendByFile(String url, final FileCallBack callBack){
        OkGo.get(url)//
                .tag(this)//
                .execute(new FileCallback() {  //文件下载时，可以指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        disposeCallBack.onSuccess(callBack,file,call,response);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)

                    }
                });
    }


    /**
     * 普通Post，直接上传String类型的文本
     * @param url
     */
    public void sendByUploadingString(String url, String upString, final HttpCallBack callBack){
        OkGo.post(url)//
                .tag(this)//
//  .params("param1", "paramValue1")//  这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
                .upString(upString)//
//  .upString("这是要上传的长文本数据！", MediaType.parse("application/xml")) // 比如上传xml数据，这里就可以自己指定请求头
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        disposeCallBack.onSuccess(callBack,s,call,response);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }

    /**
     * 普通Post，直接上传Json类型的文本
     * @param url
     */
    public void sendByPostUploadingJson(String url,Object object,final GsonCallBack<?> callBack){
//        HashMap<String, String> params = new HashMap<>();
//        params.put("key1", "value1");
//        params.put("key2", "这里是需要提交的json格式数据");
//        params.put("key3", "也可以使用三方工具将对象转成json字符串");
//        params.put("key4", "其实你怎么高兴怎么写都行");
//        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(url)//
                .tag(this)//
                .upJson(GsonUtils.getBeanToJson(object))//
//                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        disposeCallBack.onSuccess(callBack,s,call,response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        disposeCallBack.onError(callBack,call, response, e);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }
    public void sendByPostUploadingJson(String url,Object object,final StringCallBack callBack){
//        HashMap<String, String> params = new HashMap<>();
//        params.put("key1", "value1");
//        params.put("key2", "这里是需要提交的json格式数据");
//        params.put("key3", "也可以使用三方工具将对象转成json字符串");
//        params.put("key4", "其实你怎么高兴怎么写都行");
//        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(url)//
                .tag(this)//
                .upJson(GsonUtils.getBeanToJson(object))//
//                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        disposeCallBack.onSuccess(callBack,s,call,response);
                    }




                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                    }
                });
    }


    /**
     *https请求(证书可以在全局初始化的时候设置,不用每次请求设置一遍)
     * @param url
     */
    public void getHttps(String url){
        OkGo.get("https://kyfw.12306.cn/otn")//
                .tag(this)//
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
//      .setCertificates()                             //方法一：信任所有证书
//      .setCertificates(getAssets().open("srca.cer")) //方法二：也可以设置https证书
                //方法三：传入bks证书,密码,和cer证书,支持双向加密
//      .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))

//                .execute(new HttpsCallBack(this))
        ;
    }



    public void cancelTag(String url){
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
    }



    public Response sendBySynGet(String url){
        Response response = null;
        try {
            response = OkGo.get("http://www.baidu.com")//
                    .tag(this)//
                    .headers("aaa", "111")//
                    .params("bbb", "222")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 公共参数
     * @param url
     */
    public void sendCommonality(String url){
        HttpHeaders headers = new HttpHeaders();
        headers.put("HKAAA", "HVAAA");
        headers.put("HKBBB", "HVBBB");
        HttpParams params = new HttpParams();
        params.put("PKAAA", "PVAAA");
        params.put("PKBBB", "PVBBB");

        OkGo.getInstance()
                .addCommonHeaders(headers) //设置全局公共头
                .addCommonParams(params);  //设置全局公共参数


    }



//    public void sendByGet(String url){
//        OkGo.get(Urls.URL_METHOD) // 请求方式和请求url, get请求不需要拼接参数，支持get，post，put，delete，head，options请求
//                .tag(this)               // 请求的 tag, 主要用于取消对应的请求
//                .connTimeOut(10000)      // 设置当前请求的连接超时时间
//                .readTimeOut(10000)      // 设置当前请求的读取超时时间
//                .writeTimeOut(10000)     // 设置当前请求的写入超时时间
//                .cacheKey("cacheKey")    // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheTime(5000)         // 缓存的过期时间,单位毫秒
//                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) // 缓存模式，详细请看第四部分，缓存介绍
//                .setCertificates(getAssets().open("srca.cer")) // 自签名https的证书，可变参数，可以设置多个
//                .addInterceptor(interceptor)            // 添加自定义拦截器
//                .headers("header1", "headerValue1")     // 添加请求头参数
//                .headers("header2", "headerValue2")     // 支持多请求头参数同时添加
//                .params("param1", "paramValue1")        // 添加请求参数
//                .params("param2", "paramValue2")        // 支持多请求参数同时添加
//                .params("file1", new File("filepath1")) // 可以添加文件上传
//                .params("file2", new File("filepath2")) // 支持多文件同时添加上传
//                .addUrlParams("key", List<String> values) //这里支持一个key传多个参数
//                .addFileParams("key", List<File> files) //这里支持一个key传多个文件
//                .addFileWrapperParams("key", List<HttpParams.FileWrapper> fileWrappers)//这里支持一个key传多个文件
//                .addCookie("aaa", "bbb")    // 这里可以传递自己想传的Cookie
//                .addCookie(cookie)          // 可以自己构建cookie
//                .addCookies(cookies)        // 可以一次传递批量的cookie
//                //这里给出的泛型为 ServerModel，同时传递一个泛型的 class对象，即可自动将数据结果转成对象返回
//                .execute(new DialogCallback<ServerModel>(this) {
//                    @Override
//                    public void onBefore(BaseRequest request) {
//                        // UI线程 请求网络之前调用
//                        // 可以显示对话框，添加/修改/移除 请求参数
//                    }
//
//                    @Override
//                    public ServerModel convertSuccess(Response response) throws Exception{
//                        // 子线程，可以做耗时操作
//                        // 根据传递进来的 response 对象，把数据解析成需要的 ServerModel 类型并返回
//                        // 可以根据自己的需要，抛出异常，在onError中处理
//                        return null;
//                    }
//
//                    @Override
//                    public void parseError(Call call, IOException e) {
//                        // 子线程，可以做耗时操作
//                        // 用于网络错误时在子线程中执行数据耗时操作,子类可以根据自己的需要重写此方法
//                    }
//
//                    @Override
//                    public void onSuccess(ServerModel serverModel, Call call, Response response) {
//                        // UI 线程，请求成功后回调
//                        // ServerModel 返回泛型约定的实体类型参数
//                        // call        本次网络的请求信息，如果需要查看请求头或请求参数可以从此对象获取
//                        // response    本次网络访问的结果对象，包含了响应头，响应码等
//                    }
//
//                    @Override
//                    public void onCacheSuccess(ServerModel serverModel, Call call) {
//                        // UI 线程，缓存读取成功后回调
//                        // serverModel 返回泛型约定的实体类型参数
//                        // call        本次网络的请求信息
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        // UI 线程，请求失败后回调
//                        // call        本次网络的请求对象，可以根据该对象拿到 request
//                        // response    本次网络访问的结果对象，包含了响应头，响应码等
//                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 404,或大于等于500
//                    }
//
//                    @Override
//                    public void onCacheError(Call call, Exception e) {
//                        // UI 线程，读取缓存失败后回调
//                        // call        本次网络的请求对象，可以根据该对象拿到 request
//                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 404,或大于等于500
//                    }
//
//                    @Override
//                    public void onAfter(ServerModel serverModel, Exception e) {
//                        // UI 线程，请求结束后回调，无论网络请求成功还是失败，都会调用，可以用于关闭显示对话框
//                        // ServerModel 返回泛型约定的实体类型参数，如果网络请求失败，该对象为　null
//                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 404,或大于等于500
//                    }
//
//                    @Override
//                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                        // UI 线程，文件上传过程中回调，只有请求方式包含请求体才回调（GET,HEAD不会回调）
//                        // currentSize  当前上传的大小（单位字节）
//                        // totalSize 　 需要上传的总大小（单位字节）
//                        // progress     当前上传的进度，范围　0.0f ~ 1.0f
//                        // networkSpeed 当前上传的网速（单位秒）
//                    }
//
//                    @Override
//                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                        // UI 线程，文件下载过程中回调
//                        //参数含义同　上传相同
//                    }
//                });
//    }


        public <T> void onSuccess(HttpCallBack<T> callback, String result, Call call, Response response){
            if (call!=null){
                if (call instanceof GsonCallBack){
                    GsonCallBack gsonCallBack=((GsonCallBack) call);
                    gsonCallBack.onSuccess(GsonUtils.getBeanFromJson(result,gsonCallBack.getType()),call,response);
                }else if(call instanceof StringCallBack){
                    ((StringCallBack) call).onSuccess(result,call,response);
                }else if (call instanceof FileCallBack){

                }else if (call instanceof BitmapCallBack){

                }
            }
        }

        public <T> void onError(HttpCallBack<T> callback, ErrorBean errorBean, Call call, Response response, Exception e){
            if(callback!=null)
            {
                callback.onError(call,errorBean,response,e);
            }

        }



}
