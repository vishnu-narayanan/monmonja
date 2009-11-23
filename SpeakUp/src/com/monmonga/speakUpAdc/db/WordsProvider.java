package com.monmonga.speakUpAdc.db;

/*
Copyright (C) 2009 Momonja, http://www.almondmendoza.com/

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WordsProvider extends ContentProvider {
	private SQLiteOpenHelper mOpenHelper;

	private static final String sDatabaseName = "almondmendoza.howsmyenglish";
	
	
	private static final int DATABASE_VERSION = 1;
	
	private static final int WORDS = 1;
    private static final int WORD_ID = 2;
    
    public static int LIMIT = 10;
    

	private static final UriMatcher sUriMatcher;

	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private Context mContext;
		
        public DatabaseHelper(Context context) {
            super(context, sDatabaseName, null, DATABASE_VERSION);
            mContext = context;
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + WordsBase.Words.TABLE_NAME + " (" +
                    "_id INTEGER PRIMARY KEY," +
                    WordsBase.Words.DIFFICULTY + " TEXT," +
                    WordsBase.Words.WORD + " TEXT," +
                    WordsBase.Words.CREATED_DATE +"created INTEGER ," +
                    WordsBase.Words.MODIFIED_DATE +"created INTEGER" +
			");");
			db.execSQL("INSERT INTO " + WordsBase.Words.TABLE_NAME  +
					DefaultWords.getInsert()
						);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			onCreate(db);
		}
	}
	
	@Override
	public int delete(Uri uri, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (sUriMatcher.match(uri)) {
			case WORDS:
				return WordsBase.Words.CONTENT_TYPE;
			case WORD_ID:
				return WordsBase.Words.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI"  + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = -1;
		
		switch (sUriMatcher.match(uri)) {
    		case WORDS:
    			rowId = db.insert(WordsBase.Words.TABLE_NAME, WordsBase.Words.WORD, values);
	            if (rowId > 0) {
	                Uri starUri = ContentUris.withAppendedId(WordsBase.Words.CONTENT_URI, rowId);
	                getContext().getContentResolver().notifyChange(starUri, null);
	                return starUri;
	            }
	        break;
    		
    		default:
    			throw new IllegalArgumentException("Unknown URI " + uri);
        }
        
        throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		 mOpenHelper = new DatabaseHelper(getContext());
	     return true;
	}
	private static HashMap<String, String> sNotesProjectionMap;

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String limit = null;
		switch (sUriMatcher.match(uri)) {
			case WORDS:
				qb.setTables(WordsBase.Words.TABLE_NAME);
			break;
		}
		
        // Get the database and run the query
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder,String.valueOf(LIMIT));

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,	String[] selectionArgs) {
	     return 0;
	}
	
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(WordsBase.AUTHORITY, "words", WORDS);
		sUriMatcher.addURI(WordsBase.AUTHORITY, "words/#", WORD_ID);
	}		
}