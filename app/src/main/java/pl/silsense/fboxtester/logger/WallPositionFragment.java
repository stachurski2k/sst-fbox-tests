package pl.silsense.fboxtester.logger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        binding.viewWallPositionWall.setOnTouchListener((wallView, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int[] wallScreenLocation = new int[2];
                wallView.getLocationOnScreen(wallScreenLocation);

                float relativeX = (event.getRawX() - wallScreenLocation[0]);
                float relativeY = (event.getRawY() - wallScreenLocation[1]);

                float x = relativeX / wallView.getWidth();
                float y = relativeY / wallView.getHeight();

                viewModel.selectWallPosition(new WallPosition(x, y));
            }
            return true;
        });
    }
}
