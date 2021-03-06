package antdroid.cfbcoach;

//Google Play Services ID: 116207837258

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Home extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    private boolean customCareer;
    private boolean randomize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageLogo = findViewById(R.id.imageLogo);
        imageLogo.setImageResource(R.drawable.main_menu_logo);

        Button newGameButton = findViewById(R.id.buttonNewGame);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder welcome = new AlertDialog.Builder(Home.this);
                welcome.setMessage("Use Default Team Prestige or Randomize Teams Prestige?")
                        .setTitle("Game Option")
                        .setPositiveButton("Default", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent myIntent = new Intent(Home.this, MainActivity.class);
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_DYNASTY");
                                Home.this.startActivity(myIntent);
                            }
                        })
                        .setNegativeButton("Randomize", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                Intent myIntent = new Intent(Home.this, MainActivity.class);
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_DYNASTY_RANDOM");
                                Home.this.startActivity(myIntent);
                            }
                        });
                welcome.setCancelable(false);
                AlertDialog dialog = welcome.create();
                dialog.show();
                TextView msgTxt = dialog.findViewById(android.R.id.message);
                msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
        });

        Button newCustomGameButton = findViewById(R.id.buttonCustomNewGame);
        newCustomGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isExternalStorageReadable();
                customCareer = false;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("text/plain");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });


        Button newCareerButton = findViewById(R.id.buttonCareer);
        newCareerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder welcome = new AlertDialog.Builder(Home.this);
                welcome.setMessage("Use Default Team Prestige or Randomize Teams Prestige?")
                        .setTitle("Game Option")
                        .setPositiveButton("Default", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Intent myIntent = new Intent(Home.this, MainActivity.class);
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_CAREER");
                                Home.this.startActivity(myIntent);
                            }
                        })
                        .setNegativeButton("Randomize", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                Intent myIntent = new Intent(Home.this, MainActivity.class);
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_CAREER_RANDOM");
                                Home.this.startActivity(myIntent);
                            }
                        });
                welcome.setCancelable(false);
                AlertDialog dialog = welcome.create();
                dialog.show();
                TextView msgTxt = dialog.findViewById(android.R.id.message);
                msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
        });

        Button newCustomCareerButton = findViewById(R.id.buttonCustomCareer);
        newCustomCareerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isExternalStorageReadable();
                customCareer = true;
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("text/plain");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        Button loadGameButton = findViewById(R.id.buttonLoadGame);
        loadGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                loadLeague();
            }
        });

        Button deleteGameButton = findViewById(R.id.buttonDeleteSave);
        deleteGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                deleteSave();
            }
        });

        Button recentButton = findViewById(R.id.buttonUpdates);
        recentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                welcomeMessage();
            }
        });

        Button tutorialButton = findViewById(R.id.buttonTutorial);
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(Home.this, TutorialActivity.class);
                Home.this.startActivity(myIntent);
            }
        });

        Button creditsButton = findViewById(R.id.buttonCredits);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder welcome = new AlertDialog.Builder(Home.this);
                welcome.setMessage(getString(R.string.credits))
                        .setTitle("Game Acknowledgements")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                welcome.setCancelable(false);
                AlertDialog dialog = welcome.create();
                dialog.show();
                TextView msgTxt = dialog.findViewById(android.R.id.message);
                msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
        });

        Button googleButton = findViewById(R.id.buttonForum);
        googleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://groups.google.com/forum/#!forum/college-football-coach-career-edition"));
                startActivity(intent);
            }
        });


        Button subredditButton = findViewById(R.id.buttonSubreddit);
        subredditButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://m.reddit.com/r/FootballCoach"));
                startActivity(intent);
            }
        });


        Button antdroidButton = findViewById(R.id.buttonAntdroid);
        antdroidButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.Antdroid.net"));
                startActivity(intent);
            }
        });

        Button githubButton = findViewById(R.id.buttonGitHub);
        githubButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/antdroidx/CFB-Coach"));
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void welcomeMessage() {
        AlertDialog.Builder welcome = new AlertDialog.Builder(this);
        welcome.setMessage(getString(R.string.changelog))
                .setTitle("Welcome!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        welcome.setCancelable(false);
        AlertDialog dialog = welcome.create();
        dialog.show();
        TextView msgTxt = dialog.findViewById(android.R.id.message);
        msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }

    /**
     * Save League, show dialog to choose which save file to save onto.
     */
    private void loadLeague() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose File to Load:");
        final String[] fileInfos = getSaveFileInfos();
        SaveFilesListArrayAdapter saveFilesAdapter = new SaveFilesListArrayAdapter(this, fileInfos);
        builder.setAdapter(saveFilesAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (!fileInfos[item].equals("EMPTY")) {
                    finish();
                    Intent myIntent = new Intent(Home.this, MainActivity.class);
                    myIntent.putExtra("SAVE_FILE", "saveFile" + item + ".cfb");
                    Home.this.startActivity(myIntent);
                } else {
                    Toast.makeText(Home.this, "Cannot load empty file!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Delete Save
     */
    private void deleteSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose File to Delete:");
        final String[] fileInfos = getSaveFileInfos();
        SaveFilesListArrayAdapter saveFilesAdapter = new SaveFilesListArrayAdapter(this, fileInfos);
        builder.setAdapter(saveFilesAdapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                if (!fileInfos[item].equals("EMPTY")) {
                    deleteFile("saveFile" + item + ".cfb");
                } else {
                    Toast.makeText(Home.this, "Cannot load delete file!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Get info of the 10 save files for printing in the save file list
     */
    private String[] getSaveFileInfos() {
        String[] infos = new String[10];
        String fileInfo;
        File saveFile;
        File extFile;
        for (int i = 0; i < 10; ++i) {
            saveFile = new File(getFilesDir(), "saveFile" + i + ".cfb");
            if (saveFile.exists()) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile));
                    fileInfo = bufferedReader.readLine();
                    infos[i] = fileInfo.substring(0, fileInfo.length() - 1); //gets rid of % at end
                } catch (FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file");
                } catch (IOException ex) {
                    System.out.println(
                            "Error reading file");
                } catch (NullPointerException ex) {
                    System.out.println(
                            "Null pointer exception!");
                }
            } else {
                infos[i] = "EMPTY";
            }
        }
        return infos;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    /* Creates external Save directory */

    public File getExtSaveDir(Context context, String cfbCoach) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), cfbCoach);
        if (!file.mkdirs()) {
            Log.e(cfbCoach, "Directory not created");
        }
        return file;
    }

    private Uri getURI(Intent intent) {
        Uri uri = intent.getData();
        return uri;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        final Intent myIntent = new Intent(Home.this, MainActivity.class);
        //String uriStr;

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Home.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            uri = resultData.getData();
           final String uriStr = uri.toString();
            AlertDialog.Builder welcome = new AlertDialog.Builder(Home.this);
            welcome.setMessage("Use Default Team Prestige or Randomize Teams Prestige?")
                    .setTitle("Game Option")
                    .setPositiveButton("Default", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (customCareer) {
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_CAREER-CUSTOM," + uriStr);
                            } else {
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_DYNASTY-CUSTOM," + uriStr);
                            }
                            finish();
                            Home.this.startActivity(myIntent);
                        }
                    })
                    .setNegativeButton("Randomize", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (customCareer) {
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_CAREER-CUSTOM_RANDOM," + uriStr);
                            } else {
                                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_DYNASTY-CUSTOM_RANDOM," + uriStr);
                            }
                            finish();
                            Home.this.startActivity(myIntent);
                        }
                    });
            welcome.setCancelable(false);
            AlertDialog dialog = welcome.create();
            dialog.show();
            TextView msgTxt = dialog.findViewById(android.R.id.message);
            msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);



/*            if (customCareer) {
                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_CAREER-CUSTOM," + uriStr);
            } else {
                myIntent.putExtra("SAVE_FILE", "NEW_LEAGUE_DYNASTY-CUSTOM," + uriStr);
            }
            finish();
            Home.this.startActivity(myIntent);*/

        }
    }

}


