export default async function ({store, req, res}) {
  await store.dispatch("login/checkLoginStatus");
  let cookieCmds = store.state.login.cookieCmds;
  if (process.server && cookieCmds.length > 0) {
    //this is so that in mixed SSR/client mode both sides are logged in and share the session
    for (let cookieCmd of cookieCmds) {
      res.setHeader('Set-Cookie', cookieCmd);
    }
  }
}
