package com.example.kathy.logeo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {
    // crear un cliente api google
    //codigo de respuesta 9001
    private GoogleApiClient googleApiClient;
    private final int COREC = 9001;
    EditText usuario;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButton botonGoogle = (SignInButton) findViewById(R.id.logGmail);

        botonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logeoGmail();
                Intent intent = new Intent(getApplicationContext(), Principal.class); // Obtengo el activity General getApplicationContext()

                //intent.putExtra("idTexto",.getText().toString());
                startActivity(intent);
            }
        });
        Button botonRegistro=(Button)findViewById(R.id.btnRegistro1);
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registro.class); // Obtengo el activity General getApplicationContext()

                //intent.putExtra("idTexto",.getText().toString());
                startActivity(intent);
            }
        });
        final Button botonIngresar =(Button) findViewById(R.id.btnIngresar);
        usuario =((EditText)findViewById(R.id.txtCorreo));
        pass =((EditText)findViewById(R.id.txtContraseña));
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String usurio =usuario.getText().toString();
                String passsw =pass.getText().toString();
                Intent intent = new Intent(getApplicationContext(), principal.class); // Obtengo el activity General getApplicationContext()

                intent.putExtra("Usuario",usuario.getText().toString());
                intent.putExtra("pass",pass.getText().toString());

                startActivity(intent);
                /** if(usuario.equals("kath")&&pass.equals("kate")){

                 }else{
                 Toast.makeText(getApplicationContext(),"Usuario Incorrecto",Toast.LENGTH_LONG).show();
                 }**/


            }
        });
    }

    public void logeoGmail() {
        if (googleApiClient != null) {
            //desconectando
            googleApiClient.disconnect();

        }
        //solicitar correo y configuraciones de inicio de sesion
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //igaular cliente con el logeo
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        //abrir ventana de google
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIntent, COREC);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COREC) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                GoogleSignInAccount acc = result.getSignInAccount();
                String token = acc.getIdToken();
                Log.e("correo ", acc.getEmail());
                Log.e("correo ", acc.getDisplayName());
                Log.e("correo ", acc.getId());
                if (token != null) {
                    Toast.makeText(this, token, Toast.LENGTH_LONG).show();
                }

                Toast.makeText(this, "ingreso correcto", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void abrirPricipal(View view){
        Intent intent = new Intent(getApplicationContext(),principal.class);
        //intent.putExtra("idTexto","mensaje de pantalla 1");
        // intent.putExtra("idpersona",new Persona("Henry","Paz","henry.paz@epn.edu.ec","000000001"));
        startActivity(intent);
    }
}

