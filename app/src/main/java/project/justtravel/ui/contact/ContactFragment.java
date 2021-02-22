package project.justtravel.ui.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import project.justtravel.R;

public class ContactFragment extends Fragment {
    private TextView contactTextView;
    private Button callMeButton, visitMeButton;
    private static final String FACEBOOK_LINK = "https://www.facebook.com/andrei.manolache.10/";
    private static final String PHONE_NUMBER = "0746547890";
    private static final String contactString = "   If you saw the potential of this app, you can contact me, maybe we'll do some business together. \r\n " +
            "    At the moment, the only way to reach me is via cellphone or on my facebook.";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        initUiElements(view);
        contactTextView.setText(contactString);

        setBehaviourVisitMeButton();
        setBehaviourCallMeButton();

        return view;
    }

    private void initUiElements(View view) {
        contactTextView = view.findViewById(R.id.contact_text_view);
        callMeButton = view.findViewById(R.id.callMeButton);
        visitMeButton = view.findViewById(R.id.visitMeButton);
    }

    private void setBehaviourVisitMeButton() {
        visitMeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(FACEBOOK_LINK));
            startActivity(intent);
        });
    }

    private void setBehaviourCallMeButton() {
        callMeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
            startActivity(intent);
        });
    }
}