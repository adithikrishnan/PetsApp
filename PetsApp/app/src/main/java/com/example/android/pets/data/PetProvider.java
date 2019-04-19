package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.example.android.pets.data.PetContract;

public class PetProvider extends ContentProvider{
  private PetDbHelper mDbHelper;
  private static final int PETS = 100;
  private static final int PET_ID = 101;
  private static final UriMatcher sUriMatcher = new UriMatcher(UriMatchedr.NO_MATCH);
  
  static {
    sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
    sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
  }
  
  
  @Override
  public boolean onCreate(){
    mDbHelper = new PetDbHelper(getContext());
    return true;
  }
  
  @Override
  public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
    return null;
  }
  
  @Override
  public String getType(Uri uri){
    return null;
  }
  
  @Override
  public Uri insert(Uri uri, ContentValues contentValues) {
    return null;
  }
  
  @Override
  public int delete(Uri uri, String s, String[] strings) {
    return 0;
  }
  
  @Override
  public int update(Uri uri, ContentValues contentValues, String s, String[] strings){
    return 0;
  }
}
