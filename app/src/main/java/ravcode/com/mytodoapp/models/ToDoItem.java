package ravcode.com.mytodoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by ravi on 8/31/14.
 */
@Table(name = "Items")
public class ToDoItem extends Model {

    @Column(name = "Text", index = true)
    public String text;

    @Column(name = "Completed")
    public Boolean completed;

    @Column(name = "DueDate")
    public String dueDate;

    @Column(name = "Created")
    private Date created;

    @Override
    public String toString() {
        return this.text;
    }

    public static ToDoItem addItem(String text) {
        ToDoItem item = new ToDoItem();
        item.text = text;
        item.completed = false;
        item.dueDate = null;
        item.created = new Date();
        item.save();

        return item;
    }

    public static ToDoItem getItemById(int id) {
        return new Select()
                .from(ToDoItem.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<ToDoItem> getAll() {
        return new Select()
                .from(ToDoItem.class)
                .orderBy("Created ASC")
                .execute();
    }
}
