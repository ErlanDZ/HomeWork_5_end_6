package com.example.work;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    LayoutInflater layoutInflater;
    public List<TaskModel> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public TaskAdapter (Context context){
        this.layoutInflater = LayoutInflater.from(context);
    }


        public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public  void  delete (int position){
        list.remove(position);
        notifyDataSetChanged();
    }

    public void dataTry (int position, TaskModel model){
        list.get(position).setTitle(model.getTitle());
        list.get(position).setDescription(model.getDescription());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
        holder.bind(list.get(position));
//       holder.txtTitleItemTask.setText(list.get(position).getTitle());
//       holder.txtDescriptionItemTask.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addTask(TaskModel taskModel){
        this.list.add(taskModel);
        notifyDataSetChanged();
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder {
       private TextView  txtTitleItemTask, txtDescriptionItemTask;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitleItemTask = itemView.findViewById(R.id.txt_title_item_task);
            txtDescriptionItemTask = itemView.findViewById(R.id.txt_description_item_task);

        }

        public void bind(TaskModel taskModel) {
            txtTitleItemTask.setText(taskModel.getTitle());
            txtDescriptionItemTask.setText(taskModel.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.itemClick(getAdapterPosition(),taskModel);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
                    alertDialog.setTitle("ВНИМАНИЕ");
                    alertDialog.setMessage("ТОЧНО УДАЛИТЬ????");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(getAdapterPosition());
                        }
                    });
                    alertDialog.show();
                    return false;
                }
            });
        }
    }
}
