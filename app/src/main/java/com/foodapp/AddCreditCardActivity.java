package com.foodapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.foodapp.models.PaymentMethod;
import com.foodapp.utils.PaymentManager;

public class AddCreditCardActivity extends AppCompatActivity {

    private ImageView ivBack;
    private EditText etCardholderName;
    private EditText etCardNumber1;
    private EditText etCardNumber2;
    private EditText etCardNumber3;
    private EditText etCardNumber4;
    private EditText etExpiryDate;
    private EditText etCvv;
    private CheckBox cbSetPrimary;
    private Button btnSaveCard;
    
    // Card preview elements
    private TextView tvCardNumber;
    private TextView tvCardholderName;
    
    private PaymentManager paymentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        
        // Initialize PaymentManager
        paymentManager = PaymentManager.getInstance(this);
        
        // Initialize views
        initViews();
        
        // Set up back button
        ivBack.setOnClickListener(v -> finish());
        
        // Set up card input watchers
        setupCardInputWatchers();
        
        // Set up save card button
        btnSaveCard.setOnClickListener(v -> {
            if (validateCardDetails()) {
                saveCardDetails();
                // Navigate back to CheckoutActivity
                finish();
            }
        });
    }
    
    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etCardholderName = findViewById(R.id.etCardholderName);
        etCardNumber1 = findViewById(R.id.etCardNumber1);
        etCardNumber2 = findViewById(R.id.etCardNumber2);
        etCardNumber3 = findViewById(R.id.etCardNumber3);
        etCardNumber4 = findViewById(R.id.etCardNumber4);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCvv = findViewById(R.id.etCvv);
        cbSetPrimary = findViewById(R.id.cbSetPrimary);
        btnSaveCard = findViewById(R.id.btnSaveCard);
        
        // Card preview elements
        tvCardNumber = findViewById(R.id.tvCardNumber);
        tvCardholderName = findViewById(R.id.tvCardholderName);
    }
    
    private void setupCardInputWatchers() {
        // Card number input auto-focus
        etCardNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    etCardNumber2.requestFocus();
                }
                updateCardNumberPreview();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        etCardNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    etCardNumber3.requestFocus();
                } else if (s.length() == 0 && before > 0) {
                    etCardNumber1.requestFocus();
                    etCardNumber1.setSelection(etCardNumber1.getText().length());
                }
                updateCardNumberPreview();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        etCardNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    etCardNumber4.requestFocus();
                } else if (s.length() == 0 && before > 0) {
                    etCardNumber2.requestFocus();
                    etCardNumber2.setSelection(etCardNumber2.getText().length());
                }
                updateCardNumberPreview();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        etCardNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    etExpiryDate.requestFocus();
                } else if (s.length() == 0 && before > 0) {
                    etCardNumber3.requestFocus();
                    etCardNumber3.setSelection(etCardNumber3.getText().length());
                }
                updateCardNumberPreview();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        // Expiry date formatting (MM/YY)
        etExpiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                
                // Add a slash after the month
                if (text.length() == 2 && before == 0) {
                    etExpiryDate.setText(text + "/");
                    etExpiryDate.setSelection(3);
                } 
                // Move to CVV field when expiry date is complete
                else if (text.length() == 5) {
                    etCvv.requestFocus();
                }
                // Move back to card number when deleting
                else if (text.length() == 0 && before > 0) {
                    etCardNumber4.requestFocus();
                    etCardNumber4.setSelection(etCardNumber4.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        // Cardholder name update
        etCardholderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCardholderName.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void updateCardNumberPreview() {
        String part1 = etCardNumber1.getText().toString();
        String part2 = etCardNumber2.getText().toString();
        String part3 = etCardNumber3.getText().toString();
        String part4 = etCardNumber4.getText().toString();
        
        // Show dots instead of actual numbers for security in the preview
        tvCardNumber.setText("•••• •••• •••• " + part4);
    }
    
    private boolean validateCardDetails() {
        // Check cardholder name
        if (etCardholderName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter cardholder name", Toast.LENGTH_SHORT).show();
            etCardholderName.requestFocus();
            return false;
        }
        
        // Check card number
        if (etCardNumber1.getText().toString().length() < 4 ||
                etCardNumber2.getText().toString().length() < 4 ||
                etCardNumber3.getText().toString().length() < 4 ||
                etCardNumber4.getText().toString().length() < 4) {
            Toast.makeText(this, "Please enter a valid card number", Toast.LENGTH_SHORT).show();
            if (etCardNumber1.getText().toString().length() < 4) {
                etCardNumber1.requestFocus();
            } else if (etCardNumber2.getText().toString().length() < 4) {
                etCardNumber2.requestFocus();
            } else if (etCardNumber3.getText().toString().length() < 4) {
                etCardNumber3.requestFocus();
            } else {
                etCardNumber4.requestFocus();
            }
            return false;
        }
        
        // Check expiry date
        String expiryDate = etExpiryDate.getText().toString();
        if (expiryDate.length() < 5 || !expiryDate.contains("/")) {
            Toast.makeText(this, "Please enter a valid expiry date (MM/YY)", Toast.LENGTH_SHORT).show();
            etExpiryDate.requestFocus();
            return false;
        }
        
        // Check CVV
        if (etCvv.getText().toString().length() < 3) {
            Toast.makeText(this, "Please enter a valid CVV", Toast.LENGTH_SHORT).show();
            etCvv.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void saveCardDetails() {
        // Get card details
        String cardholderName = etCardholderName.getText().toString().trim();
        String lastFourDigits = etCardNumber4.getText().toString();
        
        // Create payment method
        int newId = paymentManager.getPaymentMethods().size() + 1;
        PaymentMethod creditCard = new PaymentMethod(newId, "Credit Card", "xxxx xxxx xxxx " + lastFourDigits);
        
        // Save payment method
        paymentManager.addPaymentMethod(creditCard);
        
        // Set as selected payment method if checkbox is checked
        if (cbSetPrimary.isChecked()) {
            paymentManager.setSelectedPaymentMethodId(creditCard.getId());
        }
        
        // Show success message
        Toast.makeText(this, "Credit card saved successfully", Toast.LENGTH_SHORT).show();
    }
}
