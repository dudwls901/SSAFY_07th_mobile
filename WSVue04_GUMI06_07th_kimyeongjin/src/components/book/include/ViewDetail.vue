<template>
        <div class="regist">
      <h1 class="underline">SSAFY 도서 정보</h1>
      <div class="regist_form">
        <label for="isbn">도서번호</label>
        <div class="view">{{book.isbn}}</div>
        <label for="title">도서명</label>
        <div class="view">{{book.title}}</div>
        <label for="author">저자</label>
        <div class="view">{{book.author}}</div>
        <label for="price">가격</label>
        <div class="view">{{numberWithCommas(book.price)}}원</div>
        <label for="content">설명</label>
        <div class="view" v-html="enterToBr(book.content)"></div>
        <button @click="moveModify">수정</button>
        <button @click="deleteBook">삭제</button>
        <button @click="moveList">목록</button>
      </div>
    </div>

</template>

<script>
import { mapGetters } from 'vuex';
// import http from "@/util.http-common";

export default{
    name: "viewDetail",
    computed: {
        ...mapGetters(["book"])
    },
    methods:{

    }
}
</script>
