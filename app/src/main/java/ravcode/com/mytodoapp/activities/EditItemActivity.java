package ravcode.com.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditItemActivity extends Activity {

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("item_text");
        position = getIntent().getIntExtra("position", 0);

        EditText etNewItem = (EditText)findViewById(R.id.itemEditTextInput);
        etNewItem.setText(itemText, TextView.BufferType.EDITABLE);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
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

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTodoItem(View v) {
        EditText itemEditTextInput = (EditText)findViewById(R.id.itemEditTextInput);
        String modifiedToDoText = itemEditTextInput.getText().toString();
        if (modifiedToDoText != null && !modifiedToDoText.isEmpty()) {
            Intent data = new Intent();
            data.putExtra("item_text", modifiedToDoText);
            data.putExtra("position", position);

            setResult(RESULT_OK, data);
            finish();
        }
    }
}
