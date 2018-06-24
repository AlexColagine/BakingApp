package com.example.android.bakingapp.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.utils.Connectivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.example.android.bakingapp.utils.StringUtils.DESCRIPTION;
import static com.example.android.bakingapp.utils.StringUtils.PLAYER_POSITION;
import static com.example.android.bakingapp.utils.StringUtils.PLAYER_STATE;
import static com.example.android.bakingapp.utils.StringUtils.SHORT_DESCRIPTION;
import static com.example.android.bakingapp.utils.StringUtils.STEPS;
import static com.example.android.bakingapp.utils.StringUtils.STEP_ID;
import static com.example.android.bakingapp.utils.StringUtils.THUMBNAIL_URL;
import static com.example.android.bakingapp.utils.StringUtils.VIDEO_URL;

/**
 * Created by Alessandro on 02/05/2018.
 */

@SuppressWarnings("ConstantConditions")
public class SingleStepFragment extends Fragment implements ExoPlayer.EventListener {

    private static MediaSessionCompat mMediaSession;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private PlaybackStateCompat.Builder mStateBuilder;
    private FrameLayout mFullScreenButton;
    boolean mExoPlayerFullscreen;
    private Dialog mFullScreenDialog;
    ImageView mFullScreenIcon;
    View rootView;
    long playerPosition;
    boolean playerState;
    String videoUrl;
    String thumbnailUrl;
    ProgressBar loading;

    public SingleStepFragment() {
    }

    public static SingleStepFragment newInstance(String description,
                                                 String video,
                                                 String thumbnail,
                                                 String shortDesc,
                                                 int id) {
        SingleStepFragment singleStepFragment = new SingleStepFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DESCRIPTION, description);
        bundle.putString(VIDEO_URL, video);
        bundle.putString(THUMBNAIL_URL, thumbnail);
        bundle.putString(SHORT_DESCRIPTION, shortDesc);
        bundle.putInt(STEP_ID, id);
        singleStepFragment.setArguments(bundle);

        return singleStepFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_single_step, container, false);

        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            playerState = savedInstanceState.getBoolean(PLAYER_STATE);
        }

        // Initialize the player view.
        mPlayerView = rootView.findViewById(R.id.video_exoplayer_view);
        loading = rootView.findViewById(R.id.progressbar_video);

        if (Connectivity.internetAvailable(getContext())) {
            setupStep();
        } else {
            setupUiOffline();
        }


        return rootView;
    }

    private void setupStep() {
        ImageView image_step = rootView.findViewById(R.id.step_thumbnail);
        TextView id_step = rootView.findViewById(R.id.step_id);
        TextView description_step = rootView.findViewById(R.id.step_description);

        if (getArguments() != null) {
            videoUrl = getArguments().getString(VIDEO_URL);
            thumbnailUrl = getArguments().getString(THUMBNAIL_URL);
            int id = getArguments().getInt(STEP_ID);

            if (TextUtils.isEmpty(videoUrl)) {
                mPlayerView.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
            } else {
                initializeMediaSession();
                initializePlayer(Uri.parse(videoUrl));
                setupFullScreenPlayerView();
            }

            if (thumbnailUrl.endsWith("mp4")) {
                mPlayerView.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                initializeMediaSession();
                initializePlayer(Uri.parse(thumbnailUrl));
                setupFullScreenPlayerView();
            }

            if (TextUtils.isEmpty(thumbnailUrl)) {
                image_step.setVisibility(View.GONE);
            } else {
                image_step.setVisibility(View.VISIBLE);
                Glide.with(getContext())
                        .load(thumbnailUrl)
                        .into(image_step);
            }

            String idAndShortDesc = String.valueOf(id) + " - " + getArguments().getString(SHORT_DESCRIPTION);
            id_step.setText(idAndShortDesc);
            description_step.setText(getArguments().getString(DESCRIPTION));

        }

    }

    private void setupFullScreenPlayerView(){
        initFullscreenDialog();
        initFullscreenButton();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            openFullscreenDialog();
        } else {
            closeFullscreenDialog();
        }
    }

    private void setupUiOffline() {
        FrameLayout frameLayout = rootView.findViewById(R.id.main_media_frame);
        ScrollView scrollView = rootView.findViewById(R.id.id_scroll);
        frameLayout.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);

        TextView errorMessage = rootView.findViewById(R.id.empty_text);
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(getString(R.string.no_internet_connection));
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {

        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {

        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        ((FrameLayout) rootView.findViewById(R.id.main_media_frame)).addView(mPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_expand));
    }

    private void initFullscreenButton() {

        SimpleExoPlayerView controlView = mPlayerView.findViewById(R.id.video_exoplayer_view);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), STEPS);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playerState);
            mExoPlayer.seekTo(playerPosition);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            playerPosition = mExoPlayer.getCurrentPosition();
            playerState = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // ExoPlayer Event Listeners

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync, and post the media notification.
     *
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
            loading.setVisibility(View.GONE);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
            loading.setVisibility(View.GONE);
        } else if ((playbackState == ExoPlayer.STATE_BUFFERING)) {
            loading.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
        }
        if (mStateBuilder != null) {
            mMediaSession.setPlaybackState(mStateBuilder.build());
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    /**
     * Release the player when the activity is destroyed , stopped and in pause.
     * Initialize the Player when the activity is resumed with the right Uri.
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Connectivity.internetAvailable(getContext())) {
            if (videoUrl != null) {
                initializePlayer(Uri.parse(videoUrl));
            }
            if (thumbnailUrl.endsWith("mp4")) {
                initializePlayer(Uri.parse(thumbnailUrl));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(PLAYER_STATE, playerState);
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }
}