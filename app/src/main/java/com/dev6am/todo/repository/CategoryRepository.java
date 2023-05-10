package com.dev6am.todo.repository;

import android.content.Context;

import com.dev6am.todo.model.Category;
import com.dev6am.todo.model.Data;
import com.dev6am.todo.util.ConverterJson;
import com.dev6am.todo.util.ReaderWriterFIle;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private final String fileName="Category.json";

    /**
     * ESTE METODO SOLO SE UTILIZARA CUANDO SE INICIE LA APP Y SE CREE LA CATEGORIA POR DEFECTO
     * @param category
     * @param context
     * @return
     */
    public boolean addCategory(Category category, Context context){

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        Data data = Data.builder()
                .data(categories)
                .build();

        String dataJson = ConverterJson.DataToJson(data);

        return ReaderWriterFIle.writeFileJson(dataJson,context.getFilesDir().getAbsolutePath(),fileName);
    }

    /**
     * CUANDO EL USUARIO DESEE AGREGAR MAS CATEGORIAS SE UTILIZARA ESTE METODO
     * @param categories
     * @param context
     * @return
     */
    public boolean addCategory(List<Category> categories, Context context){

        Data data = Data.builder()
                .data(categories)
                .build();

        String dataJson = ConverterJson.DataToJson(data);

        return ReaderWriterFIle.writeFileJson(dataJson,context.getFilesDir().getAbsolutePath(),fileName);
    }

    /**
     * PARA OBTENER TODAS LAS CATEGORIAS DISPONIBLES
     * @param context
     * @return
     */
    public List<Category> getCategories(Context context){

        String dataList = ReaderWriterFIle.readFileJson(context.getFilesDir().getAbsolutePath(),this.fileName);

        return ConverterJson.jsonToDataList(dataList,Category[].class);
    }
}
