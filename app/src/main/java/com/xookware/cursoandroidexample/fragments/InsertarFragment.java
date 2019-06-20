package com.xookware.cursoandroidexample.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xookware.cursoandroidexample.R;
import com.xookware.cursoandroidexample.database.DataBaseHandler;
import com.xookware.cursoandroidexample.model.Asignatura;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InsertarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InsertarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private DataBaseHandler db;
    private TextView asignatura;
    private TextView profesor;
    private boolean is1semestre;
    private RadioButton semestre1;
    private RadioButton semestre2;
    private NumberPicker nota;
    private NumberPicker annio;
    private FloatingActionButton aceptar_fab;
    private int semestre;
    private ArrayList<Asignatura> lista;

    private Asignatura edit;

    private OnFragmentInteractionListener mListener;

    public InsertarFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InsertarFragment(Asignatura object) {
        edit = object;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertarFragment newInstance(String param1, String param2) {
        InsertarFragment fragment = new InsertarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insertar, container, false);

        //inicializando la vista
        db = new DataBaseHandler(view.getContext());
        asignatura = (TextView) view.findViewById(R.id.asignatura);
        profesor = (TextView) view.findViewById(R.id.profesor);
        semestre1 = (RadioButton) view.findViewById(R.id.semestre1);
        semestre2 = (RadioButton) view.findViewById(R.id.semestre2);

        nota = (NumberPicker) view.findViewById(R.id.nota);
        nota.setMinValue(2);
        nota.setMaxValue(5);
        nota.setValue(3);

        annio = (NumberPicker) view.findViewById(R.id.annio);
        annio.setMinValue(1);
        annio.setMaxValue(5);
        annio.setValue(1);

        aceptar_fab = (FloatingActionButton) view.findViewById(R.id.ok);
        //creando el metodo onClick()
        aceptar_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exist = false;

                if (isfieldEmpty()) {

                    return;
                    //si todos los campos estan llenos procedemos a extraer los datos e insertar en la base de datos
                } else {
                    if (semestre1.isChecked()) {
                        semestre = 1;
                    } else if (semestre2.isChecked()) {
                        semestre = 2;
                    }
                    final Asignatura a = new Asignatura(asignatura.getText().toString(), profesor.getText().toString(),
                            nota.getValue(), annio.getValue(), semestre);
                    lista = (ArrayList<Asignatura>) db.getAlL();
                    for (Asignatura b : lista) {
                        if (b.getNombre().equals(a.getNombre())) {
                            exist = true;
                        }
                    }
                    if (!exist) {
                        db.insertar(a);
                        asignatura.setText("");
                        profesor.setText("");
                        Snackbar.make(view, "Asignatura insertada con éxito", Snackbar.LENGTH_SHORT).show();

                    }
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public boolean isfieldEmpty() {

        boolean error = false;

        if (asignatura.getText().toString().equals("")) {
            Snackbar.make(view, "El campo nombre de la asignatura no puede estar vacío", Snackbar.LENGTH_LONG).show();
            asignatura.setError(null);
            asignatura.requestFocus();
            error = true;
        }
        if (profesor.getText().toString().equals("")) {
            Snackbar.make(view, "El campo nombre del profesor no puede estar vacío", Snackbar.LENGTH_LONG).show();
            profesor.setError(null);
            profesor.requestFocus();
            error = true;
        }
        if (semestre1.isChecked() == false) {
            if (semestre2.isChecked() == false) {
                Snackbar.make(view, "Seleccione el semestre al que pertenece la asignatura", Snackbar.LENGTH_LONG).show();
                semestre2.setError(null);
                semestre1.setError(null);
                error = true;
            }
        }

        return error;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
