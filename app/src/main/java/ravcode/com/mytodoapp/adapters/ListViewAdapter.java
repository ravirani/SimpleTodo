package ravcode.com.mytodoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ravi on 9/5/14.
 */
public class ListViewAdapter extends ArrayAdapter<ravcode.com.mytodoapp.ToDoItem> {

    public ListViewAdapter(Context context, ArrayList<ravcode.com.mytodoapp.ToDoItem> items) {
        super(context, R.layout.list_item_view, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ravcode.com.mytodoapp.ToDoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view, parent, false);
        }

        TextView itemName = (TextView)convertView.findViewById(R.id.itemName);
        itemName.setText(item.text);
        return convertView;
    }
}
