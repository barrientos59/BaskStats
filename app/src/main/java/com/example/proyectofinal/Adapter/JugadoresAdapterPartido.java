    package com.example.proyectofinal.Adapter;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.proyectofinal.Model.Jugador;
    import com.example.proyectofinal.R;
    import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
    import com.firebase.ui.firestore.FirestoreRecyclerOptions;

    public class JugadoresAdapterPartido extends FirestoreRecyclerAdapter<Jugador, JugadoresAdapterPartido.ViewHolder> {

        public JugadoresAdapterPartido(@NonNull FirestoreRecyclerOptions<Jugador> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Jugador model) {
            // Solo establece el texto del dorsal
            holder.txtDorsal.setText(model.getDorsal());
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jugador, parent, false);
            return new ViewHolder(view);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtDorsal;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDorsal = itemView.findViewById(R.id.textViewPlayerDorsal);
            }
        }
    }
