package com.example.friendverse.COR;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegistrationChain {
    private List<RegistrationHandler> handlers  = new ArrayList<>();
    private int currentStep = 0;
    private RegistrationContext context;
    public RegistrationChain(RegistrationContext context) {
        this.context = context;
    }

    public void addHandler(RegistrationHandler handler) {
        handlers .add(handler);
    }

    public void start() {
        if (!handlers .isEmpty()) {
            handlers .get(currentStep).handle(context, this);
        }
    }

    public void nextStep() {
        currentStep++;
        if (currentStep < handlers .size()) {
            handlers .get(currentStep).handle(context, this);
        } else {
            // Registration complete
            Log.d("RegistrationChain", "Unable to process registration request!");
        }
    }
}
