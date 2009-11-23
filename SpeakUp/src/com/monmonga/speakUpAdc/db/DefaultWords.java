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

public class DefaultWords {
	private static String insertStatement = "";
	private static int counter = 0;
	
	private static String[] easyWords = {
		"pencil","color","mobile","radio","bag","cat","dog","orange","apple","blue","blank",
		"black","science","funny","work","grammar","children","waste","difficult","items","show",
		"daily","record","laptop","burn","package","habits","start","end","wife","fails","city",
		"victory","people","moon","place","drama","house","eat","tea","short","level",
		"friend","team","world","phone","force","members","army",
		"history","toilet","choice","service","famous","fuel","homes","climate","reason","delayed"
	};
	
	private static String[] mediumWords = {
		"chronicles","corner","penalty","burning","appointed","knowledge","discharge","suite","sentence",
		"tongue","ignorant","patrons","communicate","incident","notorious","sues","blimps","premier","native",
		"horror","independent","consultation","sanctions","adaptation","thoughts","depression","finite",
		"feminine","youth","ethnic","orchid","aerospace","dynamic","compound","ceremony",
		"assorted","convention","goodwill","foreigners","commission","commander","patents","schedule",
		"detailed","habitats","character","acquired","episode","exhibition","physical",
		"skeptics","bookshelf","researchers","contracting","challenge","domestic","refugees","flopped","investigation","committee"
	};
	
	private static String[] hardWords = {
		"conscientious","exhilarate","gauge","humorous","indispensable","hierarchy","liaison","maneuver","miniature",
		"minuscule","playwright","threshold","twelfth","honourable","ophthalmologist","loquacity","evidence","democratically",
		"contravene","efficient","intricate","vertebrate","distinguishes","perspectives","species","kinematic",
		"orchestra","rhythm","circulation","newsracks","indigenous","humanitarian","vandalism","abolishing","infringements",
		"foundered","silhouette","conceptual","curated","appearances","manufacturer","reincarnation","various",
		"revenues","simultaneous","detonate","coordination","guerrillas","ceasefire",
		"guarantees","enthusiasm","anxieties","catastrophe","pilgrims","venomous","memorabilia","evangelicals","illustrate","representatives"
		
	};
	
	public static void formInsert(String difficulty,String word){
		insertStatement += " SELECT "+ (++counter) +",'" + difficulty + "','" + word + "',DATETIME('NOW'),DATETIME('NOW') " +
					" UNION ALL ";
	}
	
	public static String getInsert(){
		for(String word:easyWords){
			formInsert("easy",word);
		}
		for(String word:mediumWords){
			formInsert("medium",word);
		}
		for(String word:hardWords){
			formInsert("hard",word);
		}
		
		return insertStatement.substring(0, insertStatement.lastIndexOf("UNION"));
	}
}
