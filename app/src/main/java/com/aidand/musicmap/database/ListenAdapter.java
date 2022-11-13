package com.aidand.musicmap.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.aidand.musicmap.R;
import com.aidand.musicmap.models.Listen;

import java.util.List;

public class ListenAdapter extends BaseAdapter {
    Object context;
    private List<Listen> listens;
    private static LayoutInflater inflater = null;

    public ListenAdapter(Object context, List<Listen> listens) {
        this.context = context;
        this.listens = listens;
        inflater = (LayoutInflater)((Context) context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listens.size();
    }

    @Override
    public Listen getItem(int position) {
        return listens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View vi;
        if (convertView == null)
            vi = inflater.inflate(R.layout.activity_main_rowlayout, null);
        else
            vi = convertView;
        TextView taskName = vi.findViewById(R.id.taskName);
        TextView taskDesc = vi.findViewById(R.id.taskDescription);
        //TextView taskStatus =  vi.findViewById(R.id.taskPrice);

        Listen listen = listens.get(position);

        taskName.setText(listen.getName());
        taskDesc.setText(listen.getDescription());
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0){
                    Dog toMove = dogs.get(position);
                    dogs.set(position, dogs.get(position - 1));
                    dogs.set(position - 1, toMove);
                    notifyDataSetChanged();
                }
            }
        });
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent((MainActivity)context, MainActivityRow.class);
                // creating a bundle object
                Bundle bundle = new Bundle();
                bundle.putParcelable("listen", listens.get(position));
                intent.putExtras(bundle);
                ((MainActivity)context).startActivity(intent);
            }
        });
        return vi; */
        return null;
    }
}