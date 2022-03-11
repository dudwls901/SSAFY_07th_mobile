export default {
    template: `
    <ul>
            <li @click="moveCreate">도서 등록</li>
            <li @click="moveList">도서 목록</li>
        </ul>
    `,
    methods: {
        moveList(){
            location.href="list.html";
        },
        moveCreate(){
            location.href="create.html";
        }
    }
}