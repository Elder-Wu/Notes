package com.wuzhanglao.niubi.view;

import android.app.Activity;
import android.view.View;

import com.wuzhanglao.niubi.R;
import com.wuzhanglao.niubi.base.BaseActivity;
import com.wuzhanglao.niubi.misc.DemoActivity;

/**
 * Created by ming.wu@shanbay.com on 2017/6/15.
 */

public class ExoPlayerDemo implements DemoActivity.DemoView {

	private BaseActivity mActivity;
	private View mRoot;

	public ExoPlayerDemo(final Activity activity) {
		mRoot = activity.getLayoutInflater().inflate(R.layout.layout_demo_exo_player, null);
	}

	/*private void initializePlayer() {
		Intent intent = getIntent();
		boolean needNewPlayer = player == null;
		if (needNewPlayer) {

			trackSelectionHelper = new TrackSelectionHelper(trackSelector, adaptiveTrackSelectionFactory);
			lastSeenTrackGroupArray = null;

			UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA)
					? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;
			DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
			if (drmSchemeUuid != null) {
				String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
				String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
				try {
					drmSessionManager = buildDrmSessionManager(drmSchemeUuid, drmLicenseUrl,
							keyRequestPropertiesArray);
				} catch (UnsupportedDrmException e) {
					int errorStringId = Util.SDK_INT < 18 ? R.string.error_drm_not_supported
							: (e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
							? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown);
					showToast(errorStringId);
					return;
				}
			}

			boolean preferExtensionDecoders = intent.getBooleanExtra(PREFER_EXTENSION_DECODERS, false);
			@DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
					((DemoApplication) getApplication()).useExtensionRenderers()
							? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
							: DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
							: DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
			DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(mActivity,drmSessionManager, extensionRendererMode);

			TrackSelection.Factory adaptiveTrackSelectionFactory =
					new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
			DefaultTrackSelector trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
			ExoPlayer player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
			player.addListener(this);
			player.prepare(new MediaSource() {
			});

			simpleExoPlayerView.setPlayer(player);
			player.setPlayWhenReady(shouldAutoPlay);
			debugViewHelper = new DebugTextViewHelper(player, debugTextView);
			debugViewHelper.start();
		}
		if (needNewPlayer || needRetrySource) {
			String action = intent.getAction();
			Uri[] uris;
			String[] extensions;
			if (ACTION_VIEW.equals(action)) {
				uris = new Uri[] {intent.getData()};
				extensions = new String[] {intent.getStringExtra(EXTENSION_EXTRA)};
			} else if (ACTION_VIEW_LIST.equals(action)) {
				String[] uriStrings = intent.getStringArrayExtra(URI_LIST_EXTRA);
				uris = new Uri[uriStrings.length];
				for (int i = 0; i < uriStrings.length; i++) {
					uris[i] = Uri.parse(uriStrings[i]);
				}
				extensions = intent.getStringArrayExtra(EXTENSION_LIST_EXTRA);
				if (extensions == null) {
					extensions = new String[uriStrings.length];
				}
			} else {
				showToast(getString(R.string.unexpected_intent_action, action));
				return;
			}
			if (Util.maybeRequestReadExternalStoragePermission(this, uris)) {
				// The player will be reinitialized if the permission is granted.
				return;
			}
			MediaSource[] mediaSources = new MediaSource[uris.length];
			for (int i = 0; i < uris.length; i++) {
				mediaSources[i] = buildMediaSource(uris[i], extensions[i]);
			}
			MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
					: new ConcatenatingMediaSource(mediaSources);
			boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
			if (haveResumePosition) {
				player.seekTo(resumeWindow, resumePosition);
			}
			player.prepare(mediaSource, !haveResumePosition, false);
			needRetrySource = false;
			updateButtonVisibilities();
		}
	}*/

	@Override
	public String getTitle() {
		return getClass().getSimpleName() + getClass().getSimpleName() + getClass().getSimpleName() + getClass().getSimpleName();
	}

	@Override
	public View getView() {
		return mRoot;
	}
}
