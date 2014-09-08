package ravcode.com.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoListActivity extends Activity {
    ArrayList<ravcode.com.mytodoapp.ToDoItem> items;
    ravcode.com.mytodoapp.ListViewAdapter itemsAdapter;
    ListView lvItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        lvItems = (ListView)findViewById(R.id.lvItems);
        queryAndFillItems();
        itemsAdapter = new ravcode.com.mytodoapp.ListViewAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        if (items.isEmpty()) {
            items.add(ravcode.com.mytodoapp.ToDoItem.addItem("Work on Android app"));
            items.add(ravcode.com.mytodoapp.ToDoItem.addItem("Read cliff notes"));
        }
        setupListViewListener();
    }

    private void queryAndFillItems() {
        items = new ArrayList<ravcode.com.mytodoapp.ToDoItem>(ravcode.com.mytodoapp.ToDoItem.getAll());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTodoItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String todoItemText = etNewItem.getText().toString();
        if (todoItemText != null && !todoItemText.isEmpty()) {
            itemsAdapter.add(ravcode.com.mytodoapp.ToDoItem.addItem(todoItemText));
            etNewItem.setText("");
            lvItems.requestFocus();
            lvItems.setSelection(itemsAdapter.getCount() - 1);
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                ravcode.com.mytodoapp.ToDoItem itemToRemove = items.get(position);
                items.remove(position);
                itemToRemove.delete();
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Intent editItemIntent = new Intent(ToDoListActivity.this, ravcode.com.mytodoapp.EditItemActivity.class);
                editItemIntent.putExtra("item_id", items.get(position).getId());
                startActivityForResult(editItemIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            queryAndFillItems();
            itemsAdapter.notifyDataSetChanged();
            lvItems.requestFocus();
            Toast.makeText(getApplicationContext(), "Item changed successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
