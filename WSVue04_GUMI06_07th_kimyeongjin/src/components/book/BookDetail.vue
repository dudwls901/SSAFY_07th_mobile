

<script>
import http from '@/util/http-common'

export default {

    name: 'BookDetail',
    data() {
        return {
            book: {}, 
            newBook: {}};
    },
    created() {
        this.$scope.dispatch("getBook",`/${this.$route.query.isbn}`)
        // http
        // .get(`/book/${this.$route.params.isbn}`)
        // .then((response) => {
        //     this.book = response.data
        // })
        // .catch((error) => {
        //     console.dir(error)
        // })
        },
    methods: {
        moveModify() {
            this.$router.replace({name:'book-modify', params : { isbn : `${this.book.isbn}`} } )
        },
        moveList() {
            this.$router.replace({name:'book-list'})
        },
        deleteBook() {
            http
            .delete(`/book/${this.book.isbn}`)
            .then(() => {
                alert("삭제가 완료되었습니다.");
            this.moveList()
            })
            .catch((error) => {
                console.dir(error)
            })
        }
    }
}
</script>
