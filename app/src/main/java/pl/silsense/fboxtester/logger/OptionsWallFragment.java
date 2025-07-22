package pl.silsense.fboxtester.logger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.FragmentLoggerOptionsWallBinding;

public class OptionsWallFragment extends Fragment {

    public OptionsWallFragment() {
        super(R.layout.fragment_logger_options_wall);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logger_options_wall, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoggerViewModel viewModel = new ViewModelProvider(requireActivity()).get(LoggerViewModel.class);
        FragmentLoggerOptionsWallBinding binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_logger_options_wall);
        binding.setLifecycleOwner(requireActivity());
        binding.setViewModel(viewModel);
    }
}
