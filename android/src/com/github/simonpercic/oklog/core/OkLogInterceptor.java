package com.github.simonpercic.oklog.core;

/**
 * @author Simon Percic <a href="https://github.com/simonpercic">https://github.com/simonpercic</a>
 */

import com.github.simonpercic.oklog.core.LogInterceptor;
import com.github.simonpercic.oklog.core.manager.LogManager;
import com.github.simonpercic.oklog.core.utils.Constants;
import com.github.simonpercic.oklog.core.utils.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpEngine;

/**
 * Interceptor for OkHttp3.
 * Call builder() to create an instance.
 *
 * @author Simon Percic <a href="https://github.com/simonpercic">https://github.com/simonpercic</a>
 */
public final class OkLogInterceptor implements Interceptor {

    private final LogManager logManager;

    private OkLogInterceptor(String logUrlBase, LogInterceptor logInterceptor, boolean useAndroidLog) {
        this.logManager = new LogManager(logUrlBase, logInterceptor, useAndroidLog);
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        
        Response response = chain.proceed(request);

        if (!HttpEngine.hasBody(response)) {
            return response;
        }

        MediaType contentType = response.body().contentType();
        String bodyString = response.body().string();

        logManager.log(bodyString);

        ResponseBody body = ResponseBody.create(contentType, bodyString);
        return response.newBuilder().body(body).build();
    }

    /**
     * Get a Builder instance.
     *
     * @return a Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    // region Builder

    public static final class Builder {

        private String logUrlBase;
        private LogInterceptor logInterceptor;
        private boolean useAndroidLog;

        private Builder() {
            this.logUrlBase = Constants.LOG_URL_BASE_REMOTE;
        }

        /**
         * Set the base url to 'http://localhost:8080'.
         *
         * @return Builder instance, to chain calls
         * @deprecated You can pass "http://localhost:8080" to {@link #setBaseUrl(String)} to achieve the same result.
         */
        @Deprecated
        public Builder setLocal() {
            return setBaseUrl(Constants.LOG_URL_BASE_LOCAL);
        }

        /**
         * Set the base url.
         * The set url should be without the trailing slash.
         * e.g. http://www.example.com
         *
         * @param url base url
         * @return Builder instance, to chain calls
         */
        public Builder setBaseUrl(String url) {
            if (StringUtils.isEmpty(url)) {
                return this;
            }

            this.logUrlBase = url;
            return this;
        }

        /**
         * Set whether to use Android's Log methods to log, instead of Timber.
         * Defaults to false.
         *
         * @param useAndroidLog true to use Android's Log methods, false to use Timber.
         * @return Builder instance, to chain calls
         */
        public Builder useAndroidLog(boolean useAndroidLog) {
            this.useAndroidLog = useAndroidLog;
            return this;
        }

        /**
         * Set a custom LogInterceptor.
         *
         * @param logInterceptor a instance of a LogInterceptor
         * @return Builder instance, to chain calls
         */
        public Builder setLogInterceptor(LogInterceptor logInterceptor) {
            this.logInterceptor = logInterceptor;
            return this;
        }

        /**
         * Build an instance of OkLogInterceptor.
         *
         * @return instance of OkLogInterceptor
         */
        public OkLogInterceptor build() {
            return new OkLogInterceptor(this.logUrlBase, this.logInterceptor, this.useAndroidLog);
        }
    }

    // endregion Builder
}
