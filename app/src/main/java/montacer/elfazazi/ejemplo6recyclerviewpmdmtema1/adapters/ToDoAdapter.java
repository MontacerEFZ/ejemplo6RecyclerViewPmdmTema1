package montacer.elfazazi.ejemplo6recyclerviewpmdmtema1.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import montacer.elfazazi.ejemplo6recyclerviewpmdmtema1.R;
import montacer.elfazazi.ejemplo6recyclerviewpmdmtema1.modelos.ToDo;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TodoVH> {
    //extends RecyclerView.Adapter<ToDoAdapter.TodoVH> esto va a mano y creamos clase TodoVH y rellenar, despues importar resto cosas
    private List<ToDo> objects; //la lista de tareas
    private int resource; //fila, vista para construir la fila, int porq si pones cursos encima de lbTituloToDoViewModel y las otras, pone que devuelve int
    private Context context; //la actividad que tiene el RecyclerView para mostrar

    //crear constructor con lo anterior
    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource; //aunq diga q es un int, es la vista en la q estamos
        this.context = context; //la actividad donde se mostrara
    }

    @NonNull
    @Override
    public TodoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //instancia tantos elementos como quepan en pantalla
        View todoView = LayoutInflater.from(context).inflate(resource, null);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        todoView.setLayoutParams(lp);
        return new TodoVH(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoVH holder, int position) {
        //se llama de forma automatica en base al elemento que tenemos que rellenar
        ToDo toDo = objects.get(position);

        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdate("seguro que quieres cambiar?", toDo).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete("Seguro que quieres eliminar?", holder.getAdapterPosition()).show();
                                    //dara error al devolver un int posicion, hay q usar holder.getAdapterPosition()
            }
        });
    }

    @Override
    public int getItemCount() {
        //sirve para que el adapter averigue cuantos objetos va a mostrar
        return objects.size();
    }
    private AlertDialog confirmUpdate(String titulo, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false); //para que no se cierre si aprieta fuera del cuadro de dialogo, obligado responder al dialogo

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    private AlertDialog confirmDelete(String titulo, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objects.remove(position);
                notifyItemRemoved(position);
            }
        });

        return builder.create();
    }

    public class TodoVH extends RecyclerView.ViewHolder{ //extends RecyclerView.ViewHolder esto a mano

        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado, btnDelete;
        public TodoVH(@NonNull View itemView) {
            super(itemView);

            lbTitulo = itemView.findViewById(R.id.lbTituloToDoViewModel);
            lbContenido = itemView.findViewById(R.id.lbContenidoToDoViewModel);
            lbFecha = itemView.findViewById(R.id.lbFechaToDoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);
            btnDelete = itemView.findViewById(R.id.btnDeleteToDoViewModel);

            //ahora importar metodos del ToDoAdapter


        }
    }
}
