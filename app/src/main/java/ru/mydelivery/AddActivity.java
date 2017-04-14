package ru.mydelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.mydelivery.Provider.VolleyRequest;
import ru.mydelivery.Utils.DateTimePikerDialog;

public class AddActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText editFio;
    private EditText editTelephone;
    private EditText editPickFrom;
    private EditText editAddress;
    private EditText editDesc;
    private EditText editPrice;
    private EditText editNote;
    private EditText editDate;
    private EditText editUserId;
    private VolleyRequest volleyRequest;
    private DateTimePikerDialog dateTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editFio = (EditText) findViewById(R.id.editFio);
        editTelephone = (EditText) findViewById(R.id.editPhone);
        editPickFrom = (EditText) findViewById(R.id.editPickFrom);
        editAddress = (EditText) findViewById(R.id.editAddress);
        editDesc = (EditText) findViewById(R.id.editDesc);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editNote = (EditText) findViewById(R.id.editNote);
        editDate = (EditText) findViewById(R.id.editData);
        editUserId = (EditText) findViewById(R.id.editUser);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        dateTimePickerDialog = new DateTimePikerDialog(AddActivity.this, editDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDate.setText("");
                dateTimePickerDialog.showData();

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fio = editFio.getText().toString().trim();
                String telephone = "+" + editTelephone.getText().toString().trim();
                String pickFrom = editPickFrom.getText().toString().trim();
                String address = editAddress.getText().toString().trim();
                String desc = editDesc.getText().toString().trim();
                String price = editPrice.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                String date = editDate.getText().toString().trim();
                String userId = editUserId.getText().toString().trim();
                String mNote = note;
                if (note.isEmpty()) {
                    editNote.setText("Отсутствует");
                    mNote = editNote.getText().toString().trim();
                }
                if (!fio.isEmpty() && !telephone.isEmpty() && !pickFrom.isEmpty() && !address.isEmpty() && !desc.isEmpty() && !price.isEmpty() && !date.isEmpty() && !userId.isEmpty()) {
                    volleyRequest = new VolleyRequest();
                    volleyRequest.addJob(AddActivity.this, fio, telephone, pickFrom, address, desc, price, mNote, date, userId);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
