// Initialize Firebase - copy your keys from Firebase console
import config from '~/plugins/firebaseKeysConfig.js';
//the file imported above needs to be created in same directory, and contain something like
// let config = {
//   apiKey: ...
// };
// export default config;


export default ({app}, inject) => {
  //this is to avoid things being-reinitialised on hot reload (Firebase only allows one instance)
  if (!app.$firebase) {
    const firebase = require('firebase/app');
    firebase.initializeApp(config);
    inject('firebase', firebase);

    require('firebase/auth');

    const firebaseui = require('firebaseui');
    inject('firebaseUi', new firebaseui.auth.AuthUI(firebase.auth()));

    let firebaseUiConfig = {
      callbacks: {
        signInSuccessWithAuthResult: function (authResult, redirectUrl) {
          authResult.user.getIdToken().then(accessToken => {
            app.store.dispatch("login/updateFirebaseToken", accessToken);
          });
          // Return type determines whether we continue the redirect automatically
          // or whether we leave that to developer to handle.
          return false;
        },
        signInFailure: function (error) {
          // Some unrecoverable error occurred during sign-in.
          // Return a promise when error handling is completed and FirebaseUI
          // will reset, clearing any UI. This commonly occurs for error code
          // 'firebaseui/anonymous-upgrade-merge-conflict' when merge conflict
          // occurs. Check below for more details on this.
          //TODO: error handling
          console.log("Failure! " + error);
          console.log(app.store);
          return handleUIError(error);
        }
      },
      credentialHelper: firebaseui.auth.CredentialHelper.NONE,
      signInFlow: 'popup',
      signInSuccessUrl: '/',
      signInOptions: [
        // Leave the lines for the providers you want to offer your users.
        firebase.auth.GoogleAuthProvider.PROVIDER_ID,
        firebase.auth.FacebookAuthProvider.PROVIDER_ID,
        firebase.auth.TwitterAuthProvider.PROVIDER_ID,
        {
          provider: firebase.auth.EmailAuthProvider.PROVIDER_ID,
          // Whether the display name should be displayed in the Sign Up page.
          requireDisplayName: true
        }
      ],
      // Terms of service url.
      tosUrl: 'tandc.html'
    };
    inject('firebaseUiConfig', firebaseUiConfig);
  }
}
