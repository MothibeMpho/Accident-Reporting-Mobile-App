package team1.rims.rims;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {

private EditText txtcell;
private EditText txtcode;
private ProgressBar progressBar;
private Button btnRegister;
private  Button btnResendCode;
private  Button btnVerify;
private  String phoneVerificationId;
private FirebaseAuth Auth;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks CallBacks;
private PhoneAuthProvider.ForceResendingToken resendToken;
private  static  final  String TAG = "PhoneLogin";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtcell= (EditText)findViewById(R.id.txtCell);
        txtcode=(EditText)findViewById(R.id.txtCode);
        btnRegister =(Button) findViewById(R.id.btnRegister);
        btnResendCode=(Button)findViewById(R.id.btnResendCode);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        btnVerify=(Button)findViewById(R.id.btnVerify);

        btnVerify.setEnabled(true);
        btnResendCode.setEnabled(false);

        Auth= FirebaseAuth.getInstance();




    }


    public void sendVerificationCode(View view) {

        String phone = txtcell.getText().toString();


        setUpVerificaionCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                phone, // number being verified
                60, // timeout
                TimeUnit.SECONDS,//unit of timeout
                this,
                CallBacks



        );
        Toast.makeText(Register.this,"Working...", Toast.LENGTH_SHORT).show();
    }

    private  void setUpVerificaionCallbacks() {
        CallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.d(TAG, "Invalid Credential: " + e.getLocalizedMessage());

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.d(TAG, "SMS quota exceeded: ");
                }
            }
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                Toast.makeText(Register.this,"Verification code has been sent to your number.", Toast.LENGTH_LONG).show();
                phoneVerificationId= verificationId;
                resendToken=token;


            }
        };
    }



    //method to verify what user entered is correct
    public void verifySignInCode(View view)
    {
        String code= txtcode.getText().toString();



        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(phoneVerificationId,code); //comparing code sent to user and the one entered by user
        signInWithPhoneAuthCredential(credential);
    }

public void resendCode(View view){

        String phone=txtcell.getText().toString();
        setUpVerificaionCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(

            phone, // number being verified
            60, // timeout
            TimeUnit.SECONDS,//unit of timeout
            this,
            CallBacks,
                resendToken);

}

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            Intent reportIntent = new Intent(Register.this, Report.class);
                            Register.this.startActivity(reportIntent);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}










