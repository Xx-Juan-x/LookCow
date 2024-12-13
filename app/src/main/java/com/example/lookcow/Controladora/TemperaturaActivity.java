package com.example.lookcow.Controladora;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.lookcow.Modelo.Temperatura;
import com.example.lookcow.R;
import com.example.lookcow.databinding.ActivityTemperaturaBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TemperaturaActivity extends DrawerBaseActivity {

    private EditText txtid, txtTempActual, txtTempMax, txtTemMin, txtRangoTemp, txtestado;
    private Button btnbus, btnmod, btnreg, btneli, btnBusActual, btnBusMax;
    private ListView lvDatos;

    ActivityTemperaturaBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityTemperaturaBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

        txtid   = findViewById(R.id.txtid);
        txtTempActual = findViewById(R.id.txtTempActual);
        txtTempMax = findViewById(R.id.txtTempMax);
        txtTemMin = findViewById(R.id.txtTempMin);
        //txtRangoTemp = findViewById(R.id.txtRangoTemp);
        txtestado   = findViewById(R.id.txtestado);

        btnbus  = findViewById(R.id.btnbus);
        btnmod  = findViewById(R.id.btnmod);
        btnreg  = findViewById(R.id.btnreg);
        btneli  = findViewById(R.id.btneli);
        lvDatos = findViewById(R.id.lvDatos);
        btnBusActual = findViewById(R.id.btnBusActual);
        //btnBusMax = findViewById(R.id.btnbusMax);

        botonBuscar();
        botonBuscarTemperatura();
        //botonBuscarRaza();
        botonModificar();
        botonRegistrar();
        botonEliminar();
        listarTemperaturas();

    }   //cierra el onCreate

    private void botonBuscar(){

        btnbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtid.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(TemperaturaActivity.this, "Escriba una Identificacion para BUSCAR!!!", Toast.LENGTH_SHORT).show();

                } else {
                    int id = Integer.parseInt(txtid.getText().toString());
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = db.getReference(Temperatura.class.getSimpleName());
                    // DatabaseReference databaseReference = db.getReference().child("Usuario");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String auxId = Integer.toString(id);
                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()) {

                                if (auxId.equalsIgnoreCase(x.child("idSensor").getValue().toString())) {
                                    res = true;
                                    ocultarTeclado();
                                    txtTempActual.setText(x.child("temperaturaActual").getValue().toString());
                                    txtTempMax.setText(x.child("temperaturaMax").getValue().toString());
                                    txtTemMin.setText(x.child("temperaturaMin").getValue().toString());
                                    //txtRangoTemp.setText(x.child("rangoTemperatura").getValue().toString());
                                    txtestado.setText(x.child("estado").getValue().toString());
                                    break;
                                }

                            }
                            if (!res){
                                ocultarTeclado();
                                Toast.makeText(TemperaturaActivity.this, "ID ("+auxId+") no encontrado!!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } //cierra el if/else
            }
        });

    }//cierra el metodo boton buscar x ID
    private void botonBuscarTemperatura(){

        btnBusActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtTempActual.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(TemperaturaActivity.this, "Escriba una Temperatura para BUSCAR!!!", Toast.LENGTH_SHORT).show();

                } else {

                    String tempActual = txtTempActual.getText().toString();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = db.getReference(Temperatura.class.getSimpleName());
                    // DatabaseReference databaseReference = db.getReference().child("Usuario");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()) {

                                if (tempActual.equalsIgnoreCase(x.child("temperaturaActual").getValue().toString())) {
                                    res = true;
                                    ocultarTeclado();
                                    txtid.setText(x.child("idSensor").getValue().toString());
                                    txtTempMax.setText(x.child("temperaturaMax").getValue().toString());
                                    txtTemMin.setText(x.child("temperaturaMin").getValue().toString());
                                    //txtRangoTemp.setText(x.child("rangoTemperatura").getValue().toString());
                                    txtestado.setText(x.child("estado").getValue().toString());
                                    break;
                                }

                            }
                            if (!res){
                                ocultarTeclado();
                                Toast.makeText(TemperaturaActivity.this, "Temp Actual ("+tempActual+") no encontrado!!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } //cierra el if/else
            }
        });

    }//cierra el metodo boton buscar x Sexo
    /*private void botonBuscarRaza(){

        btnBusMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtTempMax.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(TemperaturaActivity.this, "Escriba una Temp para BUSCAR!!!", Toast.LENGTH_SHORT).show();

                } else {

                    String tempMax = txtTempMax.getText().toString();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = db.getReference(Temperatura.class.getSimpleName());
                    // DatabaseReference databaseReference = db.getReference().child("Temperaturas");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()) {

                                if (tempMax.equalsIgnoreCase(x.child("temperaturaMax").getValue().toString())) {
                                    res = true;
                                    ocultarTeclado();
                                    txtid.setText(x.child("idSensor").getValue().toString());
                                    txtTempActual.setText(x.child("temperaturaActual").getValue().toString());
                                    txtTemMin.setText(x.child("temperaturaMin").getValue().toString());
                                    txtRangoTemp.setText(x.child("rangoTemperatura").getValue().toString());
                                    txtestado.setText(x.child("estado").getValue().toString());
                                    break;
                                }

                            }
                            if (!res){
                                ocultarTeclado();
                                Toast.makeText(TemperaturaActivity.this, "Temp Maxima ("+tempMax+") no encontrada!!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } //cierra el if/else
            }
        });

    }*///cierra el metodo boton buscar x Raza

    private void botonModificar() {
        btnmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar que todos los campos no estén vacíos
                if (txtid.getText().toString().trim().isEmpty()
                        || txtTempActual.getText().toString().trim().isEmpty()
                        || txtTempMax.getText().toString().trim().isEmpty()
                        || txtTemMin.getText().toString().trim().isEmpty()
                        || txtestado.getText().toString().trim().isEmpty()) {
                    ocultarTeclado();
                    Toast.makeText(TemperaturaActivity.this, "Campos en blanco, completar para Modificar", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Validar que los campos numéricos sean números válidos
                        int id = Integer.parseInt(txtid.getText().toString());
                        double tempActual = Double.parseDouble(txtTempActual.getText().toString());
                        double tempMax = Double.parseDouble(txtTempMax.getText().toString());
                        double tempMin = Double.parseDouble(txtTemMin.getText().toString());
                        String estado = txtestado.getText().toString();

                        // Calcular el rango de temperatura
                        double rangoTemperatura = tempMax - tempMin;

                        // Acceder a la base de datos
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Temperatura.class.getSimpleName());

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String auxid = Integer.toString(id);
                                boolean res = false;
                                for (DataSnapshot tabla : snapshot.getChildren()) {
                                    // Verificar si el ID del sensor coincide
                                    if (tabla.child("idSensor").getValue().toString().equals(auxid)) {
                                        res = true;
                                        ocultarTeclado();
                                        // Modificar el registro en Firebase
                                        tabla.getRef().child("temperaturaActual").setValue(tempActual);
                                        tabla.getRef().child("temperaturaMax").setValue(tempMax);
                                        tabla.getRef().child("temperaturaMin").setValue(tempMin);
                                        tabla.getRef().child("rangoTemperatura").setValue(rangoTemperatura);
                                        tabla.getRef().child("estado").setValue(estado);
                                        Toast.makeText(com.example.lookcow.Controladora.TemperaturaActivity.this, "Registro modificado exitosamente!!", Toast.LENGTH_SHORT).show();
                                        // Limpiar los campos después de modificar
                                        txtid.setText("");
                                        txtTempActual.setText("");
                                        txtTempMax.setText("");
                                        txtTemMin.setText("");
                                        txtestado.setText("");
                                        listarTemperaturas();
                                        break;
                                    }
                                }

                                if (!res) {
                                    ocultarTeclado();
                                    Toast.makeText(TemperaturaActivity.this, "Identificación (" + auxid + ") no encontrada. No se puede modificar", Toast.LENGTH_SHORT).show();
                                    // Limpiar los campos si no se encuentra el registro
                                    txtid.setText("");
                                    txtTempActual.setText("");
                                    txtTempMax.setText("");
                                    txtTemMin.setText("");
                                    txtestado.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Manejar el error de Firebase si ocurre
                                Toast.makeText(TemperaturaActivity.this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (NumberFormatException e) {
                        // Si ocurre un error de conversión de número, se muestra un mensaje
                        ocultarTeclado();
                        Toast.makeText(TemperaturaActivity.this, "Por favor, ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void botonRegistrar(){
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtid.getText().toString().trim().isEmpty()
                        || txtTempActual.getText().toString().trim().isEmpty()
                        || txtTempMax.getText().toString().trim().isEmpty()
                        || txtTemMin.getText().toString().trim().isEmpty()
                        // txtRangoTemp.getText().toString().trim().isEmpty()
                        || txtestado.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(TemperaturaActivity.this, "Campos en blanco, completar!!", Toast.LENGTH_SHORT).show();

                }else{
                    int idSensor = Integer.parseInt(txtid.getText().toString());
                    double temperaturaActual = Double.parseDouble(txtTempActual.getText().toString());
                    double temperaturaMax = Double.parseDouble(txtTempMax.getText().toString());
                    double temperaturaMin = Double.parseDouble(txtTemMin.getText().toString());
                    double rangoTemperatura = (double) (temperaturaMax - temperaturaMin);
                    String estado = txtestado.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = db.getReference(Temperatura.class.getSimpleName());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String auxid= Integer.toString(idSensor);
                            boolean res = false;
                            for (DataSnapshot tabla: snapshot.getChildren()){

                                if (tabla.child("idSensor").getValue().toString().equals(auxid)){
                                    res = true;
                                    ocultarTeclado();
                                    Toast.makeText(TemperaturaActivity.this, "Registro("+auxid+") ya existe", Toast.LENGTH_SHORT).show();
                                    break;
                                }

                            }

                            if(!res){

                                Temperatura temperatura = new Temperatura(idSensor, temperaturaActual, temperaturaMax,temperaturaMin, rangoTemperatura, estado);
                                databaseReference.push().setValue(temperatura);
                                ocultarTeclado();
                                Toast.makeText(TemperaturaActivity.this, "Temperatura registrada correctamente", Toast.LENGTH_SHORT).show();
                                txtid.setText("");
                                txtTempActual.setText("");
                                txtTempMax.setText("");
                                txtTemMin.setText("");
                                //txtRangoTemp.setText("");
                                txtestado.setText("");

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }//Cierra el if else

            }
        });

    }//Cierra el metodo boton registrar
    private void botonEliminar(){
        btneli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtid.getText().toString().trim().isEmpty() ||
                        txtTempActual.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(TemperaturaActivity.this, "Buscar una Identificacion o un Dispositivo para ELIMINAR!!!", Toast.LENGTH_SHORT).show();

                } else {
                    int id = Integer.parseInt(txtid.getText().toString());
                    String tempActual = txtTempActual.getText().toString();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = db.getReference(Temperatura.class.getSimpleName());
                    // DatabaseReference databaseReference = db.getReference().child("Usuario");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String auxId = Integer.toString(id);
                            final boolean[] res = {false};
                            for (DataSnapshot x : snapshot.getChildren()) {

                                if (auxId.equalsIgnoreCase(x.child("idSensor").getValue().toString())
                                        || tempActual.equalsIgnoreCase(x.child("temperaturaMin").getValue().toString())) {

                                    AlertDialog.Builder a = new AlertDialog.Builder(TemperaturaActivity.this);
                                    a.setCancelable(false);
                                    a.setTitle("ATENCION");
                                    a.setMessage("Estas por ELIMINAR el registro..");
                                    res[0] = true;
                                    Toast.makeText(com.example.lookcow.Controladora.TemperaturaActivity.this, "ID ( "+auxId+" ) con ( "+tempActual+" ) encontrado.\nUsted puede eliminar!!", Toast.LENGTH_SHORT).show();
                                    a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ocultarTeclado();
                                            x.getRef().removeValue();
                                            listarTemperaturas();
                                            txtid.setText("");
                                            txtTempActual.setText("");
                                            txtTempMax.setText("");
                                            txtTemMin.setText("");
                                            txtestado.setText("");
                                        }
                                    });
                                    a.show();
                                    break;
                                }
                            }
                            if (!res[0]){
                                ocultarTeclado();
                                Toast.makeText(TemperaturaActivity.this, "ID ( "+auxId+" ) con ( "+tempActual+" ) no encontrada.\nNo se puede eliminar!!", Toast.LENGTH_SHORT).show();
                                txtid.setText("");
                                txtTempActual.setText("");
                                txtTempMax.setText("");
                                txtTemMin.setText("");
                                txtestado.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } //cierra el if/else

            }
        });
    }//cierra el metodo boton Eliminar

    private void listarTemperaturas(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference(Temperatura.class.getSimpleName());

        ArrayList<Temperatura> listaTemperatura = new ArrayList<Temperatura>();
        ArrayAdapter<Temperatura> adapter = new ArrayAdapter<Temperatura>(TemperaturaActivity.this, android.R.layout.simple_list_item_1 , listaTemperatura);
        lvDatos.setAdapter(adapter);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Temperatura temperatura = snapshot.getValue(Temperatura.class);
                listaTemperatura.add(temperatura);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Temperatura temperatura = listaTemperatura.get(position);
                AlertDialog.Builder a = new AlertDialog.Builder(TemperaturaActivity.this);
                a.setCancelable(true);
                a.setTitle("Temperatura Elegida");
                String msg = "ID : "+ temperatura.getIdSensor() + "\n\n";
                msg += "Temp Actual  : " + temperatura.getTemperaturaActual()+ "\n\n";
                msg += "Temp Maxima : " + temperatura.getTemperaturaMax()+ "\n\n";
                msg += "Temp Minima : " + temperatura.getTemperaturaMin()+ "\n\n";
                msg += "Rango Temp : " + temperatura.getRangoTemperatura()+ "\n\n";
                msg += "Estado : " + temperatura.getEstado();

                a.setMessage(msg);
                a.show();
            }
        });

    }//cierra el metodo listarTemperaturas

    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    } // Cierra el método ocultarTeclado.


}   //cierra la clase
