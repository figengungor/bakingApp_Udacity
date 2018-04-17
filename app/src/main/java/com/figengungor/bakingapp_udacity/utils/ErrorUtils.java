package com.figengungor.bakingapp_udacity.utils;

import android.content.Context;

import com.figengungor.bakingapp_udacity.R;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by figengungor on 4/17/2018.
 */

public class ErrorUtils {

    public static final String EMPTY = "empty";
    public static final String SERVER_ERROR = "server_error";

    public static String displayFriendlyErrorMessage(Context context, Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            return context.getString(R.string.time_out);
        } else if (throwable instanceof IOException) {
            return context.getString(R.string.no_internet);
        } else if (throwable.getMessage() != null) {
            String message = throwable.getMessage();
            if (message.equals(EMPTY)) {
                return context.getString(R.string.no_data_found);
            } else if (message.equals(SERVER_ERROR)) {
                return context.getString(R.string.server_error);
            } else {
                return message;
            }
        } else {
            return context.getString(R.string.unexpected_error);
        }
    }

}
