export default {
    template: `
    
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
                        <tr v-for="(book, index) in books" :key="index">
                            <td>{{index + 1}}</td>
                            <td>{{book.isbn}}</td>
                            <td><a :href="'view.html?isbn='  + book.isbn">{{book.title}}</a> </td>
                            <td>{{book.author}}</td>
                            <td>{{book.price | priceFilter}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div v-else class="text-center">게시글이 없습니다.</div>
        </div>

    `,

    date(){
        return {
            books: []
        };
    },
    created(){
        const bookList = localStorage.getItem("bookList");
        let newBook ={
            books: []
        };

        if(bookList){
            newBook = JSON.parse(bookList);
        }
        else{
            localStorage.setItem("bookList",JSON.stringify(newBook));
        }

        //가격순으로 정렬
        newBook.books.sort((a,b)=>{
            return -(a.price - b.price);
        });

        this.books = newBook.books;
        console.log(this.books);
    },
    methods: {
        movePage(){
            location.href = "create.html";
        }
    }

}