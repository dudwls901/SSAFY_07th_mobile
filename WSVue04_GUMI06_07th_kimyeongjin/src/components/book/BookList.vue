<template>
    <div>
      <h1 class="underline">도서 목록</h1>
      <div style="text-align: right">
        <button @click="movePage">도서 등록</button>
      </div>
      <div v-if="books.length">
        <table id="book-list">
          <colgroup>
            <col style="width: 5%" />
            <col style="width: 20%" />
            <col style="width: 40%" />
            <col style="width: 20%" />
            <col style="width: 15%" />
          </colgroup>
          <thead>
            <tr>
              <th>번호</th>
              <th>ISBN</th>
              <th>제목</th>
              <th>저자</th>
              <th>가격</th>
            </tr>
          </thead>
          <tbody>
            <list-row v-for="(book, index) in books"
              :key="index"
              :no="`${index + 1}`"
              :isbn="book.isbn"
              :title="book.title"
              :author="book.author"
              :price="book.price"
            />

          </tbody>
        </table>
      </div>
      <div v-else class="text-center">게시글이 없습니다.</div>
    </div>
</template>

<script>
import {mapGetters} from "vuex";
import ListRow from "@/components/book/include/ListRow.vue"
export default {

    name: 'bookList',
    components:{
      ListRow
    },
    computed: {
      ...mapGetters(["books"])
    },
    created() {
        //  this.getAllBooks()
      this.$store.dispatch("getBooks");
        // 가격순으로 정렬
        // this.books.sort((a, b) => {
        //     return -(a.price - b.price);
        // });
    },

    methods: {
        // getAllBooks() {
        //     http
        //         .get("/book")
        //         .then((response) => {
        //             this.books = response.data
        //         })
        //         .catch((error) => {
        //             console.dir(error)
        //         })
        // },
        movePage() {
            this.$router.replace({ name: 'book-create' })
        },
    },
    // filters:{
    //   priceFilter
    // }
}
</script>
