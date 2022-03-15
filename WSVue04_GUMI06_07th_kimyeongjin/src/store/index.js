import http from '@/util/http-common';
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    books: [],
    book: {}
  },
  getters: {
    books(state) {
      return state.books;
    },
    book(state) {
      return state.book;
    }
  },
  mutations: {
    setBooks(state, payload) {
      state.books = payload;
    },
    setBook(state, payload) {
      state.book = payload;
    }
  },
  actions: {
    createBook({ commit }, book) {
      commit('CREATE_BOOK', book);
    },
    getBooks(context) {
      http
        .get("")
        .then(({ data }) => {
          context.commit("setBooks", data);
        })
        .catch(() => {
          alert("에러발생!");
        });
    },
    getBook(context, payload) {
      http.get(payload).then(({ data }) => {
        context.commit("setBook", data);
      });
    }
  },
  modules: {
  }
  
})
