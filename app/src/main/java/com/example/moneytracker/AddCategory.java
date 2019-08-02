package com.example.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneytracker.Database.Category;

import java.util.ArrayList;
import java.util.List;

public class AddCategory extends AppCompatActivity {
    private ArrayList<SymbolItem> mSymbolList;
    private SymbolAdapter mAdapter;
    EditText addNewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        category_refresh();

        initList();

        final Spinner spinnerSymbols = findViewById(R.id.symbols);

        mAdapter = new SymbolAdapter(this, mSymbolList);
        spinnerSymbols.setAdapter(mAdapter);

        spinnerSymbols.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SymbolItem clickedItem = (SymbolItem) parent.getItemAtPosition(position);
                Toast.makeText(AddCategory.this, "Symbol selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addNewCategory =findViewById(R.id.add_new_category);

        Button saveCategoryButton = findViewById(R.id.save_button_category);
        saveCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mIcon =  spinnerSymbols.getSelectedItemPosition();
                int mIconData = mAdapter.getItem(mIcon).getmSymbolIcon();
                String categoryName = addNewCategory.getText().toString();

                if ((categoryName.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Enter new category", Toast.LENGTH_SHORT).show();
                }
                else{
                    Category category = new Category();
                    category.setCategoryName(categoryName);
                    category.setIcon(mIconData);
                    MainActivity.catDB.daoAccess().insert(category);
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
                    category_refresh();
                    onRestart();
                    finish();
                }
            }
        });


    }
    private void initList(){
        mSymbolList = new ArrayList<>();
        mSymbolList.add(new SymbolItem(R.drawable.ic_grocery_image));
        mSymbolList.add(new SymbolItem(R.drawable.ic_rent_image));
        mSymbolList.add(new SymbolItem(R.drawable.books));
        mSymbolList.add(new SymbolItem(R.drawable.ic_gadgets_image));
        mSymbolList.add(new SymbolItem(R.drawable.ic_shopping_image));
        mSymbolList.add(new SymbolItem(R.drawable.ic_insurance_image));
        mSymbolList.add(new SymbolItem(R.drawable.ic_movie_image));
        mSymbolList.add(new SymbolItem(R.drawable.ic_furniture_image));

    }


    private void category_refresh(){
        List<Category> categories = null;
        categories = MainActivity.catDB.daoAccess().getAllCategories();
        System.out.println(categories);
        Spinner category = findViewById(R.id.category3_add);

        List<String> list = new ArrayList<String>();
        for(Category c : categories){
            list.add(c.getCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);
    }

}