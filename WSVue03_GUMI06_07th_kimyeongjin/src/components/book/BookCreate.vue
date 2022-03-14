<template>
        <div class="regist">
                <h1 class="underline">SSAFY 도서 등록</h1>
                <div class="regist_form">
                    <label for="isbn">도서번호</label>
                    <input type="text" id="isbn" name="isbn" v-model="isbn" ref="isbn" /> <br>
                    <label for="title">도서명</label>
                    <input type="text" id="title" name="title" v-model="title" ref="title" /><br>
                    <label for="author">저자</label>
                    <input type="text" id="author" name="author" v-model="author" ref="author" /><br>
                    <label for="price">가격</label>
                    <input type="number" id="price" name="price" v-model="price" ref="price" /><br>
                    <label for="content">설명</label>
                    <br>
                    <textarea id="content" name="content" v-model="content" ref="content" cols="35" rows="5"></textarea><br>
                    <button @click="createHandler">등록</button>
                    <button @click="moveList">목록</button>
                </div>
            </div>
</template>

<script>
import http from '@/util/http-common';

export default{
    name: 'BookCreate',
    components:{

    },
     data(){
                return{
                    isbn: '',
                    title: '',
                    author: '',
                    price: '',
                    content: ''
                };
            },
            methods: {
              checkValue(){
                let err = false;
                let msg = '';

                if(!this.isbn){
                  msg = "isbn 입력해주세요 !!!!";
                  err = true;
                  //isbn인 input에 포커스 주기
                  this.$refs.isbn.focus();
                }
                else if(!this.title){
                  msg = "title 입력해주세요 !!!"
                  err = true;
                  this.$refs.title.focus();
                }
                else if(!this.author){
                  msg = "author 입력해주세요 !!!"
                  err = true;
                  this.$refs.author.focus();
                }
                else if(!this.price){
                  msg = "price 입력해주세요 !!!"
                  err = true;
                  this.$refs.price.focus();
                }
                else if(!this.content){
                  msg = "content 입력해주세요 !!!"
                  err = true;
                  this.$refs.content.focus();
                }
                if(err){
                  alert(msg);
                }
                else{
                  //입력한 도서 등록하기
                  this.registBook();
                }
              },
                createHandler() {
                    http
                    .post('/book', {
                    author: this.author,
                    content: this.content,
                    isbn: this.isbn,
                    price: this.price,
                    title: this.title,
                    })
                    .then(({ data }) => {
                    let msg = '등록 처리시 문제가 발생했습니다.';
                    if (data === 'success') {
                        msg = '등록이 완료되었습니다.';
                    }
                    alert(msg);
                    this.moveList();
                    })
                    .catch(() => {
                    alert('등록 처리시 에러가 발생했습니다.');
                    });
                },
              moveList(){
                this.$router.push('/list');
              }
            }
    }
</script>
