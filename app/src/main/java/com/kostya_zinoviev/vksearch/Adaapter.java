package com.kostya_zinoviev.vksearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class Adaapter extends RecyclerView.Adapter<Adaapter.ViewHolder> {

    List<MyModel> myModelList;
    int newPosition;

    public Adaapter(List<MyModel> myModelList) {
        this.myModelList = myModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyModel currentdIndex = myModelList.get(position);
        holder.name.setText(currentdIndex.getName());
        holder.lastName.setText(currentdIndex.getLastName());
        holder.id.setText(String.valueOf(currentdIndex.getId()));
        newPosition = holder.getAdapterPosition();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myModelList.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, myModelList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return myModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView lastName;
        TextView id;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            lastName = itemView.findViewById(R.id.lastName);
            id = itemView.findViewById(R.id.idLox);
            delete = itemView.findViewById(R.id.delete);
        }

    }
   public void updateRecyclerView(int position){
        notifyItemInserted(position);
    }
}
