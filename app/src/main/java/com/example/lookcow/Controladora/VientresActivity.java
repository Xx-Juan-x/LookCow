package com.example.lookcow.Controladora;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import com.example.lookcow.Modelo.Vientre;
import com.example.lookcow.R;
import com.example.lookcow.databinding.ActivityVientresBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VientresActivity extends DrawerBaseActivity {

        private EditText txtid, txtInicio, txtParto, txtObserva, txtEstado;
        private Button btnbus, btnmod, btnreg, btneli, btnbusInicio, btnbusParto;
        private ListView lvDatos;

    ActivityVientresBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityVientresBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

            txtid   = findViewById(R.id.txtid);
            txtInicio = findViewById(R.id.txtFechaInicio);
            txtParto = findViewById(R.id.txtFechaParto);
            txtObserva = findViewById(R.id.txtObservaciones);
            txtEstado = findViewById(R.id.txtEstado);
            btnbus  = findViewById(R.id.btnbus);
            btnmod  = findViewById(R.id.btnmod);
            btnreg  = findViewById(R.id.btnreg);
            btneli  = findViewById(R.id.btneli);
            lvDatos = findViewById(R.id.lvDatos);
            btnbusInicio = findViewById(R.id.btnbusInicio);
            btnbusParto = findViewById(R.id.btnbusParto);

            botonBuscar();
            botonBuscarInicio();
            botonBuscarParto();
            botonModificar();
            botonRegistrar();
            botonEliminar();
            listarVientres();

        }   //cierra el onCreate

        private void botonBuscar(){

            btnbus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtid.getText().toString().trim().isEmpty()){
                        ocultarTeclado();
                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Escriba una Identificacion para BUSCAR!!!", Toast.LENGTH_SHORT).show();

                    } else {
                        int id = Integer.parseInt(txtid.getText().toString());
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Vientre.class.getSimpleName());
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String auxId = Integer.toString(id);
                                boolean res = false;
                                for (DataSnapshot x : snapshot.getChildren()) {

                                    if (auxId.equalsIgnoreCase(x.child("id").getValue().toString())) {
                                        res = true;
                                        ocultarTeclado();
                                        txtInicio.setText(x.child("fechaInicio").getValue().toString());
                                        txtParto.setText(x.child("fechaParto").getValue().toString());
                                        txtObserva.setText(x.child("observaciones").getValue().toString());
                                        txtEstado.setText(x.child("estado").getValue().toString());
                                        break;
                                    }

                                }
                                if (!res){
                                    ocultarTeclado();
                                    Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "ID ("+auxId+") no encontrado!!", Toast.LENGTH_SHORT).show();

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
        private void botonBuscarInicio(){

            btnbusInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtInicio.getText().toString().trim().isEmpty()){
                        ocultarTeclado();
                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Escriba una Fecha para BUSCAR!!!", Toast.LENGTH_SHORT).show();

                    } else {

                        String inicio = txtInicio.getText().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Vientre.class.getSimpleName());
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                boolean res = false;
                                for (DataSnapshot x : snapshot.getChildren()) {

                                    if (inicio.equalsIgnoreCase(x.child("fechaInicio").getValue().toString())) {
                                        res = true;
                                        ocultarTeclado();
                                        txtid.setText(x.child("id").getValue().toString());
                                        txtParto.setText(x.child("fechaParto").getValue().toString());
                                        txtObserva.setText(x.child("observaciones").getValue().toString());
                                        txtEstado.setText(x.child("estado").getValue().toString());
                                        break;
                                    }

                                }
                                if (!res){
                                    ocultarTeclado();
                                    Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Fecha ("+inicio+") no encontrada!!", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    } //cierra el if/else
                }
            });

        }//cierra el metodo boton buscar x Nombre
        private void botonBuscarParto(){

            btnbusParto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtParto.getText().toString().trim().isEmpty()){
                        ocultarTeclado();
                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Escriba una Fecha para BUSCAR!!!", Toast.LENGTH_SHORT).show();

                    } else {

                        String parto = txtParto.getText().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Vientre.class.getSimpleName());
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                boolean res = false;
                                for (DataSnapshot x : snapshot.getChildren()) {

                                    if (parto.equalsIgnoreCase(x.child("fechaParto").getValue().toString())) {
                                        res = true;
                                        ocultarTeclado();
                                        txtid.setText(x.child("id").getValue().toString());
                                        txtInicio.setText(x.child("fechaInicio").getValue().toString());
                                        txtObserva.setText(x.child("observaciones").getValue().toString());
                                        txtEstado.setText(x.child("estado").getValue().toString());
                                        break;
                                    }

                                }
                                if (!res){
                                    ocultarTeclado();
                                    Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Fecha ("+parto+") no encontrada!!", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    } //cierra el if/else
                }
            });

        }//cierra el metodo boton buscar x Apellido
        private void botonModificar(){

            btnmod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtid.getText().toString().trim().isEmpty()
                            || txtInicio.getText().toString().trim().isEmpty()
                            || txtParto.getText().toString().trim().isEmpty()
                            || txtObserva.getText().toString().trim().isEmpty()
                            || txtEstado.getText().toString().trim().isEmpty()){
                        ocultarTeclado();
                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Campos en blanco, completar para Modificar", Toast.LENGTH_SHORT).show();

                    }else{
                        int id = Integer.parseInt(txtid.getText().toString());
                        String inicio = txtInicio.getText().toString();
                        String parto = txtParto.getText().toString();
                        String observa = txtObserva.getText().toString();
                        String estado = txtEstado.getText().toString();

                        //Valida el formato de la fecha
                        if (!isValidDate(inicio) || !isValidDate(parto)) {
                            ocultarTeclado();
                            Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Formato de fecha incorrecto. Use dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Verificar que la fecha de inicio sea menor o igual a la de parto
                        if (!isStartDateBeforeEndDate(inicio, parto)) {
                            ocultarTeclado();
                            Toast.makeText(VientresActivity.this, "La fecha de inicio no puede ser mayor que la fecha de parto.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Vientre.class.getSimpleName());

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String auxid= Integer.toString(id);
                                boolean res = false;
                                for (DataSnapshot tabla: snapshot.getChildren()){

                                    if (tabla.child("id").getValue().toString().equals(auxid)){
                                        res = true;
                                        ocultarTeclado();
                                        tabla.getRef().child("fechaInicio").setValue(inicio);
                                        tabla.getRef().child("fechaParto").setValue(parto);
                                        tabla.getRef().child("observaciones").setValue(observa);
                                        tabla.getRef().child("estado").setValue(estado);
                                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Registro modificado exitosamente!!", Toast.LENGTH_SHORT).show();
                                        txtid.setText("");
                                        txtInicio.setText("");
                                        txtParto.setText("");
                                        txtObserva.setText("");
                                        txtEstado.setText("");
                                        listarVientres();
                                        break;
                                    }
                                }

                                if(!res){

                                    ocultarTeclado();
                                    Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Identificacion ("+auxid+") No encontrado.\nNo se puede modificar", Toast.LENGTH_SHORT).show();
                                    txtid.setText("");
                                    txtInicio.setText("");
                                    txtParto.setText("");
                                    txtObserva.setText("");
                                    txtEstado.setText("");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }//Cierra el if else

                }
            });

        } //cierra el metodo boton modificar
        private void botonRegistrar(){
            btnreg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtid.getText().toString().trim().isEmpty()
                            || txtInicio.getText().toString().trim().isEmpty()
                            || txtParto.getText().toString().trim().isEmpty()
                            || txtObserva.getText().toString().trim().isEmpty()
                            || txtEstado.getText().toString().trim().isEmpty()){
                        ocultarTeclado();
                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Campos en blanco, completar!!", Toast.LENGTH_SHORT).show();

                    }else{
                        int id = Integer.parseInt(txtid.getText().toString());
                        String inicio = txtInicio.getText().toString();
                        String parto = txtParto.getText().toString();
                        String observa = txtObserva.getText().toString();
                        String estado = txtEstado.getText().toString();

                        // Validar formato de fechas
                        if (!isValidDate(inicio) || !isValidDate(parto)) {
                            ocultarTeclado();
                            Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Formato de fecha incorrecto. Use dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Verificar que la fecha de inicio sea menor o igual a la de parto
                        if (!isStartDateBeforeEndDate(inicio, parto)) {
                            ocultarTeclado();
                            Toast.makeText(VientresActivity.this, "La fecha de inicio no puede ser mayor que la fecha de parto.", Toast.LENGTH_SHORT).show();
                            return;
                        }



                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Vientre.class.getSimpleName());

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String auxid= Integer.toString(id);
                                boolean res = false;
                                for (DataSnapshot tabla: snapshot.getChildren()){

                                    if (tabla.child("id").getValue().toString().equals(auxid)){
                                        res = true;
                                        ocultarTeclado();
                                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Registro("+auxid+") ya existente", Toast.LENGTH_SHORT).show();
                                        break;
                                    }

                                }
                                if(!res){

                                    Vientre vientre = new Vientre(id, inicio, parto, observa, estado);
                                    databaseReference.push().setValue(vientre);
                                    ocultarTeclado();
                                    Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                    txtid.setText("");
                                    txtInicio.setText("");
                                    txtParto.setText("");
                                    txtObserva.setText("");
                                    txtEstado.setText("");

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
                            txtInicio.getText().toString().trim().isEmpty()){
                        ocultarTeclado();
                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "Buscar una Identificacion o una Fecha para ELIMINAR!!!", Toast.LENGTH_SHORT).show();

                    } else {
                        int id = Integer.parseInt(txtid.getText().toString());
                        String inicio = txtInicio.getText().toString();
                        String parto = txtParto.getText().toString();
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = db.getReference(Vientre.class.getSimpleName());
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String auxId = Integer.toString(id);
                                final boolean[] res = {false};
                                for (DataSnapshot x : snapshot.getChildren()) {

                                    if (auxId.equalsIgnoreCase(x.child("id").getValue().toString())
                                            || inicio.equalsIgnoreCase(x.child("fechaInicio").getValue().toString()) || parto.equalsIgnoreCase((x.child("fechaParto").getValue().toString()))) {

                                        AlertDialog.Builder a = new AlertDialog.Builder(com.example.lookcow.Controladora.VientresActivity.this);
                                        a.setCancelable(false);
                                        a.setTitle("ATENCION");
                                        a.setMessage("Estas por ELIMINAR el registro..");
                                        res[0] = true;
                                        Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "ID ( "+auxId+" ) con inicio: ( "+inicio+" ) y parto ( "+parto+" )encontrado.\nUsted puede eliminar!!", Toast.LENGTH_SHORT).show();
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
                                                listarVientres();
                                                txtid.setText("");
                                                txtInicio.setText("");
                                                txtParto.setText("");
                                                txtObserva.setText("");
                                                txtEstado.setText("");
                                            }
                                        });
                                        a.show();
                                        break;
                                    }
                                }
                                if (!res[0]){
                                    ocultarTeclado();
                                    Toast.makeText(com.example.lookcow.Controladora.VientresActivity.this, "ID ( "+auxId+" ) con inicio: ( "+inicio+" ) y parto ( "+parto+" ) no encontrado.\nUsted no puede eliminar!!", Toast.LENGTH_SHORT).show();
                                    txtid.setText("");
                                    txtInicio.setText("");
                                    txtParto.setText("");
                                    txtObserva.setText("");
                                    txtEstado.setText("");

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

        private void listarVientres(){
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference reference = db.getReference(Vientre.class.getSimpleName());

            ArrayList<Vientre> listaVientre = new ArrayList<Vientre>();
            ArrayAdapter<Vientre> adapter = new ArrayAdapter<Vientre>(com.example.lookcow.Controladora.VientresActivity.this, android.R.layout.simple_list_item_1 , listaVientre);
            lvDatos.setAdapter(adapter);

            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Vientre vientre = snapshot.getValue(Vientre.class);
                    listaVientre.add(vientre);
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
                    Vientre vientre = listaVientre.get(position);
                    AlertDialog.Builder a = new AlertDialog.Builder(com.example.lookcow.Controladora.VientresActivity.this);
                    a.setCancelable(true);
                    a.setTitle("Vientre Elegido");
                    String msg = "ID : "+ vientre.getId() + "\n\n";
                    msg += "FechaInicio : " + vientre.getFechaInicio()+ "\n\n";
                    msg += "FechaParto : " + vientre.getFechaParto()+ "\n\n";
                    msg += "Observaciones : " + vientre.getObservaciones()+ "\n\n";
                    msg += "Estado : " + vientre.getEstado();

                    a.setMessage(msg);
                    a.show();
                }
            });

        }//cierra el metodo listarUsuarios

    // Metodo que valida si realmente la fecha existe
    private boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Desactiva la interpretación flexible de fechas
        try {
            Date date = sdf.parse(dateString); // Intenta convertir la cadena a una fecha
            return true; // La fecha es válida
        } catch (ParseException e) {
            return false; // La fecha es inválida
        }
    }

    // Metodo de validación que indica si la fecha de inicio es mayor a la de parto
    private boolean isStartDateBeforeEndDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return !start.after(end); // Devuelve verdadero si la fecha de inicio no es después de la de parto
        } catch (ParseException e) {
            return false; // En caso de error, considerar fechas no válidas
        }
    }

        private void ocultarTeclado(){
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } // Cierra el método ocultarTeclado.


    }   //cierra la clase