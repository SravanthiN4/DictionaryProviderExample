/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.example.com.dictionaryproviderexample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.provider.UserDictionary.Words;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * This is the central activity for the Provider Dictionary Example App. The purpose of this app is
 * to show an example of accessing the {@link Words} list via its' Content Provider.
 */
public class MainActivity extends ActionBarActivity {

    // For the SimpleCursorAdapter to match the UserDictionary columns to layout items.
    private static final String[] COLUMNS_TO_BE_BOUND  = new String[] {
            UserDictionary.Words.WORD,
            UserDictionary.Words.FREQUENCY
    };

    private static final int[] LAYOUT_ITEMS_TO_FILL = new int[] {
            android.R.id.text1,
            android.R.id.text2
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView which will be populated with the Dictionary ContentProvider data.
       // ListView dictListView = (ListView) findViewById(R.id.dictionary_list_view);

        TextView dictTextView = (TextView)findViewById(R.id.dictionary_text_view);


        ContentValues newValues = new ContentValues();

        newValues.put(Words._ID, "my_app_id");
        newValues.put(Words.WORD, "test");
        newValues.put(Words.FREQUENCY, "10");

        // Get the ContentResolver which will send a message to the ContentProvider
        ContentResolver resolver = getContentResolver();


        Uri result = resolver.insert(UserDictionary.Words.CONTENT_URI,newValues);



        // Get a Cursor containing all of the rows in the Words table
        Cursor cursor = resolver.query(UserDictionary.Words.CONTENT_URI, null, null, null, null);


        // -- YOUR CODE BELOW HERE -- //

        try {
            dictTextView.setText("UserDictionary contains " + cursor.getCount() + " words\n");
            dictTextView.append("COLUMNS: " + Words._ID  + " - " + Words.FREQUENCY +
                    " - " + Words.WORD);
            int idColumn = cursor.getColumnIndex(UserDictionary.Words._ID);
            int frequencyColumn = cursor.getColumnIndex(UserDictionary.Words.FREQUENCY);
            int wordColumn = cursor.getColumnIndex(UserDictionary.Words.WORD);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumn);
                int frequency = cursor.getInt(frequencyColumn);
                String word = cursor.getString(wordColumn);
                dictTextView.append(("\n" + id + " - " + frequency + " - " + word));
            }
        } finally {
            cursor.close();
        }

//        // Set the Adapter to fill the standard two_line_list_item layout with data from the Cursor.
//        SimpleCursorAdapter adapter = null;
//        adapter = new SimpleCursorAdapter(this,
//                android.R.layout.two_line_list_item,
//                cursor,
//                COLUMNS_TO_BE_BOUND,
//                LAYOUT_ITEMS_TO_FILL,
//                0);
//
//        // Attach the adapter to the ListView.
//        dictListView.setAdapter(adapter);


    }
}