package layout;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.Evento;

import pe.edu.ulima.eventosulima.DetailActivity;
import pe.edu.ulima.eventosulima.R;

public class General extends Fragment {

    Firebase myFirebaseRef;

    RecyclerView recyclerView;

    public ArrayList<Evento> eventosLista  = new ArrayList<Evento>();
    public General() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getActivity().setTitle("General");
        setHasOptionsMenu(true);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Firebase.setAndroidContext(getActivity().getApplicationContext());
        myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/").child("Evento");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    Evento eventos = data.getValue(Evento.class);

                    eventosLista.add(eventos);
                }

                Log.i("Carrerass", eventosLista.get(0).getCarrera());
                ContentAdapter adapter = new ContentAdapter(getActivity(),eventosLista);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getContext(), "Conexión a firebase incompleta", Toast.LENGTH_LONG );
            }


        });
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);


        return recyclerView;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_general, menu);
    }



    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 2;
        private String[] mEvento;

        private String[] mDescripcion;

        private Drawable[] mPlacePictures;

        private List<Evento> mEventos;


        public ContentAdapter(Context context, ArrayList<Evento> eventos) {
            mEventos = eventos;

            Log.i("Hola", "ContentAdapter");
            Log.i("Eventos", "" + eventos.size());

            mEvento = new String[eventos.size()];
            for(int i = 0; i < eventos.size(); i++){
                Log.i("Carrera" + i, eventos.get(i).getCarrera());
                Log.i("Descrpcion" + i, eventos.get(i).getDescripcion());
                Log.i("Descrpcion_Detalle" + i, eventos.get(i).getDescripcion_detalle());
                Log.i("Evento" + i, eventos.get(i).getEvento());
                mEvento[i] = eventos.get(i).getEvento();
                mDescripcion[i] = eventos.get(i).getDescripcion();
                //StorageReference storageRef = storage.getReferenceFromUrl(eventos.get(i).getImagen());
                //storageRef.getDownloadUrl().
                //mPlacePictures[i] = eventos.get(i).getImagen();
                Log.i("imagen" + i, eventos.get(i).getImagen());
                Log.i("Ubicacion" + i, eventos.get(i).getUbicacion());
            }
            Resources resources = context.getResources();

            //mEvento = resources.getStringArray(R.array.evento);
            mDescripcion = resources.getStringArray(R.array.decripcion);
            TypedArray a = resources.obtainTypedArray(R.array.imagen);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl(mEventos.get(position).getImagen());
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    uri.getPath();
                    Picasso.with()
                            .load(url)
                            .placeholder(R.drawable.user_placeholder)
                            .error(R.drawable.user_placeholder_error)
                            .into(imageView);
                }
            });
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(mEvento[position % mEvento.length]);
            holder.description.setText(mDescripcion[position % mDescripcion.length]);
        }

        @Override
        public int getItemCount() {
            return mEvento.length;
        }
    }


}
