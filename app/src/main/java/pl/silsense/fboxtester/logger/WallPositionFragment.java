package pl.silsense.fboxtester.logger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import pl.silsense.fboxtester.R;
import pl.silsense.fboxtester.databinding.FragmentLoggerWallPositionBinding;
import pl.silsense.fboxtester.log.WallPosition;

public class WallPositionFragment extends Fragment {

    public WallPositionFragment() {
        super(R.layout.fragment_logger_wall_position);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logger_wall_position, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoggerViewModel viewModel = new ViewModelProvider(requireActivity()).get(LoggerViewModel.class);
        FragmentLoggerWallPositionBinding binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_logger_wall_position);
        binding.setLifecycleOwner(requireActivity());
        binding.setViewModel(viewModel);

        binding.linearLayoutWallPositionRoot.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int[] location = new int[2];
                binding.viewWallPositionWall.getLocationOnScreen(location);
                int left = location[0];
                int top = location[1];
                int right = left + binding.viewWallPositionWall.getWidth();
                int bottom = top + binding.viewWallPositionWall.getHeight();

                float x = event.getRawX();
                float y = event.getRawY();

                if (x >= left && x <= right && y >= top && y <= bottom) {
                    float normX = x / v.getWidth();
                    float normY = y / v.getHeight();
                    viewModel.selectWallPosition(new WallPosition(normX, normY));
                }
            }
            return true;
        });
    }
}
