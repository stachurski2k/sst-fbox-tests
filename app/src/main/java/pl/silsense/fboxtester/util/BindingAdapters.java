package pl.silsense.fboxtester.util;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

public class BindingAdapters {

    @BindingAdapter("app:error")
    public static void setErrorFromResource(TextInputLayout textInputLayout, @StringRes Integer resId) {
        if (resId != null) {
            textInputLayout.setErrorEnabled(true);
            Context context = textInputLayout.getContext();
            textInputLayout.setError(context.getString(resId));
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }
}