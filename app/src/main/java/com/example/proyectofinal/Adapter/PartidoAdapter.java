package com.example.proyectofinal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.Model.JugadorPartido;
import com.example.proyectofinal.Model.Partido;
import com.example.proyectofinal.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PartidoAdapter extends FirestoreRecyclerAdapter<Partido, PartidoAdapter.ViewHolder> {
    private Context context;
    public PartidoAdapter(@NonNull FirestoreRecyclerOptions<Partido> options, Context context) {
        super(options);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Partido model) {
        holder.textViewEquipoLocal.setText(model.getEquipoLocal());
        holder.textViewEquipoVisitante.setText(model.getEquipoVisitante());
        holder.textViewPuntosLocal.setText(String.valueOf(model.getPuntosLocal()));
        holder.textViewPuntosVisitante.setText(String.valueOf(model.getPuntosVisitante()));

        // Set click listener for the download button
        holder.buttonDescargar.setOnClickListener(v -> {
            try {
                File file = downloadPartido(model);
                shareFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error generating HTML", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File downloadPartido(Partido partido) throws IOException {
        String htmlContent = generateHtmlContent(partido);

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "Partido_" + partido.getIdPartido() + ".html");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(htmlContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "HTML generated successfully!", Toast.LENGTH_SHORT).show();
        return file;
    }

    private String generateHtmlContent(Partido partido) {
        StringBuilder html = new StringBuilder();
        html.append("<html>")
                .append("<head>")
                .append("<style>")
                .append("table { width: 100%; border-collapse: collapse; }")
                .append("th, td { border: 1px solid black; padding: 8px; text-align: left; }")
                .append("th { background-color: #f2f2f2; }")
                .append("h1 { text-align: center; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h1>Estadísticas Baloncesto</h1>")
                .append("<table>")
                .append("<tr>")
                .append("<th>Equipo Local</th>")
                .append("<td>").append(partido.getEquipoLocal()).append("</td>")
                .append("<th>Equipo Visitante</th>")
                .append("<td>").append(partido.getEquipoVisitante()).append("</td>")
                .append("</tr>")
                .append("<tr>")
                .append("<th>Puntos Local</th>")
                .append("<td>").append(partido.getPuntosLocal()).append("</td>")
                .append("<th>Puntos Visitante</th>")
                .append("<td>").append(partido.getPuntosVisitante()).append("</td>")
                .append("</tr>")
                .append("</table>")
                .append("<h2>Jugadores Local</h2>")
                .append("<table>")
                .append("<tr>")
                .append("<th>Nombre</th><th>Apellido</th><th>Dorsal</th><th>Posición</th><th>Puntos</th><th>TL</th><th>T2</th><th>T3</th>")
                .append("<th>ASI</th><th>REB</th><th>ROB</th><th>PER</th><th>TAP</th><th>FP</th>")
                .append("</tr>");

        for (JugadorPartido jugador : partido.getJugadoresLocal()) {
            int puntos = jugador.getTirosAnotadosT1() + (jugador.getTirosAnotadosT2() * 2) + (jugador.getTirosAnotadosT3() * 3);
            html.append("<tr>")
                    .append("<td>").append(jugador.getNombre()).append("</td>")
                    .append("<td>").append(jugador.getApellido()).append("</td>")
                    .append("<td>").append(jugador.getDorsal()).append("</td>")
                    .append("<td>").append(jugador.getPosicion()).append("</td>")
                    .append("<td>").append(puntos).append("</td>")
                    .append("<td>").append(jugador.getTirosAnotadosT1()).append("/").append(jugador.getTirosAnotadosT1() + jugador.getTirosFalladosT1()).append("</td>")
                    .append("<td>").append(jugador.getTirosAnotadosT2()).append("/").append(jugador.getTirosAnotadosT2() + jugador.getTirosFalladosT2()).append("</td>")
                    .append("<td>").append(jugador.getTirosAnotadosT3()).append("/").append(jugador.getTirosAnotadosT3() + jugador.getTirosFalladosT3()).append("</td>")
                    .append("<td>").append(jugador.getAsistencias()).append("</td>")
                    .append("<td>").append(jugador.getRebotes()).append("</td>")
                    .append("<td>").append(jugador.getRobos()).append("</td>")
                    .append("<td>").append(jugador.getPerdidas()).append("</td>")
                    .append("<td>").append(jugador.getTapones()).append("</td>")
                    .append("<td>").append(jugador.getFaltas()).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>")
                .append("<h2>Jugadores Visitante</h2>")
                .append("<table>")
                .append("<tr>")
                .append("<th>Nombre</th><th>Apellido</th><th>Dorsal</th><th>Posición</th><th>Puntos</th><th>TL</th><th>T2</th><th>T3</th>")
                .append("<th>ASI</th><th>REB</th><th>ROB</th><th>PER</th><th>TAP</th><th>FP</th>")
                .append("</tr>");

        for (JugadorPartido jugador : partido.getJugadoresVisitante()) {
            int puntos = jugador.getTirosAnotadosT1() + (jugador.getTirosAnotadosT2() * 2) + (jugador.getTirosAnotadosT3() * 3);
            html.append("<tr>")
                    .append("<td>").append(jugador.getNombre()).append("</td>")
                    .append("<td>").append(jugador.getApellido()).append("</td>")
                    .append("<td>").append(jugador.getDorsal()).append("</td>")
                    .append("<td>").append(jugador.getPosicion()).append("</td>")
                    .append("<td>").append(puntos).append("</td>")
                    .append("<td>").append(jugador.getTirosAnotadosT1()).append("/").append(jugador.getTirosAnotadosT1() + jugador.getTirosFalladosT1()).append("</td>")
                    .append("<td>").append(jugador.getTirosAnotadosT2()).append("/").append(jugador.getTirosAnotadosT2() + jugador.getTirosFalladosT2()).append("</td>")
                    .append("<td>").append(jugador.getTirosAnotadosT3()).append("/").append(jugador.getTirosAnotadosT3() + jugador.getTirosFalladosT3()).append("</td>")
                    .append("<td>").append(jugador.getAsistencias()).append("</td>")
                    .append("<td>").append(jugador.getRebotes()).append("</td>")
                    .append("<td>").append(jugador.getRobos()).append("</td>")
                    .append("<td>").append(jugador.getPerdidas()).append("</td>")
                    .append("<td>").append(jugador.getTapones()).append("</td>")
                    .append("<td>").append(jugador.getFaltas()).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>")
                .append("</body>")
                .append("</html>");

        return html.toString();
    }

    private void shareFile(File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, "Share HTML File"));
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewEquipoLocal;
        TextView textViewEquipoVisitante;
        TextView textViewPuntosLocal;
        TextView textViewPuntosVisitante;
        ImageButton buttonDescargar;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewEquipoLocal = itemView.findViewById(R.id.textView_equipoLocal);
            textViewEquipoVisitante = itemView.findViewById(R.id.textView_equipoVisitante);
            textViewPuntosLocal = itemView.findViewById(R.id.textView_puntosLocal);
            textViewPuntosVisitante = itemView.findViewById(R.id.textView_puntosVisitante);
            buttonDescargar = itemView.findViewById(R.id.button_descargar);
        }
    }
}
