package com.example.whatsappclone.helper;

import android.util.Base64;

import javax.sql.StatementEvent;

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString( texto.getBytes(), Base64.DEFAULT ).replaceAll("\\n|\\r", "");
    }

    public static String decodificarBase64(String textoCodificado){
        return Base64.encodeToString( textoCodificado.getBytes(), Base64.DEFAULT );
    }

}
