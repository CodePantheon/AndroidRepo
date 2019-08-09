package org.codepantheon.takenotes.data;

import android.provider.BaseColumns;

public class NotesContract {

    public static final class NotesEntry implements BaseColumns {

        public  static  final String TABLE_NAME = "notes";
        public  static  final String COLUMN_TITLE = "title";
        public  static  final String COLUMN_CONTENT = "content";
        public  static  final String COLUMN_TIMESTAMP = "timestamp";

    }
}
