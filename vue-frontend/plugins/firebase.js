//This is not actually a plugin YET - but should be turned into one

// Initialize Firebase - copy your keys from Firebase console
import '~/plugins/firebaseKeysConfig';
//the file above needs to be created in same directory, and contain something like
// export default config = {
//   apiKey: "xxx",
//   authDomain: "yyy.firebaseapp.com",
//   databaseURL: "https://yyy.firebaseio.com",
//   projectId: "yyy",
//   storageBucket: "yyy.appspot.com",
//   messagingSenderId: "zzz"
// };


//this is to avoid things being-reinitialised on hot reload (Firebase only allows one instance)
let firebaseInitialized = false;

if (!process.server && !firebaseInitialized) {
  const firebase = require('firebase/app');
  const firebaseui = require('firebaseui');
  require('firebase/auth');

  firebase.initializeApp(config);
  const ui = new firebaseui.auth.AuthUI(firebase.auth());
  firebaseInitialized = true;
}


export function showFirebaseButtons(store) {
  const uiConfig = {
    callbacks: {
      signInSuccessWithAuthResult: function (authResult, redirectUrl) {
        console.log("Success! redirect: " + redirectUrl);
        console.log(authResult);
        console.log(authResult.user.getIdTokenResult());
        authResult.user.getIdToken().then(accessToken => {
          console.log(accessToken);
          store.dispatch("login/updateFirebaseToken", accessToken);
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
        //TODO:
        console.log("Failure! " + error);
        console.log(store);
        return handleUIError(error);
      }
      // ,
      // uiShown: function () {
      //   // The widget is rendered.
      //   // Hide the loader.
      //   document.getElementById('loader').style.display = 'none';
      // }
    },
    credentialHelper: firebaseui.auth.CredentialHelper.ACCOUNT_CHOOSER_COM,
    // Will use popup for IDP Providers sign-in flow instead of the default, redirect.
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
// The start method will wait until the DOM is loaded.
  ui.start('#firebaseui-auth-container', uiConfig);
}


export async function firebaseLogout(store) {
  await firebase.auth().signOut();
  await store.dispatch("login/logout");
}
