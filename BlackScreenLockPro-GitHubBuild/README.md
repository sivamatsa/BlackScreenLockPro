# Black Screen Lock Pro

This is an Android project designed so the APK can be built by GitHub Actions without installing Android Studio locally.

Features:
- Black overlay while the current app remains underneath.
- Double-tap black screen to unlock.
- Persistent notification with Unlock action.
- Quick Settings tile for one-tap activation.
- No account/backend required.

Important: this does NOT truly power off the Android display. It covers it with an opaque overlay. A normal third-party Android app cannot guarantee arbitrary apps continue running after a real screen lock/power-off. YouTube playback is also governed by YouTube's own background-playback rules.

Build online:
1. Create a GitHub repository.
2. Upload this project.
3. Push to main.
4. Open Actions > Build APK and run it.
5. Download the generated artifact named BlackScreenLock-debug.
