package com.dev6am.todo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dev6am.todo.R;
import com.dev6am.todo.adapter.TaskAdapter;
import com.dev6am.todo.adapter.TaskViewPagerAdapter;
import com.dev6am.todo.model.Category;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.model.User;
import com.dev6am.todo.repository.CategoryRepository;
import com.dev6am.todo.util.DialogCategoryListener;
import com.dev6am.todo.util.GeneratorIdCategory;
import com.dev6am.todo.util.PageChangeTaskCategory;
import com.dev6am.todo.util.SelectListener;
import com.dev6am.todo.util.TabLayoutConfigure;
import com.dev6am.todo.util.TaskCategoryTabListener;
import com.dev6am.todo.viewmodel.MainViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements SelectListener, DialogCategoryListener {

    private MainViewModel mainViewModel;
    private RecyclerView rvTaks;
    private List<Task> taskList;
    private TabLayout tbLayout;
    private ViewPager2 viewPager2;
    private List<Category> categories;
    private Toolbar toolbar;
    private TaskViewPagerAdapter taskViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel= new MainViewModel();

        this.toolbar = findViewById(R.id.toolbar);


        this.toolbar.setOnMenuItemClickListener(item -> {

            if(item.getItemId()==R.id.itemBtn){

                AddCategoryDialog addCategoryDialog = new AddCategoryDialog(this);
                addCategoryDialog.show(getSupportFragmentManager(),"Dialogc Category");

                //TODO AGREGAR CODIGO DE ADD CATEGORY
                return true;
            }
            return false;
        });

        if(!this.fileCategoryExist(this)){
            this.createDefaultCategory(this);
        }



        this.categories = this.loadCategories();


        if(validateUserNotExist(this)){
            this.showDialogUser(); //ESTO ES UN PROCESO ASINCRONO
        }else {
            this.setInfoUser(this);
        }

        if (this.validateFileTaskExits(this)){
            this.taskList = this.mainViewModel.getTasks(this);
            //this.showInfoRecycleView(this.rvTaks);
        }else {
            this.taskList = Collections.emptyList();
        }

        tbLayout = findViewById(R.id.tabLayout1);
        viewPager2 = findViewById(R.id.viewPager);

        this.implementTabLyoutCategory();




    }



    public void showDialogTask(View view){
        //showDialogTask();
        showAddTaskActivity();
    }

    private void showAddTaskActivity(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);

    }


    /**
     * Muestra la ventana de dialogo la primera vez que el usuario ingrese su usuario
     * o lo quiera cambiar
     */
    private void showDialogUser(){

        DialogFragment dialogFragment = new AddUserDialog();
        dialogFragment.show(getSupportFragmentManager(),"Dialog Add User");
    }

    /**
     * Valida si no se ha creado un usuario
     * @return true : en caso de que no exista el usuario
     * @param context
     */
    private boolean validateUserNotExist(Context context){

        File file = new File(context.getFilesDir().getAbsolutePath(), "UserData.json");
        return !file.exists()|| file.length()<=0;
    }

    private boolean validateFileTaskExits(Context context){

        File file = new File(context.getFilesDir().getAbsolutePath(), "Tasks.json");
        return file.exists() && file.length()>0;
    }


    private void setInfoUser(Context context){

        User user = this.mainViewModel.getUser(context);

        Button button = findViewById(R.id.btnAdd);

        button.setText(user.getUserName());
    }


    private void showInfoRecycleView(RecyclerView rvTaks){

        TaskAdapter taskAdapter = new TaskAdapter(this.taskList,this);

        rvTaks.setLayoutManager(new LinearLayoutManager(this));
        rvTaks.setAdapter(taskAdapter);
    }

    /**
     * SE UTILIZA PARA ASIGNAR LA POSICION DE LA TAREA QUE SE QUIERE VER TODA LA INFORMACION
     * @param position
     * DEPRECATED
     */
    @Override
    public void setTaskSelectMoreInfoListener(int position) {

        Intent intent = new Intent(this,EditTaskActivity.class);
        intent.putExtra("ID_TASK",this.taskList.get(position).getId().toString());
        startActivity(intent);
        //Toast.makeText(this,"Title: "+this.taskList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
    }

    public List<Category> loadCategories(){

        CategoryRepository categoryRepository = new CategoryRepository();

        return categoryRepository.getCategories(this);
    }

    private void createDefaultCategory(Context context) {

        Category category = Category.builder()
                .id(GeneratorIdCategory.getGeneratorIdCategory().incrementAndGet())
                .color(getColor(R.color.categoryDefault))
                .name("Default")
                .build();

        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.addCategory(category,context);

    }

    private boolean fileCategoryExist(Context context){

        File file = new File(context.getFilesDir().getAbsolutePath(),"Categories.json");

        return file.exists() && file.length()>0;
    }

    private void implementTabLyoutCategory(){

        this.taskViewPagerAdapter = new TaskViewPagerAdapter(getSupportFragmentManager()
                , getLifecycle(), this.taskList, this.categories);

        PageChangeTaskCategory pageChangeTaskCategory= new PageChangeTaskCategory(tbLayout);

        TaskCategoryTabListener tabListener = new TaskCategoryTabListener(viewPager2);



        this.viewPager2.setAdapter(taskViewPagerAdapter);
        this.viewPager2.registerOnPageChangeCallback(pageChangeTaskCategory);

        this.tbLayout.addOnTabSelectedListener(tabListener);


        TabLayoutConfigure tabLayoutConfigure = new TabLayoutConfigure(this.categories);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tbLayout, viewPager2, tabLayoutConfigure);
        tabLayoutMediator.attach();

    }

    @Override
    public void updateCategory(Category category) {

        this.categories.add(category);

        CategoryRepository categoryRepository = new CategoryRepository();

        if(categoryRepository.addCategory(this.categories,this)){
            this.taskViewPagerAdapter.notifyItemInserted(this.categories.size()-1);
        }else {
            Toast.makeText(this,"can´t  save category",Toast.LENGTH_SHORT).show();
        }


    }
}