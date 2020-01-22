package project.julie.assignment2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<Friend> mArrayList;

    private SQLiteDatabase mDatabase;

    public FriendsAdapter(Context context, ArrayList<Friend> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }

    public ArrayList<Friend> getmArrayList() {
        return mArrayList;
    }

    public void setmArrayList(ArrayList<Friend> mArrayList) {
        this.mArrayList = mArrayList;
    }

    @NonNull
    @Override
    public FriendsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView firstName = view.findViewById(R.id.firstName_layout);
                TextView lastName = view.findViewById(R.id.lastName_layout);
                TextView email = view.findViewById(R.id.email_layout);
                TextView phone = view.findViewById(R.id.phone_layout);
                TextView id = view.findViewById(R.id.id_layout);

                Intent i = new Intent(view.getContext(), EditContact.class);
                i.putExtra("keyFirstName", firstName.getText().toString());
                i.putExtra("keyLastName", lastName.getText().toString());
                i.putExtra("keyEmail", email.getText().toString());
                i.putExtra("keyPhone", phone.getText().toString());
                i.putExtra("keyId", Integer.valueOf(id.getText().toString()));
                view.getContext().startActivity(i);
            }
        });

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.MyViewHolder holder, int position) {
        holder.firstName.setText(mArrayList.get(position).getFirstName());
        holder.lastName.setText(mArrayList.get(position).getLastName());
        holder.email.setText(mArrayList.get(position).getEmail());
        holder.phone.setText(mArrayList.get(position).getPhone());
        holder.id.setText(String.valueOf(mArrayList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView firstName;
        private TextView lastName;
        private TextView email;
        private TextView phone;
        private TextView id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName_layout);
            lastName = itemView.findViewById(R.id.lastName_layout);
            email = itemView.findViewById(R.id.email_layout);
            phone = itemView.findViewById(R.id.phone_layout);
            id = itemView.findViewById(R.id.id_layout);
        }
    }


}
