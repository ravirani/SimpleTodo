package ravcode.com.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import ravcode.com.mytodoapp.Item;

import java.util.ArrayList;

public class ToDoList extends Activity {
    ArrayList<Item> items;
    ArrayAdapter<Item> itemsAdapter;
    ListView lvItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<Item>(Item.getAll());
        itemsAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        if (items.isEmpty()) {
            items.add(Item.addItem("First Item"));
            items.add(Item.addItem("Second Item"));
        }
        setupListViewListener();
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
            itemsAdapter.add(Item.addItem(todoItemText));
            etNewItem.setText("");
            lvItems.requestFocus();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Item itemToRemove = items.get(position);
                itemToRemove.delete();
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Intent editItemIntent = new Intent(ToDoList.this, ravcode.com.mytodoapp.EditItemActivity.class);
                editItemIntent.putExtra("item_text", items.get(position).text);
                editItemIntent.putExtra("position", position);
                startActivityForResult(editItemIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String modifiedToDoText = data.getExtras().getString("item_text");
            int position = data.getExtras().getInt("position");
            Item itemToModify = items.get(position);
            itemToModify.text = modifiedToDoText;
            itemToModify.save();
            itemsAdapter.notifyDataSetChanged();

            lvItems.requestFocus();
            Toast.makeText(getApplicationContext(), "Item changed successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
