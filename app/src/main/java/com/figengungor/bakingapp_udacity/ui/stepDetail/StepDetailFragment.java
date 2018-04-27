package com.figengungor.bakingapp_udacity.ui.stepDetail;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.figengungor.bakingapp_udacity.R;
import com.figengungor.bakingapp_udacity.data.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by figengungor on 4/20/2018.
 */

public class StepDetailFragment extends Fragment {

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    @BindView(R.id.descriptionTv)
    TextView descriptionTv;

    @BindView(R.id.previousStepBtn)
    ImageButton previousStepBtn;

    @BindView(R.id.nextStepBtn)
    ImageButton nextStepBtn;

    @BindView(R.id.stepNoTv)
    TextView stepNoTv;

    @BindView(R.id.thumbnailIv)
    ImageView thumbnailIv;

    @OnClick(R.id.previousStepBtn)
    void onPreviousStepBtnClicked() {
        callback.onPreviousStepClicked(stepIndex);
    }

    @OnClick(R.id.nextStepBtn)
    void onNextStepBtnClicked() {
        callback.onNextStepClicked(stepIndex);
    }


    private static final String ARG_STEPS = "steps";
    private static final String ARG_STEP_INDEX = "step_index";
    List<Step> steps;
    int stepIndex;
    Step step;

    private SimpleExoPlayer player;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private OnInteractionListener callback;

    public interface OnInteractionListener {
        void onPreviousStepClicked(int stepIndex);

        void onNextStepClicked(int stepIndex);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        steps = Parcels.unwrap(getArguments().getParcelable(ARG_STEPS));
        stepIndex = getArguments().getInt(ARG_STEP_INDEX);
        step = steps.get(stepIndex);
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            shouldAutoPlay = true;
            bandwidthMeter = new DefaultBandwidthMeter();
            mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakeMe"), (TransferListener<? super DataSource>) bandwidthMeter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    private void setupUI() {
        descriptionTv.setText(step.getDescription());
        displayStepNavigationUI();
        displayThumbnailIfNoVideo();
    }

    void displayStepNavigationUI() {
        stepNoTv.setText(getString(R.string.stepNo, stepIndex + 1));
        int stepSize = steps.size();
        if (stepIndex == stepSize - 1)
            nextStepBtn.setVisibility(View.GONE);
        else {
            nextStepBtn.setVisibility(View.VISIBLE);
        }
        if (stepIndex == 0) {
            previousStepBtn.setVisibility(GONE);
        } else {
            previousStepBtn.setVisibility(View.VISIBLE);
        }
    }

    void displayThumbnailIfNoVideo() {
        if (TextUtils.isEmpty(step.getVideoURL())) {
            thumbnailIv.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(step.getThumbnailURL())) {
                Picasso.with(getContext()).load(R.drawable.placeholder)
                        .into(thumbnailIv);
            } else {
                Picasso.with(getContext()).load(step.getThumbnailURL())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(thumbnailIv);
            }
        } else {
            thumbnailIv.setVisibility(View.GONE);
        }
    }

    public static StepDetailFragment newInstance(List<Step> steps, int stepIndex) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEPS, Parcels.wrap(steps));
        args.putInt(ARG_STEP_INDEX, stepIndex);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initializePlayer() {
        playerView.requestFocus();

        trackSelector = new DefaultTrackSelector();

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        playerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(step.getVideoURL()),
                mediaDataSourceFactory, extractorsFactory, null, null);

        player.prepare(mediaSource);
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    /*Starting with API level 24 Android supports multiple windows. As our app can be visible but not
    active in split window mode, we need to initialize the player in onStart. Before API level 24
    we wait as long as possible until we grab resources, so we wait until onResume before
    initializing the player.

    https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2

    */

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            if (Util.SDK_INT > 23) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            if ((Util.SDK_INT <= 23 || player == null)) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnInteractionListener");
        }
    }
}
