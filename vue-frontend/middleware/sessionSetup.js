export default async function ({store, req, res}) {
  await store.dispatch("login/checkLoginStatus");
  let cookieCmd = store.state.login.cookieCmd;
  if (process.server && cookieCmd) {
    // console.log("On the server - passing on Set-Cookie header " + cookieCmd + " to client");
    //this is so that in mixed SSR/client mode both sides are logged in and share the session
    res.setHeader('Set-Cookie', cookieCmd);
  }
}
