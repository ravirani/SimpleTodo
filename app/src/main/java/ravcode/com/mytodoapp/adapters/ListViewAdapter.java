package ravcode.com.mytodoapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ravi on 9/5/14.
 */
public class ListViewAdapter extends ArrayAdapter<ravcode.com.mytodoapp.ToDoItem> {

    private static final String TAG = "ListViewAdapter";
    private Context context;

    private static class ViewHolder {
        TextView itemName;
        TextView itemDueDateTextView;
        CheckBox itemCompletedCheckbox;
    }

    public ListViewAdapter(Context ctx, ArrayList<ravcode.com.mytodoapp.ToDoItem> items) {
        super(ctx, R.layout.list_item_view, items);
        context = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ravcode.com.mytodoapp.ToDoItem item = getItem(position);

        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_view, parent, false);
            viewHolder.itemName = (TextView)convertView.findViewById(R.id.itemName);
            viewHolder.itemCompletedCheckbox = (CheckBox)convertView.findViewById(R.id.itemNameCheckbox);
            viewHolder.itemDueDateTextView = (TextView)convertView.findViewById(R.id.dueDateTextView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.itemName.setText(item.text);
        viewHolder.itemCompletedCheckbox.setChecked(item.completed);
        viewHolder.itemCompletedCheckbox.setTag(item.getId());

        viewHolder.itemCompletedCheckbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CheckBox completedCheckbox = (CheckBox)view;
                ravcode.com.mytodoapp.ToDoItem toDoItem = ravcode.com.mytodoapp.ToDoItem.getItemById(((Long)completedCheckbox.getTag()).longValue());
                toDoItem.completed = completedCheckbox.isChecked();
                toDoItem.save();
            }
        });

        if (item.dueDate != null) {
            viewHolder.itemDueDateTextView.setText("Due " + ravcode.com.mytodoapp.ToDoItem.getFormattedDueDate(item.dueDate, false));
            viewHolder.itemDueDateTextView.setVisibility(View.VISIBLE);

            if (item.isDueDateInPast()) {
                viewHolder.itemDueDateTextView.setTextColor(context.getResources().getColor(R.color.due_date_in_past_color));
            }
            else {
                viewHolder.itemDueDateTextView.setTextColor(context.getResources().getColor(R.color.due_date_color));
            }
        }
        else {
            viewHolder.itemDueDateTextView.setText("");
            viewHolder.itemDueDateTextView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
