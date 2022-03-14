<template>
        <div class="regist">
                <h1 class="underline">SSAFY 도서 정보</h1>
                <div class="regist_form">
                    <label for="isbn">도서번호</label>
                    <input type="text" id="isbn" name="isbn" v-model="isbn" ref="isbn" readonly/> <br>
                    <label for="title">도서명</label>
                    <input type="text" id="title" name="title" v-model="title" ref="title" readonly /><br>
                    <label for="author">저자</label>
                    <input type="text" id="author" name="author" v-model="author" ref="author" readonly /><br>
                    <label for="price">가격</label>
                    <input type="number" id="price" name="price" v-model="price" ref="price" readonly /><br>
                    <label for="content">설명</label>
                    <br>
                    <textarea id="content" name="content" v-model="content" ref="content" cols="35" rows="5"></textarea><br>
                    <a :href="'modify.html?isbn='  + isbn"><button @click="moveEdit">수정</button></a>
                    <button @click="deleteValue">삭제</button>
                    <button @click="moveList">목록</button>
                </div>
            </div>
</template>


<script>
import http from '@/util/http-common';
// import ListRow from '@/components/Row.vue';
export default {
  name: 'BookDetail',
  components: {
    // BookLis,
  },
  data: function() {
    return {
        isbn: '',
      book: {},
    };
  },
  created() {
    http
      .get('/book/{isbn}')
      .then(({ data }) => {
        this.books = data;
      })
      .catch(() => {
        alert('에러가 발생했습니다.');
      });
  },
  methods: {
    moveCreate() {
      this.$router.push('/create');
    },
  },
};
</script>