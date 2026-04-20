# android-drops-sdk-example

Example Android app for `android-drops-sdk`.

This app mirrors the React Native example at a higher level:
- a setup screen for auth/token/tracker/DDC inputs
- a dedicated payer-auth demo screen

## Local setup

This example expects the SDK repo to exist as a sibling directory:

- `../android-drops-sdk`

The example includes the SDK module directly from that path.

## Run

```bash
cd /Users/thomas/Code/android-drops-sdk-example
./gradlew :app:installDebug
```

Then launch the app from Android Studio or with `adb`.

## Flow

1. Enter:
   - environment
   - auth token
   - tracker
   - DDC JWT
   - DDC URL
2. Tap `Open Payer Auth Demo`
3. The app opens a screen that hosts `SafepayPayerAuthenticationView`
4. Event callbacks are appended to the on-screen log
