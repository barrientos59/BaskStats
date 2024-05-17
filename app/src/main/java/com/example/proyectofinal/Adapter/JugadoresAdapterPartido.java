    package com.example.proyectofinal.Adapter;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.proyectofinal.Model.Jugador;
    import com.example.proyectofinal.R;
    import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
    import com.firebase.ui.firestore.FirestoreRecyclerOptions;

    public class JugadoresAdapterPartido extends FirestoreRecyclerAdapter<Jugador, JugadoresAdapterPartido.ViewHolder> {
        private OnItemClickListener mListener;
        // Interfaz para manejar los clics en los elementos del RecyclerView
        public interface OnItemClickListener {
            void onItemClick(Jugador jugador, RecyclerView recyclerView);
        }
        public JugadoresAdapterPartido(@NonNull FirestoreRecyclerOptions<Jugador> options, OnItemClickListener listener) {
            super(options);
            mListener = listener;
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Jugador model) {
            // Solo establece el texto del dorsal
            holder.txtDorsal.setText(model.getDorsal());
            // Manejar clics en el elemento del RecyclerView
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(model, (RecyclerView) holder.itemView.getParent()); // Pasar el jugador seleccionado y el RecyclerView al listener
                        }
                    }
                }
            });
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
