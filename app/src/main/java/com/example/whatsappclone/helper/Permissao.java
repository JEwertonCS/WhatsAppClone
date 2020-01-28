package com.example.whatsappclone.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode){

        //Verificar se a versão é maior que 23 (Marshmallow) para pedir as permissões
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<>();

            //Percorre as permissões passadas, verificando uma a uma se já tem permissão liberada
            for (String permissao : permissoes ){
                Boolean temPermissao = ContextCompat.checkSelfPermission( activity, permissao ) == PackageManager.PERMISSION_GRANTED;
                if ( !temPermissao ){
                    listaPermissoes.add( permissao );
                }
            }

            //Caso lista esteja vazia, não será necessario pedir permissão
            if ( listaPermissoes.isEmpty() ) { return  true; }

            String[] novasPermissoes = new String[ listaPermissoes.size() ];
            listaPermissoes.toArray( novasPermissoes );

            //Solicita permissão
            ActivityCompat.requestPermissions( activity, novasPermissoes, requestCode );
        }

        return true;
    }

}
