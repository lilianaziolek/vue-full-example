export const state = () => ({
  loginDetails: {},
  cookieCmds: []
});

export const getters = {
  loggedIn: state => state.loginDetails.email != null
};

export const mutations = {
  setCookieCmds(state, cookieCmds) {
    state.cookieCmds = cookieCmds;
  },
  setLoginDetails(state, newLoginDetails) {
    state.loginDetails = newLoginDetails;
  }
};

export const actions = {
  async checkLoginStatus({commit}) {
    try {
      const successResult = await this.$axios.get('/api/public/loginStatus');
      commit('setLoginDetails', successResult.data);
      let setCookieCmds = successResult.headers['set-cookie'];
      if (setCookieCmds) {
        commit('setCookieCmds', setCookieCmds);
        for (let setCookieCmd of setCookieCmds) {
          let sessionCookie = setCookieCmd.split(';')[0].split('=');
          this.$axios.defaults.headers.common['Cookie'] = sessionCookie[0] + "=" + sessionCookie[1];
        }
      }
    } catch (error) {
      console.error("User loginStatus check failed " + error);
      commit('setLoginDetails', {});
    }
  },
  async logout({commit, dispatch}) {
    delete this.$axios.defaults.headers.common['X-Firebase-Auth'];

    try {
      await this.$axios.post('/logout');
      await dispatch("checkLoginStatus");
    } catch (error) {
      console.error("Logout failed " + error);
      commit('setLoginDetails', {});
    }
  },
  async updateFirebaseToken({commit, dispatch, state}, token) {
    this.$axios.defaults.headers.common['X-Firebase-Auth'] = token;
    await dispatch("checkLoginStatus");
  }

};

