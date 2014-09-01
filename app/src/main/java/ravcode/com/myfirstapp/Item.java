package ravcode.com.mytodoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import android.support.v4.util.LruCache;

import java.util.Date;
import java.util.List;

/**
 * Created by ravi on 8/31/14.
 */
@Table(name = "Items")
public class Item extends Model {

    @Column(name = "Text", index = true)
    public String text;

    @Column(name = "Completed")
    public Boolean completed;

    @Column(name = "Created")
    private Date created;

    @Override
    public String toString() {
        return this.text;
    }

    public static Item addItem(String text) {
        Item item = new Item();
        item.text = text;
        item.completed = false;
        item.created = new Date();
        item.save();

        return item;
    }

    public static Item getItemById(int id) {
        return new Select()
                .from(Item.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static List<Item> getAll() {
        return new Select()
                .from(Item.class)
                .orderBy("Created ASC")
                .execute();
    }
}
