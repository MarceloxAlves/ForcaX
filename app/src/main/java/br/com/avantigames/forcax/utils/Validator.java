package br.com.avantigames.forcax.utils;

import android.widget.TextView;

import br.com.avantigames.forcax.R;

/**
 * Created by Marcelo on 11/05/2018.
 */

public class Validator {
    public static String validade(TextView editText) throws IllegalArgumentException {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.requestFocus();
            throw new IllegalArgumentException(editText.getContext().getString(R.string.exception_campo_obrigatorio));
        }
        return editText.getText().toString().trim();
    }
}
