package ravcode.com.mytodoapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ravcode.com.mytodoapp.fragments.DatePickerFragment;

import java.util.Calendar;
import java.util.Formatter;


public class EditItemActivity extends FragmentActivity {

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

    public void showDatePicker(View view) {
        // Really hacky implementation to finish this part
        Button dueDateButton = (Button)findViewById(R.id.datePicker);
        String[] currentDate = ((String)dueDateButton.getText()).split("/");
        DialogFragment newFragment;
        if (currentDate.length == 3) {
            newFragment = DatePickerFragment.newInstance(Integer.parseInt(currentDate[2]), Integer.parseInt(currentDate[0]), Integer.parseInt(currentDate[1]));
        }
        else {
            final Calendar c = Calendar.getInstance();
            newFragment = DatePickerFragment.newInstance(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        }

        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onDateSet(int year, int month, int day) {
        Button dueDateButton = (Button)findViewById(R.id.datePicker);
        dueDateButton.setText(month + "/" + day + "/" + year);
    }
}
