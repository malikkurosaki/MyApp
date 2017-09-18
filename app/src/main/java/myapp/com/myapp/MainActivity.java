package myapp.com.myapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.R.id.empty;
import static android.R.id.list;
import static android.R.id.list_container;

public class MainActivity extends AppCompatActivity{
    private static final int RC_SIGN_IN = 1;
    DatabaseReference reference;
    ListView listView;
    FirebaseListAdapter<Chat> adapter;
    FirebaseAuth auth;
    Button masuk;
    Button keluar;
    ImageView fotoUser;
    TextView mNamaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mNamaUser = (TextView)findViewById(R.id.nama_pengguna);
        masuk = (Button)findViewById(R.id.masuk);
        keluar = (Button)findViewById(R.id.keluar);
        auth = FirebaseAuth.getInstance();
        fotoUser = (ImageView)findViewById(R.id.foto_user);
        //final String namaPengguna = auth.getCurrentUser().getDisplayName();
        //picaso load image
        //Picasso.with(getApplicationContext()).load(uri).error(R.mipmap.ic_launcher_round).into(fotoUser);

       // mNamaUser.setText(namaPengguna);



        reference = FirebaseDatabase.getInstance().getReference().child("pesan");
        listView = (ListView) findViewById(R.id.nav_recycler);

        adapter = new FirebaseListAdapter<Chat>(this,Chat.class,R.layout.chat,reference) {
            @Override
            protected void populateView(View v, Chat model, int position) {
                ((ImageView)v.findViewById(R.id.chat_gambar)).setImageResource(model.getGambarUser());
                ((TextView)v.findViewById(R.id.chat_nama)).setText(model.getNamaUser());
                ((TextView)v.findViewById(R.id.chat_pesan)).setText(model.getPesanUser());
            }

        };

        listView.setDivider(null);
        listView.setAdapter(adapter);


        //cek masuk akun

        if (auth.getCurrentUser() != null) {
            masuk.setVisibility(View.GONE);
            keluar.setVisibility(View.VISIBLE);
            Uri uri = auth.getCurrentUser().getPhotoUrl();
            


        } else {
            keluar.setVisibility(View.GONE);
            masuk.setVisibility(View.VISIBLE);
            masuk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(AuthUI
                            .getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays
                                    .asList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build())).build(),RC_SIGN_IN);
                }
            });
        }




        //kirim pesan
        final EditText pesan = (EditText)findViewById(R.id.isi_pesan);
        Button kirim = (Button)findViewById(R.id.btn_kirim);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.push().setValue(new Chat(R.mipmap.ic_launcher_round,"malik",pesan.getText().toString()));
                pesan.setText("");
            }
        });

    }
}
