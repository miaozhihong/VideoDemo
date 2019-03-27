package cn.com.yktour.network;
/**
 * @author sky
 * @date 2017/11/19
 * description:用于在Http请求开始时，自动显示一个ProgressDialog
 * changed:创建
 */
public class ApiException extends RuntimeException {
    public static final int errData = 0;
    public static final int SERVICE_ERROR5000 = 5000;
    public static final int SERVICE_ERROR5001 = 5001;
    public static final int SERVICE_ERROR404 = 404;
    public static final int WRONG_PASSWORD = 101;

    public int code;
    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case SERVICE_ERROR5000:
            case SERVICE_ERROR404:
                message = "服务器访问异常，请稍后重试";
                break;
            case SERVICE_ERROR5001:
                message = "请登录";
                break;
            default:
                message = "服务器访问异常，请稍后重试";
        }
        return message;
    }
}

