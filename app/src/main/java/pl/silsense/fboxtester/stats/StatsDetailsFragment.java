package pl.silsense.fboxtester.stats;

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
import pl.silsense.fboxtester.databinding.FragmentStatsDetailsBinding;

public class StatsDetailsFragment extends Fragment {

    public StatsDetailsFragment() {
        super(R.layout.fragment_stats_details);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StatsViewModel viewModel = new ViewModelProvider(requireActivity()).get(StatsViewModel.class);
        FragmentStatsDetailsBinding binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_stats_details);
        binding.setLifecycleOwner(requireActivity());
        binding.setViewModel(viewModel);
    }
}
