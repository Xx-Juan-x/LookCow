package com.example.lookcow.Controladora;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lookcow.Modelo.Grupo;
import com.example.lookcow.Modelo.Permiso;
import com.example.lookcow.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity {
    TextView textViewData;
    EditText txtDato;
    Spinner spinner;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner);

        textViewData = findViewById(R.id.textViewData);
        spinner = findViewById(R.id.spinner);
        txtDato = findViewById(R.id.txtDato);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        //LoadPermisos();
        //LoadGrupos();
    }
    public void LoadPermisos(){
        List<Permiso> permisos = new ArrayList<>();
        String[] desc = new String[1];

        databaseReference.child("Permiso").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot ds: snapshot.getChildren()) {
                        int id = Integer.parseInt(ds.child("id").getValue().toString());
                        String accion = ds.child("accion").getValue().toString();
                        String desripcion = ds.child("desc").getValue().toString();
                        //empesamos a leer la tabla permiso
                        ArrayAdapter<Permiso> arrayAdapter = new ArrayAdapter<>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, permisos);
                        spinner.setAdapter(arrayAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String item = parent.getSelectedItem().toString();
                                txtDato.setText(item);
                                desc[0] = item;
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        permisos.add(new Permiso(id, accion, desripcion));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void LoadGrupos() {
        List<Grupo> grupos = new ArrayList<>();

        databaseReference.child("Grupo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Iterar sobre los grupos obtenidos de Firebase
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String nombreGrupo = ds.child("nombreGrupo").getValue(String.class);
                        String permiso = ds.child("permiso").getValue(String.class);

                        // Aquí se agrega el grupo a la lista
                        grupos.add(new Grupo(nombreGrupo, permiso));
                    }

                    // Crear el adaptador y asignarlo al Spinner
                    ArrayAdapter<Grupo> arrayAdapter = new ArrayAdapter<>(SpinnerActivity.this, android.R.layout.simple_dropdown_item_1line, grupos);
                    spinner.setAdapter(arrayAdapter);

                    // Escuchar selección del Spinner
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Grupo grupoSeleccionado = grupos.get(position);
                            txtDato.setText(grupoSeleccionado.getNombreGrupo());  // Solo mostrar el nombre del grupo
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // No hacer nada si no se selecciona nada
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar posibles errores
            }
        });
    }

}