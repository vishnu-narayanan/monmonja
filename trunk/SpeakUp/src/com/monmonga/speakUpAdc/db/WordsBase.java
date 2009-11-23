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

import android.net.Uri;
import android.provider.BaseColumns;

public class WordsBase {
	public static final String PACKAGE = "monmonga.speakUpAdc.db";
	public static final String AUTHORITY = "com." + PACKAGE;

	
	private WordsBase(){
	}
	
	public static final class Words implements BaseColumns {
		public static final String TABLE_NAME = "words";
		private Words() {}
		 
		 
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + PACKAGE + "." + TABLE_NAME;
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + PACKAGE + "." + TABLE_NAME;

		public static final String DIFFICULTY = "diffucult";
		public static final String WORD = "word";
		public static final String CREATED_DATE = "created";
		public static final String MODIFIED_DATE = "modified";
	 }
}
