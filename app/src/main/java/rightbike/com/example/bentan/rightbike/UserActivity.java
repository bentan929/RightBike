package rightbike.com.example.bentan.rightbike;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserActivity extends AppCompatActivity {

    TableLayout bicycleTable;
    TextView rentalPeriod, noBike;
    Button btnRenew,btnUnlock;
    public int clickCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bicycleTable=(TableLayout)findViewById(R.id.bicycleTable);
        rentalPeriod=(TextView)findViewById(R.id.rentalPeriod);
        noBike=(TextView)findViewById(R.id.noBike);
        btnRenew=(Button)findViewById(R.id.btnRenew);
        btnUnlock=(Button)findViewById(R.id.btnUnlock);

        final int noOfDays=7;
        Date dateofOrder=new Date();
        Calendar c= Calendar.getInstance();
        c.setTime(dateofOrder);
        c.add(c.DAY_OF_YEAR,noOfDays);
        final String periodEnd=DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        rentalPeriod.setText(periodEnd);
        c.add(c.DAY_OF_YEAR,noOfDays);
        final String RenewOnce=DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        c.add(c.DAY_OF_YEAR,noOfDays);
        final String RenewTwice=DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        Intent mbook=getIntent();
        int rented=mbook.getIntExtra("rented",0);

        if(rented==0){
            bicycleTable.setVisibility(View.INVISIBLE);
        }else{
            noBike.setVisibility(View.INVISIBLE);
            btnUnlock.setVisibility(View.INVISIBLE);
        }

        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QR= new Intent(UserActivity.this,QRCode.class);
                startActivity(QR);
            }
        });

        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickCount+=1;

                if(clickCount==1) {
                    rentalPeriod.setText(RenewOnce);
                    Toast.makeText(UserActivity.this, "You have successfully renewing the bike. You can renew once more.", Toast.LENGTH_SHORT).show();
                } else if(clickCount==2){
                    rentalPeriod.setText(RenewTwice);
                    Toast.makeText(UserActivity.this, "You have successfully renewing the bike.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserActivity.this, "You cannot renew the bike anymore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.btn_logout:

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Log Out");
                builder.setMessage("Confirm log out?");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent signOut=new Intent(UserActivity.this,MainActivity.class);
                        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(signOut);
                        finish();
                    }
                });

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;

            case R.id.aboutUs:

                Intent aboutUs=new Intent(UserActivity.this,aboutUs.class);
                startActivity(aboutUs);
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
