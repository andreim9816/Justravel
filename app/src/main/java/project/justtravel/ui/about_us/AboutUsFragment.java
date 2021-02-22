package project.justtravel.ui.about_us;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import project.justtravel.R;

public class AboutUsFragment extends Fragment {
    private TextView aboutUsTextView;
    private static final String aboutUsString = "    Just travel is a new, highly appreciated mobile app that allows the user to add, edit or see details about a trip. Developed in one week, " +
            "it has just been valued to a record number of $1.2 billion, making its owner the youngest self-made billionaire at the delicate age of 22. \r\n    Currently looking forward " +
            "to investing in other tech companies like Tesla, Apple or Google, Andrei Manolache's plan is take over Facebook, Instagram or other social media platforms";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        aboutUsTextView = view.findViewById(R.id.about_us_text_view);
        aboutUsTextView.setText(aboutUsString);
        return view;
    }
}