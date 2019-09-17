package com.example.animarol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class EnemyCreatorFormActivity extends AppCompatActivity {
    // Variables
    String[] categoriesList = {"Normal Enemy","Boss"};
    Spinner categoriesSpinner;
    boolean isCreation = true;
    EnemyEntity enemyToModify;

    //Datos del enemigo
    String category;
    String name;
    int level;
    int health;
    int strength;
    int agility;
    int constitution;
    int dexterity;
    int weapon;
    int armor;
    int attack;
    int defense;

    //Métodos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemy_creator_form);
        checkCreationOrModification();
        StartFormElements();
    }

    //Métodos de la ejecución
    private void checkCreationOrModification() {
            enemyToModify = (EnemyEntity)getIntent().getSerializableExtra("EnemyToModify");
            if (enemyToModify != null){
                isCreation= false;

                enemyToModify.ShowEnemyData();
            } else {
                isCreation = true;
            }
    }

    private void StartFormElements() {

        // Introduciendo las diferentes categorias disponibles de enemigos que hay.
        // Para introducir nuevas categorías modificar el Array categoriesList.
        categoriesSpinner = (Spinner)findViewById(R.id.ECFSpinnerCategory);
        // Se crea un adaptador que hace de intermediario entre los elementos de una lista y el propio spinner.
        ArrayAdapter adpt = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,categoriesList);
        // Se añade el adaptador al propio spinner.
        categoriesSpinner.setAdapter(adpt);
        TextView formTitle = (TextView) findViewById(R.id.ECFTitle);
        // Cambio de los componentes de la interfaz
        if (isCreation){
            formTitle.setText("Entity Creator");
        } else {
            formTitle.setText("Entity Modificator");

            // Se introducen todos los elementos que vienen del archivo a modificiar.
            EditText formName = (EditText) findViewById(R.id.ECFEditTextName);
            formName.setText(enemyToModify.name);

            Spinner formCategory = (Spinner) findViewById(R.id.ECFSpinnerCategory);
            formCategory.setBackgroundColor(Color.YELLOW);

            EditText formLevel = (EditText) findViewById(R.id.ECFEditTextLevel);
            formLevel.setText(Integer.toString(enemyToModify.level));

            EditText formHealth = (EditText) findViewById(R.id.ECFEditTextHealth);
            formHealth.setText(Integer.toString(enemyToModify.health));

            EditText formStrength = (EditText) findViewById(R.id.ECFEditTextStrength);
            formStrength.setText(Integer.toString(enemyToModify.strength));

            EditText formAgility = (EditText) findViewById(R.id.ECFEditTextAgility);
            formAgility.setText(Integer.toString(enemyToModify.agility));

            EditText formConstitution = (EditText) findViewById(R.id.ECFEditTextConstitution);
            formConstitution.setText(Integer.toString(enemyToModify.constitution));

            EditText formDexterity = (EditText) findViewById(R.id.ECFEditTextDexterity);
            formDexterity.setText(Integer.toString(enemyToModify.dexterity));

            EditText formWeapon= (EditText) findViewById(R.id.ECFEditTextWeapon);
            formWeapon.setText(Integer.toString(enemyToModify.weapon));

            EditText formArmor= (EditText) findViewById(R.id.ECFEditTextArmor);
            formArmor.setText(Integer.toString(enemyToModify.armor));

            EditText formAttack= (EditText) findViewById(R.id.ECFEditTextAttack);
            formAttack.setText(Integer.toString(enemyToModify.attack));

            EditText formDefense= (EditText) findViewById(R.id.ECFEditTextDefense);
            formDefense.setText(Integer.toString(enemyToModify.defense));
        }
    }
    // Métodos de eventos
    // si no hay errores en el formulario guardará la entidad en un archivo en el movil y se dirigirá al menu MasterMenu
    public void SaveEntityButton (View view){
        if (CheckInputs()){
            CheckEntityFile();
            Toast.makeText(getApplicationContext(),"Entidad Guardada",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,MasterMenu.class);
            startActivity(intent);
        } else{
            Toast.makeText(getApplicationContext(),"No se han rellenado todos los campos",Toast.LENGTH_LONG).show();
        }

    }

    private void CheckEntityFile() {
        EditText name = (EditText) findViewById(R.id.ECFEditTextName);
        String nameText = name.getText().toString();

        try {
            // Aquí comprobaremos si estamos creando un enemigo nuevo o modificando uno
            if (isCreation){
                // TODO comprobar si poniendo la extension .enemy si detecta el mismo nombre.
                //TODO deberian registrarse todos en mayuscula o minuscula para evitar diferencias de esa manera.
                FileInputStream FIS = openFileInput(nameText);// esto busca el archivo en modo creación, si no lo encuentra salta la excepción y lo crea, en cambio si lo encuentra simplemente muestra un toast de error. y no produce cambios.
                Toast.makeText(getApplicationContext(),"Ya existe un enemigo con ese nombre",Toast.LENGTH_LONG).show();
                name.setBackgroundColor(Color.RED);
            }else{
                saveEntity(nameText);
            }


        } catch (FileNotFoundException e) {
            saveEntity(nameText);
        }
    }

    private void saveEntity(String nameEntity) {
        try {

            String fileName = nameEntity+".enemy";
            FileOutputStream FOS = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream OS = new ObjectOutputStream(FOS);
            OS.writeObject(new EnemyEntity(category, name, level, health, strength, agility, constitution, dexterity, weapon, armor, attack, defense));
            OS.close();
            FOS.close();
        } catch (Exception i){
            Log.d("Save Exception",i.toString());
        }
    }

    // Comprueba todos los inputs del formulario, si hay alguno vacío no dejará continuar y marcará en rojo los errores.
    private boolean CheckInputs() {
        boolean isCorrect= true;
        //Control del nombre
        EditText formName = (EditText) findViewById(R.id.ECFEditTextName);
        name = formName.getText().toString();
        if (name.equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formName.setBackgroundColor(Color.RED);
        } else {
            formName.setBackgroundColor(Color.WHITE);
        }
        // Control de la categoría
        Spinner formCategory = (Spinner) findViewById(R.id.ECFSpinnerCategory);
        category = formCategory.getSelectedItem().toString();
        if (category.equals(FormControl.EMPTYSTRING)){
            isCorrect = false;
            formCategory.setBackgroundColor(Color.RED);
        }else {
            formCategory.setBackgroundColor(Color.WHITE);
        }
        //Contol de nivel
        EditText formLevel = (EditText) findViewById(R.id.ECFEditTextLevel);

        if (formLevel.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formLevel.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formLevel.getText().toString())) {
                level = Integer.parseInt(formLevel.getText().toString()) ;
                formLevel.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formLevel.setBackgroundColor(Color.RED);
            }
        }
        //Contol de vida
        EditText formHealth = (EditText) findViewById(R.id.ECFEditTextHealth);

        if (formHealth.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formHealth.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formHealth.getText().toString())) {
                health = Integer.parseInt(formHealth.getText().toString()) ;
                formHealth.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formHealth.setBackgroundColor(Color.RED);
            }
        }
        //Contol de fuerza
        EditText formStrength = (EditText) findViewById(R.id.ECFEditTextStrength);

        if (formStrength.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formStrength.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formStrength.getText().toString())) {
                strength = Integer.parseInt(formStrength.getText().toString()) ;
                formStrength.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formStrength.setBackgroundColor(Color.RED);
            }
        }
        // Control de Agilidad
        EditText formAgility = (EditText) findViewById(R.id.ECFEditTextAgility);

        if (formAgility.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formAgility.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formAgility.getText().toString())) {
                agility = Integer.parseInt(formAgility.getText().toString()) ;
                formAgility.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formAgility.setBackgroundColor(Color.RED);
            }
        }
        //Contol de Constitución
        EditText formConstitution = (EditText) findViewById(R.id.ECFEditTextConstitution);

        if (formConstitution.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formConstitution.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formConstitution.getText().toString())) {
                constitution = Integer.parseInt(formConstitution.getText().toString()) ;
                formConstitution.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formConstitution.setBackgroundColor(Color.RED);
            }
        }
        //Contol de Destreza
        EditText formDexterity = (EditText) findViewById(R.id.ECFEditTextDexterity);

        if (formDexterity.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formDexterity.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formDexterity.getText().toString())) {
                dexterity = Integer.parseInt(formDexterity.getText().toString()) ;
                formDexterity.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formDexterity.setBackgroundColor(Color.RED);
            }
        }
        //Contol de Arma
        EditText formWeapon= (EditText) findViewById(R.id.ECFEditTextWeapon);

        if (formWeapon.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formWeapon.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formWeapon.getText().toString())) {
                weapon = Integer.parseInt(formWeapon.getText().toString()) ;
                formWeapon.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formWeapon.setBackgroundColor(Color.RED);
            }
        }
        //Contol de Armadura
        EditText formArmor= (EditText) findViewById(R.id.ECFEditTextArmor);

        if (formArmor.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formArmor.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formArmor.getText().toString())) {
                armor = Integer.parseInt(formArmor.getText().toString()) ;
                formArmor.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formArmor.setBackgroundColor(Color.RED);
            }
        }
        //Contol de Ataque
        EditText formAttack= (EditText) findViewById(R.id.ECFEditTextAttack);

        if (formAttack.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formAttack.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formAttack.getText().toString())) {
                attack = Integer.parseInt(formAttack.getText().toString()) ;
                formAttack.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formAttack.setBackgroundColor(Color.RED);
            }
        }
        //Contol de Defensa
        EditText formDefense= (EditText) findViewById(R.id.ECFEditTextDefense);

        if (formDefense.getText().toString().equals(FormControl.EMPTYSTRING)) {
            isCorrect = false;
            formDefense.setBackgroundColor(Color.RED);
        } else {
            if (FormControl.IsANumber(formDefense.getText().toString())) {
                defense = Integer.parseInt(formDefense.getText().toString()) ;
                formDefense.setBackgroundColor(Color.WHITE);
            } else{
                isCorrect = false;
                formDefense.setBackgroundColor(Color.RED);
            }
        }
        return isCorrect;
    }
}
