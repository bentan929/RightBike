package rightbike.com.example.bentan.rightbike;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText reg_Name,reg_Matrics,reg_Password;
    Button reg_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_Name=(EditText)findViewById(R.id.regName);
        reg_Matrics=(EditText)findViewById(R.id.regMatrics);
        reg_Password=(EditText)findViewById(R.id.regPassword);
        reg_Btn=(Button)findViewById(R.id.regBtn);

        //Init Firebase
        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("Users");


        reg_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(Register.this);
                mDialog.setMessage("Please wait..");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(reg_Matrics.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(Register.this,"Matricsno already in database", Toast.LENGTH_SHORT).show();
                        }else{
                            mDialog.dismiss();
                            User user= new User(reg_Name.getText().toString(),reg_Password.getText().toString());
                            table_user.child(reg_Matrics.getText().toString()).setValue(user);
                            Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
