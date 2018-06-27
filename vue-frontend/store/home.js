export const state = () => ({
  welcomeMessage: null
});

export const getters = {};

export const mutations = {
  setWelcomeMessage(state, msg) {
    state.welcomeMessage = msg;
  }
};

export const actions = {
  async fetchWelcomeMessage({commit, rootGetters}) {
    try {
      let successResult = null;
      if (rootGetters['login/loggedIn']) {
        successResult = await this.$axios.get('/api/private/hello');
      } else {
        successResult = await this.$axios.get('/api/public/hello');
      }
      commit('setWelcomeMessage', successResult.data);
    } catch (error) {
      console.error("Welcome message check failed " + error);
    }
  }

};

