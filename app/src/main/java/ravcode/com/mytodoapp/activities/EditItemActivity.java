package ravcode.com.mytodoapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ravcode.com.mytodoapp.fragments.DatePickerFragment;


public class EditItemActivity extends FragmentActivity {

    ravcode.com.mytodoapp.ToDoItem toDoItem;
    long item_id;
    private Date enteredDueDate;
    private static final String TAG = "EditItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item_id = getIntent().getLongExtra("item_id", 1L);
        toDoItem = ravcode.com.mytodoapp.ToDoItem.getItemById(item_id);

        if (toDoItem != null) {
            EditText etNewItem = (EditText) findViewById(R.id.itemEditTextInput);
            etNewItem.setText(toDoItem.text, TextView.BufferType.EDITABLE);
            setFormattedDueDate(toDoItem.dueDate);
        }

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

        if (enteredDueDate != null) {
            toDoItem.dueDate = enteredDueDate;
            setResult(RESULT_OK);
        }

        if (modifiedToDoText != null && !modifiedToDoText.isEmpty()) {
            toDoItem.text = modifiedToDoText;
            setResult(RESULT_OK);
        }

        toDoItem.save();
        finish();
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment;
        final Calendar c = Calendar.getInstance();
        if (enteredDueDate != null) {
            c.setTime(enteredDueDate);
        }

        newFragment = DatePickerFragment.newInstance(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onDateSet(int year, int month, int day) {
        String dueDateString = (month + 1) + "/" + day + "/" + year;
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        Date validDate = null;

        // First lets validate if the date is in expected format and is the correct date
        try {
            validDate = dateFormat.parse(dueDateString);
        }
        catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Invalid Due Date!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dateFormat.format(validDate).equals(dueDateString)) {
            Toast.makeText(getApplicationContext(), "Invalid Due Date!", Toast.LENGTH_SHORT).show();
            return;
        }

        setFormattedDueDate(validDate);
    }

    private void setFormattedDueDate(Date dueDate) {
        if (dueDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM, yyyy");
            Button dueDateButton = (Button)findViewById(R.id.dueDateButton);
            dueDateButton.setText("Due " + dateFormat.format(dueDate));
            enteredDueDate = dueDate;

            if (new Date().after(dueDate)) {
                dueDateButton.setTextColor(getResources().getColor(R.color.due_date_in_past_color));
            }
            else {
                dueDateButton.setTextColor(getResources().getColor(R.color.due_date_color));
            }
        }
    }
}
