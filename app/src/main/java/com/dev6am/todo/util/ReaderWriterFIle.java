package com.dev6am.todo.util;

import android.util.Log;

import com.dev6am.todo.model.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ReaderWriterFIle {

    public static boolean writeFileJson(String dataJson, String pathFile, String nameFile){

        try {



            File file = new File(pathFile,nameFile); //CARGA ARCHIVO
            FileOutputStream fo = new FileOutputStream(file); //ASIGNA EL ARCHIVO EN EL QUE SE VA A ESCRIBIR
            OutputStreamWriter osw = new OutputStreamWriter(fo, StandardCharsets.UTF_8); //PARA QUE SE PUEDA ESCRIBIR CARACTERES Y QUE LA CODIFICACION SEA CORRECTA

            osw.write(dataJson);
            osw.close();

            return true;

        }catch (IOException exception){

            exception.printStackTrace();
            return false;
        }

    }


    public static String readFileJson(String pathFile,String nameFile){

        try {
            File file = new File(pathFile,nameFile);

            FileInputStream fi = new FileInputStream(file);//ASIGNA EL ARCHIVO QUE SE VA A LEER
            InputStreamReader isr = new InputStreamReader(fi,StandardCharsets.UTF_8);

            StringBuilder stringBuilder = new StringBuilder();

            int i=0;

            while ((i=isr.read())!= -1){
                stringBuilder.append((char) i);
            }

            isr.close();
            fi.close();

            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
