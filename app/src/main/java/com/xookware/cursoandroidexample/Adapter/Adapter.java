package com.xookware.cursoandroidexample.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xookware.cursoandroidexample.R;
import com.xookware.cursoandroidexample.database.DataBaseHandler;
import com.xookware.cursoandroidexample.model.Asignatura;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Bismay on 13/6/2019.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<AsignaturasItem> mValues;
    private LayoutInflater inflater;
    private DataBaseHandler db;
    private TextView asignatura;
    private TextView profesor;
    private RadioButton semestre1;
    private RadioButton semestre2;
    private NumberPicker nota;
    private NumberPicker annio;
    private Asignatura a;
    private View view;


    public Adapter(Context context) {
        inflater = LayoutInflater.from(context);
        db = new DataBaseHandler(context);
        mValues = AsignaturasItem.getObjectList((ArrayList<Asignatura>) db.getAlL());

    }

    public void recargarVista() {
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.list_cardview, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
        builder.setCancelable(false);
        builder.setIcon(inflater.getContext().getResources().getDrawable(R.drawable.alert));
        builder.setNegativeButton(inflater.getContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mValues.add(position, mValues.get(position));
                notifyItemInserted(position);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(inflater.getContext().getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.delete(mValues.get(position).getObject());
                mValues.remove(position);
                notifyItemRemoved(position);
            }
        });
        AlertDialog d = builder.create();
        d.setMessage("Seguro que desea eliminar la asignatura " + mValues.get(position).getNombre());
        d.show();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AsignaturasItem current = mValues.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public  TextView nombre;
        public  TextView profesor;
        public AsignaturasItem current_object;
        public int position;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomSheetDialog bs = new BottomSheetDialog(v.getContext());
                    bs.setContentView(R.layout.editar);
                    asignatura = (TextView) bs.findViewById(R.id.asignatura);
                    profesor = (TextView) bs.findViewById(R.id.profesor);
                    semestre1 = (RadioButton) bs.findViewById(R.id.semestre1);
                    semestre2 = (RadioButton) bs.findViewById(R.id.semestre2);

                    nota = (NumberPicker) bs.findViewById(R.id.nota);
                    nota.setMinValue(2);
                    nota.setMaxValue(5);
                    nota.setValue(3);

                    annio = (NumberPicker) bs.findViewById(R.id.annio);
                    annio.setMinValue(1);
                    annio.setMaxValue(5);
                    annio.setValue(1);


                    asignatura.setText(current_object.getObject().getNombre());
                    profesor.setText(current_object.getObject().getNombreProfesor());
                    nota.setValue(current_object.getObject().getNota());
                    annio.setValue(current_object.getObject().getAnnio());
                    if (current_object.getObject().getSemestre() == 1) {
                        semestre1.setChecked(true);
                    } else if (current_object.getObject().getSemestre() == 2) {
                        semestre2.setChecked(true);
                    }

                    FloatingActionButton aceptar_fab = (FloatingActionButton) bs.findViewById(R.id.ok);
                    aceptar_fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            boolean error = false;
                            int semestre = 0;

                            if (asignatura.getText().toString().equals("")) {
                                Toast.makeText(v.getContext(), "El campo nombre de la asignatura no puede estar vacío", Toast.LENGTH_SHORT).show();
                                asignatura.setError(null);
                                asignatura.requestFocus();
                                error = true;
                            }
                            if (profesor.getText().toString().equals("")) {
                                Toast.makeText(v.getContext(), "El campo nombre del profesor no puede estar vacío", Toast.LENGTH_SHORT).show();
                                profesor.setError(null);
                                profesor.requestFocus();
                                error = true;
                            }

                            if (error) {

                                return;
                                //si todos los campos estan llenos procedemos a extraer los datos e insertar en la base de datos
                            } else {
                                if (semestre1.isChecked()) {
                                    semestre = 1;
                                } else if (semestre2.isChecked()) {
                                    semestre = 2;
                                }

                                a = new Asignatura(current_object.getObject().getId(), asignatura.getText().toString(), profesor.getText().toString(),
                                        nota.getValue(), annio.getValue(), semestre);

                                if (a.equals(current_object.getObject())) {
                                    return;
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setCancelable(false);
                                    builder.setIcon(v.getContext().getResources().getDrawable(R.drawable.alert));
                                    builder.setNegativeButton(v.getContext().getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.setPositiveButton(v.getContext().getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            db.update(a);
                                            asignatura.setText("");
                                            profesor.setText("");
                                            Toast.makeText(v.getContext(), "Asignatura modificada con éxito", Toast.LENGTH_SHORT).show();
                                            mValues.get(position).setObject(a);
                                            notifyDataSetChanged();
                                        }
                                    });
                                    AlertDialog d = builder.create();
                                    d.setMessage("Esta a punto de modificar una asignatura. Desea continuar?");
                                    d.show();

                                }
                            }
                        }
                    });
                    bs.show();
                }
            });

            nombre = (TextView) view.findViewById(R.id.nombre);
            profesor = (TextView) view.findViewById(R.id.profesor);
        }

        public void setData(AsignaturasItem current_object, int pos) {
            this.nombre.setText(current_object.getNombre());
            this.profesor.setText(current_object.getProfesor());
            this.current_object = current_object;
            this.position = pos;

        }


        @Override
        public String toString() {
            return profesor.getText().toString() + nombre.getText().toString();
        }


    }
}