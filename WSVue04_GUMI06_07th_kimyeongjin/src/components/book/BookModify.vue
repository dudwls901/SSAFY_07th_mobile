<template>
    <div class="regist">
      <h1 class="underline">SSAFY 도서 정보</h1>
      <div class="regist_form">
        <label for="isbn">도서번호</label>
        <input type="text" id="isbn" name="isbn" v-model="book.isbn" ref="isbn" disabled /><br />
        <label for="title">도서명</label>
        <input type="text" id="title" name="title" v-model="book.title" ref="title" /><br />
        <label for="author">저자</label>
        <input type="text" id="author" name="author" v-model="book.author" ref="author" /><br />
        <label for="price">가격</label>
        <input type="number" id="price" name="price" v-model="book.price" ref="price" /><br />
        <label for="content">설명</label>
        <br />
        <textarea type="content" id="content" name="content" v-model="book.content" ref="content" cols="35" rows="5"
        ></textarea><br />
        <button @click="checkValue">수정</button>
        <button @click="moveList">목록</button>
      </div>
    </div>
</template>

<script>
import http from '@/util/http-common'

export default {

    name: 'BookModify',
    data() {
        return {
            book: {},
        };
    },

    created() {
        http
        .get(`/book/${this.$route.params.isbn}`)
        .then((response) => {
            this.book = response.data
        })
        .catch((error) => {
            console.dir(error)
        })
    },

    methods: {
        checkValue() {
            let err = false;
            let msg = "";

            if (!this.book.isbn) {
                msg = "isbn 입력해주세요 !!!";
                err = true;
                this.$refs.isbn.focus();
            } else if (!this.book.title) {
                msg = "title 입력해주세요 !!!";
                err = true;
                this.$refs.title.focus();
            } else if (!this.book.author) {
                msg = "author 입력해주세요 !!!";
                err = true;
                this.$refs.author.focus();
            } else if (!this.book.price) {
                msg = "price 입력해주세요 !!!";
                err = true;
                this.$refs.price.focus();
            } else if (!this.book.content) {
                msg = "content 입력해주세요 !!!";
                err = true;
                this.$refs.content.focus();
            }

            if (err) {
                alert(msg);
            } else {
                this.modify();
            }
        },
        modify() {
            http
            .put(`/book/${this.book.isbn}`,{
                isbn: this.book.isbn,
                title: this.book.title,
                author: this.book.author,
                price: this.book.price,
                content: this.book.content,
            })
            .then((response) => {
                console.dir(response)
                alert("수정이 완료되었습니다.");
                this.moveList()
            })
            .catch((error) => {
                console.dir(error)
            })
        },
        moveList() {
            this.$router.replace({ name : 'book-list' })
        },
    },
}
</script>
