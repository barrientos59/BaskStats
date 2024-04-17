package com.example.proyectofinal;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PatidoFragment extends Fragment implements View.OnClickListener {

    private Button btnAsistencia,btnPerdida,
            btnTap,btnFalta, btnRob,btnReb,
            btnFT3,btnMT3,btnFT2,btnMT2,
            btnFTl,btnMTl,btnFinalizar;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patido, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        btnAsistencia = rootView.findViewById(R.id.btnAsistencia);
        btnPerdida = rootView.findViewById(R.id.btnPerdida);
        btnTap = rootView.findViewById(R.id.btnTap);
        btnFalta = rootView.findViewById(R.id.btnfalta);
        btnRob = rootView.findViewById(R.id.btnRobo);
        btnReb = rootView.findViewById(R.id.btnRebote);
        btnFT3 = rootView.findViewById(R.id.btnFallaT3);
        btnMT3 = rootView.findViewById(R.id.btnAnotaT3);
        btnFT2 = rootView.findViewById(R.id.btnFallaT2);
        btnMT2 = rootView.findViewById(R.id.btnAnotaT2);
        btnFTl = rootView.findViewById(R.id.btnFallaTl);
        btnMTl = rootView.findViewById(R.id.btnAnotaTl);

        btnFinalizar =rootView.findViewById(R.id.btnFinalizar);


        // Ocultar botones de asistencia al iniciar el fragmento
        hideButtons();

        // Configurar clics de botones de jugadores
        rootView.findViewById(R.id.j1_e1).setOnClickListener(this);
        rootView.findViewById(R.id.j2_e1).setOnClickListener(this);
        rootView.findViewById(R.id.j3_e1).setOnClickListener(this);
        rootView.findViewById(R.id.j4_e1).setOnClickListener(this);
        rootView.findViewById(R.id.j5_e1).setOnClickListener(this);
        rootView.findViewById(R.id.j1_e2).setOnClickListener(this);
        rootView.findViewById(R.id.j2_e2).setOnClickListener(this);
        rootView.findViewById(R.id.j3_e2).setOnClickListener(this);
        rootView.findViewById(R.id.j4_e2).setOnClickListener(this);
        rootView.findViewById(R.id.j5_e2).setOnClickListener(this);

        // Configurar clics de botones de asistencia, perdida, tapon y falta
        btnAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });

        btnPerdida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });

        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });

        btnFalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnRob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnReb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnFT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnMT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnFT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnMT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnFTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnMTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View view) {
        // Mostrar botones de asistencia al hacer clic en un jugador
        showButtons();
    }

    private void showButtons() {
        btnAsistencia.setVisibility(View.VISIBLE);
        btnPerdida.setVisibility(View.VISIBLE);
        btnTap.setVisibility(View.VISIBLE);
        btnFalta.setVisibility(View.VISIBLE);
        btnRob.setVisibility(View.VISIBLE);
        btnReb.setVisibility(View.VISIBLE);
        btnFT3.setVisibility(View.VISIBLE);
        btnMT3.setVisibility(View.VISIBLE);
        btnFT2.setVisibility(View.VISIBLE);
        btnMT2.setVisibility(View.VISIBLE);
        btnFTl.setVisibility(View.VISIBLE);
        btnMTl.setVisibility(View.VISIBLE);
    }

    private void hideButtons() {
        btnAsistencia.setVisibility(View.GONE);
        btnPerdida.setVisibility(View.GONE);
        btnTap.setVisibility(View.GONE);
        btnFalta.setVisibility(View.GONE);
        btnRob.setVisibility(View.GONE);
        btnReb.setVisibility(View.GONE);
        btnFT3.setVisibility(View.GONE);
        btnMT3.setVisibility(View.GONE);
        btnFT2.setVisibility(View.GONE);
        btnMT2.setVisibility(View.GONE);
        btnFTl.setVisibility(View.GONE);
        btnMTl.setVisibility(View.GONE);
    }

}
