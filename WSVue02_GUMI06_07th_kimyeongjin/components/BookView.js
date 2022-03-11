export default {
    template: `
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
    `,
    data(){
        return{
            isbn: '',
            title: '',
            author: '',
            price: '',
            content: '',
            idx:'',
            books:[],
            newBook: {}
        };
    },
    created(){

        const bookList = localStorage.getItem("bookList");
        var newBook ={
            books: []
        };

        if(bookList){
            newBook = JSON.parse(bookList);
        }
        this.books = newBook.books;
        const params = new URL(document.location).searchParams;

        

        for(let i=0; i<newBook.books.length; i++){
            if(newBook.books[i].isbn == params.get('isbn')){
                console.log(newBook.books[i].isbn + "ANJDITLQKF");
                this.isbn = newBook.books[i].isbn;
                this.title = newBook.books[i].title;
                this.author = newBook.books[i].author;
                this.price = newBook.books[i].price;
                this.content = newBook.books[i].content;
                this.idx = i;
            }
        }
        console.log(this.idx);
        this.newBook = newBook;
    },
    methods: {
        
        moveList(){
            location.href = "./list.html";
        },
        moveEdit(){
            location.href = "./modify.html";
        },
        deleteValue(){
            let idx = this.idx;
            this.newBook.books.splice(idx,1);
            localStorage.setItem("bookList",JSON.stringify(this.newBook));
            alert("삭제가 완료되었습니다.");
            location.href = "./list.html";
        }
    }
}